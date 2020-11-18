/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Salvatore
 */
public class Msg {
    
    private String msg;
    private boolean pieno=false;
    
    public synchronized String leggi (){
        while (pieno == false)
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        pieno = false;
        
        notify();
        return msg;
    }
    
    
    public synchronized void metti (String m){
        while (pieno == true)
            try { wait();}
            catch (InterruptedException ex){}
        pieno = true;
        msg = m;
        
        notify();
    }
    
}