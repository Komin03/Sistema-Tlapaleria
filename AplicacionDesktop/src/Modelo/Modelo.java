package Modelo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edit_
 */
public class Modelo {
  public Connection getConection(){
   //String url = "jdbc:mysql://db4free.net:3306/ferreteria?useSSL=false";
     //  String user = "kevin45789";
  //String password = "kevin45789";
  String url = "jdbc:mysql://localhost:3306/ferreteria?useSSL=false";
   String user = "root";
   String password = "";
   try {
       Connection conn = DriverManager.getConnection(url, user, password);
       System.out.println("Conexión exitosa a la base de datos!");
       return conn;
   }catch (SQLException e) {
       e.printStackTrace();
   }
   return null;
}


}