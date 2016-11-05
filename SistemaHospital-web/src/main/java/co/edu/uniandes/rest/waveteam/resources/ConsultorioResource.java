/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.resources;
import co.edu.uniandes.rest.waveteam.exceptions.ConsultorioLogicException;
import co.edu.uniandes.rest.waveteam.mocks.ConsultorioLogicMock;
import co.edu.uniandes.rest.waveteam.dtos.ConsultorioDTO;
import co.edu.uniandes.rest.waveteam.dtos.MedicoDTO;
import co.edu.uniandes.waveteam.sistemahospital.api.IConsultorioLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultorioEntity;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Clase que implementa el recurso REST de "Consultorio"
 * 
 * El path de la clase es /api/consultorios.
 * 
 * @author Rogelio García
 */
@Path("consultorios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ConsultorioResource {
    private final static Logger logger = Logger.getLogger(ConsultorioLogicMock.class.getName());
    
//    ConsultorioLogicMock consultorioLogic = new ConsultorioLogicMock();
      
    @Inject
    IConsultorioLogic consultorioLogic;
    /**
     * Devuelve la lista de los consultorios
     * 
     * @return List de consultorios
     * @throws ConsultorioLogicException 
     */
    @GET
    public List<ConsultorioDTO> getConsultorios() throws ConsultorioLogicException
    {
        List<ConsultorioDTO> consultorios = new ArrayList();
        List<ConsultorioEntity> consEntity = consultorioLogic.getConsultorios();
        for (ConsultorioEntity cons : consEntity){
            consultorios.add(new ConsultorioDTO(cons));
        }
        return consultorios;
    }
    
    /**
     * Devuelve un consultorio con el id/nombre dado
     * 
     * @param id
     * @return ConsultorioDTO
     * @throws ConsultorioLogicException 
     */
    @GET
    @Path("{id: \\d+}")
    public ConsultorioDTO getConsultorio(@PathParam("id") long id) throws ConsultorioLogicException
    {
        ConsultorioDTO cons = new ConsultorioDTO(consultorioLogic.getConsultorio(id));
        return cons;
    }
    
    /**
     * Actualiza la información de un consultorio con el id/nombre dado
     * 
     * @param id
     * @return ConsultorioDTO
     * @throws ConsultorioLogicException 
     */
    @PUT
    @Path("{id: \\d+}")
    public ConsultorioDTO updateConsultorio(@PathParam("id") long id, ConsultorioDTO updatedConsultorio) throws ConsultorioLogicException            
    {
        updatedConsultorio.setId(id);
        try{
            consultorioLogic.updateConsultorio(updatedConsultorio.entity());
        }catch(WaveTeamLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
        ConsultorioDTO res = new ConsultorioDTO(consultorioLogic.getConsultorio(updatedConsultorio.getId()));
        return res;
    }
    
    /**
     * Crea un consultorio con la información dada
     * 
     * @param consultorioNuevo
     * @return ConsultorioDTO
     * @throws ConsultorioLogicException
     */
    @POST
    public ConsultorioDTO createConsultorio(ConsultorioDTO consultorioNuevo) throws ConsultorioLogicException
    {
        logger.info("ME LLEGO UN CONSULTORIO "+consultorioNuevo);
        
        try{
            consultorioNuevo = new ConsultorioDTO(consultorioLogic.createConsultorio(consultorioNuevo.entity()));
        }catch(WaveTeamLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
        ConsultorioDTO res = consultorioNuevo;
        return res;
    }
    
    /**
     * Elimina el consultorio con el id/nombre dado
     * 
     * @param id
     * @throws ConsultorioLogicException 
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteConsultorio(@PathParam("id") long id) throws ConsultorioLogicException
    {
        try{
            consultorioLogic.deleteConsultorio(id);
        }catch(WaveTeamLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }
    
    //REQUERIMIENTO R8 - ASIGNAR CONSULTORIO A MEDICO
    
    /**
     * Elimina todos los doctores asignados de un consultorio
     * @param idConsultorio
     * @return
     * @throws ConsultorioLogicException 
     */
    @DELETE
    @Path("{idConsultorio: \\d+}/doctores")
    public ConsultorioDTO unasignDoctors(@PathParam("idConsultorio") long idConsultorio) throws ConsultorioLogicException
    {
        try{
            consultorioLogic.unasignDoctors(idConsultorio);
        }catch(WaveTeamLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
        ConsultorioDTO res = new ConsultorioDTO(consultorioLogic.getConsultorio(idConsultorio));
        return res;
    }
    
    /**
     * Elimina un doctor asignado de un consultorio
     * @param idConsultorio
     * @param idDoctor
     * @return
     * @throws ConsultorioLogicException 
     */
    @DELETE
    @Path("{idConsultorio: \\d+}/doctores/{idDoctor: \\d+}")
    public ConsultorioDTO unasignDoctor(@PathParam("idConsultorio") long idConsultorio, @PathParam("idDoctor") long idDoctor) throws ConsultorioLogicException
    {
        try{
            consultorioLogic.unasignDoctor(idConsultorio, idDoctor);
        }catch(WaveTeamLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
        ConsultorioDTO res = new ConsultorioDTO(consultorioLogic.getConsultorio(idConsultorio));
        return res;
    }
    
    /**
     * Asigna un nuevo doctor al consultorio
     * @param idConsultorio
     * @param doc
     * @return
     * @throws ConsultorioLogicException 
     */
    @POST
    @Path("{idConsultorio: \\d+}/doctores")
    public ConsultorioDTO asignDoctor(@PathParam("idConsultorio") long idConsultorio, MedicoDTO doc) throws ConsultorioLogicException
    {   
        try{
            consultorioLogic.asignDoctor(idConsultorio, doc.toEntity());
        }catch(WaveTeamLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
        ConsultorioDTO res = new ConsultorioDTO(consultorioLogic.getConsultorio(idConsultorio));
        return res;
    }
    
//    /**
//     * Asigna doctores al consultorio
//     * @param idConsultorio
//     * @param idDoctor
//     * @return
//     * @throws ConsultorioLogicException 
//     */
//    @PUT
//    @Path("{idConsultorio: \\d+}/doctores")
//    public ConsultorioDTO asignDoctors(@PathParam("idConsultorio") long idConsultorio, List<MedicoDTO> nuevaLista) throws ConsultorioLogicException
//    {
//        return consultorioLogic.asignDoctors(idConsultorio, nuevaLista);
//    }
}
