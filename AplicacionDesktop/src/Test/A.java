/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Modelo.Modelo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.jws.WebMethod;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author edit_
 */
public class A {
    
   

    /**
     * This is a sample web service operation
     */
        public static String obtenerProductosJSON() {
         Modelo _modelo = new Modelo();
Connection conn = _modelo.getConection();
StringBuilder sb = new StringBuilder();

try {
    conn.setAutoCommit(true);
    Statement stmt = conn.createStatement();

    // aquí colocas tu consulta SQL
    ResultSet rs = stmt.executeQuery("SELECT * FROM productos");

    while (rs.next()) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idProducto", rs.getInt("idProducto"));
        jsonObject.put("nombre", rs.getString("nombre"));
        jsonObject.put("descripcion", rs.getString("descripcion"));
        jsonObject.put("precio", rs.getFloat("precio"));
        jsonObject.put("existencias", rs.getInt("existencias"));

        if (!sb.toString().isEmpty()) {
            sb.append(",\n");
        }
        sb.append(jsonObject.toString());
    }

    rs.close();
    stmt.close();
    conn.close();
} catch (SQLException e) {
    System.err.println("Error al crear el objeto Statement: " + e.getMessage());
    return e.toString();
}

return sb.toString();
            
        }
        
        
        
         public static String obtenerProductosJSONVentas() {
         Modelo _modelo = new Modelo();
Connection conn = _modelo.getConection();
StringBuilder sb = new StringBuilder();

try {
    conn.setAutoCommit(true);
    Statement stmt = conn.createStatement();

    // aquí colocas tu consulta SQL
    ResultSet rs = stmt.executeQuery("SELECT * FROM ventas");

    while (rs.next()) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idVentas", rs.getInt("idVentas"));
        jsonObject.put("fecha", rs.getString("fecha"));
        jsonObject.put("id_cliente", rs.getString("id_cliente"));
        jsonObject.put("id_repartidor", rs.getFloat("id_repartidor"));

        if (!sb.toString().isEmpty()) {
            sb.append(",\n");
        }
        sb.append(jsonObject.toString());
    }

    rs.close();
    stmt.close();
    conn.close();
} catch (SQLException e) {
    System.err.println("Error al crear el objeto Statement: " + e.getMessage());
    return e.toString();
}

return sb.toString();
            
        }
         
    
    }


