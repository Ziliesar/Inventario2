/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control_inventario.JFrame;

import control_inventario.area_productos;
import control_inventario.conexion;
import control_inventario.unidad_producto;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author arlir
 */
public final class Registrar_Productos extends javax.swing.JFrame {
        control_inventario.conexion cc = new conexion();
        Connection cn=cc.conexion();

    /**
     * Creates new form Registrar_Productos
     */
    public Registrar_Productos() {
        initComponents();
        mostrarProductos();
        jButton2.setEnabled(false);
        jLabel11.setVisible(false);
        txt_new_cantidad.setVisible(false);
        
        control_inventario.area_productos arp = new area_productos();
        arp.mostrarAreaProducto(jComboBox1);
        
        control_inventario.unidad_producto uni = new unidad_producto();
        uni.mostrarUnidad(jComboBox2);
        
        this.setTitle("Registrar personal");
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
        //jLabel0.setForeground(colorGrisL);
        jLabel4.setForeground(colorGrisL);
        jLabel5.setForeground(colorGrisL);
        jLabel6.setForeground(colorGrisL);
        jLabel7.setForeground(colorGrisL);
        jLabel8.setForeground(colorGrisL);
        
      //  TextPrompt placeholder = new TextPrompt("Apellido Paterno", txt_busqueda_codigo);
 
    }
    
