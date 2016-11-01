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
     * Obtiene el listado de médicos.
     *
     * @return lista de médicos
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
     * 
     * @param id
     * @param doctor
     * @return
     * @throws MedicoLogicException 
     */
    @PUT
    @Path("{id: \\d+}")
    public MedicoDTO updateDoctor(@PathParam("id") Long id, MedicoDTO doctor) throws MedicoLogicException {
        return cityLogic.updateDoctor(id, doctor);
    }
    
    /**
     * Asigna un consultorio a un médico
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
     * Agrega un médico
     *
     * @param doctor
     * @return doctor2 Datos del médico agregado
     * @throws MedicoLogicException cuando ya existe un médico con la cédula
     * suministrada
     */
    @POST
    public MedicoDTO createDoctor(MedicoDTO doctor) throws MedicoLogicException {
        return cityLogic.createDoctor(doctor);
    }

    @DELETE
    @Path("{id: \\d+}")
    public void deleteDoctor(@PathParam("id") Long id) throws MedicoLogicException {
        cityLogic.deleteDoctor(id);
    }
    
    //REQUERIMIENTOS R4 Y R7 - MEDICO Y SUS DISPONIBILIDADES

    @POST
    @Path("{id: \\d+}/disponibilidad/")
    public void setDisponibilidad(@PathParam("id") Long id, ArrayList days) throws MedicoLogicException {
        cityLogic.definirHorarioMedico(id, days);
    }

    @GET
    @Path("{id: \\d+}/disponibilidad/")
    public List<CitaDTO> getDisponibilidad(@PathParam("id") Long id) throws MedicoLogicException {
        return cityLogic.getDoctorSchedule(id);
    }
}
