/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control_inventario.JFrame;

import static control_inventario.JFrame.Registrar_Ventas.txt_rtn_cliente;
import control_inventario.Reporte;
import control_inventario.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author arlir
 */
public final class Consultar_Credito_Cliente extends javax.swing.JFrame {
    control_inventario.conexion cc = new conexion();
    Connection cn=cc.conexion();
    
    
    public void agreharF(){
        txt_fecha_credito.setText(fecha());
    }
    
    public static String fecha;
    public static String fecha(){
      Calendar c1 = Calendar.getInstance();
        Calendar c2 = new GregorianCalendar();
        
        String dia = Integer.toString(c1.get(Calendar.DATE));
        if(dia.equals("1")||dia.equals("2")||dia.equals("3")||dia.equals("4")||dia.equals("5")||dia.equals("6")||
                dia.equals("7")||dia.equals("8")||dia.equals("9")){
            dia="0"+dia;
        }
        String mes = Integer.toString(c1.get(Calendar.MONTH));
        
        int mes2=Integer.parseInt(mes);
        mes2=mes2+1;
        mes=Integer.toString(mes2);
        if(mes.equals("1")||mes.equals("2")||mes.equals("3")||mes.equals("4")||mes.equals("5")||mes.equals("6")||
                mes.equals("7")||mes.equals("8")||mes.equals("9")){
            mes="0"+mes;
        }
        String annio = Integer.toString(c1.get(Calendar.YEAR));
        fecha=annio+"-"+mes+"-"+dia;
      return fecha;
  }

    /**
     * Creates new form Consultar_Credito_Cliente
     */
    public Consultar_Credito_Cliente() {
        initComponents();
        agreharF();
    }
    
