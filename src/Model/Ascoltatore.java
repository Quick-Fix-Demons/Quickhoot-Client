/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author Quick Fix Demons
 */
public class Ascoltatore extends Thread {
    private Scanner socketScanner;
    private TextField out;
    private Button b0;
    private Button b1;
    private Button b2;
    private Button b3;
    private boolean rispostaInviata;
    
    public Ascoltatore(
            String name, 
            Socket connectionSocket, 
            TextField output, 
            Button button0, 
            Button button1, 
            Button button2, 
            Button button3,
            boolean rispostaInviata) {
        super(name);
        try {
            socketScanner = new Scanner(connectionSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Ascoltatore.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.out = output;
        this.b0 = button0;
        this.b1 = button1;
        this.b2 = button2;
        this.b3 = button3;
        this.rispostaInviata = false;
        
    }

    @Override
    public void run() {
        while(true) {
            String ricevuto = socketScanner.nextLine();
            System.out.println(ricevuto);
            
            // Solo 0 msg di sistema
            // 4 domanda risposta multipla
            // 2 domanda vero o falso
            String[] array = ricevuto.split(" - ");
            System.out.println(array.length);
            boolean vero_o_falso = array.length == 3 ? true : false;
            boolean messaggio_di_sistema = array.length == 1 ? true : false;
            
            Platform.runLater(() -> {
                if(messaggio_di_sistema) {
                    out.setText(array[0]);
                    b0.setText("");
                    b1.setText("");
                    b2.setText("");
                    b3.setText("");
                }
                else if(vero_o_falso) {
                    out.setText(array[0]);
                    b0.setText("VERO");
                    b1.setText("FALSO");
                    b2.setText("");
                    b3.setText("");
                }
                else {
                    out.setText(array[0]);
                    b0.setText(array[1]);
                    b1.setText(array[2]);
                    b2.setText(array[3]);
                    b3.setText(array[4]);
                }
            });
        }
    }
    
}
