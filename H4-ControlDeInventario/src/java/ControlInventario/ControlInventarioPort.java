/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlInventario;

import ControlInventario_client.ControlInventario;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author edit_
 */
@Path("controlinventarioport")
public class ControlInventarioPort {
    private ControlInventario port;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ControlInventarioPort
     */
    public ControlInventarioPort() {
        port = getPort();
    }

    /**
     * Invokes the SOAP method obtenerProductosJSON
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("obtenerproductosjson/")
    public String getObtenerProductosJSON() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.obtenerProductosJSON();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     *
     */
    private ControlInventario getPort() {
        try {
            // Call Web Service Operation
            ControlInventario_client.ControlInventario_Service service = new ControlInventario_client.ControlInventario_Service();
            ControlInventario_client.ControlInventario p = service.getControlInventarioPort();
            return p;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }
}