    void BuscarCredito(String id){
        DefaultTableModel modelo=new DefaultTableModel();
        modelo.addColumn("Codigo Fac");
        modelo.addColumn("Fecha");
        modelo.addColumn("Total");
        jTable_credito.setModel(modelo);
        String sql="";
        sql="SELECT cd.codigo_factura, cd.fecha, cd.total FROM credito cd INNER JOIN cliente cl ON cd.identidadC=cl.identidadC where cl.identidadC = '"+id+"' AND canselado = false";
        String []datos=new String [3];
        try{
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
            datos[0]=rs.getString(1);
            datos[1]=rs.getString(2);
            datos[2]=rs.getString(3); 
            modelo.addRow(datos);
            }
            jTable_credito.setModel(modelo);
        }catch(SQLException ex){
            Logger.getLogger(Registrar_Personal.class.getName()).log(Level.SEVERE,null,ex);
        }
        SaldoCredito(id);
    }
    
    void BuscarPagoMonto(String id){
        DefaultTableModel modelo=new DefaultTableModel();
        modelo.addColumn("Id Pago");
        modelo.addColumn("Monto");
        modelo.addColumn("Fecha");
        
        jTable_Monto.setModel(modelo);
        String sql= "SELECT id_pago, monto, fecha_pago FROM cuotacredito WHERE identidadC =  '"+id+"' AND cancelado = false";
        String []datos=new String [3];
        try{
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
            datos[0]=rs.getString(1);
            datos[1]=rs.getString(2);
            datos[2]=rs.getString(3); 
            modelo.addRow(datos);
            }
            jTable_Monto.setModel(modelo);
        }catch(SQLException ex){
            Logger.getLogger(Registrar_Personal.class.getName()).log(Level.SEVERE,null,ex);
        }
        SaldoPagoMonto(id);
    }
    
    public String SaldoCredito(String t){
        String Prt = "";      //Extarer el valor actual de la tabla credito
        String id = txt_idC.getText(); //extraer id texto
        String suma = "select SUM(total) from credito WHERE identidadC = '"+t+"' AND canselado = false";
        try {
            Statement stCCD=cn.createStatement();
            ResultSet rsCCD=stCCD.executeQuery(suma);
            while(rsCCD.next()){
                Prt = rsCCD.getString(1);
            }
        } catch (Exception e) {
        }
        txt_total_credito.setText(Prt);
        return Prt;
    }
    
    public String SaldoPagoMonto(String t){
        String MTP = "";      //Extarer el valor actual de la tabla credito
        String id = txt_idC.getText(); //extraer id texto
        String suma = "select SUM(monto) from cuotacredito WHERE identidadC = '"+t+"' AND cancelado = false";
        try {
            Statement stCCD=cn.createStatement();
            ResultSet rsCCD=stCCD.executeQuery(suma);
            while(rsCCD.next()){
                MTP = rsCCD.getString(1);
            }
        } catch (Exception e) {
        }
        txt_monto_pagado.setText(MTP);
        
        return MTP;
    }
    
    public void Saldo(){
        String idC = txt_idC.getText();
        String saldoCredito = SaldoCredito(idC);
        String saldoPago = SaldoPagoMonto(idC);
        
        if(saldoCredito == null){
            saldoCredito = "0";
            
        }else if(saldoPago == null){
            saldoPago = "0";
        }
        
        double totalcredito = Double.parseDouble(saldoCredito);
        double totalPago = Double.parseDouble(saldoPago);
        
        double saldorestante = totalcredito - totalPago;
        
        txt_saldo.setText(saldorestante+"");
    }
    
    public void PagarMonto(){
        String id = txt_idC.getText(); //extraer id texto
        String monto = txt_monto.getText();
        int error = 0;
        
        if(monto.equals("")){
            error= 1;
        }
        if(error==0){
            
            try {
                PreparedStatement pst=cn.prepareStatement("INSERT INTO cuotacredito(identidadC, monto, fecha_pago, cancelado) VALUES (?,?,?,?)");
                pst.setString(1, id);
                pst.setString(2, monto);
                pst.setString(3, txt_fecha_credito.getText());
                pst.setBoolean(4, false);
                
                int a = pst.executeUpdate();
                if(a>0){
                    JOptionPane.showMessageDialog(null,"Registro exitoso");  
                }
            } catch (Exception e) {
            }
        }
        else{
             JOptionPane.showMessageDialog(null,"Ingrese una Cantidad");
        }
    }
    
    public void ActualizarPagoMonto(String idP){
        try {
        String Ssql = "UPDATE cuotacredito SET cancelado=? WHERE identidadC =? and id_pago=?";
        PreparedStatement prest = cn.prepareStatement(Ssql);
        prest.setBoolean(1, true);
        prest.setString(2, txt_idC.getText());
        prest.setString(3, idP);
        if(prest.executeUpdate() > 0){
            JOptionPane.showMessageDialog(null, "El Pago se ha cancelado", "Operación Exitosa",JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "No se ha podido realizar la actualización de los datos\n"
            + "Inténtelo nuevamente.", "Error en la operación", JOptionPane.ERROR_MESSAGE);
        }
        }catch(SQLException e) {System.out.println("error: "+e);}
    }
    
    public void ActualizarFacturaCredito(String cod){
        try {
        String Ssql = "UPDATE credito SET canselado=? WHERE identidadC=? and codigo_factura=?";
        PreparedStatement prest = cn.prepareStatement(Ssql);
        prest.setBoolean(1, true);
        prest.setString(2, txt_idC.getText());
        prest.setString(3, cod);
        if(prest.executeUpdate() > 0){
            JOptionPane.showMessageDialog(null, "La Factura se ha cancelado", "Operación Exitosa",JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "No se ha podido realizar la actualización de los datos\n"
            + "Inténtelo nuevamente.", "Error en la operación", JOptionPane.ERROR_MESSAGE);
        }
        }catch(SQLException e) {System.out.println("error: "+e);}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu_factura_credito = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPopupMenu_pago_credito = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_credito = new javax.swing.JTable();
        txt_total_credito = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_monto = new javax.swing.JTextField();
        txt_nombreC = new javax.swing.JTextField();
        txt_idC = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txt_fecha_credito = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_Monto = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_monto_pagado = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_saldo = new javax.swing.JTextField();

        jMenuItem1.setText("Cancelar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu_factura_credito.add(jMenuItem1);

        jMenuItem2.setText("Cancelar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu_pago_credito.add(jMenuItem2);

        jMenuItem3.setText("Imprimir");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jPopupMenu_pago_credito.add(jMenuItem3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Consulta Credito Cliente");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre del Cliente");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Identidad");

        jTable_credito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable_credito.setComponentPopupMenu(jPopupMenu_factura_credito);
        jScrollPane1.setViewportView(jTable_credito);

        txt_total_credito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total_creditoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Total");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Pago de Credito");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Monto a Pagar:");

        txt_nombreC.setEnabled(false);

        txt_idC.setEnabled(false);

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Regresar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Fecha");

        jButton3.setText("Pagar");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable_Monto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable_Monto.setComponentPopupMenu(jPopupMenu_pago_credito);
        jScrollPane2.setViewportView(jTable_Monto);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Datos Credito");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Dato Pago Credito");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Total");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Saldo Restante");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_fecha_credito, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(txt_monto))
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_saldo)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addGap(82, 82, 82)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_idC, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(txt_nombreC))
                        .addGap(29, 29, 29)
                        .addComponent(jButton1)
                        .addGap(62, 62, 62))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(76, 76, 76)
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_total_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(42, 42, 42))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(65, 65, 65)))
                                .addGap(51, 51, 51))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(txt_monto_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_idC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_nombreC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_total_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txt_monto_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_fecha_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addGap(65, 65, 65))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String Prt = "";      //Extarer el valor actual de la tabla credito
        String id = txt_idC.getText(); //extraer id texto
        String suma = "select canselado from credito WHERE identidadC = '"+id+"' and canselado = false";
        try {
            Statement stCCD=cn.createStatement();
            ResultSet rsCCD=stCCD.executeQuery(suma);
            while(rsCCD.next()){
                Prt = rsCCD.getString(1);
            }
        } catch (Exception e) {
        }
        if(Prt.equals("")){
            JOptionPane.showMessageDialog(null, "El cliente no tine Credito Pendiente", "",JOptionPane.INFORMATION_MESSAGE);
        }else{
            jButton3.setEnabled(true);
            BuscarCredito(txt_idC.getText());
            BuscarPagoMonto(txt_idC.getText());
            Saldo();
        }      
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        double saldo = Double.parseDouble(txt_saldo.getText());
        
        if(saldo==0.0){
            JOptionPane.showMessageDialog(null, "El sado ya es de cero", "Alerta",JOptionPane.ERROR_MESSAGE);
        }else{
            PagarMonto();
            txt_monto_pagado.setText("");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txt_total_creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total_creditoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_creditoActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        int filaSelec = jTable_Monto.getSelectedRow();
        String idPG = "";
        Reporte re = new Reporte();
        try {
            idPG = jTable_Monto.getValueAt(filaSelec, 0).toString();
            
        } catch (Exception e) {
        }
        ActualizarPagoMonto(idPG);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        int filaSelec = jTable_credito.getSelectedRow();
        String idPG = "";
        Reporte re = new Reporte();
        try {
            idPG = jTable_credito.getValueAt(filaSelec, 0).toString();
            
        } catch (Exception e) {
        }
        ActualizarFacturaCredito(idPG);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        int filaSelec = jTable_Monto.getSelectedRow();
        
        Reporte re = new Reporte();
        try {
            String idPG = jTable_Monto.getValueAt(filaSelec, 0).toString();
            String UrlReporte = System.getProperty ("user.dir")+"\\src\\control_inventario\\JFrame\\Reportes\\FacturaCuota.jrxml";
            Map parametros = new HashMap();
            parametros.put("idPago", idPG);
            re.verReporte(UrlReporte, parametros);
            
        } catch (Exception e) {
        }
            
    }//GEN-LAST:event_jMenuItem3ActionPerformed

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
            java.util.logging.Logger.getLogger(Consultar_Credito_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Consultar_Credito_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Consultar_Credito_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Consultar_Credito_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Consultar_Credito_Cliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPopupMenu jPopupMenu_factura_credito;
    private javax.swing.JPopupMenu jPopupMenu_pago_credito;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_Monto;
    private javax.swing.JTable jTable_credito;
    private javax.swing.JTextField txt_fecha_credito;
    public static javax.swing.JTextField txt_idC;
    private javax.swing.JTextField txt_monto;
    private javax.swing.JTextField txt_monto_pagado;
    public static javax.swing.JTextField txt_nombreC;
    private javax.swing.JTextField txt_saldo;
    private javax.swing.JTextField txt_total_credito;
    // End of variables declaration//GEN-END:variables
}
