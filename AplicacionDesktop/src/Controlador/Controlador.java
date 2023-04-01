package Controlador;

import Vistas.*;
import controlinventario.ControlInventario;
import controlinventario.ControlInventario_Service;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import javafx.application.Platform;
import javafx.scene.Node;
import javax.swing.JTable;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONException;
import org.json.JSONTokener;

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
    private static Stage ventanaCarga;

    
    
    
    public Controlador(Inventario2 inventario, Venta venta, InformeVentas informeVentas, Lealtad lealtad, Promociones promociones) {
        this.Inventario = inventario;
        this.Venta = venta;
        this.InformeVentas = informeVentas;
        this.Lealtad = lealtad;
        this.Promociones = promociones;
        
        //Agregar Listeners
        this.Venta.btnMostrarInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                mostrarInventario2("");
            
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
    
    public void mostrarInventario(){
        
        /*
        WSBDatos_Service obj2=new WSBDatos_Service();
         WSBDatos obj = obj2.getWSBDatosPort();
        
        */
        // Parsear el JSON en un objeto JSONObject
       // Platform.runLater(() -> mostrarCirculoDeCarga2("Cargando datos..."));
        
        
        
        
         //ocultarCirculoDeCarga();
        
        }
    
   public void mostrarInventario2(String jsonStr) {
       // mostrarCirculoDeCarga2("Cargando...");
       
        ControlInventario_Service obj2=new ControlInventario_Service();
         ControlInventario WSPDF = obj2.getControlInventarioPort();
        // Crear un panel para agregar las tablas
        JPanel panel = new JPanel();

        // Dividir la cadena de texto en filas
        String[] rows = jsonStr.split("\n");

        
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("idProducto");
            tableModel.addColumn("nombre");
            tableModel.addColumn("descipcion");
            tableModel.addColumn("existencias");
            tableModel.addColumn("precio");

            String jsonString = WSPDF.obtenerProductosJSON() ;

           
            String[] jsonDataArray = jsonString.split("\\r?\\n");
            for (String jsonData : jsonDataArray) {
        try {
               JSONObject jsonObject = new JSONObject(jsonData);
            Object[] rowData = {
                    jsonObject.getInt("idProducto"),
                    jsonObject.getString("nombre"),
                    jsonObject.getString("descipcion"),
                    jsonObject.getInt("existencias"),
                    jsonObject.getDouble("precio")
            };
            tableModel.addRow(rowData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
            

            // Crear la tabla con el modelo de tabla
            JTable table = new JTable(tableModel);

            // Agregar la tabla a un JScrollPane para permitir desplazamiento
            JScrollPane scrollPane = new JScrollPane(table);

            // Agregar el JScrollPane al panel
            panel.add(scrollPane);
        

        // Agregar el panel al JFrame Inventario
        Inventario.setContentPane(panel);

        // Mostrar el JFrame Inventario
        Inventario.pack();
        Inventario.setVisible(true);
        //ocultarCirculoDeCarga();
    
}
  
    public static void mostrarCirculoDeCarga2(String mensaje) {
        if (ventanaCarga == null) {
            Arc arc = new Arc(20, 20, 20, 20, 0, 300);
            arc.setFill(null);
            arc.setStrokeWidth(4);
            

            Label etiquetaMensaje = new Label(mensaje);

            Group grupoEtiqueta = new Group((Collection<Node>) etiquetaMensaje);
            Group grupo = new Group(arc, grupoEtiqueta);

            Paint paintTransparente = null;

            ventanaCarga = new Stage();
            ventanaCarga.initStyle(StageStyle.TRANSPARENT);
            ventanaCarga.setScene(new Scene(grupo, 50, 50));
        }

        ventanaCarga.show();
    }

    public static void ocultarCirculoDeCarga() {
        if (ventanaCarga != null) {
            ventanaCarga.hide();
        }
    }

        
}
