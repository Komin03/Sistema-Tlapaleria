/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlInventario;

import Modelo.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author edit_
 */
@WebService(serviceName = "ControlInventario")
public class ControlInventario {

    /**
     * This is a sample web service operation
     */
     @WebMethod(operationName = "obtenerProductosJSON")
    public String ObtenerProductos() {
         Modelo _modelo = new Modelo();
        Connection conn = _modelo.getConection();
       
String Return = "";


    
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
    if (!Return.isEmpty()) {
        Return = Return + ",";
    }
    Return = Return + jsonObject.toString();
}

        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        System.err.println("Error al crear el objeto Statement: " + e.getMessage());
        return e.toString();
    }
        return Return;}
}



