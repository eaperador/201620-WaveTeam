/*
 * PatientResource.java
 * Clase que representa el recurso "/patient"
 * Implementa varios métodos para manipular los pacientes
 */
package co.edu.uniandes.rest.waveteam.resources;

import co.edu.uniandes.rest.waveteam.dtos.CitaDTO;
import co.edu.uniandes.rest.waveteam.dtos.PatientDetailDTO;
import co.edu.uniandes.rest.waveteam.exceptions.CitaLogicException;
import co.edu.uniandes.rest.waveteam.exceptions.PatientLogicException;
import co.edu.uniandes.rest.waveteam.mocks.PatientLogicMock;
import co.edu.uniandes.waveteam.sistemahospital.api.IPacienteLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.PacienteEntity;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;
import java.util.ArrayList;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;

/**
 * Clase que implementa el recurso REST correspondiente a "patient".
 *
 * Note que la aplicación (definida en RestConfig.java) define la ruta "/api" y
 * este recurso tiene la ruta "cities". Al ejecutar la aplicación, el recurse
 * será accesibe a través de la ruta "/api/cities"
 *
 * @author je.ardila1501
 */
@Path("patient")
@Produces("application/json")
@RequestScoped
public class PatientResource {

    PatientLogicMock patientLogic = new PatientLogicMock();
    
    @Inject
    IPacienteLogic pacienteLogic;

    
    
    private List<PatientDetailDTO> convertEntityToDTO(List<PacienteEntity> entityList) {
        List<PatientDetailDTO> list = new ArrayList<>();
        for (PacienteEntity entity : entityList) {
            list.add(new PatientDetailDTO(entity));
        }
        return list;
    }
    
    /**
     * Obtiene el listado de pacientes.
     *
     * @return lista de pacientes
     * @throws PatientLogicException excepción retornada por la lógica
     */
    @GET
    public List<PatientDetailDTO> getPatients() throws PatientLogicException {
        return convertEntityToDTO(pacienteLogic.findAllPacientes());
    }
    
     /**
     * Obtieneel paciente identificado con el id del parametro
     * @param id
     * @return el paciente cuyo id corresponde al del parametro
     * @throws CityLogicException 
     */
    @GET
    @Path("{id: \\d+}")
    public PatientDetailDTO getPatient (@PathParam("id") Long id) throws PatientLogicException{
        return new PatientDetailDTO(pacienteLogic.getPaciente(id));
    }
    
    /**
     * 
     * Modifica los atributos del paciente identificado con el id por parametro,
     * de acuerdo a los atributos del objeto ingresado por paramtero
     * @param id
     * @param patient
     * @return el paciente con los nuevos valor(es) de atributo(s) 
     * @throws PatientLogicException
     */
    @PUT
    @Path("{id: \\d+}")
    public PatientDetailDTO updatePatient (@PathParam("id") Long id, PatientDetailDTO patient) throws PatientLogicException, WaveTeamLogicException{
        PacienteEntity pacienteentity = patient.toEntity();
        pacienteentity.setId(id);
        return new PatientDetailDTO(pacienteLogic.updatePaciente(pacienteentity));
    }
    
    /**
     * elimina el paciente identificado con el id del parametro
     * @param id
     * @throws PatientLogicException 
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deltePatient (@PathParam("id") Long id) throws PatientLogicException{
        pacienteLogic.deletePaciente(id);
    }

    /**
     * Agrega un nuevo paciente
     *
     * @param patient paciente a agregar
     * @return datos del paciente a agregar
     * @throws PatientLogicException cuando ya existe una ciudad con el id
     * suministrado
     */
    @POST
    public PatientDetailDTO createPatient(PatientDetailDTO patient) throws PatientLogicException, WaveTeamLogicException {
        
        return new PatientDetailDTO(pacienteLogic.createPaciente(patient.toEntity()));
    }
    
    /**
     * 
     */
    @GET
    @Path ("{id: \\d+}/citaspaciente")
    public List<CitaDTO>getCitasPaciente(@PathParam("id") long id) throws PatientLogicException, CitaLogicException{
        return patientLogic.getCitas(id);
    }
    

}
