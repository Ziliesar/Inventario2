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
public class conexion {
    Connection conectar=null;
    public Connection conexion(){   
      try{
        Class.forName("org.gjt.mm.mysql.Driver");//.newInstance();
        conectar=DriverManager.getConnection("jdbc:mysql://localhost/control_inventario","root","");
        //conectar=DriverManager.getConnection("jdbc:mysql://192.168.1.5:3306/personas","luis","");

      }catch(SQLException ex) {
                 JOptionPane.showMessageDialog(null, "Error de conexion de la base de datos");
                                       }catch(ClassNotFoundException ex) {                                                                }
      return conectar;
    }
}
