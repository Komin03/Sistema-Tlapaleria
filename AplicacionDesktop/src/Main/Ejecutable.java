/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controlador.Controlador;
import Vistas.*;
import java.awt.Frame;

/**
 *
 * @author edit_
 */
public class Ejecutable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Inventario2 inventario = new Inventario2 ();
        Venta venta = new Venta ();
        InformeVentas informeVentas = new InformeVentas ();
        Lealtad lealtad = new Lealtad ();
        Promociones promociones = new Promociones ();        
        Controlador controlador = new Controlador(inventario,venta,informeVentas,lealtad,promociones);
        venta.setVisible(true);
        
    }
    
}
