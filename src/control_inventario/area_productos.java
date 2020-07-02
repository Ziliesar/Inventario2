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
public class area_productos {
    private int id_area_producto ;
    private String area_producto;
    
    public area_productos(int id_area_producto, String area_producto){
        this.id_area_producto = id_area_producto;
        this.area_producto = area_producto;
    }
    
    public area_productos(){
        
    }

    public int getId_area_producto() {
        return id_area_producto;
    }

    public void setId_area_producto(int id_area_producto) {
        this.id_area_producto = id_area_producto;
    }

    public String getArea_producto() {
        return area_producto;
    }

    public void setArea_producto(String area_producto) {
        this.area_producto = area_producto;
    }
    
    public void mostrarAreaProducto(JComboBox<area_productos> comboAreaProducto){
        try {
            control_inventario.conexion cc = new conexion();
            Connection cn=cc.conexion();
            
            String sql = "select * from areas_productos";
            //Conexion con = new Conexion();
            //con.ConexionPostgres();
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                comboAreaProducto.addItem(
                        new area_productos(
                                rs.getInt("id_area_producto"),
                                rs.getString("area_producto")
                        )
                );
            }
        } catch (Exception ex) {
            Logger.getLogger(cargo.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR LOS cargos");
        }
    }

    @Override
    public String toString() {
        return area_producto;
    }
    
    
    
}
