/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;
import Modelo.Modelo;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;
import org.json.JSONArray;
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
public class Test {
    public static void main(String[] args) {
        
        System.out.println(ObtenerProductos());
        
        
        
        
        
        
        
        
        
        /*
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
    //return e.toString();
    System.out.println(e.toString());
}
        System.out.println(sb.toString());
//return sb.toString();
*/
        
        
        
        
        /*
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
            jsonObject.put("descipcion", rs.getString("descripcion"));
            jsonObject.put("precio", rs.getFloat("precio"));
            jsonObject.put("existencias", rs.getInt("existencias"));
            Return = Return +jsonObject.toString();
        }

        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        System.err.println("Error al crear el objeto Statement: " + e.getMessage());
    }
        System.out.println(Return);
        */
    }
    
    
    
    
     public static String ObtenerProductos() {
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
 }

    
    