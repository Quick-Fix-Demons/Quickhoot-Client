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
import javafx.scene.control.TextArea;

/**
 *
 * @author Quick Fix Demons
 */
public class Ascoltatore extends Thread {
    private Scanner socketScanner;
    private TextArea out;
    
    public Ascoltatore(String name, Socket connectionSocket, TextArea output) {
        super(name);
        try {
            socketScanner = new Scanner(connectionSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Ascoltatore.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.out = output;
    }

    @Override
    public void run() {
        out.setText(out.getText() + "\nbrrrrrr");
        while(true) {
            String ricevuto = socketScanner.nextLine();
            System.out.println(ricevuto);
            out.setText(out.getText() + "\n" + ricevuto);
        }
    }
    
    
    
}
