package Controlador;

import Modelo.Modelo;
import Test.A;
import Vistas.*;
import controlinventario.ControlInventario;
import controlinventario.ControlInventario_Service;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private  Stage ventanaCarga;
    private CargaDialog cargaDialog;
    private JTable tabla;
    private static final String FILENAME = "usuarios.txt";
    private static HashMap<String, String[]> users = new HashMap<>();
    private static String currentUser = null;
    JPanel panelbusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15)); // 5px de margen
    JTextField txtBusca = new JTextField(20); // 20 es el ancho en caracteres del campo de texto
    JButton btnBusca = new JButton("Buscar");
    double costoTotal;
    
    
    
    public Controlador(Inventario2 inventario, Venta venta, InformeVentas informeVentas, Lealtad lealtad, Promociones promociones,Login login) {
        this.Inventario = inventario;
        this.Venta = venta;
        this.InformeVentas = informeVentas;
        this.Lealtad = lealtad;
        this.Promociones = promociones;
        this.Login = login;
        fecha();
        
        
        
        
        
        this.Login.btnAcceder.addActionListener(new ActionListener() {
          @Override
           public void actionPerformed(ActionEvent e) {
              boolean login1 = login(Login.txtUsuario.getText(), Login.txtContrasena.getText(), Login.cbxRol.getSelectedItem().toString());
                  if (login1) {
                  Venta.setVisible(true);
                  Login.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                  Login.dispose();
                  Venta.lblEmpleado.setText(currentUser);
                  Venta.lblOperacion.setText(max());
              }
           }
       });
        
        //Agregar Listeners
        
        this.Venta.btnVentas.addActionListener(new ActionListener() {
          @Override
           public void actionPerformed(ActionEvent e) {
                
                mostrarVentas();
                //agregarBuscador();
            
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
         
         
         
         
         
         
         
         
        
         /*Inventario.btnCerrar.addMouseListener(new MouseAdapter() {
         @Override
      public void mouseClicked(MouseEvent e) {
        Inventario.setVisible(false);
                Inventario.setFocusableWindowState(false);
                Venta.setVisible(true);
        // Realiza la acción que deseas cuando se hace clic en el JLabel
      }
    });
        */
    }
    
    
   public void mostrarInventario() {
       //mostrarCarga("Cargando Productos");
       
       System.out.println("cargando...");
       ControlInventario_Service obj2=new ControlInventario_Service();
       ControlInventario WSPDF = obj2.getControlInventarioPort();
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

        // Agregar los paneles existentes al nuevo panel
        mainPanel.add(labelPanel);
        mainPanel.add(panelbusqueda);

// Agregar el nuevo panel al panel principal del inventario
panel.add(mainPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Nombre", "Descripción", "Existencias", "Precio"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnNames, 0);;
        
               String jsonRecibido = "{'productos':["+ WSPD.obtenerProductosJSON()+"]}";
       jsonRecibido = jsonRecibido.replace("\"", "'");
       System.out.println(jsonRecibido);
       String json = jsonRecibido;
       JSONObject objetoJson = new JSONObject(json);
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
        Inventario.setTitle("Inventario");
        Inventario.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        Inventario.setLocation(0, 0);
        Inventario.setVisible(true);
       
       
        //cerrarCarga();
    
}
  
    public void mostrarCarga(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            cargaDialog = new CargaDialog(mensaje);
            cargaDialog.setVisible(true);
        });
    }
    
   public void circulo2() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Circle Panel Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            CargaDialog panel = new CargaDialog("Loading...");
            frame.add(panel, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
    }
    public void cerrarCarga() {
        SwingUtilities.invokeLater(() -> {
           // cargaDialog.dispose();
        });
    }
    
    public void buscarProducto() {
    // Obtener el texto del JTextField
    String textoBusqueda = txtBusca.getText();
    
    // Realizar la consulta a la base de datos con el texto de búsqueda
    Modelo modelo = new Modelo();
    Connection conn = modelo.getConection();
    try {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM productos WHERE nombre LIKE ?");
        stmt.setString(1, "%" + textoBusqueda + "%");
        ResultSet rs = stmt.executeQuery();

        // Crear un nuevo modelo de tabla con los resultados de la consulta
        String[] columnNames = {"ID", "Nombre", "Descripción", "Existencias", "Precio"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnNames, 0);
        while (rs.next()) {
            int idProducto = rs.getInt("idProducto");
            String nombre = rs.getString("nombre");
            String descripcion = rs.getString("descripcion");
            int existencias = rs.getInt("existencias");
            double precio = rs.getDouble("precio");
            Object[] rowData = {idProducto, nombre, descripcion, existencias, precio};
            modeloTabla.addRow(rowData);
        }
        
        // Actualizar la tabla en el panel de inventario
        JTable table = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        Inventario.setContentPane(tablePanel);
        Inventario.pack();
        
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public void buscarProducto2() {
        Modelo modelo = new Modelo();
    Connection conn = modelo.getConection();
    String busqueda = txtBusca.getText().trim(); // Obtener texto del JTextField y quitar espacios en blanco
    if (busqueda.isEmpty()) {
        // Si el JTextField está vacío, mostrar todos los productos
        mostrarInventario();
    } else {
        // Si el JTextField no está vacío, buscar coincidencias en la base de datos
        A WSPD = new A();
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Descripción");
        modeloTabla.addColumn("Existencias");
        modeloTabla.addColumn("Precio");
        try {
            String consulta = "SELECT * FROM productos WHERE nombre LIKE ? OR descripcion LIKE ? OR id LIKE ?";
            PreparedStatement pstmt = modelo.getConection().prepareStatement(consulta);
            pstmt.setString(1, "%" + busqueda + "%"); // Buscar en la columna "nombre"
            pstmt.setString(2, "%" + busqueda + "%"); // Buscar en la columna "descripcion"
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("idProducto");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("descripcion");
                fila[3] = rs.getInt("existencias");
                fila[4] = rs.getDouble("precio");
                modeloTabla.addRow(fila);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error al buscar producto: " + e.getMessage());
            e.printStackTrace();
        }
        // Actualizar la tabla con los resultados de la búsqueda
        tabla.setModel(modeloTabla);
    }
}
    
    public void agregarProductoTemporal() {
        double costoProducto;
        Modelo _modelo = new Modelo();
Connection conn = _modelo.getConection();
    String idProductoStr = Venta.txtIdProducto.getText();
    if (!idProductoStr.isEmpty()) {
        int idProducto = Integer.parseInt(idProductoStr);
        ControlInventario_Service obj2 = new ControlInventario_Service();
        ControlInventario WSPDF = obj2.getControlInventarioPort();
        String jsonRecibido = "{'productos':[" + WSPD.obtenerProductosJSON() + "]}";
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
                
                 DecimalFormat df = new DecimalFormat("#,##");
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
            JOptionPane.showMessageDialog(null, "Compra cancelada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
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
    
    public String max() {
        Modelo modelo = new Modelo();
Connection conn = modelo.getConection();
int registro_mas_alto = 0;
try {
    Statement stmt = conn.createStatement();
    String sql = "SELECT MAX(idVentas) AS registro_mas_alto FROM ventas";
    ResultSet rs = stmt.executeQuery(sql);
    if (rs.next()) {
         registro_mas_alto = rs.getInt("registro_mas_alto");
        registro_mas_alto =+1;
        return String.valueOf(registro_mas_alto);
    }
} catch (SQLException e) {
    e.printStackTrace();
}
    return String.valueOf(registro_mas_alto);}
    
    
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
        
        
        
        panelbusqueda.add(txtBusca);
        panelbusqueda.add(btnBusca);

        
        JPanel mainPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(boxLayout);

        // Agregar los paneles existentes al nuevo panel
        mainPanel.add(labelPanel);
        mainPanel.add(panelbusqueda);

// Agregar el nuevo panel al panel principal del inventario
panel.add(mainPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"idVentas", "fecha", "id_cliente", "id_repartidor"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnNames, 0);;
        
               String jsonRecibido = "{'ventas':["+ WSPD.obtenerProductosJSONVentas()+"]}";
       jsonRecibido = jsonRecibido.replace("\"", "'");
       System.out.println(jsonRecibido);
       String json = jsonRecibido;
       JSONObject objetoJson = new JSONObject(json);
       JSONArray arrayProductos = objetoJson.getJSONArray("ventas");
       for (int i = 0; i < arrayProductos.length(); i++) {
           JSONObject objetoProducto = arrayProductos.getJSONObject(i);
           int idProducto = objetoProducto.getInt("idVentas");
           String nombre = objetoProducto.getString("fecha");
           String descripcion = objetoProducto.getString("id_cliente");
           int existencias = objetoProducto.getInt("id_repartidor");
           Object[] rowData = {idProducto, nombre, descripcion, existencias};
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
