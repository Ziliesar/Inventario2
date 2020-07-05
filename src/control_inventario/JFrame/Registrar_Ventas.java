/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates SELECT id_unidad, unidad FROM tipo_unidad
 * and open the template in the editor.
 */
package control_inventario.JFrame;

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
public class Registrar_Ventas extends javax.swing.JFrame {

    /**
     * Creates new form Registrar_Ventas
     */
    DefaultTableModel tablaV;
    Object[] llenar = new Object[5];
    public Registrar_Ventas() {
        initComponents();
        txt_total_pagar.setEditable(false);
        tablaV = (DefaultTableModel) jTable_Venta_Productos.getModel();
    }
    
    void BuscarProducto(){
        control_inventario.conexion cc = new conexion();
        Connection cn = cc.conexion();
        String Buscar_descrip_producto = txt_des_venta.getText();
        String sql="";
       
        sql="SELECT pd.codigo, pd.descripcion, uni.unidad, pd.cantidad, pd.precio_compra, pd.precio_venta, apd.area_producto\n" +
            "FROM producto pd INNER JOIN tipo_unidad uni on pd.id_unidad=uni.id_unidad INNER JOIN areas_productos apd on pd.id_area_producto=apd.id_area_producto where descripcion = '"+Buscar_descrip_producto+"'";
     
        try{
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                System.err.println(rs);
                txt_codigo_venta.setText(rs.getString(1));
                txt_des_venta.setText(rs.getString(2));
                jlabelUnidad.setText(rs.getString(3));
                txt_cantidad_existente.setText(rs.getString(4));
                txt_precio_venta.setText(rs.getString(6));
            
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Registrar_Ventas.class.getName()).log(Level.SEVERE,null,ex);
        }
        
    }
    
    void AgregarProducto(){
        //--------------------------------------------------------------------------
        String Cantidad = txt_cantidad_venta.getText();
        String PrecioVenta = txt_precio_venta.getText();
        
        
        int Total1 = Integer.parseInt(Cantidad) * Integer.parseInt(PrecioVenta);
        String Total2 = Integer.toString(Total1);
        //--------------------------------------------------------------------------
            llenar[0]= txt_codigo_venta.getText();
            llenar[1]= txt_des_venta.getText();
            llenar[2]= Cantidad;
            llenar[3]= PrecioVenta;
            llenar[4]= Total2;

            tablaV.addRow(llenar);
    }
    
    public void RevisarCantidad(){
        String cantidad_existente = txt_cantidad_existente.getText();
        String cantidad_venta = txt_cantidad_venta.getText();
        
        int cantidaEx = Integer.parseInt(cantidad_existente);
        int cantidadVenta = Integer.parseInt(cantidad_venta);
        
        if(cantidadVenta > cantidaEx){
            btb_Agregar_Producto.setEnabled(false);
        }else{
            btb_Agregar_Producto.setEnabled(true);
        }
            
    }

    public void Total(){
        int fila = 0;
        int total = 0;
        int total_filas = jTable_Venta_Productos.getRowCount();
       
        try {
            for(int i = 0; i < total_filas; i++){
                fila = Integer.parseInt(jTable_Venta_Productos.getValueAt(i, 4).toString());
                total = total + fila;
            }
        } catch (Exception e) {
            System.err.println("El error es: " + e);
        }
        txt_total_pagar.setText(Integer.toString(total));
    }
    
    public void limpiar(){
        txt_cantidad_venta.setText("");
        txt_codigo_venta.setText("");
        txt_des_venta.setText("");
        txt_cantidad_existente.setText("");
        txt_precio_venta.setText("");
    }
    
    public void RegistrarDetalleVenta(){
        control_inventario.conexion cc = new conexion();
        Connection cn=cc.conexion();
            
        int total_filas = jTable_Venta_Productos.getRowCount();
        System.err.println(total_filas);
        int valorActual = 0;
        
        for(int p =0; p<total_filas; p++){
            String codigo = jTable_Venta_Productos.getValueAt(p, 0).toString();
            String des = jTable_Venta_Productos.getValueAt(p, 1).toString();
            String can = jTable_Venta_Productos.getValueAt(p, 2).toString();
            String pre = jTable_Venta_Productos.getValueAt(p, 3).toString();
            String tot = jTable_Venta_Productos.getValueAt(p, 4).toString();
            
            //----------------Restar la cantidad de producto--------------------
            int canV = Integer.parseInt(can);
            int canEx = 0;
            String consulta = "select cantidad from producto where codigo = '"+codigo+"'";
            
            try {
                Statement stcanex=cn.createStatement();
                ResultSet rscanex=stcanex.executeQuery(consulta);
                while(rscanex.next()){
                    canEx = rscanex.getInt(1);
                }
                int nuevacan = canEx - canV;
                
                PreparedStatement pst=cn.prepareStatement("UPDATE producto SET cantidad=? WHERE codigo ="+codigo+"");
                pst.setInt(1, nuevacan);
                if(pst.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null, "La cantidad han sido modificados con éxito", "Operación Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (Exception e) {
                System.out.println("Error: "+e);
            }
            //------------------------------------------------------------------
            //-----------contador pra codigo de tabla detalle factura---------
            String sql3 = "SELECT COUNT(*)+1 FROM factura";
            
            try {
                Statement val = cn.createStatement();
                ResultSet rsval = val.executeQuery(sql3);
                
                while(rsval.next()){
                    valorActual = rsval.getInt(1);
                }
            } catch (Exception e) {
                System.err.println("error al ejecutar consulta: " +e);
            }
            System.out.println("Valor actual: " + valorActual);
            

            //------------------------------------------------------------------
            //--------------Agregar Producto a Tabla detalle Factura------------
            int codigo_detalle_fac = valorActual;
            try {
                PreparedStatement insert_detalle_Fac = cn.prepareStatement("INSERT INTO detalle_factura(cog_detalle_fac, codigo, cantidadV, total) VALUES (?,?,?,?);");
                insert_detalle_Fac.setInt(1, codigo_detalle_fac);
                insert_detalle_Fac.setString(2, codigo);
                insert_detalle_Fac.setString(3, can);
                insert_detalle_Fac.setString(4, tot);
                
                int resultado_detalle_fac = insert_detalle_Fac.executeUpdate();
                if(resultado_detalle_fac>0){
                    JOptionPane.showMessageDialog(null,"Detalle Factura Registrada con exito");
                }else{
                    JOptionPane.showMessageDialog(null,"Error al agregar");
                }
                
            } catch (Exception e) {
               // System.err.println("Erro al ingresar: "+e);
            }
            //------------------------------------------------------------------
            
        }
        
        //-------------------Generar codigo Factura-------------------------
                String CodigoFac = "";
                String numFac = Integer.toString(valorActual);
                if(valorActual<=9){
                    CodigoFac = "000-000-00"+numFac;
                }
                else if(valorActual<=99){
                    CodigoFac = "000-000-0"+numFac;
                }
                else if(valorActual<=999){
                    CodigoFac = "000-000-"+numFac;
                }
                else if(valorActual<=9999){
                    CodigoFac = "000-00"+numFac;
                }
                else if(valorActual<=99999){
                    CodigoFac = "000-0"+numFac;
                }
                else if(valorActual<=999999){
                    CodigoFac = "000-"+numFac;
                }
                else if(valorActual<=9999999){
                    CodigoFac = "00"+numFac;
                }
                else if(valorActual<=99999999){
                    CodigoFac = "0"+numFac;
                }
                else if(valorActual<=999999999){
                    CodigoFac = numFac;
                }
            //------------------------------------------------------------------
            
            //----------------------Guardar Factura-----------------------------
            try {
                PreparedStatement insert_detalle_Fac = cn.prepareStatement("INSERT INTO factura(codigo_factura, cog_detalle_fac, identidad, total, fecha, hora) VALUES (?,?,?,?,?,?);");
                insert_detalle_Fac.setString(1, CodigoFac);
                insert_detalle_Fac.setString(2, numFac);
                insert_detalle_Fac.setString(3, txt_id_vendedor.getText());
                insert_detalle_Fac.setString(4, txt_total_pagar.getText());
                insert_detalle_Fac.setString(5, txt_fecha_fac.getText());
                insert_detalle_Fac.setString(6, txt_hora_fac.getText());
                
                int resultado_detalle_fac = insert_detalle_Fac.executeUpdate();
                if(resultado_detalle_fac>0){
                    JOptionPane.showMessageDialog(null,"Factura Registrada con exito");
                }else{
                    JOptionPane.showMessageDialog(null,"Error al agregar");
                }
                
            } catch (Exception e) {
                System.err.println("Erro al ingresar: "+e);
            }
            //------------------------------------------------------------------
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Venta_Productos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_codigo_venta = new javax.swing.JTextField();
        txt_des_venta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_cantidad_venta = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_precio_venta = new javax.swing.JTextField();
        btb_Agregar_Producto = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jlabelUnidad = new javax.swing.JLabel();
        txt_cantidad_existente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txt_total_pagar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_fecha_fac = new javax.swing.JTextField();
        txt_id_vendedor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_hora_fac = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable_Venta_Productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Decripcion", "Cantidad", "Precio Venta", "Total"
            }
        ));
        jScrollPane1.setViewportView(jTable_Venta_Productos);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Producto"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Codigo:");

        txt_des_venta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_des_ventaMouseExited(evt);
            }
        });
        txt_des_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_des_ventaKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("cantidad:");

        txt_cantidad_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cantidad_ventaKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Precio Venta:");

        txt_precio_venta.setEnabled(false);

        btb_Agregar_Producto.setText("Agregar Producto");
        btb_Agregar_Producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btb_Agregar_ProductoActionPerformed(evt);
            }
        });

        jButton3.setText("Limpiar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Descripcion:");

        jlabelUnidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlabelUnidad.setText("jLabel2");

        txt_cantidad_existente.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(45, 45, 45)
                        .addComponent(txt_codigo_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txt_cantidad_existente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txt_des_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_cantidad_venta, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(txt_precio_venta))
                        .addGap(18, 18, 18)
                        .addComponent(jlabelUnidad))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btb_Agregar_Producto)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_codigo_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txt_cantidad_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlabelUnidad))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_precio_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txt_des_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btb_Agregar_Producto)
                    .addComponent(jButton3)
                    .addComponent(txt_cantidad_existente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel5.setText("Venta de Productos");
        jLabel5.setToolTipText("");

        jButton1.setText("Realizar Compra");
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

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Total Lps:");

        txt_total_pagar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Otros datos"));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Nombre Vendedor");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Nombre del Cliente");

        jTextField2.setText("null");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Fecha");

        txt_fecha_fac.setText("2020-07-05");

        txt_id_vendedor.setText("1007-1998-5421");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Hora");

        txt_hora_fac.setText("16:50:20");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField1)
                        .addComponent(jLabel8)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10)
                            .addGap(29, 29, 29)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txt_id_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_fecha_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txt_hora_fac)))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_id_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_fecha_fac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_hora_fac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txt_total_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addContainerGap(359, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(62, 62, 62)
                        .addComponent(jButton1)
                        .addGap(142, 142, 142)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(298, 298, 298))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel5)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_total_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btb_Agregar_ProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btb_Agregar_ProductoActionPerformed
        // TODO add your handling code here:
        AgregarProducto();
        Total();
        limpiar();
    }//GEN-LAST:event_btb_Agregar_ProductoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Menu me = new Menu();
        me.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_des_ventaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_des_ventaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_des_ventaMouseExited

    private void txt_cantidad_ventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantidad_ventaKeyReleased
        // TODO add your handling code here:
        String cantidad_venta = txt_cantidad_venta.getText();
        if("".equals(cantidad_venta)){
            btb_Agregar_Producto.setEnabled(false);
        }else{
            btb_Agregar_Producto.setEnabled(true);
            RevisarCantidad();
        }
    }//GEN-LAST:event_txt_cantidad_ventaKeyReleased

    private void txt_des_ventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_des_ventaKeyReleased
        // TODO add your handling code here:
        BuscarProducto();
    }//GEN-LAST:event_txt_des_ventaKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        RegistrarDetalleVenta();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Registrar_Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registrar_Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registrar_Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registrar_Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registrar_Ventas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btb_Agregar_Producto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Venta_Productos;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel jlabelUnidad;
    private javax.swing.JTextField txt_cantidad_existente;
    private javax.swing.JTextField txt_cantidad_venta;
    private javax.swing.JTextField txt_codigo_venta;
    private javax.swing.JTextField txt_des_venta;
    private javax.swing.JTextField txt_fecha_fac;
    private javax.swing.JTextField txt_hora_fac;
    private javax.swing.JTextField txt_id_vendedor;
    private javax.swing.JTextField txt_precio_venta;
    private javax.swing.JTextField txt_total_pagar;
    // End of variables declaration//GEN-END:variables
}
