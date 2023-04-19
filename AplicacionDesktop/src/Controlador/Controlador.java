package Controlador;

import Modelo.Modelo;
import Test.A;
import Vistas.*;
import WSBuenosUwU.WS;
import WSBuenosUwU.WS_Service;
import controlinventario.ControlInventario;
import controlinventario.ControlInventario_Service;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.*;
import javax.swing.JTable;
import javafx.stage.Stage;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;




//Este es un cambio random

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edit_
 */
public class Controlador   {
    A WSPD = new A();
    
    private Inventario2 Inventario;
    private Venta Venta;
    private Login Login;    
    private InformeVentas InformeVentas;
    private Lealtad Lealtad;
    private Promociones Promociones;
    private MultiOpcionesDePago MultiOpcionesDePago ;
    private  Stage ventanaCarga;
    private CargaDialog cargaDialog;
    private JTable tabla;
    private static final String FILENAME = "usuarios.txt";
    private static HashMap<String, String[]> users = new HashMap<>();
    private static String currentUser = null;
    JPanel panelbusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15)); // 5px de margen
    JTextField txtBusca = new JTextField(20); // 20 es el ancho en caracteres del campo de texto
    JButton btnBusca = new JButton("Buscar");
    double costoTotal=0.0;
    WS_Service obj2=new WS_Service();
    WS WS = obj2.getWSPort();
    
    
    
    public Controlador(Inventario2 inventario, Venta venta, InformeVentas informeVentas, Lealtad lealtad, Promociones promociones,Login login, MultiOpcionesDePago multiOpcionesDePago) {
        this.Inventario = inventario;
        this.Venta = venta;
        this.InformeVentas = informeVentas;
        this.Lealtad = lealtad;
        this.Promociones = promociones;
        this.Login = login;
        this.MultiOpcionesDePago = multiOpcionesDePago;
        fecha();
        max();
        
        
        
        
        
        this.Login.btnAcceder.addActionListener(new ActionListener() {
          @Override
           public void actionPerformed(ActionEvent e) {
              boolean login1 = login(Login.txtUsuario.getText(), Login.txtContrasena.getText(), Login.cbxRol.getSelectedItem().toString());
                  //if (login1) {
              if (login1) {
                  Venta.setVisible(true);
                  Login.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                  Login.dispose();
                  Venta.lblEmpleado.setText(currentUser);
                  
                  max();
              }
           }
       });
        
        //Agregar Listeners
        
        this.Venta.btnVentas.addActionListener(new ActionListener() {
          @Override
           public void actionPerformed(ActionEvent e) {
              
                mostrarVentas();
                //agregarBuscador();
            max();
           }
       });
        this.Venta.btnInventario.addActionListener(new ActionListener() {
          @Override
           public void actionPerformed(ActionEvent e) {
                
                mostrarInventario();
                //agregarBuscador();
            
           }
       });
        
        
         btnBusca.addActionListener(new ActionListener() {
          @Override
           public void actionPerformed(ActionEvent e) {
                      buscarProducto2();
           }
       });
         
         Venta.btnAgregarProd.addActionListener(new ActionListener() {
          @Override
           public void actionPerformed(ActionEvent e) {
                     agregarProductoTemporal(); 
                      
           }
       });
         
         Venta.btnCancel.addActionListener(new ActionListener() {
          @Override
           public void actionPerformed(ActionEvent e) {
                     CancelarCompra();
                      actualizarTablaProductos();
                      
                       
           }
       });
         
         Venta.btnPagar.addActionListener(new ActionListener() {
          @Override
           public  void actionPerformed(ActionEvent e) {
                     
                      MultiOpcionesDePago.setVisible(true);
                  Venta.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                  Venta.dispose();
                       max();
           }
       });
         
         MultiOpcionesDePago.btnConfirmar.addActionListener(new ActionListener() {
          @Override
           public  void actionPerformed(ActionEvent e) {
                   WS.completarVenta(MultiOpcionesDePago.txtCliente.getText());
                   Venta.setVisible(true);
                  MultiOpcionesDePago.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                  MultiOpcionesDePago.dispose();
                  CancelarCompra2();max();
                   
                       
           }
       });
         
         MultiOpcionesDePago.btnRegresar.addActionListener(new ActionListener() {
          @Override
           public  void actionPerformed(ActionEvent e) {
                     
                  Venta.setVisible(true);
                  MultiOpcionesDePago.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                  MultiOpcionesDePago.dispose();
                       max();
           }
       });
         
     //Fin Listeners
    }
    
 //Ya quedo en soap
   public void mostrarInventario() {
       JPanel panel = new JPanel(new BorderLayout());
       panel.setBackground(Color.WHITE);
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5)); // 5px de margen
        JLabel label = new JLabel("Inventario");
       labelPanel.add(label);
        panelbusqueda.add(txtBusca);
        panelbusqueda.add(btnBusca);
        JPanel mainPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(boxLayout);
        mainPanel.add(labelPanel);
        mainPanel.add(panelbusqueda);
        panel.add(mainPanel, BorderLayout.NORTH);
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Nombre", "Descripción", "Existencias", "Precio"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnNames, 0);
        
