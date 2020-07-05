/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control_inventario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author arlir
 */
public class conexionH {
    Connection conectarH=null;
    public Connection conexionH(){   
      try{
        Class.forName("org.gjt.mm.mysql.Driver");//.newInstance();
        conectarH=DriverManager.getConnection("jdbc:mysql://sql260.main-hosting.eu:3306/u389287320_seriales","u389287320_digitalpalace","Motaguacampeon1928");
        //conectar=DriverManager.getConnection("jdbc:mysql://192.168.1.5:3306/personas","luis","");

      }catch(SQLException ex) {
                 JOptionPane.showMessageDialog(null, "Error de conexion de la base de datosHHHHHH");
                                       }catch(ClassNotFoundException ex) {                                                                }
      return conectarH;
    }
}
