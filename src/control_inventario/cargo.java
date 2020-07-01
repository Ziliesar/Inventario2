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
public class cargo {
    private int idcargo;
    private String cargo;
    
    public cargo(int idcargo, String cargo){
        this.idcargo = idcargo;
        this.cargo = cargo;
    }

    public cargo() {
        
    }
    
    
    public int getIdCargo(){
        return idcargo;
    }
    
    public void setidCargo(int idcargo){
        this.idcargo = idcargo;
    }
    
    public String getCargo(){
        return cargo;
    }
    
    public void setCargo(String cargo){
        this.cargo = cargo;
    }
    
    
    public void mostrarCargo(JComboBox<cargo> comboCargo){
        try {
            control_inventario.conexion cc = new conexion();
            Connection cn=cc.conexion();
            
            String sql = "select * from cargo";
            //Conexion con = new Conexion();
            //con.ConexionPostgres();
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                comboCargo.addItem(
                        new cargo(
                                rs.getInt("id_cargo"),
                                rs.getString("cargo")
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
        return cargo ;
    }


    
    
}
