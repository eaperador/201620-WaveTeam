/*
 * MedicoResource.java
 * Clase que representa el recurso "/doctors"
 * Implementa varios métodos para manipular los doctores
 */
package co.edu.uniandes.rest.waveteam.resources;

import co.edu.uniandes.rest.waveteam.dtos.*;
import co.edu.uniandes.rest.waveteam.exceptions.MedicoLogicException;
import co.edu.uniandes.rest.waveteam.mocks.MedicoLogicMock;
import co.edu.uniandes.waveteam.sistemahospital.api.*;
import co.edu.uniandes.waveteam.sistemahospital.entities.*;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Clase que implementa el recurso REST correspondiente a "doctors".
 *
 * Note que la aplicación (definida en RestConfig.java) define la ruta "/api" y
 * este recurso tiene la ruta "doctors". Al ejecutar la aplicación, el recurse
 * será accesibe a través de la ruta "/api/doctors"
 *
 * @author Luis Felipe Plazas
 */
@Path("doctors")
@Produces("application/json")
@Consumes("application/json")
public class MedicoResource {

    MedicoLogicMock cityLogic = new MedicoLogicMock();
    
    @Inject
    private IDoctorLogic logic;
    
    /**
     * Get all doctors.
     *
     * @return List with all the doctors
     * @throws WebApplicationException 
     */
    @GET
    public List<MedicoDTO> getDoctors() {
        List <MedicoDTO> doctors = new ArrayList<>();
        for (DoctorEntity entity: logic.getDoctores()){
            doctors.add(new MedicoDTO(entity));
        }
        return doctors;
    }

    /**
     * Get a doctor by his id.
     * @param id
     * @return
     * @throws WebApplicationException 
     */
    @GET
    @Path("{id: \\d+}")
    public MedicoDTO getDoctor(@PathParam("id") Long id) {
        MedicoDTO doc = new MedicoDTO(logic.getDoctorById(id));
        if (doc == null)
            throw new WebApplicationException("The given doctor does nor exist", Response.Status.BAD_REQUEST);
        return doc;
    }

    /**
     * Update a doctor
     * @param id
     * @param doctor
     * @return
     * @throws MedicoLogicException 
     */
    @PUT
    @Path("{id: \\d+}")
    public MedicoDTO updateDoctor(@PathParam("id") Long id, MedicoDTO doctor) throws WebApplicationException {
        try{
            logic.updateDoctor(doctor.toEntity());
        } catch (WaveTeamLogicException w){
             throw new WebApplicationException(w.getMessage(), Response.Status.BAD_REQUEST);
        }
        return new MedicoDTO(logic.getDoctorById(id));
    }
    
    /**
     * Assign a consulting room to a doctor
     * @param idMedico
     * @param idConsultorio
     * @return
     * @throws MedicoLogicException 
     */
    @PUT
    @Path("{idMedico: \\d+}/{idConsultorio: \\d+}")
    public MedicoDTO asignConsultorio(@PathParam("idMedico") Long idMedico, @PathParam("idConsultorio") Long idConsultorio) throws MedicoLogicException {
        return cityLogic.asignConsultorio(idMedico, idConsultorio);
    }
    
    /**
     * Create a new doctor
     * @param doctor
     * @return Created doctor
     * @throws MedicoLogicException cuando ya existe un médico con la cédula
     * suministrada
     */
    @POST
    public MedicoDTO createDoctor(MedicoDTO doctor) throws MedicoLogicException {
        try{
            logic.createDoctor(doctor.toEntity());
        } catch (Exception w){
            throw new WebApplicationException(w.getMessage(), Response.Status.BAD_REQUEST);
        }
        
        return new MedicoDTO(logic.getDoctorById(doctor.getId()));
    }

    /**
     * Delete a doctor
     * @param id
     * @throws MedicoLogicException 
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteDoctor(@PathParam("id") Long id) throws MedicoLogicException {
        try{
            logic.deleteDoctor(id);
        } catch (WaveTeamLogicException w){
            throw new WebApplicationException(w.getMessage(), Response.Status.BAD_REQUEST);
        }
    }
    
    //REQUERIMIENTOS R4 Y R7 - MEDICO Y SUS DISPONIBILIDADES

    /**
     * 
     * @param id
     * @param days
     * @throws MedicoLogicException 
     */
    @POST
    @Path("{id: \\d+}/disponibilidad/")
    public void setDisponibilidad(@PathParam("id") Long id, ArrayList days) throws MedicoLogicException {
        cityLogic.definirHorarioMedico(id, days);
    }

    /**
     * 
     * @param id
     * @return
     * @throws MedicoLogicException 
     */
    @GET
    @Path("{id: \\d+}/disponibilidad/")
    public List<CitaDTO> getDisponibilidad(@PathParam("id") Long id) throws MedicoLogicException {
        return cityLogic.getDoctorSchedule(id);
    }
}
