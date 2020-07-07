/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control_inventario.JFrame;

import com.mysql.jdbc.PreparedStatement;
import control_inventario.Control_Inventario;
import control_inventario.Control_Inventario.metod;
import control_inventario.conexion;
import control_inventario.conexionH;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileWriter;
import java.io.Serial;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author arlir
 */

public class InicioSesion extends javax.swing.JFrame {
    String usuarioA;
    public static String x;
    public static String usuarioB;
     
    public String conseguirN () {
        String x;
        
        x=usuarioB;   
        return x;
    }
    /**
     * Creates new form InicioSesion
     */
    
    
    public InicioSesion() {
        int valorInt;
        initComponents();
        metod llam=new metod();
        llam.mensaje();
        valorInt=llam.cubo();
        System.out.println("Valot INT: "+valorInt);
         macO p = new macO();//Llamar Función MAC
        Mac=p.conseguirMAC();
        
        System.out.println("Mac es: "+Mac);
        
        if(valorInt==1111){
       control_inventario.conexionH ccI = new conexionH(); //where nombre_user='"+user+"' and '"+contra+"'
        Connection cnI = ccI.conexionH();
        String serialING="SELECT serial FROM seriales where mac='"+Mac+"'";  
        String almING="12345";
        try {
            
            Statement st6=cnI.createStatement();
            ResultSet rs6=st6.executeQuery(serialING);
            
            while(rs6.next()){
                almING = rs6.getString(1);  
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        System.out.println("Serial almacenado:"+almING);
        
        if(almING.equals("12345")){
            jButton1.setEnabled(false);
            
        }
        }  
        
        
        
        
        this.setTitle("Inicio de sesión");
        Image ico= Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/icono.png"));
        this.setIconImage(ico);
        this.setResizable(false);
        setLocationRelativeTo(null);
        Color colorAzulF=new Color(20, 63, 82);//AZULDE FONDO
        Color colorGrisL=new Color(215, 225, 229);//LETRAS
        //this.getContentPane().setBackground(colorAzulF);
        //ImageIcon imagen=new ImageIcon(new ImageIcon(getClass().getResource("img/fond.JPG")).getImage());
        jLabel1.setForeground(colorGrisL);
        jLabel2.setForeground(colorGrisL);
        jLabel3.setForeground(colorGrisL);

        
    }
    String user2a;
    public void Login(){
         String user = jTextField1.getText();
        String contra = jpass_contra_user.getText();
        
        
        control_inventario.conexion cc = new conexion(); //where nombre_user='"+user+"' and '"+contra+"'
        Connection cn = cc.conexion();
        
        
        conexionH ccH = new conexionH(); //where nombre_user='"+user+"' and '"+contra+"'
        Connection cnH = ccH.conexionH();
        String sql="SELECT nombre_user FROM usuario where nombre_user='"+user+"'";
        String sql2="SELECT contra_user FROM usuario where contra_user='"+contra+"'";
        String verificar1="SELECT identidad FROM usuario where nombre_user='"+user+"'";
        String verificar2="SELECT identidad FROM usuario where contra_user='"+contra+"'";
        
        String cp3="NN!";
        String cp4="NN$";
        
        
        int paseuser = 0;
        int pasecontra = 0;
        int entra = 0;

                
                
        try {
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            
            while(rs.next()){
                String usn = rs.getString(1);
               
                
                if(usn.equals(user)){
                    //System.out.println("Bienvenido");
                    paseuser = 1;
                    
                }
            }
            
            Statement st2=cn.createStatement();
            ResultSet rs2=st2.executeQuery(sql2);
            
            while(rs2.next()){
                String cp = rs2.getString(1);
                if(cp.equals(contra)){
                    //System.out.println("Bienvenido contra");
                    pasecontra = 1;
                }
            }
            
            
            Statement st3=cn.createStatement();
            ResultSet rs3=st3.executeQuery(verificar1);
            
            while(rs3.next()){
                 cp3 = rs3.getString(1);
                
            }
            Statement st4=cn.createStatement();
            ResultSet rs4=st4.executeQuery(verificar2);
            
            while(rs4.next()){
                cp4 = rs4.getString(1);
                
            }
            
            
            
            
            
            
            if(((pasecontra==0) && (paseuser==0))){
                JOptionPane.showMessageDialog(null, "Usuario y Contraseña Incorrecta"); 
                jpass_contra_user.setText("");
                jTextField1.setText("");
                entra=1;
            }
            
            if((pasecontra==0) && (entra==0)){
                JOptionPane.showMessageDialog(null, "Contraseña Incorrecta"); jpass_contra_user.setText("");
            }
            
            if((paseuser==0) && (entra==0)){
                JOptionPane.showMessageDialog(null, "Usuario incorrecto"); jTextField1.setText("");
            }
            
            
            
            if(cp3.equals(cp4)){
                
            }else{
                JOptionPane.showMessageDialog(null, "Favor verifique sus datos"); jTextField1.setText("");
                paseuser=0;
                pasecontra=0;
            }
            
            if((paseuser==1) && (pasecontra==1)){
                
                JOptionPane.showMessageDialog(null, "Bienvenido");
                Menu m = new Menu();
                m.setVisible(true);
                dispose();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
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
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jpass_contra_user = new javax.swing.JPasswordField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        serialjbottom = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("Inicio de Sesión");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 24, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Nombre de Usuario:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 101, -1, -1));

        jButton1.setBackground(new java.awt.Color(215, 225, 229));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(20, 63, 82));
        jButton1.setText("Iniciar Sesion");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Contraseña:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 143, -1, -1));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(219, 105, 145, -1));
        getContentPane().add(jpass_contra_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(219, 147, 145, -1));

        jButton2.setBackground(new java.awt.Color(219, 225, 229));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(20, 63, 82));
        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, -1, -1));

        jButton3.setText("Conseguir serial");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 130, 30));

        serialjbottom.setText("Serial");
        serialjbottom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serialjbottomActionPerformed(evt);
            }
        });
        getContentPane().add(serialjbottom, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_inventario/JFrame/fondoI.JPG"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 350));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        usuarioB=jTextField1.getText();
       
        String pp123;
        pp123=conseguirN();
        System.out.println("PROBANDOBBBBBBBB: "+pp123);
        
        Login();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    //OBTENER MAC
    public class macO {

    
     public String conseguirMAC(){
            StringBuilder sb = new StringBuilder();
  NetworkInterface a; String linea;
  try {
   a = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
   byte[] mac = a.getHardwareAddress();
   

   for (int i = 0; i < mac.length; i++) {
    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));  
   } 
   FileWriter fwriter = new FileWriter("mac.dat");
   fwriter.write("MAC: " + sb.toString());
   fwriter.close();
   
 //  lmac.setText("SE ha registrado la MAC exitosamente.");
  } catch (Exception e) {
   e.printStackTrace(); 
  }
  return ""+sb.toString();
 }
   
    
}
    //FIN OBTENER MAC
    //VERIFICAR CONEXION
    
    //FIN VERIFICAR CONEXION
    String Mac;
    private void serialjbottomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serialjbottomActionPerformed
        
        
        
        String serialIn;//Serial de la base de datos
        String Mac;//Dirección MAC de la pc
        
        macO p = new macO();//Llamar Función MAC
        Mac=p.conseguirMAC();
        serialIn=JOptionPane.showInputDialog("Ingrese su serial: ");
        control_inventario.conexionH ccH = new conexionH(); //where nombre_user='"+user+"' and '"+contra+"'
        Connection cnH = ccH.conexionH();
        String serialBDH="SELECT serial FROM seriales where serial='"+serialIn+"'";  
        String almS="NN!";
        
        
        
        try {
            
            Statement st5=cnH.createStatement();
            ResultSet rs5=st5.executeQuery(serialBDH);
            
            while(rs5.next()){
                almS = rs5.getString(1);  
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        if(serialIn.equals(almS)){
            
            
            
           
        }
        
        
        try {
            PreparedStatement pstH = (PreparedStatement) cnH.prepareStatement("UPDATE seriales SET mac='"+Mac+"' WHERE serial='"+serialIn+"'");
        pstH.executeUpdate();
        } catch (Exception e) {
        }
        
        ////
        control_inventario.conexionH ccI = new conexionH(); //where nombre_user='"+user+"' and '"+contra+"'
        Connection cnI = ccI.conexionH();
        String macDB="SELECT mac FROM seriales where mac='"+Mac+"'";  
        String macDB2="HHH";
        try {
            
            Statement st7=cnI.createStatement();
            ResultSet rs7=st7.executeQuery(macDB);
            
            while(rs7.next()){
                macDB2 = rs7.getString(1);  
                System.out.println("MACCCCCC222222: "+macDB2);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        if(Mac.equals(macDB2)){
            jButton1.setEnabled(true);
        }
        
        
    }//GEN-LAST:event_serialjbottomActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
        String url = "http://digitalpalace.net/";
        java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
    } catch (java.io.IOException e) {
        System.out.println(e.getMessage());
    }
    }//GEN-LAST:event_jButton3ActionPerformed
String nombre; 
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
       
    }//GEN-LAST:event_jTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //INICIO VERIFICAR CONEXION
        
        
        
        
        
        //FIN VERIFICAR CONEXION
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
            java.util.logging.Logger.getLogger(InicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InicioSesion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public static javax.swing.JTextField jTextField1;
    private javax.swing.JPasswordField jpass_contra_user;
    private javax.swing.JButton serialjbottom;
    // End of variables declaration//GEN-END:variables

        conexionH ccH = new conexionH(); //where nombre_user='"+user+"' and '"+contra+"'
        Connection cnH = ccH.conexionH();

}