    void mostrarProductos(){
        DefaultTableModel modelo=new DefaultTableModel();
       
        modelo.addColumn("Codigo");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Unidad");
        modelo.addColumn("cantidad");
        modelo.addColumn("Precio Compra");
        modelo.addColumn("Precio Venta");
        modelo.addColumn("Area Producto");
          
        jTable_productos.setModel(modelo);
        String sql="";
       
        sql="SELECT pd.codigo, pd.descripcion, uni.unidad, pd.cantidad, pd.precio_compra, pd.precio_venta, apd.area_producto\n" +
            "FROM producto pd INNER JOIN tipo_unidad uni on pd.id_unidad=uni.id_unidad INNER JOIN areas_productos apd on pd.id_area_producto=apd.id_area_producto ORDER BY pd.cantidad ASC;";
     
        
        String []datos=new String [7];
        try{
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
            datos[0]=rs.getString(1);
            datos[1]=rs.getString(2);
            datos[2]=rs.getString(3);
            datos[3]=rs.getString(4);
            datos[4]=rs.getString(5);
            datos[5]=rs.getString(6);
            datos[6]=rs.getString(7);
            
            modelo.addRow(datos);
            }
            jTable_productos.setModel(modelo);
        }catch(SQLException ex){
            Logger.getLogger(Registrar_Productos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    void BusquedaCodigo(String codi){
        DefaultTableModel modelo=new DefaultTableModel();
       
        modelo.addColumn("Codigo");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Unidad");
        modelo.addColumn("cantidad");
        modelo.addColumn("Precio Compra");
        modelo.addColumn("Precio Venta");
        modelo.addColumn("Area Producto");
          
        jTable_productos.setModel(modelo);
        String sql="";
       
        sql="SELECT pd.codigo, pd.descripcion, uni.unidad, pd.cantidad, pd.precio_compra, pd.precio_venta, apd.area_producto\n" +
            "FROM producto pd INNER JOIN tipo_unidad uni on pd.id_unidad=uni.id_unidad INNER JOIN areas_productos apd on pd.id_area_producto=apd.id_area_producto where pd.codigo ='"+codi+"'";
     
        
        String []datos=new String [7];
        try{
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
            datos[0]=rs.getString(1);
            datos[1]=rs.getString(2);
            datos[2]=rs.getString(3);
            datos[3]=rs.getString(4);
            datos[4]=rs.getString(5);
            datos[5]=rs.getString(6);
            datos[6]=rs.getString(7);
            
            modelo.addRow(datos);
            }
            jTable_productos.setModel(modelo);
        }catch(SQLException ex){
            Logger.getLogger(Registrar_Productos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void RegistrarProductos(){
        int error = 0;
        String mensaje = "";
        String codigo = txt_codigo.getText();
        String decrip = txtA_descripcion.getText();
        
        int idArea, idUnidad;
        idArea = jComboBox1.getItemAt(jComboBox1.getSelectedIndex()).getId_area_producto(); 
        idUnidad = jComboBox2.getItemAt(jComboBox2.getSelectedIndex()).getId_unidad();
        
        String can = txt_cantidad.getText();
        String preciocompra = txtx_precio_compra.getText();
        String preciventa = txt_precio_venta.getText();
        
        //----------------comprovar si codigo ya existe-------------------------
        String codEx = "select codigo from producto where codigo='"+codigo+"'";
        String codigoEx = "";
        try {
            Statement stcodigo=cn.createStatement();
            ResultSet rscodigo=stcodigo.executeQuery(codEx);
            
            while(rscodigo.next()){
                codigoEx = rscodigo.getString(1);
            }
        } catch (Exception e) {
        }
        if(codigo.equals(codigoEx)){
            error = 6;
        }
        
        //----------------------------------------------------------------------
        else if(codigo.equals("")){
            error = 1;
            mensaje = "el codigo";
        }
        else if(decrip.equals("")){
            error = 2;
            mensaje = "la descripcion";
        }
        else if(can.equals("")){
            error = 3;
            mensaje = "la cantidad";
        }
        else if(preciocompra.equals("")){
            error = 4;
            mensaje = "el precio compra";
        }
        else if(preciventa.equals("")){
            error = 5;
            mensaje = "el precio venta";
        }
        
        if(error==0){
                try{
                    PreparedStatement pst=cn.prepareStatement("INSERT INTO producto(codigo, descripcion, id_unidad, cantidad, precio_compra, precio_venta, id_area_producto) VALUES(?,?,?,?,?,?,?)");
                    pst.setString(1,codigo);
                    pst.setString(2,decrip);
                    pst.setInt(3, idUnidad);
                    pst.setString(4,can);
                    pst.setString(5, preciocompra);
                    pst.setString(6, preciventa);
                    pst.setInt(7, idArea);


                    int a=pst.executeUpdate();
                    if(a>0){
                        JOptionPane.showMessageDialog(null,"Producto Registro con exito");
                         mostrarProductos();
                    }
                    else{
                         JOptionPane.showMessageDialog(null,"Error al agregar");
                    }
                }catch(Exception e){
            }
                limpiar();
        }else{
            if(error==6){
                JOptionPane.showMessageDialog(null,"El codigo que esta ingresando ya existe");
                txt_codigo.setText("");
            }else{
                JOptionPane.showMessageDialog(null,"Asegurese de llenar todo los campos");
            }
            
        }
        
    }
    
    public void ActualizarProducto(){
        int nuevacan, canvieja, GNC;
        nuevacan = Integer.parseInt(txt_new_cantidad.getText());
        canvieja = Integer.parseInt(txt_cantidad.getText());
        
        GNC = nuevacan + canvieja;
        try {
        
        String Ssql = "UPDATE producto SET descripcion=?, cantidad=?, precio_compra=?, precio_venta=? WHERE codigo =?";
        
        PreparedStatement prest = cn.prepareStatement(Ssql);
        
        prest.setString(1, txtA_descripcion.getText());
        prest.setInt(2, GNC);
        prest.setString(3, txtx_precio_compra.getText());
        prest.setString(4, txt_precio_venta.getText());
        prest.setString(5, txt_codigo.getText());
        
        if(prest.executeUpdate() > 0){
        
            JOptionPane.showMessageDialog(null, "Los datos han sido modificados con éxito", "Operación Exitosa",JOptionPane.INFORMATION_MESSAGE);
            txt_codigo.setEditable(true);
        }else{
        
            JOptionPane.showMessageDialog(null, "No se ha podido realizar la actualización de los datos\n"
            + "Inténtelo nuevamente.", "Error en la operación", JOptionPane.ERROR_MESSAGE);
        
        }
        
        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "No se ha podido realizar la actualización de los datos\n"
                                              + "Inténtelo nuevamente.\n"
                                              + "Error: "+e, "Error en la operación", 
                                              JOptionPane.ERROR_MESSAGE);

        }
        
        mostrarProductos();
    }
    
    public void limpiar(){
        txtA_descripcion.setText("");
        txt_cantidad.setText("");
        txt_codigo.setText("");
        txt_precio_venta.setText("");
        txtx_precio_compra.setText("");
        txt_busqueda_codigo.setText("");
        txt_new_cantidad.setText("0");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_productos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtA_descripcion = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtx_precio_compra = new javax.swing.JTextField();
        txt_cantidad = new javax.swing.JTextField();
        txt_precio_venta = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txt_busqueda_codigo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_new_cantidad = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        jMenuItem1.setText("Seleccionar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_productos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTable_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable_productos.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(jTable_productos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 671, 213));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("Registro de producto");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Código:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Descripción:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));
        getContentPane().add(txt_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 190, -1));

        jScrollPane2.setOpaque(false);

        txtA_descripcion.setColumns(20);
        txtA_descripcion.setRows(5);
        jScrollPane2.setViewportView(txtA_descripcion);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 190, 70));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Cantidad:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Precio compra:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Precio venta:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 220, -1, -1));
        getContentPane().add(txtx_precio_compra, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 180, 160, -1));
        getContentPane().add(txt_cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, 160, -1));

        txt_precio_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_precio_ventaActionPerformed(evt);
            }
        });
        getContentPane().add(txt_precio_venta, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 220, 160, -1));

        jButton1.setText("Registrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, -1, -1));

        jButton2.setText("Actualizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 330, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Área producto:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 260, -1, -1));

        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 260, 160, -1));

        jButton3.setText("Registrar área producto");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 330, 150, -1));

        jButton4.setText("Regresar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 330, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Unidad:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 190, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Busqueda de Producto");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 380, -1, -1));

        txt_busqueda_codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_busqueda_codigoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_busqueda_codigoKeyReleased(evt);
            }
        });
        getContentPane().add(txt_busqueda_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 380, 130, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Nueva cantidad:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 140, -1, -1));

        txt_new_cantidad.setText("0");
        getContentPane().add(txt_new_cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, 160, -1));

        jButton5.setText("Agregar Unidad");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 330, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_inventario/JFrame/fondoR.jpg"))); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 650));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_precio_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_precio_ventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_precio_ventaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        RegistrarProductos();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Registrar_Area_Productos Arp = new Registrar_Area_Productos();
        Arp.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Menu me = new Menu();
        me.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        int filaSelec = jTable_productos.getSelectedRow();
        
        try {
            txt_codigo.setText(jTable_productos.getValueAt(filaSelec, 0).toString());
            txtA_descripcion.setText(jTable_productos.getValueAt(filaSelec, 1).toString());
            txt_cantidad.setText(jTable_productos.getValueAt(filaSelec, 3).toString());
            txtx_precio_compra.setText(jTable_productos.getValueAt(filaSelec, 4).toString());
            txt_precio_venta.setText(jTable_productos.getValueAt(filaSelec, 5).toString());
        } catch (Exception e) {
        }
        jButton2.setEnabled(true);
        jButton1.setEnabled(false);
        txt_codigo.setEditable(false);
        
        jLabel11.setVisible(true);
        txt_new_cantidad.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ActualizarProducto();
        
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        
        jLabel11.setVisible(false);
        txt_new_cantidad.setVisible(false);
        
        limpiar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_busqueda_codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busqueda_codigoKeyReleased
        // TODO add your handling code here:
        String cdg = txt_busqueda_codigo.getText();
        if("".equals(cdg)){
            mostrarProductos();
        }else{
            BusquedaCodigo(cdg);
        }
            
        
    }//GEN-LAST:event_txt_busqueda_codigoKeyReleased

    private void txt_busqueda_codigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busqueda_codigoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_busqueda_codigoKeyPressed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        dispose();
        Registrar_Unidad_Producto rupd = new Registrar_Unidad_Producto();
        rupd.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(Registrar_Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registrar_Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registrar_Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registrar_Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registrar_Productos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<area_productos> jComboBox1;
    private javax.swing.JComboBox<unidad_producto> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_productos;
    private javax.swing.JTextArea txtA_descripcion;
    private javax.swing.JTextField txt_busqueda_codigo;
    private javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_new_cantidad;
    private javax.swing.JTextField txt_precio_venta;
    private javax.swing.JTextField txtx_precio_compra;
    // End of variables declaration//GEN-END:variables
}
