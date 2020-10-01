/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates SELECT id_unidad, unidad FROM tipo_unidad
 * and open the template in the editor.
 */
package control_inventario.JFrame;

import control_inventario.Control_Inventario;
import static control_inventario.JFrame.Consultar_Credito_Cliente.txt_idC;
import static control_inventario.JFrame.calendario.codigoFac;
import control_inventario.conexion;
import control_inventario.conexionH;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
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
    Object[] llenar = new Object[6];
    String nombreU;
  public static  String usuarioRE;
    public static  String horaS, minutos, segundos;
    public static  int horaSA, minutosA, segundosA;
  //METODO INICIAL
    public static String fecha;
    public static String hora;
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
  public static String hora(){
      Calendar calendario = Calendar.getInstance();
      
      horaSA =calendario.get(Calendar.HOUR_OF_DAY);
      horaS=Integer.toString(horaSA);
      if(horaS.equals("0")||horaS.equals("1")||horaS.equals("2")||horaS.equals("3")||horaS.equals("4")||horaS.equals("5")||horaS.equals("6")||
                horaS.equals("7")||horaS.equals("8")||horaS.equals("9")){
            horaS="0"+horaS;
        }
      
      minutosA = calendario.get(Calendar.MINUTE);
      minutos=Integer.toString(minutosA);
      if(minutos.equals("0")||minutos.equals("1")||minutos.equals("2")||minutos.equals("3")||minutos.equals("4")||minutos.equals("5")||minutos.equals("6")||
                minutos.equals("7")||minutos.equals("8")||minutos.equals("9")){
            minutos="0"+minutos;
        }
      
      segundosA = calendario.get(Calendar.SECOND);
      segundos=Integer.toString(segundosA);
      if(segundos.equals("0")||segundos.equals("1")||segundos.equals("2")||segundos.equals("3")||segundos.equals("4")||segundos.equals("5")||segundos.equals("6")||
                segundos.equals("7")||segundos.equals("8")||segundos.equals("9")){
            segundos="0"+segundos;
        }
      hora=horaS+":"+minutos+":"+segundos;
      //System.out.println("HORA: "+hora);
      
      
      
      
      return hora;
  }
  //TIEMPO
 
  
  //FIN TIEMPO
  
  public static void agregarH(){
            txt_hora_fac.setText(hora());
            txt_fecha_fac.setText(fecha());
        }
    public Registrar_Ventas() {
        
        
        initComponents();
        txt_total_pagar.setEditable(true);
        tablaV = (DefaultTableModel) jTable_Venta_Productos.getModel();
        
       // OcultarColumnaTabla();
        
        txt_fecha_fac.setEditable(false);
        txt_hora_fac.setEditable(false);
        
        agregarH();
        
        
        
        
        InicioSesion llam23=new InicioSesion();
       usuarioRE=llam23.conseguirN();
        //OBTENER IDENTIDAD
        control_inventario.conexion cc8 = new conexion(); //where nombre_user='"+user+"' and '"+contra+"'
        Connection cn8 = cc8.conexion();
        String identBD8="SELECT identidad FROM usuario where nombre_user='"+usuarioRE+"'";  
        String identBD8a="HHH";
        try {
            
            Statement st8=cn8.createStatement();
            ResultSet rs8=st8.executeQuery(identBD8);
            
            while(rs8.next()){
                identBD8a = rs8.getString(1);  
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        //OBTENER NOMBRES
        control_inventario.conexion cc9 = new conexion(); //where nombre_user='"+user+"' and '"+contra+"'
        Connection cn9 = cc9.conexion();
        String nombreBD9="SELECT nombres FROM personal where identidad='"+identBD8a+"'";  
        String nombreBD9a="HHH";
        try {
            
            Statement st9=cn9.createStatement();
            ResultSet rs9=st9.executeQuery(nombreBD9);
            
            while(rs9.next()){
                nombreBD9a = rs9.getString(1);  
            }
            
          
        } catch (SQLException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        //OBTENER APELLIDOS
        control_inventario.conexion cc10 = new conexion(); //where nombre_user='"+user+"' and '"+contra+"'
        Connection cn10 = cc10.conexion();
        String apellBD10="SELECT apellidos FROM personal where identidad='"+identBD8a+"'";  
        String apellBD10a="HHH";
        try {
            
            Statement st10=cn10.createStatement();
            ResultSet rs10=st10.executeQuery(apellBD10);
            
            while(rs10.next()){
                apellBD10a = rs10.getString(1);  
            }
            
          
        } catch (SQLException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        
        txt_id_vendedor.setText(identBD8a);
        recibir1.setText(nombreBD9a+" "+apellBD10a);
        txt_id_vendedor.setEditable(false);
        recibir1.setEditable(false);
        
        
        
        this.setTitle("Registrar ventas");
        Image ico= Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/icono.png"));
        this.setIconImage(ico);
        this.setResizable(false);
        setLocationRelativeTo(null);
        Color colorAzulF=new Color(20, 63, 82);//AZULDE FONDO
        Color colorGrisL=new Color(215, 225, 229);//LETRAS
        
        
        java.util.Timer timer=new java.util.Timer();
        
        TimerTask tarea=new TimerTask(){
            @Override
            public void run() {                                
                hora();
                agregarH();
            }            
        };
        timer.schedule(tarea, 0, 1000);
        
    }
    
    void BuscarProducto(){
        txt_cantidad_venta.setText("1");
        control_inventario.conexion cc = new conexion();
        Connection cn = cc.conexion();
        String Buscar_cod_producto = txt_codigo_venta.getText();
        int tamañoCodigo=Buscar_cod_producto.length();
        
        String sql="";
       
        sql="SELECT pd.codigo, pd.descripcion, uni.unidad, pd.cantidad, pd.precio_compra, pd.precio_venta, apd.area_producto\n" +
            "FROM producto pd INNER JOIN tipo_unidad uni on pd.id_unidad=uni.id_unidad INNER JOIN areas_productos apd on pd.id_area_producto=apd.id_area_producto where codigo = '"+Buscar_cod_producto+"'";
     
        try{
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
              //  System.err.println(rs);
                txt_codigo_venta.setText(rs.getString(1));
                txt_des_venta.setText(rs.getString(2));
                jlabelUnidad.setText(rs.getString(3));
                txt_cantidad_existente.setText(rs.getString(4));
                txt_precio_venta.setText(rs.getString(6));
            
            }
            
        }catch(SQLException ex){
            Logger.getLogger(Registrar_Ventas.class.getName()).log(Level.SEVERE,null,ex);
        }
       
            if(tamañoCodigo>7){
                EvaluarProductoRepetido();
                txt_codigo_venta.setText("");
                txt_des_venta.setText("");
                txt_cantidad_venta.setText("");
            }
        
    }
    
    public static String existenciaBD;
    void AgregarProducto(){
        //--------------------------------------------------------------------------
        String Cantidad = txt_cantidad_venta.getText();
        String PrecioVenta = txt_precio_venta.getText();
        String CantidadEx = txt_cantidad_existente.getText();
        
        int Total1 = Integer.parseInt(Cantidad) * Integer.parseInt(PrecioVenta);
        String Total2 = Integer.toString(Total1);
        //--------------------------------------------------------------------------
            llenar[0]= txt_codigo_venta.getText();
            llenar[1]= txt_des_venta.getText();
            llenar[2]= Cantidad;
            llenar[3]= PrecioVenta;
            llenar[4]= Total2;
            llenar[5]= CantidadEx;

            tablaV.addRow(llenar);
            //RESTAR EXISTENCIAS
            String codigo=txt_codigo_venta.getText();
        //    System.out.println(codigo);
            
             control_inventario.conexion cc8 = new conexion(); //where nombre_user='"+user+"' and '"+contra+"'
        Connection cn8 = cc8.conexion();
        String dbCanT="SELECT cantidadTem FROM producto where codigo='"+codigo+"'";  
        existenciaBD="HHH";
        try {           
            Statement st8=cn8.createStatement();
            ResultSet rs8=st8.executeQuery(dbCanT);            
            while(rs8.next()){
                existenciaBD = rs8.getString(1);  
            }
        } catch (SQLException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        } 
      //  System.out.println("EXISTENCIAAAAAS BBBBDDDDD: "+existenciaBD);   
        
        String compra=txt_cantidad_venta.getText();
        
        int existenciaN=Integer.parseInt(existenciaBD);
        int compraN=Integer.parseInt(compra);
        
        existenciaN=existenciaN-compraN;
        if(existenciaN>0){
            
      
        control_inventario.conexion ccV = new conexion(); //where nombre_user='"+user+"' and '"+contra+"'
        Connection cnV = ccV.conexion();
        try {
            com.mysql.jdbc.PreparedStatement pstH = (com.mysql.jdbc.PreparedStatement) cnV.prepareStatement("UPDATE producto SET cantidadTem='"+existenciaN+"' WHERE codigo='"+codigo+"'");
        pstH.executeUpdate();
        } catch (Exception e) {
        }
        }else{
            existenciaN=existenciaN+compraN;
        }  
    }
    //FIN AGREGAR PRODUCTO
    public void RevisarCantidad(){
       // System.out.println("Ingresando revisar valor");
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
      //      System.err.println("El error es: " + e);
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
     //   System.err.println(total_filas);
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
      //          System.out.println("Error: "+e);
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
        //        System.err.println("error al ejecutar consulta: " +e);
            }
         //   System.out.println("Valor actual: " + valorActual);
            

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
                if(jRadioButton1.isSelected()){
                    PreparedStatement insert_detalle_Fac = cn.prepareStatement("INSERT INTO credito(codigo_factura, cog_detalle_fac, identidad, total, fecha, hora, identidadC, canselado) VALUES (?,?,?,?,?,?,?,?);");
                    insert_detalle_Fac.setString(1, CodigoFac);
                    insert_detalle_Fac.setString(2, numFac);
                    insert_detalle_Fac.setString(3, txt_id_vendedor.getText());
                    
                    insert_detalle_Fac.setString(4, txt_total_pagar.getText());
                    insert_detalle_Fac.setString(5, txt_fecha_fac.getText());
                    insert_detalle_Fac.setString(6, txt_hora_fac.getText());
                    
                    insert_detalle_Fac.setString(7, txt_rtn_cliente.getText());
                    insert_detalle_Fac.setBoolean(8, false);

                    int resultado_detalle_fac = insert_detalle_Fac.executeUpdate();
                    if(resultado_detalle_fac>0){
                        JOptionPane.showMessageDialog(null,"Factura con Credito Registrada con exito");
                    }else{
                        JOptionPane.showMessageDialog(null,"Error al agregar");
                    }
                    
//------------------------------------------Ingresar Total Credito--------------------------------------
                    
                    
                    String Prt = "";      //Extarer el valor actual de la tabla credito
                    
                    
                    String id = txt_rtn_cliente.getText(); //extraer id texto
                    String suma = "select SUM(total) from credito WHERE identidadC = '"+id+"'";
                    try {
                        Statement stCCD=cn.createStatement();
                        ResultSet rsCCD=stCCD.executeQuery(suma);
                        while(rsCCD.next()){
                            Prt = rsCCD.getString(1);
                        }
                    } catch (Exception e) {
                    }
                    
                    System.out.println("total credito: "+Prt);
                    System.out.println("ide: "+id);
                    
                    //------------Verificar si el Cliente ya tiene un Total vredito-------------------
                    
                    int IDCExistente = 0;      //Extarer el valor actual de la tabla credito
                    
                    String BuscarIDCL = "SELECT COUNT(identidadC) FROM total_credito WHERE identidadC = '"+id+"'";
                    try {
                        Statement stCCD=cn.createStatement();
                        ResultSet rsCCD=stCCD.executeQuery(BuscarIDCL);
                        while(rsCCD.next()){
                            IDCExistente = rsCCD.getInt(1);
                        }
                    } catch (Exception e) {
                    }
                    
                    if(IDCExistente==0){
                        //----------------------------registar total credito-------------------------------
                        PreparedStatement insert_total_credito = cn.prepareStatement("INSERT INTO total_credito(identidadC, total) VALUES (?,?);");
                        insert_total_credito.setString(1, id);
                        insert_total_credito.setString(2, Prt);
                        
                        int resultado_total_credito = insert_total_credito.executeUpdate();
                        if(resultado_total_credito>0){
                            JOptionPane.showMessageDialog(null,"Total Credito Registrada con exito");
                        }else{
                            JOptionPane.showMessageDialog(null,"Error al agregar");
                        }
                        
                    } else {
                        //----------------------------adctualizar total credito-----------------------------

                        String Ssql = "UPDATE total_credito SET total=?  WHERE identidadC =?";

                        PreparedStatement prest;
                        try {
                            prest = cn.prepareStatement(Ssql);

                            prest.setString(1, Prt);
                            prest.setString(2, id);
                            
                            if(prest.executeUpdate() > 0){
        
                                JOptionPane.showMessageDialog(null, "Total cresito actualizado", "Operación Exitosa",JOptionPane.INFORMATION_MESSAGE);
                                
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(Consultar_Credito_Cliente.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        System.out.println("Actualizar");
                    }
//------------------------------------------------------------------------------------------------------
                    
                }else{
                    PreparedStatement insert_detalle_Fac = cn.prepareStatement("INSERT INTO factura(codigo_factura, cog_detalle_fac, identidad, total, fecha, hora) VALUES (?,?,?,?,?,?);");
                    insert_detalle_Fac.setString(1, CodigoFac);
                    insert_detalle_Fac.setString(2, numFac);
                    insert_detalle_Fac.setString(3, txt_id_vendedor.getText());
                    //insert_detalle_Fac.setString(4, txt_rtn_cliente.getText());
                    insert_detalle_Fac.setString(4, txt_total_pagar.getText());
                    insert_detalle_Fac.setString(5, txt_fecha_fac.getText());
                    insert_detalle_Fac.setString(6, txt_hora_fac.getText());

                    int resultado_detalle_fac = insert_detalle_Fac.executeUpdate();
                    if(resultado_detalle_fac>0){
                        JOptionPane.showMessageDialog(null,"Factura Registrada con exito");
                    }else{
                        JOptionPane.showMessageDialog(null,"Error al agregar");
                    }
                }
                
            } catch (Exception e) {
         //       System.err.println("Erro al ingresar: "+e);
            }
            
           //--------------------
            //------------------------------------------------------------------
            tablaV = (DefaultTableModel) jTable_Venta_Productos.getModel();
    }
    
    public void EvaluarProductoRepetido(){
      //  System.out.println("Ingresando revisar codigo producto");
        String codigoBu = txt_codigo_venta.getText();
        int igual = 0; int canex = Integer.parseInt(txt_cantidad_existente.getText()); int canTv = 0;

        AgregarProducto();
        Total();
        limpiar();
        
            
            int total_filas2 = jTable_Venta_Productos.getRowCount();
            System.out.println("total filas: "+total_filas2);
            int UltimaF = total_filas2 -1;
            for(int a =0; a<total_filas2; a++){
            String codigo = jTable_Venta_Productos.getValueAt(a, 0).toString();
            
            if(codigoBu.equals(codigo)){
                
                String canv = jTable_Venta_Productos.getValueAt(a, 2).toString();
                int suma_canv = Integer.parseInt(canv);
                canTv = suma_canv + canTv;
                /*igual = 1;
                a=total_filas+1;*/
            }else{
                igual = 0;
            }
            System.out.println("Suma cantidad venta: "+canTv);
            if(canTv>canex){
                JOptionPane.showMessageDialog(null, "La cantida ingresada excede el inventario");
                tablaV.removeRow(UltimaF);
            }
        }
    }
    
    public void EvaluarProductoRepetido2(String f , int l, int can, int pre, int filas, int canvieja){
      //  System.out.println("Ingresando revisar codigo producto");
        String codigoBu = f;
        int igual = 0; int canex = l; int canTv = 0, total = 0;
        
            
            int total_filas2 = jTable_Venta_Productos.getRowCount();
            System.out.println("total filas: "+total_filas2);
            int UltimaF = total_filas2 -1;
            
            for(int a =0; a<total_filas2; a++){
            String codigo = jTable_Venta_Productos.getValueAt(a, 0).toString();
            
            if(codigoBu.equals(codigo)){
                
                String canv = jTable_Venta_Productos.getValueAt(a, 2).toString();
                int suma_canv = Integer.parseInt(canv);
                canTv = suma_canv + canTv;
                /*igual = 1;
                a=total_filas+1;*/
            }else{
                igual = 0;
            }
            System.out.println("Suma cantidad venta: "+canTv);
            if(canTv>canex){
                int TotalViejo = canvieja * pre;
                JOptionPane.showMessageDialog(null, "La cantida ingresada excede el inventario");
                jTable_Venta_Productos.setValueAt(canvieja, filas, 2);
                jTable_Venta_Productos.setValueAt(TotalViejo, filas, 4);
                Total();
            }
            else{
                total = can * pre;
                System.out.println("Total tabla: "+total);

                jTable_Venta_Productos.setValueAt(total, filas, 4);
                Total();
            }
        }
    }
    
    public void SumaCantidadTabla(){
        int filaSelec = jTable_Venta_Productos.getSelectedRow(); 
        String CanT = "", PreT = "", CodigoT="", CanEx="", TotalPD=""; 
        int canta = 0, preta = 0, cantaex = 0, totalta=0, cantidadvieja=0;
        
        CodigoT = jTable_Venta_Productos.getValueAt(filaSelec, 0).toString();
        CanT = jTable_Venta_Productos.getValueAt(filaSelec, 2).toString();
        PreT = jTable_Venta_Productos.getValueAt(filaSelec, 3).toString();
        TotalPD = jTable_Venta_Productos.getValueAt(filaSelec, 4).toString();
        CanEx = jTable_Venta_Productos.getValueAt(filaSelec, 5).toString();
        
        canta = Integer.parseInt(CanT);
        preta = Integer.parseInt(PreT);
        cantaex = Integer.parseInt(CanEx);
        totalta = Integer.parseInt(TotalPD);
        
        cantidadvieja = totalta / preta;
        if(canta>cantaex){
            JOptionPane.showMessageDialog(null, "La cantida ingresada excede el inventario");
            
            jTable_Venta_Productos.setValueAt(cantidadvieja, filaSelec, 2);
        }else{
            System.out.println("canvieja: "+cantidadvieja);
            EvaluarProductoRepetido2(CodigoT, cantaex, canta, preta, filaSelec, cantidadvieja);
        }
    }
    
    private  void OcultarColumnaTabla(){
            //Oculta la primera columna de la tabla
       this.jTable_Venta_Productos.getColumnModel().getColumn(5).setMaxWidth(0);
       this.jTable_Venta_Productos.getColumnModel().getColumn(5).setMinWidth(0);
       this.jTable_Venta_Productos. getColumnModel().getColumn(5).setPreferredWidth(0);
      
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        descuento = new javax.swing.ButtonGroup();
        credito = new javax.swing.ButtonGroup();
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
        jLabel16 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txt_total_pagar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        recibir1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_nombre_cliente = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_fecha_fac = new javax.swing.JTextField();
        txt_id_vendedor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_hora_fac = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_rtn_cliente = new javax.swing.JTextField();
        BTN_buscar_cliente = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jrb_si = new javax.swing.JRadioButton();
        jrb_no = new javax.swing.JRadioButton();
        jButton4 = new javax.swing.JButton();
        txt_descuento = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_Venta_Productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Decripción", "Cantidad", "Precio venta", "Total", "Existencia"
            }
        ));
        jTable_Venta_Productos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable_Venta_ProductosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_Venta_Productos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 378, 533, 253));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Producto"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Código:");

        txt_codigo_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_codigo_ventaKeyReleased(evt);
            }
        });

        txt_des_venta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_des_ventaMouseExited(evt);
            }
        });
        txt_des_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_des_ventaActionPerformed(evt);
            }
        });
        txt_des_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_des_ventaKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Cantidad:");

        txt_cantidad_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cantidad_ventaKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Precio venta:");

        txt_precio_venta.setEnabled(false);

        btb_Agregar_Producto.setText("Agregar producto");
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
        jLabel6.setText("Descripción:");

        jlabelUnidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_cantidad_existente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_cantidad_existente.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Existencias:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(45, 45, 45)
                        .addComponent(txt_codigo_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_des_venta)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_cantidad_existente, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btb_Agregar_Producto)
                        .addComponent(jButton3)
                        .addComponent(txt_cantidad_existente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel16))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 122, -1, -1));

        jLabel5.setBackground(new java.awt.Color(215, 225, 229));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(240, 240, 240));
        jLabel5.setText("Venta de productos");
        jLabel5.setToolTipText("");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, -1, -1));

        jButton1.setText("Realizar compra");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 323, -1, -1));

        jButton2.setText("Regresar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 323, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(215, 225, 229));
        jLabel2.setText("Total Lps:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 650, -1, -1));

        txt_total_pagar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        getContentPane().add(txt_total_pagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(447, 651, 120, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Otros datos"));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Datos del vendedor");

        recibir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recibir1ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Crédito");

        txt_nombre_cliente.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Fecha");

        txt_fecha_fac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fecha_facActionPerformed(evt);
            }
        });

        txt_id_vendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_vendedorActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Hora");

        txt_hora_fac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hora_facActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Nombre:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Identidad:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Nombre:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Identidad:");

        txt_rtn_cliente.setEnabled(false);
        txt_rtn_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rtn_clienteActionPerformed(evt);
            }
        });

        BTN_buscar_cliente.setText("Buscar Cliente");
        BTN_buscar_cliente.setEnabled(false);
        BTN_buscar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_buscar_clienteActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Descuento");

        descuento.add(jrb_si);
        jrb_si.setText("SI");
        jrb_si.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_siActionPerformed(evt);
            }
        });

        descuento.add(jrb_no);
        jrb_no.setText("NO");
        jrb_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_noActionPerformed(evt);
            }
        });

        jButton4.setText("Aplicar");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        txt_descuento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_descuento.setEnabled(false);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("cantiad");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("%");

        credito.add(jRadioButton1);
        jRadioButton1.setText("SI");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        credito.add(jRadioButton2);
        jRadioButton2.setText("NO");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 207, Short.MAX_VALUE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(106, 106, 106)
                                .addComponent(jLabel10))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_fecha_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(txt_hora_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14))
                                .addGap(13, 13, 13)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_nombre_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txt_rtn_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                                .addComponent(recibir1)
                                                .addComponent(txt_id_vendedor))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jRadioButton1)
                                                .addGap(18, 18, 18)
                                                .addComponent(jRadioButton2))))))
                            .addComponent(jLabel8)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_descuento)
                                    .addComponent(jrb_si, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jrb_no)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(BTN_buscar_cliente)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_id_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(recibir1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_rtn_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nombre_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addComponent(BTN_buscar_cliente)
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jrb_si)
                    .addComponent(jrb_no))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jButton4))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_fecha_fac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_hora_fac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 120, 320, 510));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_inventario/JFrame/fondoVenta.JPG"))); // NOI18N
        jLabel11.setText("jLabel11");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        limpiar();
        btb_Agregar_Producto.setEnabled(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btb_Agregar_ProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btb_Agregar_ProductoActionPerformed
        String canv = txt_cantidad_venta.getText();
        if(("".equals(canv)) || ("0".equals(canv))){
            JOptionPane.showMessageDialog(null,"Por favor ingrese una cantidad");
        }else{
            EvaluarProductoRepetido();
        }
        
        
        
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
        
    }//GEN-LAST:event_txt_des_ventaKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        RegistrarDetalleVenta();
        int total_filas = jTable_Venta_Productos.getRowCount();
        total_filas=total_filas-1;
        //System.out.println("Filas: "+total_filas);
        for(int p =total_filas; p>=0; p--){
            tablaV.removeRow(p);
            //System.out.println("Limpiar fila: "+p);
           
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_codigo_ventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigo_ventaKeyReleased
        BuscarProducto();
        
    }//GEN-LAST:event_txt_codigo_ventaKeyReleased

    private void txt_id_vendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_vendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_vendedorActionPerformed

    private void recibir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recibir1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_recibir1ActionPerformed

    private void txt_rtn_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rtn_clienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rtn_clienteActionPerformed

    private void txt_hora_facActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hora_facActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hora_facActionPerformed

    private void BTN_buscar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_buscar_clienteActionPerformed
        // TODO add your handling code here:
        Registrar_Cliente regiscl = new Registrar_Cliente();
        regiscl.setVisible(true);
    }//GEN-LAST:event_BTN_buscar_clienteActionPerformed

    private void txt_des_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_des_ventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_des_ventaActionPerformed

    private void jTable_Venta_ProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable_Venta_ProductosKeyReleased
        // TODO add your handling code here:
        SumaCantidadTabla();
    }//GEN-LAST:event_jTable_Venta_ProductosKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        String TOTAL = txt_total_pagar.getText();
        double total = Double.parseDouble(TOTAL);
        double descuento = Double.parseDouble(txt_descuento.getText());
        
        double descuento2 = descuento / 100;
        
        double subdescuento = total * descuento2;
        
        int resp = JOptionPane.showConfirmDialog(null, "El Descuento es de: Lps. "+subdescuento+" ¿Esta seguro?", "Alerta!", JOptionPane.YES_NO_OPTION);
        double totaldescuento = 0;
        if(resp==0){
            totaldescuento = total - subdescuento;
            txt_total_pagar.setText(totaldescuento+"");
        }
        
        
        
        System.out.println("respuesta: " + resp);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txt_fecha_facActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fecha_facActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fecha_facActionPerformed

    private void jrb_siActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb_siActionPerformed
        // TODO add your handling code here:
        if(jrb_si.isSelected()){
            jButton4.setEnabled(true);
            txt_descuento.setEnabled(true);
        }
    }//GEN-LAST:event_jrb_siActionPerformed

    private void jrb_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb_noActionPerformed
        // TODO add your handling code here:
        if(jrb_no.isSelected()){
            jButton4.setEnabled(false);
            txt_descuento.setEnabled(false);
            txt_descuento.setText(" ");
        }
    }//GEN-LAST:event_jrb_noActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton1.isSelected()){
            txt_rtn_cliente.setEnabled(true);
            txt_nombre_cliente.setEnabled(true);
            BTN_buscar_cliente.setEnabled(true);
        }
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton2.isSelected()){
            
            txt_rtn_cliente.setEnabled(false);
            txt_rtn_cliente.setText(" ");
            txt_nombre_cliente.setEnabled(false);
            txt_nombre_cliente.setText(" ");
            BTN_buscar_cliente.setEnabled(false);
        
        }
    }//GEN-LAST:event_jRadioButton2ActionPerformed

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
    private javax.swing.JButton BTN_buscar_cliente;
    private javax.swing.JButton btb_Agregar_Producto;
    private javax.swing.ButtonGroup credito;
    private javax.swing.ButtonGroup descuento;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Venta_Productos;
    private javax.swing.JLabel jlabelUnidad;
    private javax.swing.JRadioButton jrb_no;
    private javax.swing.JRadioButton jrb_si;
    public static javax.swing.JTextField recibir1;
    private javax.swing.JTextField txt_cantidad_existente;
    private javax.swing.JTextField txt_cantidad_venta;
    private javax.swing.JTextField txt_codigo_venta;
    private javax.swing.JTextField txt_des_venta;
    private javax.swing.JTextField txt_descuento;
    public static javax.swing.JTextField txt_fecha_fac;
    public static javax.swing.JTextField txt_hora_fac;
    private javax.swing.JTextField txt_id_vendedor;
    public static javax.swing.JTextField txt_nombre_cliente;
    private javax.swing.JTextField txt_precio_venta;
    public static javax.swing.JTextField txt_rtn_cliente;
    private javax.swing.JTextField txt_total_pagar;
    // End of variables declaration//GEN-END:variables
}
