package Controlador;

import Test.A;
import Vistas.*;
import controlinventario.ControlInventario;
import controlinventario.ControlInventario_Service;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.*;
import javax.swing.JTable;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
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
    private Inventario2 Inventario;
    private Venta Venta;
    private InformeVentas InformeVentas;
    private Lealtad Lealtad;
    private Promociones Promociones;
    private  Stage ventanaCarga;

    private CargaDialog cargaDialog;

    
    
    
    public Controlador(Inventario2 inventario, Venta venta, InformeVentas informeVentas, Lealtad lealtad, Promociones promociones) {
        this.Inventario = inventario;
        this.Venta = venta;
        this.InformeVentas = informeVentas;
        this.Lealtad = lealtad;
        this.Promociones = promociones;
        
        //Agregar Listeners
        this.Venta.btnInventario.addActionListener(new ActionListener() {
          @Override
           public void actionPerformed(ActionEvent e) {
                
                mostrarInventario();
            
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
       A a = new A();
       System.out.println("cargando...");
       ControlInventario_Service obj2=new ControlInventario_Service();
       ControlInventario WSPDF = obj2.getControlInventarioPort();
       JPanel panel = new JPanel();
       String[] columnNames = {"ID", "Nombre", "Descripción", "Existencias", "Precio"};
       DefaultTableModel modeloTabla = new DefaultTableModel(columnNames, 0);
       String jsonRecibido = "{'productos':["+ A.obtenerProductosJSON()+"]}";
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
       }
       JTable table = new JTable(modeloTabla);
       JScrollPane scrollPane = new JScrollPane(table);
       panel.add(scrollPane);
       Inventario.setContentPane(panel);
       Inventario.pack();
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

        
}
