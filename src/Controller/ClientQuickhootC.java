/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Ascoltatore;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author Quick Fix Demons
 */
public class ClientQuickhootC implements Initializable {
    
    private String nomeClient;
    private Ascoltatore reader;
    private PrintWriter pw;
    private Socket clientSocket;
    private boolean rispostaInviata;
    
    @FXML
    private TextField header;
    @FXML
    private Button button0;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    
    
    
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
            reader = new Ascoltatore("reader", clientSocket, header, button0, button1, button2, button3, rispostaInviata);
            reader.start();
            rispostaInviata = false;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore di connessione al server.");
            alert.setContentText(ex.toString());

            alert.showAndWait();
            System.exit(0);
        }
        header.setEditable(false);
        header.setText("Connessione Instanziata");
    }

    public String getNomeClient() {
        return nomeClient;
    }
    
    public void setNomeClient(String nomeClient) {
        this.nomeClient = nomeClient;
    }
    
    public void inviaMessaggio(int idBottone) {
        int tempo = 2;
        
        String messaggio_da_inviare = getNomeClient() + " - " + idBottone+" - " + tempo;
        // Spedisco al server il messaggio con un printwriter
        if(!rispostaInviata) {
            pw.println(messaggio_da_inviare);
            rispostaInviata = false;
            
        }
        
    }
    
    @FXML
    public void click0() {
        inviaMessaggio(0);
    }
    
    @FXML
    public void click1() {
        inviaMessaggio(1);
    }
    
    @FXML
    public void click2() {
        inviaMessaggio(2);
    }
    
    @FXML
    public void click3() {
        inviaMessaggio(3);
    }
    
}
