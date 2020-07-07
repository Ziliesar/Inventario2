/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control_inventario.JFrame;

import control_inventario.cargo;
import control_inventario.conexion;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.ImageIcon;



/**
 *
 * @author arlir
 */
public class Registrar_Personal extends javax.swing.JFrame {
    
        control_inventario.conexion cc = new conexion();
        Connection cn=cc.conexion();

    /**
     * Creates new form Registrar_Personal
     */
    public Registrar_Personal() {
        initComponents();
        mostrarPersonal();
        control_inventario.cargo car = new cargo();
        car.mostrarCargo(jComboBox1);
        this.setTitle("Inventario Digital Palace");
        Image ico= Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/icono.png"));
        this.setIconImage(ico);
    
        Color colorAzulF=new Color(20, 63, 82);//AZULDE FONDO
        Color colorGrisL=new Color(215, 225, 229);//LETRAS
        //this.getContentPane().setBackground(colorAzulF);
       
        //ImageIcon imagen=new ImageIcon(new ImageIcon(getClass().getResource("img/fond.JPG")).getImage());
        
        jLabel1.setForeground(colorGrisL);
        jLabel2.setForeground(colorGrisL);
        jLabel3.setForeground(colorGrisL);
        jLabel4.setForeground(colorGrisL);
        jLabel6.setForeground(colorGrisL);
        jLabel7.setForeground(colorGrisL);
        jLabel8.setForeground(colorGrisL);
        
        this.setResizable(false);
    }
    
    void mostrarPersonal(){
        DefaultTableModel modelo=new DefaultTableModel();
       
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellodos");
        modelo.addColumn("Nombre Usuario");
        modelo.addColumn("Cargo");
          
        jtable_personal.setModel(modelo);
        String sql="";
       
        sql="SELECT per.identidad, per.nombres, per.apellidos, us.nombre_user, car.cargo FROM personal per INNER JOIN usuario us on per.identidad=us.identidad INNER JOIN cargo car on per.id_cargo=car.id_cargo";
     
        
        String []datos=new String [5];
        try{
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
            datos[0]=rs.getString(1);
            datos[1]=rs.getString(2);
            datos[2]=rs.getString(3);
            datos[3]=rs.getString(4);
            datos[4]=rs.getString(5);
            
            modelo.addRow(datos);
            }
            jtable_personal.setModel(modelo);
        }catch(SQLException ex){
            Logger.getLogger(Registrar_Personal.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void limpiar(){
        txt_id.setText("");
        txt_nombres.setText("");
        txt_apellidos.setText("");
        txt_user.setText("");
        txt_contra.setText("");
    }
    
    public void RegistrarPersonal(){
        String id, nombres, apellidos;
        int error = 0;
        
        id = txt_id.getText();
        nombres = txt_nombres.getText();
        apellidos = txt_apellidos.getText();
        if("".equals(id)){
            error = 1;
        }
        else if("".equals(nombres)){
            error = 2;
        }
        else if("".equals(apellidos)){
            error = 3;
        }
         
        if(error==0){
            try{
                int idCargo;
                idCargo = jComboBox1.getItemAt(jComboBox1.getSelectedIndex()).getIdCargo(); 

                PreparedStatement pst=cn.prepareStatement("INSERT INTO personal(identidad, nombres, apellidos, id_cargo) VALUES(?,?,?,?)");
                pst.setString(1, id);
                pst.setString(2, nombres);
                pst.setString(3, apellidos);
                pst.setInt(4, idCargo);


            int a=pst.executeUpdate();
            if(a>0){
                JOptionPane.showMessageDialog(null,"Registro exitoso");
                 mostrarPersonal();
                 limpiar();
            }
            else{
                 JOptionPane.showMessageDialog(null,"Error al agregar");
            }
            }catch(Exception e){
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Asegurese de llenar todos los campos");
        }
         
    }
    
    public void RegistrarUsuario(){
        control_inventario.conexion cc = new conexion();
        Connection cn=cc.conexion();
        String contr;
        try{
            PreparedStatement pst=cn.prepareStatement("INSERT INTO usuario(identidad, nombre_user, contra_user)  VALUES(?,?,?)");
            pst.setString(1,txt_id.getText());
            pst.setString(2,txt_user.getText());
            
            contr=String.valueOf(txt_contra.getPassword());
            /*Convertir contraseña*****************************/
            
            
            pst.setString(3,contr);    
        
        int a=pst.executeUpdate();
        if(a>0){
            JOptionPane.showMessageDialog(null,"Registro exitoso");
             mostrarPersonal();
        }
        else{
             JOptionPane.showMessageDialog(null,"Error al agregar");
        }
        }catch(Exception e){
        } 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        txt_nombres = new javax.swing.JTextField();
        txt_apellidos = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_user = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txt_contra = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtable_personal = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Registro de Personal");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 32, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Nombres:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 173, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Nombre Usuario");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 126, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Apellidos:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 227, -1, -1));
        getContentPane().add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 130, 145, -1));
        getContentPane().add(txt_nombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 177, 145, -1));
        getContentPane().add(txt_apellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 231, 145, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Identidad:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 126, -1, -1));
        getContentPane().add(txt_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(562, 130, 132, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Contraseña");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 173, -1, -1));

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(562, 230, 132, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Cargo");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 227, -1, -1));
        getContentPane().add(txt_contra, new org.netbeans.lib.awtextra.AbsoluteConstraints(562, 177, 132, -1));

        jButton1.setText("Registrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 330, 90, 40));

        jtable_personal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jtable_personal);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 382, 697, 122));

        jButton2.setText("Regresar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 330, 90, 40));

        jButton3.setText("Limpiar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 330, 90, 40));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_inventario/JFrame/fond2.JPG"))); // NOI18N
        jLabel10.setText("jLabel10");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 530));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         
        RegistrarPersonal();
        RegistrarUsuario();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        //Creamos objeto tipo Connection    
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Menu me = new Menu();
        me.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Registrar_Personal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registrar_Personal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registrar_Personal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registrar_Personal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registrar_Personal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<cargo> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtable_personal;
    private javax.swing.JTextField txt_apellidos;
    private javax.swing.JPasswordField txt_contra;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nombres;
    private javax.swing.JTextField txt_user;
    // End of variables declaration//GEN-END:variables
}

//SELECT per.identidad, per.nombres, per.apellidos, per.correo, us.nombre_user, us.contra_user, car.cargo FROM personal per INNER JOIN usuario us on per.identidad=us.identidad INNER JOIN cargo car on per.id_cargo=car.id_cargo