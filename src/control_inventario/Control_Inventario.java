/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control_inventario;

import control_inventario.JFrame.InicioSesion;
import control_inventario.JFrame.Menu;
import control_inventario.JFrame.Registrar_Personal;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;


public class Control_Inventario {
static int VerFCon;

public static class metod{
    public void mensaje() {
    System.out.println("hola mundo");  
    
    int valorConec;
    String dirWeb = "www.digitalpalace.net";
       int conec;
        int puerto = 80;
       
        try{
  Socket s = new Socket(dirWeb, puerto);
  
  if(s.isConnected()){
  System.out.println("Conexión establecida con la dirección: " +  dirWeb + " a travéz del puerto: " + puerto);
  VerFCon=1111;
}
  
  }catch(Exception e){
  System.err.println("No se pudo establecer conexión con: " + dirWeb + " a travez del puerto: " + puerto);
  VerFCon=2222;
}
    valorConec=VerFCon;
        System.out.println(valorConec);
}
    

public int cubo () {
    int x;
        x=VerFCon;
        System.out.println("Valor de X es: "+x);
        return x;
    }
}


   
    public static void main(String[] args) {      
        control_inventario.JFrame.InicioSesion sesion = new InicioSesion();
        sesion.setVisible(true);
        
        
    }      
}