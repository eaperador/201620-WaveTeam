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
import co.edu.uniandes.waveteam.sistemahospital.api.IEspecialidadLogic;
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
    @Produces("application/json")
    @GET
    public Response getDoctors() {
        ArrayList <MedicoDetailDTO> doctors = new ArrayList<>();
        for (DoctorEntity entity: logic.getDoctores()){
            doctors.add(new MedicoDetailDTO(entity));
        }
        return Response.status(200).entity(doctors).build();
    }

    /**
     * Get a doctor by his id.
     * @param id
     * @return
     * @throws WebApplicationException 
     */
    @Produces("application/json")
    @GET
    @Path("{id: \\d+}")
    public Response getDoctor(@PathParam("id") Long id) { 
        DoctorEntity doc = logic.getDoctorById(id);
        if (doc == null)
            return Response
                    .status(400)
                    .entity("The given doctor does not exist.")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        MedicoDetailDTO med = new MedicoDetailDTO(logic.getDoctorById(id));
        return Response
                .status(200)
                .entity(med)
                .build();
    }

    /**
     * Update a doctor
     * @param id
     * @param doctor
     * @return
     * @throws MedicoLogicException 
     */
    @Produces("application/json")
    @Consumes("application/json")
    @PUT
    @Path("{id: \\d+}")
    public Response updateDoctor(@PathParam("id") Long id, MedicoDTO doctor) throws WebApplicationException {
        try{
            logic.updateDoctor(doctor.toEntity());
        } catch (WaveTeamLogicException w){
             return Response
                    .status(400)
                    .entity(w.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
        return Response
                .status(200)
                .entity(logic.getDoctorById(id))
                .build();
    }
    
    /**
     * Assign a consulting room to a doctor
     * @param idMedico
     * @param idConsultorio
     * @return
     * @throws MedicoLogicException 
     */
    @Produces("application/json")
    @Consumes("application/json")
    @PUT
    @Path("{idMedico: \\d+}/{idConsultorio: \\d+}")
    public Response asignConsultorio(@PathParam("idMedico") Long idMedico, @PathParam("idConsultorio") Long idConsultorio) throws MedicoLogicException {
        return Response
                    .status(400)
                    .entity(cityLogic.asignConsultorio(idMedico, idConsultorio))
                    .type(MediaType.TEXT_PLAIN)
                    .build();
    }
    
    /**
     * Create a new doctor
     * @param doctor
     * @return Created doctor
     * @throws MedicoLogicException cuando ya existe un médico con la cédula
     * suministrada
     */
    @Produces("application/json")
    @Consumes("application/json")
    @POST
    public Response createDoctor(MedicoDetailDTO doctor) throws MedicoLogicException {
        try{
            DoctorEntity ent = logic.createDoctor( doctor.toEntity() );
            System.out.println("ENTITY WAS CREATED");
            MedicoDetailDTO med = new MedicoDetailDTO( ent );
            return Response
                    .status(200)
                    .entity(med)
                    .build();
        } catch (Exception w){
            w.printStackTrace();
            return Response
                    .status(400)
                    .entity(w.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
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
     * Set the availability of a given doctor
     * @param id
     * @param days
     * @throws MedicoLogicException 
     */
    @Consumes("application/json")
    @POST
    @Path("{id: \\d+}/disponibilidad/")
    public void setDisponibilidad(@PathParam("id") Long id, ArrayList days) throws MedicoLogicException {
        logic.setDisponibilidad(id, days);
    }

    /**
     * Get a given doctor availability
     * @param id
     * @return List of {@link CitaDTO} if retrieved successfully
     * @throws MedicoLogicException 
     */
    @Produces("application/json")
    @GET
    @Path("{id: \\d+}/disponibilidad/")
    public Response getDisponibilidad(@PathParam("id") Long id) throws MedicoLogicException {
        List<CitaEntity> citas = logic.getDisponibilidad(id);
        ArrayList<CitaDTO> dtos = new ArrayList<>();
        for (CitaEntity entity: citas){
            dtos.add( new CitaDTO(entity, true) );
        }
        return Response
                .status(200)
                .entity(dtos)
                .build();
    }
}