//////////////////////AQUI SE USA EL WS ///////////////////////
        String jsonRecibido = "{'productos':["+ WS.obtenerJason()+"]}";
        JSONObject objetoJson = new JSONObject(jsonRecibido);
        JSONArray arrayProductos = objetoJson.getJSONArray("productos");

        for (int i = 0; i < arrayProductos.length(); i++) {
            JSONObject objetoProducto = arrayProductos.getJSONObject(i);
            int idProducto = objetoProducto.getInt("idProducto");
            String nombre = objetoProducto.getString("nombre");
            String descripcion = objetoProducto.getString("descripcion");
            int existencias = objetoProducto.getInt("existencias");
            double precio = objetoProducto.getDouble("precio");
            Object[] rowData = {idProducto, nombre, descripcion, existencias, precio};
            modeloTabla.addRow(rowData);
        }
       
        
       tabla = new JTable(modeloTabla);
       //tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        // Crear el renderer personalizado
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setVerticalAlignment(JLabel.TOP);

        // Establecer el renderer para la columna 2
        tabla.setDefaultRenderer(Object.class, renderer);
        tabla.getColumnModel().getColumn(2).setCellRenderer(renderer);

       
       
       tablePanel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Agregar el panel de la tabla con margen
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0)); // 5px arriba y abajo
        Inventario.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Inventario.setContentPane(panel);
        Inventario.pack();
        Inventario.setTitle("Inventario");
        Inventario.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        Inventario.setLocation(0, 0);
        Inventario.setVisible(true);
       }
