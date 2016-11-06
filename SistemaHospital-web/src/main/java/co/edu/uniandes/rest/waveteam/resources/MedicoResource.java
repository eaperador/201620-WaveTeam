/*
 * MedicoResource.java
 * Clase que representa el recurso "/doctors"
 * Implementa varios métodos para manipular los doctores
 */
package co.edu.uniandes.rest.waveteam.resources;

import co.edu.uniandes.rest.waveteam.dtos.*;
import co.edu.uniandes.rest.waveteam.exceptions.MedicoLogicException;
import co.edu.uniandes.rest.waveteam.mocks.MedicoLogicMock;
import co.edu.uniandes.waveteam.sistemahospital.api.IDoctorLogic;

import co.edu.uniandes.waveteam.sistemahospital.entities.*;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;
import java.util.ArrayList;
import java.util.List;
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
@RequestScoped
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
    public List<MedicoDetailDTO> getDoctors() {
        List <MedicoDetailDTO> doctors = new ArrayList<>();
        for (DoctorEntity entity: logic.getDoctores()){
            doctors.add(new MedicoDetailDTO(entity));
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
    public MedicoDetailDTO getDoctor(@PathParam("id") Long id) { 
        DoctorEntity doc = logic.getDoctorById(id);
        if (doc == null)
            throw new WebApplicationException("The given doctor does nor exist", Response.Status.BAD_REQUEST);
        return new MedicoDetailDTO(logic.getDoctorById(id));
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
    public MedicoDetailDTO updateDoctor(@PathParam("id") Long id, MedicoDTO doctor) throws WebApplicationException {
        try{
            logic.updateDoctor(doctor.toEntity());
        } catch (WaveTeamLogicException w){
             throw new WebApplicationException(w.getMessage(), Response.Status.BAD_REQUEST);
        }
        return new MedicoDetailDTO(logic.getDoctorById(id));
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
    public MedicoDetailDTO createDoctor(MedicoDetailDTO doctor) throws MedicoLogicException {
        try{
            DoctorEntity ent = logic.createDoctor( doctor.toEntity() );
            System.out.println("ENTITY WAS CREATED");
            MedicoDetailDTO med = new MedicoDetailDTO( ent );
            return med;
        } catch (Exception w){
            w.printStackTrace();
            throw new WebApplicationException(w.getMessage(), Response.Status.BAD_REQUEST);
        }
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
        logic.setDisponibilidad(id, days);
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
        List<CitaEntity> citas = logic.getDisponibilidad(id);
        List<CitaDTO> dtos = new ArrayList<>();
        for (CitaEntity entity: citas){
            System.out.println("Fecha "+entity.getFecha());
            System.out.println("Habilitada "+entity.getHabilitada());
            System.out.println("Name "+entity.getName());
            System.out.println("Duracion "+entity.getDuracion());
            System.out.println("Doctor "+entity.getDoctor());
            System.out.println("Hora "+entity.getHora());
            CitaDTO citaaa = new CitaDTO(entity, true);
            System.out.println("DTO-Fecha "+citaaa.getFecha());
            System.out.println("DTODoctor "+citaaa.getMedico());
            System.out.println("DTOHora "+citaaa.getHora());
            dtos.add( citaaa );
        }
        return dtos;
    }
}
