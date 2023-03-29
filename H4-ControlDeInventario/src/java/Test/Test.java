/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;
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
        String url = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10607574";
String user = "sql10607574";
String password = "LFtU79Pkcq";

try {
    Connection conn = DriverManager.getConnection(url, user, password);
    System.out.println("Conexión exitosa a la base de datos!");
    
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
            System.out.println(jsonObject.toString());
        }

        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        System.err.println("Error al crear el objeto Statement: " + e.getMessage());
    }
} catch (SQLException e) {
    e.printStackTrace();
}

        
        
    }
}
