/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.resources;


import co.edu.uniandes.rest.waveteam.dtos.CitaDTO;
import co.edu.uniandes.rest.waveteam.exceptions.CitaLogicException;
import co.edu.uniandes.rest.waveteam.exceptions.MedicoLogicException;
import co.edu.uniandes.rest.waveteam.mocks.CitaLogicMock;
import co.edu.uniandes.waveteam.sistemahospital.api.ICitaLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.CitaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.PacienteEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
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
    
    @Inject
    ICitaLogic citLogic;
    
    CitaLogicMock citaLogic = new CitaLogicMock();

 //   public CitaResource() throws ParseException {
   //     this.citaLogic = new AppLogicMock();
  //  }
    
    private List<CitaDTO> listaEntity(List<CitaEntity> listaECitas){
        List<CitaDTO> lista = new ArrayList();
        for(CitaEntity cita : listaECitas){
            lista.add(new CitaDTO(cita));
        }
        return lista;
    }
    
    /**
     * Obtiene el listado de citas
     * 
     * @return lista de citas
     */
    @GET
    public List<CitaDTO> getCitas() throws CitaLogicException{
        return listaEntity(citLogic.getCitas());
    }
    
    
    @GET
    @Path("{id: \\d+}")
    public CitaDTO getCita(@PathParam("id") Long id) throws CitaLogicException {
        return new  CitaDTO( citLogic.getCita(id));
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
        CitaEntity citaE = cita.toEntity();
        citaE.setId(id);
        return new CitaDTO (citLogic.updateCita(citaE));
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
        return new CitaDTO(citLogic.createCita(cita.toEntity()));
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
        citLogic.deleteCita(id);
    }
    
    
    @GET
    @Path("/paciente/{id: \\d+}")
    public List<CitaDTO> getCitasByPaciente(@PathParam("id") Long paciente) throws CitaLogicException{
        return citaLogic.getCitasByPaciente(paciente);
    }
    
    @GET
    @Path("/doctor/{fechaInicio: \\w+}-{fechaFin: \\w+}/{id: \\d+}")
    public List<CitaEntity> getCitasByDoctorEnFecha(@PathParam("doctor") DoctorEntity doctor, @PathParam("fechaInicio") Long fechaInicio, @PathParam("fechaFin") Long fechaFin) throws CitaLogicException, MedicoLogicException{
        return citLogic.getCitasByDoctorEnFecha(doctor, fechaInicio, fechaFin);
        
    }
    
    @GET
    @Path("/paciente/{fechaInicio: \\w+}-{fechaFin: \\w+}/{id: \\d+}")
    public List<CitaEntity> getCitasByPacienteEnFecha(@PathParam("paciente") PacienteEntity paciente, @PathParam("fechaInicio") Long fechaInicio, @PathParam("fechaFin") Long fechaFin) throws CitaLogicException{
        return citLogic.getCitasByPacienteEnFecha(paciente, fechaInicio, fechaFin);
    }
    
    
    @PUT
    @Path("{id: \\d+}/terminar")
    public CitaDTO terminarCita(@PathParam("id") Long id) throws CitaLogicException{
        return citaLogic.terminarCita(id);
    }
    
}
