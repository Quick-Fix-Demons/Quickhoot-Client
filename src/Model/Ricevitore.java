/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Utente
 */
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Salvatore
 */
public class Ricevitore extends Thread {
    
    Socket socket;
    Scanner in;
    
    public Ricevitore (Socket s) throws IOException{
        socket = s;
        in = new Scanner(s.getInputStream());
    }
    
    public void run(){
        while (true) {
            System.out.println(in.nextLine());
        }
    }
    
}
