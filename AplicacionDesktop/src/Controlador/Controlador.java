package Controlador;

import Vistas.*;
import controlinventario.ControlInventario_Service;
import controlinventario.ControlInventario;
import java.awt.Frame;
import javax.swing.JTable;
import org.json.JSONArray;
import org.json.JSONObject;

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
public class Controlador {
    private Inventario Inventario;
    private Venta Venta;
    private InformeVentas InformeVentas;
    private Lealtad Lealtad;
    private Promociones Promociones;

    
    
    
    public Controlador(Inventario inventario, Venta venta, InformeVentas informeVentas, Lealtad lealtad, Promociones promociones) {
        this.Inventario = inventario;
        this.Venta = venta;
        this.InformeVentas = informeVentas;
        this.Lealtad = lealtad;
        this.Promociones = promociones;
    }
    
    public void mostrarInventario(){
        
        /*
        WSBDatos_Service obj2=new WSBDatos_Service();
         WSBDatos obj = obj2.getWSBDatosPort();
        
        */
        // Parsear el JSON en un objeto JSONObject
        ControlInventario_Service obj2=new ControlInventario_Service();
        ControlInventario WS_ControlInventario = obj2.getControlInventarioPort();
        
        String jsonString = WS_ControlInventario.obtenerProductosJSON();
        JSONObject jsonObject = new JSONObject(jsonString);

        // Obtener los datos del objeto JSON en un arreglo
        JSONArray dataArray = jsonObject.getJSONArray("data");

        // Crear una matriz de objetos para almacenar los datos de la tabla
        Object[][] tableData = new Object[dataArray.length()][3];

        // Iterar sobre los datos del arreglo y almacenarlos en la matriz
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject dataObject = dataArray.getJSONObject(i);
            tableData[i][0] = dataObject.getString("nombre");
            tableData[i][1] = dataObject.getString("apellido");
            tableData[i][2] = dataObject.getInt("edad");
        }

        // Crear un objeto JTable con los datos de la matriz
        JTable table = new JTable();
        table.setModel(Inventario.tblInventario.getModel());
        table = new JTable(tableData, new Object[] {"Nombre", "Apellido", "Edad"});
        
        Inventario.tblInventario = table;
    }
    
    
}
