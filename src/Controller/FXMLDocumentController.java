/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Quick Fix Demons
 */
public class FXMLDocumentController implements Initializable {
    
    private String nomeClient;
    private Ascoltatore reader;
    private PrintWriter pw;
    private Socket clientSocket;
    
    @FXML
    private TextField inserimento;
    @FXML
    private TextArea output;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        event.consume();
        inviaMessaggio();
    }
    
    @FXML
    private void keyPress(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER) {
            inviaMessaggio();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int porta = 9991;
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Client");
        dialog.setHeaderText("Login");
        dialog.setContentText("Inserisci il nome utente:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Nome utente: " + result.get());
        }
        
        result.ifPresent(risultato -> setNomeClient(risultato));
        
        try {
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            clientSocket = new Socket(addr, porta);
            
            pw = new PrintWriter(clientSocket.getOutputStream(), true);
            pw.println(this.getNomeClient());
            reader = new Ascoltatore("reader", clientSocket, output);
            reader.start();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        output.setEditable(false);
        output.setText("Connessione Instanziata");
    }

    public String getNomeClient() {
        return nomeClient;
    }

    public void setNomeClient(String nomeClient) {
        this.nomeClient = nomeClient;
    }
    
    public void inviaMessaggio() {
        String parola = inserimento.getText();
        while(parola.startsWith(" ")) {
            parola = parola.substring(1);
        }
        if(parola.equals("")) return;
        output.setText(output.getText() + "\n" + parola);
        inserimento.setText("");
        pw.println(parola);
        if(parola.equals("/end")) {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }   
    
}
