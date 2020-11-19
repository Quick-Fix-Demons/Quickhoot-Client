/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Ascoltatore;
import Model.Msg;
import Model.Ricevitore;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;


/**
 *
 * @author Quick Fix Demons
 */
public class ClientQuickhootC implements Initializable {
    
    private String nomeClient;
    private Ascoltatore reader;
    private Ricevitore reciver;
    private PrintWriter pw;
    private Socket clientSocket;
    private Msg messaggio=new Msg();
    
    @FXML
    private TextArea output;
    
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
        Scanner sc=new Scanner(System.in);
        String parola ; //parola che indica la risposta alla domanda
        String nick;// nickname del giocatore
        String tempo="2s"; //esempio di tempo predefinito
        
        nick=sc.nextLine();//scrivere sentro al nick il tuo nickname
        sc.nextLine();
        parola=sc.nextLine(); //scrivere dentro a parola la risposta con uno scanner
        
        
        String messaggio_da_inviare=nick+" - "+parola+" - "+tempo; //vado a mettere dentro a messaggio tutti i parametri che mi servono
        if(parola.equals("")) return;
       
        //messaggio.metti(messaggio_da_inviare);
        pw.println(messaggio_da_inviare);//spedisco al server il messaggio con un printwriter
        if(messaggio_da_inviare.equals("/end")) {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }
    
    public void RiceviDomanda() throws IOException{
       Scanner sc;
       String messaggio_ricevuto;
       sc=new Scanner(clientSocket.getInputStream());
       messaggio_ricevuto=sc.nextLine();
       
       System.out.println(messaggio_ricevuto);
    } 

    
}
