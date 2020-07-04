/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control_inventario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author arlir
 */
public class unidad_producto {
    
    private int id_unidad;
    private String unidad;
    
    
    
    public unidad_producto(int id_unidad, String unidad){
        this.id_unidad = id_unidad;
        this.unidad = unidad;
    }
    
    public unidad_producto(){
        
    }
    
    public int getId_unidad() {
        return id_unidad;
    }

    public void setId_unidad(int id_unidad) {
        this.id_unidad = id_unidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    
    public void mostrarUnidad(JComboBox<unidad_producto> comboUnidad){
        try {
            control_inventario.conexion cc = new conexion();
            Connection cn=cc.conexion();
            
            String sql = "SELECT id_unidad, unidad FROM tipo_unidad";
            //Conexion con = new Conexion();
            //con.ConexionPostgres();
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                comboUnidad.addItem(
                    new unidad_producto(
                            rs.getInt("id_unidad"),
                            rs.getString("unidad")
                    )
                );
            }
        } catch (Exception ex) {
            Logger.getLogger(unidad_producto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR LOS cargos");
        }
    }

    @Override
    public String toString() {
        return unidad;
    }
    
    
    
}
