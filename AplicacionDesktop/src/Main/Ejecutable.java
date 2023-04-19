/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controlador.Controlador;
import Vistas.*;
import java.awt.Frame;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 *
 * @author edit_
 */
public class Ejecutable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Ejecutable instancia = new Ejecutable();
        ImageIcon icono = new ImageIcon(instancia.getClass().getResource("/IMAGES/unico.png"));

        Image imagen = icono.getImage();


        
        UIManager.put("JFrame.icon", icono);
        Inventario2 inventario = new Inventario2 ();
        Venta venta = new Venta ();
        InformeVentas informeVentas = new InformeVentas ();
        Lealtad lealtad = new Lealtad ();
        Promociones promociones = new Promociones ();     
        Login  login = new Login();
        MultiOpcionesDePago multiOpcionesDePago = new MultiOpcionesDePago();
        Controlador controlador = new Controlador(inventario,venta,informeVentas,lealtad,promociones,login,multiOpcionesDePago);
        venta.setIconImage(imagen);
        login.setVisible(true);
        
    }
    
}
