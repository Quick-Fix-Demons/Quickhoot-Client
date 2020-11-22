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
import javafx.scene.control.Button;


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
    private Msg messaggio=new Msg();
    
    @FXML
    private Button button;
    @FXML
    private Button butt;
    @FXML
    private Button butto;
    @FXML
    private Button buttons;
    
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
    
    
    public void inviaMessaggio(int idbottone) {
        Scanner sc=new Scanner(System.in);
        String parola="0123" ; //parola che indica la risposta alla domanda
        String nick;// nickname del giocatore
        String tempo="2s"; //esempio di tempo predefinito
        
        nick=sc.nextLine();//scrivere sentro al nick il tuo nickname
        sc.nextLine();
         //scrivere dentro a parola la risposta con uno scanner
       
        
        String messaggio_da_inviare=nick+" - "+idbottone+" - "+tempo; //vado a mettere dentro a messaggio tutti i parametri che mi servono
        
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
    @FXML
    public void Click1(){
        
        inviaMessaggio(0);
    }
    @FXML
    public void Click2(){
        
        inviaMessaggio(1);
    }
    @FXML
    public void Click3(){
        
        inviaMessaggio(2);
    }
    @FXML
    public void Click4(){
        
        inviaMessaggio(3);
    }
    public void RiceviDomanda() throws IOException{
       Scanner sc;
       String messaggio_ricevuto;
       
       sc=Scanner(clientSocket.getInputStream());
       messaggio_ricevuto=messaggio.leggi();
       
       System.out.println(messaggio_ricevuto);
    } 

    private Scanner Scanner(InputStream inputStream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
