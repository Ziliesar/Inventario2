package coneccion;

import java.net.Socket;

public class Coneccion {
    public static void main(String[] args) {
       String dirWeb = "www.digitalpalace.net";
        int puerto = 80;
        try{
  Socket s = new Socket(dirWeb, puerto);
  
  if(s.isConnected()){
  System.out.println("Conexión establecida con la dirección: " +  dirWeb + " a travéz del puerto: " + puerto);
}
  
  }catch(Exception e){
  System.err.println("No se pudo establecer conexión con: " + dirWeb + " a travez del puerto: " + puerto);
}
    }
    
}
