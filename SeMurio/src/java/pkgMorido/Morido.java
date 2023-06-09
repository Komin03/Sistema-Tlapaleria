/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgMorido;

import Modelo.Modelo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.json.JSONObject;

/**
 *
 * @author edit_
 */
@WebService(serviceName = "Morido")
public class Morido {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
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
            sb.append(",");
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
            Return = Return +jsonObject.toString();
           
                Return = Return +",";
            
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