//Ya quedo en soap
    public void buscarProducto2() {
        
        String json = "{'productos':["+WS.buscarInventario(txtBusca.getText())+"]}";

        JSONObject jsonObj = new JSONObject(json);
        JSONArray productosArr = jsonObj.getJSONArray("productos");

        Object[][] rows = new Object[productosArr.length()][5];
        for (int i = 0; i < productosArr.length(); i++) {
            JSONObject productoObj = productosArr.getJSONObject(i);
            rows[i][0] = productoObj.getInt("idProducto");
            rows[i][1] = productoObj.getString("nombre");
            rows[i][2] = productoObj.getString("descripcion");
            rows[i][3] = productoObj.getDouble("precio");
            rows[i][4] = productoObj.getInt("existencias");
        }

        DefaultTableModel model = new DefaultTableModel(
                rows,
                new String[]{"ID", "Nombre", "Descripción", "Precio", "Existencias"}
        );
        
        tabla.setModel(model);
    
}
//Ya quedo en soap
    public void agregarProductoTemporal() {
        double costoProducto;
        Modelo _modelo = new Modelo();
Connection conn = _modelo.getConection();
    String idProductoStr = Venta.txtIdProducto.getText();
    if (!idProductoStr.isEmpty()) {
        int idProducto = Integer.parseInt(idProductoStr);
        ////////////////Aqui WS /////////////////////////////
        String jsonRecibido = "{'productos':[" + WS.obtenerJason() + "]}";
        System.out.println("\n\n\n\n{'productos':[" + WS.obtenerJason() + "]}");
        jsonRecibido = jsonRecibido.replace("\"", "'");
        String json = jsonRecibido;
        JSONObject objetoJson = new JSONObject(json);
        JSONArray arrayProductos = objetoJson.getJSONArray("productos");
        for (int i = 0; i < arrayProductos.length(); i++) {
            JSONObject objetoProducto = arrayProductos.getJSONObject(i);
            if (objetoProducto.getInt("idProducto") == idProducto) {
                String nombre = objetoProducto.getString("nombre");
                int cantidad = 1;
                double precio = objetoProducto.getDouble("precio");
                DefaultTableModel modeloTabla = (DefaultTableModel) Venta.tblTicket.getModel();
                costoProducto = precio * cantidad;
                
                costoTotal = costoTotal + costoProducto;
                
                Object[] rowData = {idProducto, nombre, precio, cantidad, costoProducto};
 ////////////////////////////////
                WS.ejecutarVenta();
                
                DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                simbolos.setDecimalSeparator('.'); // Establecer el separador de decimales
                //simbolos.setGroupingSeparator(','); // Establecer el separador de miles

DecimalFormat df = new DecimalFormat("####.##", simbolos); // Usar los símbolos personalizados
                df.setRoundingMode(RoundingMode.DOWN);
                
                Venta.lblTotal.setText(Double.parseDouble(df.format(costoTotal))+"$");
                System.out.println(costoTotal+"$");
                 modeloTabla.addRow(rowData);
                 //double totalIva;// = actualizarTablaProductos()+ (actualizarTablaProductos()*(0.16)) ;
                
                
                return;
            }
        }
        
        JOptionPane.showMessageDialog(null, "No se encontró un producto con el ID especificado");
    } else {
        JOptionPane.showMessageDialog(null, "Debe ingresar el ID del producto");
    }
}

    public double actualizarTablaProductos() {
    
    try {
        //Obtener la conexión a la base de datos
        Modelo _modelo = new Modelo();
        Connection conn = _modelo.getConection();
        
        //Crear una consulta para obtener los datos de la tabla temporal
        String consulta = "SELECT idProducto, nombreProducto, precioProducto, cantidad FROM temp_venta";
        PreparedStatement pst = conn.prepareStatement(consulta);
        ResultSet rs = pst.executeQuery();
        
        //Limpiar los datos de la tabla
        DefaultTableModel model = (DefaultTableModel) Venta.tblTicket.getModel();
        model.setRowCount(0);
        
        //Iterar sobre los resultados y agregarlos a la tabla
        while(rs.next()) {
            int idProducto = rs.getInt("idProducto");
            String nombreProducto = rs.getString("nombreProducto");
            double precioProducto = rs.getDouble("precioProducto");
            int cantidad = rs.getInt("cantidad");
            System.out.println(precioProducto);
            System.out.println("cantidad: "+cantidad);
            
            double costoProducto = precioProducto * cantidad;
            costoTotal += costoProducto;
            Venta.lblTotal.setText(Double.toString(costoTotal));
            
            Object[] rowEmty = null;
            Object[] rowData = {idProducto, nombreProducto, precioProducto, cantidad, costoProducto};
            model.addRow(rowEmty);
            model.addRow(rowData);
            
        }
    } catch (SQLException ex) {
        System.out.println("Error al actualizar la tabla de productos: " + ex.getMessage());
    }
    
       
    return costoTotal;
}

    public  void CancelarCompra() {
         int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cancelar la compra, es un cambio IRREVERSIBLE?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
        try {
            // Establecer la conexión a la base de datos
            Modelo _modelo = new Modelo();
            Connection conn = _modelo.getConection();
            // Ejecutar la sentencia para borrar los registros de la tabla
            String query = "TRUNCATE TABLE temp_venta";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            
            // Cerrar las conexiones a la base de datos
            stmt.close();
            conn.close();
            Venta.lblTotal.setText("0.0$");
             costoTotal =0.0;
            JOptionPane.showMessageDialog(null, "Compra cancelada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
    
    public  void CancelarCompra2() {
         int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea realizar la compraI?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
        try {
            // Establecer la conexión a la base de datos
            Modelo _modelo = new Modelo();
            Connection conn = _modelo.getConection();
            // Ejecutar la sentencia para borrar los registros de la tabla
            String query = "DELETE FROM temp_venta;";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            actualizarTablaProductos();
            // Cerrar las conexiones a la base de datos
            stmt.close();
            conn.close();
            Venta.lblTotal.setText("0.0$");
           costoTotal =0.0;
            JOptionPane.showMessageDialog(null, "Compra Realizada con exito", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
    
    public void fecha() {
          Thread t = new Thread(() -> {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss");

            while (true) {
                
                LocalDateTime fechaHoraActual = LocalDateTime.now();
                String fechaHoraFormateada = fechaHoraActual.format(formato);

                SwingUtilities.invokeLater(() -> {
                    Venta.lblFecha.setText(fechaHoraFormateada);
                   
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }
    
   public boolean login(String username, String password, String role) {
       
        Modelo modelo = new Modelo();
        Connection conn = modelo.getConection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
             
            
            String query = "SELECT * FROM trabajadores WHERE nombre=? AND Contrasena=? AND rol=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            rs = stmt.executeQuery();
            if (rs.next()) {
                // Si el usuario y contraseña son correctos y tienen el rol correcto, se muestra la ventana de ventas
                currentUser = username;
                Venta.setVisible(true);
                return true;
            } else {
                // Si los datos son incorrectos, se muestra un mensaje de error
               JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);

                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos Por favor verifique su conexion a internet", "Error", JOptionPane.ERROR_MESSAGE);

            //System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            return false;
        } finally {
            // Se cierran los recursos de base de datos
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {}
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
    
    // Función para obtener el usuario actual
    public static String getCurrentUser() {
        return currentUser;
    }
    
    public void max() {
        Modelo modelo = new Modelo();
Connection conn = modelo.getConection();
int registro_mas_alto;
try {
    Statement stmt = conn.createStatement();
    String sql = "SELECT MAX(id) AS registro_mas_alto FROM ticket";
    ResultSet rs = stmt.executeQuery(sql);
    if (rs.next()) {
         registro_mas_alto = rs.getInt("registro_mas_alto");
        registro_mas_alto =+1;
        System.out.println(registro_mas_alto);
        Venta.lblTurno.setText(String.valueOf(registro_mas_alto));
        
    }
} catch (SQLException e) {
    e.printStackTrace();
}
    ;}
    
    
    public void mostrarVentas() {
       //mostrarCarga("Cargando Productos");
       
       System.out.println("cargando...");
       ControlInventario_Service obj2=new ControlInventario_Service();
       ControlInventario WSPDF = obj2.getControlInventarioPort();
       JPanel panel = new JPanel(new BorderLayout());
       panel.setBackground(Color.WHITE);
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5)); // 5px de margen
        JLabel label = new JLabel("Ventas");
       
               
        labelPanel.add(label);
        
        
        
      

        
        JPanel mainPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(boxLayout);

        // Agregar los paneles existentes al nuevo panel
        mainPanel.add(labelPanel);
        mainPanel.add(panelbusqueda);

// Agregar el nuevo panel al panel principal del inventario
panel.add(mainPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"id", "idProducto", "nombreProducto", "precioProducto","cantidad","cliente","fecha"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnNames, 0);;
        
               String jsonRecibido = "{'ticket':["+ WSPD.obtenerProductosJSONVentas()+"]}";
       jsonRecibido = jsonRecibido.replace("\"", "'");
       System.out.println(jsonRecibido);
       String json = jsonRecibido;
       JSONObject objetoJson = new JSONObject(json);
       JSONArray arrayProductos = objetoJson.getJSONArray("ticket");
       for (int i = 0; i < arrayProductos.length(); i++) {
           JSONObject objetoProducto = arrayProductos.getJSONObject(i);
           int id = objetoProducto.getInt("id");
           int idProducto = objetoProducto.getInt("idProducto");
           String nombreProducto = objetoProducto.getString("nombreProducto");
           double precioProducto = objetoProducto.getInt("precioProducto");
           int cantidad = objetoProducto.getInt("cantidad");
           String cliente = objetoProducto.getString("cliente");
           String fecha = objetoProducto.getString("fecha");
           Object[] rowData = {id, idProducto,nombreProducto,precioProducto,cantidad,cliente,fecha};
           modeloTabla.addRow(rowData);
           System.out.println(WSPDF);
       }
       
        
       tabla = new JTable(modeloTabla);
       //tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        // Crear el renderer personalizado
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setVerticalAlignment(JLabel.TOP);

        // Establecer el renderer para la columna 2
        tabla.setDefaultRenderer(Object.class, renderer);
        tabla.getColumnModel().getColumn(2).setCellRenderer(renderer);

       
       
       tablePanel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Agregar el panel de la tabla con margen
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0)); // 5px arriba y abajo
        Inventario.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Inventario.setContentPane(panel);
        Inventario.pack();
        Inventario.setTitle("Informe de Ventas");
        Inventario.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        Inventario.setLocation(0, 0);
        Inventario.setVisible(true);
       
       
        //cerrarCarga();
    
}
    
    
  
     public void CuentaAbierta2() {
        
    }
      public void CuentaAbierta3() {
        
    }
       public void CuentaAbierta4() {
        
    }
        public void CuentaAbierta5() {
        
    }
        
}
