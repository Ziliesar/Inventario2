/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control_inventario;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author arlir
 */
public class Reporte {
    
    control_inventario.conexion cc = new conexion();
    Connection cn=cc.conexion();
    
    
    public void verReporte(String ruta, Map parametro){
        
        try {
            JasperReport jr = JasperCompileManager.compileReport(ruta);
            
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, cn);
            
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
   
}
