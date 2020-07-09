/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control_inventario.JFrame;

import control_inventario.TextPromp;
import control_inventario.conexion;
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
public class Devoluciones extends javax.swing.JFrame {
        control_inventario.conexion cc = new conexion();
        Connection cn=cc.conexion();
    /**
     * Creates new form Devoluciones
     */
    public Devoluciones() {
        initComponents();
        
        control_inventario.TextPromp placeholder = new TextPromp("Ingrese codigo Factura", txt_buscar_cod_fac);
    }
    
    void BusquedaCodigoFac(String codFac){
        DefaultTableModel modelo=new DefaultTableModel();
       
        modelo.addColumn("Codigo Factura");
        modelo.addColumn("Codigo Det. factura");
        modelo.addColumn("Nombre Vendedor");
        modelo.addColumn("RTN Cliente");
        modelo.addColumn("Nombre Cliente");
        modelo.addColumn("Total");
        modelo.addColumn("Fecha");
        modelo.addColumn("Hora");
          
        jTable_devolucion.setModel(modelo);
        String sql="";
       
        sql="SELECT fac.codigo_factura, fac.cog_detalle_fac, CONCAT(per.nombres,\" \",per.apellidos), cl.rtn ,CONCAT(cl.nombres, \" \", cl.apellidos), fac.total, fac.fecha, fac.hora FROM factura fac INNER JOIN personal per on fac.identidad=per.identidad INNER JOIN cliente cl ON fac.rtn=cl.rtn WHERE fac.codigo_factura='"+codFac+"'";
     
        
        String []datos=new String [8];
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
            datos[7]=rs.getString(8);
            
            modelo.addRow(datos);
            }
            jTable_devolucion.setModel(modelo);
        }catch(SQLException ex){
            Logger.getLogger(Devoluciones.class.getName()).log(Level.SEVERE,null,ex);
        }
        
        ObtenerDetalleFac();
    }
    
    void ObtenerDetalleFac(){
        String codDFac = jTable_devolucion.getValueAt(0, 1).toString();

            DefaultTableModel modelo=new DefaultTableModel();

            modelo.addColumn("Codigo Producto");
            modelo.addColumn("Descripcion");
            modelo.addColumn("Precio Venta");
            modelo.addColumn("Cantidad");
            modelo.addColumn("Total");

            jTable_detalle_fac.setModel(modelo);
            String sql="";

            sql="SELECT pd.codigo, pd.descripcion, pd.precio_venta, df.cantidadV, df.total FROM detalle_factura df INNER JOIN producto pd on df.codigo=pd.codigo where df.cog_detalle_fac='"+codDFac+"'";


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
                jTable_detalle_fac.setModel(modelo);
            }catch(SQLException ex){
                Logger.getLogger(Devoluciones.class.getName()).log(Level.SEVERE,null,ex);
            }
        
    }
    
    void devolver(String cod, String can, String pre){
        int c = Integer.parseInt(can);
        int p = Integer.parseInt(pre);
        int t = c*p;
        try {
            String Dsql = "UPDATE detalle_factura SET cantidadV=?, total=? WHERE cog_detalle_fac =?";
            PreparedStatement prest = cn.prepareStatement(Dsql);
            prest.setString(1, can);
            prest.setInt(2, t);
            prest.setString(3, cod);
            
            if(prest.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null,"Producto Modificado");
            }
        } catch (Exception e) {
            System.err.println("Error al devolver Producto: "+3);
        }
        int CanEx = 0;
        int CanDev = 0;
        try {
            String Csql = "SELECT cantidad FROM producto WHERE codigo='"+cod+"'";
            Statement stcodigo=cn.createStatement();
            ResultSet rscodigo=stcodigo.executeQuery(Csql);
            
            while(rscodigo.next()){
                CanEx = rscodigo.getInt(1);
            }
            CanDev = c + CanDev;
            
            String Dcansql = "UPDATE producto SET cantidad=?, WHERE codigo=?";
            PreparedStatement pst = cn.prepareStatement(Dcansql);
            pst.setInt(1, CanDev);
        } catch (Exception e) {
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_devolucion = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txt_buscar_cod_fac = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_detalle_fac = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txt_cpd = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_dpc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_can = new javax.swing.JTextField();
        txt_can_devol = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txt_precioV = new javax.swing.JTextField();

        jMenuItem1.setText("Seleccionar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("Devolucion de Productos");

        jTable_devolucion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable_devolucion);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Buscar Factura:");

        txt_buscar_cod_fac.setText("000-000-001");
        txt_buscar_cod_fac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscar_cod_facKeyReleased(evt);
            }
        });

        jTable_detalle_fac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable_detalle_fac.setComponentPopupMenu(jPopupMenu1);
        jScrollPane2.setViewportView(jTable_detalle_fac);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Detalle de la Factura");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Codigo Producto");

        txt_cpd.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Descripcion");

        txt_dpc.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Cantidad");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Cantidad a devolver");

        txt_can.setEnabled(false);

        txt_can_devol.setText("0");

        jButton2.setText("Devolver");
        jButton2.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Precio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(124, 124, 124))
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txt_buscar_cod_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txt_can, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(txt_precioV))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txt_cpd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_dpc, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(txt_can_devol, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_buscar_cod_fac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_cpd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jLabel5)
                    .addComponent(txt_dpc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(txt_can, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_can_devol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txt_precioV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_buscar_cod_facKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscar_cod_facKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_buscar_cod_facKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String codf = txt_buscar_cod_fac.getText();
        BusquedaCodigoFac(codf);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
         int filaSelec = jTable_detalle_fac.getSelectedRow();
         String CD, DPD, PPD, CAN;
         CD = jTable_detalle_fac.getValueAt(filaSelec, 0).toString();
         DPD = jTable_detalle_fac.getValueAt(filaSelec, 1).toString();
         PPD = jTable_detalle_fac.getValueAt(filaSelec, 2).toString();
         CAN = jTable_detalle_fac.getValueAt(filaSelec, 3).toString();
         
         txt_cpd.setText(CD);
         txt_dpc.setText(DPD);
         txt_precioV.setText(PPD);
         txt_can.setText(CAN);
         
         jButton2.setEnabled(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(Devoluciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Devoluciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Devoluciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Devoluciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Devoluciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_detalle_fac;
    private javax.swing.JTable jTable_devolucion;
    private javax.swing.JTextField txt_buscar_cod_fac;
    private javax.swing.JTextField txt_can;
    private javax.swing.JTextField txt_can_devol;
    private javax.swing.JTextField txt_cpd;
    private javax.swing.JTextField txt_dpc;
    private javax.swing.JTextField txt_precioV;
    // End of variables declaration//GEN-END:variables
}
