/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.resources;


import co.edu.uniandes.rest.waveteam.dtos.CitaDTO;
import co.edu.uniandes.rest.waveteam.exceptions.CitaLogicException;
import co.edu.uniandes.rest.waveteam.mocks.AppLogicMock;

import java.text.ParseException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *Clase que implementa el recurso REST de "citas"
 * 
 * El Path de la clase ed /api/citas.
 * 
 * @author jm.lizarazo10
 */

@Path("citas")
@Produces("application/json")
@Consumes("application/json")
public class CitaResource {
    
    AppLogicMock citaLogic;

    public CitaResource() throws ParseException {
        this.citaLogic = new AppLogicMock();
    }
    
    /**
     * Obtiene el listado de citas
     * 
     * @return lista de citas
     */
    @GET
    public List getAppointments() throws CitaLogicException{
        return citaLogic.getCitas();
    }
    
    
    @GET
    @Path("{id: \\d+}")
    public CitaDTO getCita(@PathParam("id") Long id) throws CitaLogicException {
        return citaLogic.getCita(id);
    }
    
    /**
     * Actualiza la información de una cita con el id provisto
     * 
     * @param id
     * @return CitaDTO
     * @throws CitaLogicException 
     */
    @PUT
    @Path("{id: \\d+}")
    public CitaDTO updateCita(@PathParam("id") Long id, CitaDTO cita) throws CitaLogicException{
        return citaLogic.updateCita(id, cita);
    }
    
    /**
     * Crea una cita con la informacion dada
     * 
     * @param cita
     * @return CitaDTO
     * @throws CitaLogicException
     */
    @POST
    public CitaDTO createCita(CitaDTO cita) throws CitaLogicException{
        return citaLogic.crearCita(cita);
    }
    
    
     /**
     * Elimina la cita con el id/nombre dado
     * 
     * @param id
     * @throws CitaLogicException 
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteCita(@PathParam("id") Long id) throws CitaLogicException {
        citaLogic.deleteCita(id);
    }
    
}
