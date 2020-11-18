/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Ascoltatore;
import Model.Ricevitore;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.Initializable;


/**
 *
 * @author Utente
 */
public class ClientQuickhootC implements Initializable {
    
    private String nomeClient;
    private Ascoltatore reader;
    private Ricevitore reciver;
    private PrintWriter pw;
    private Socket clientSocket;
    
    
    public void initialize(URL url, ResourceBundle rb) {
        int porta = 9991;
        
       
       
       
        
        try {
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            clientSocket = new Socket(addr, porta);
            
            pw = new PrintWriter(clientSocket.getOutputStream(), true);
            pw.println(this.getNomeClient());
            reader = new Ascoltatore("reader", clientSocket);
            reciver=new Ricevitore(clientSocket);
            reciver.start();
            reader.start();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        
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
        
        
        String messaggio=nick+"-"+parola+"-"+tempo; //vado a mettere dentro a messaggio tutti i parametri che mi servono
        if(parola.equals("")) return;
       
       
        pw.println(messaggio);//spedisco al server il messaggio con un printwriter
        if(messaggio.equals("/end")) {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }
    
    public void RiceviRisposta(){
        
    } 
    
}
