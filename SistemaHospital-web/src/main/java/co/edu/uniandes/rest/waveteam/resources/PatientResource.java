/*
 * PatientResource.java
 * Clase que representa el recurso "/patient"
 * Implementa varios métodos para manipular los pacientes
 */
package co.edu.uniandes.rest.waveteam.resources;

import co.edu.uniandes.rest.waveteam.dtos.CitaDTO;
import co.edu.uniandes.rest.waveteam.dtos.PatientDTO;
import co.edu.uniandes.rest.waveteam.dtos.PatientDetailDTO;
import co.edu.uniandes.rest.waveteam.exceptions.CitaLogicException;
import co.edu.uniandes.rest.waveteam.exceptions.PatientLogicException;
import co.edu.uniandes.rest.waveteam.mocks.PatientLogicMock;
import co.edu.uniandes.waveteam.sistemahospital.api.ICitaLogic;
import co.edu.uniandes.waveteam.sistemahospital.api.IPacienteLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.PacienteEntity;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;
import java.util.ArrayList;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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

    // MIENTRAS SE SABE COMO LO VA A DEJAR..
    PatientLogicMock patientLogic = new PatientLogicMock();
    
    @Inject
    IPacienteLogic pacienteLogic;
    @Inject
    ICitaLogic citaLogic;

    
    
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
    public PatientDTO getPatient (@PathParam("id") Long id) throws PatientLogicException{
         PatientDTO paciente = new PatientDTO(pacienteLogic.getPaciente(id));
        return paciente;
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
    public void deltePatient (@PathParam("id") Long id) throws WaveTeamLogicException{
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
    
    @Produces("application/json")
    @Consumes("application/json")
    @POST
    public PatientDetailDTO createPatient(PatientDetailDTO patient) throws PatientLogicException, WaveTeamLogicException {
//        return new PatientDetailDTO(pacienteLogic.createPaciente(patient.toEntity()));
        System.out.println("*******************************************************************");
        System.out.println("asfjksdfn" + patient);
        System.out.println("*******************************************************************");
        PacienteEntity xxx = patient.toEntity();
        System.out.println("======================================= " + xxx);
        PacienteEntity entity = pacienteLogic.createPaciente(patient.toEntity());
        System.out.println("*******************************************************************");
            System.out.println("paciente creado : " + entity);
            System.out.println("*******************************************************************");
            PatientDetailDTO patientdto = new PatientDetailDTO(entity);
             System.out.println("*******************************************************************");
             System.out.println("pacientedto : " + patientdto);
            return patientdto;
            
    }
    
    //************************************
    //************************************
    /**
     * AUN NO FUNCIONA, CITAS DTO RECIBE EL OBJETO PACIENTE Y DOCTOR, NO SU ID..
     * TOCARIA MIRAR COMO EN EL HTML LE PIDO EL ID AL OBJETO MEDICO Y PACIENTE..
     * ESPERAR EN Q VA A QUEDAR....
     */
    
//            @GET
//            @Path ("{id: \\d+}/citaspaciente")
//            public List<CitaDTO>getCitasPaciente(@PathParam("id") long id) throws PatientLogicException, CitaLogicException{
//                List<CitaDTO> citasdto = new ArrayList<>();
//                int size =citaLogic.getCitasByPaciente(id).size();
//                for (int i = 0; i < size; i++) {
//                    CitaDTO citadto = new CitaDTO(citaLogic.getCitasByPaciente(id).get(i));
//                    citasdto.add(citadto);
//                }
//                return citasdto ;
//            }
    
    //************************************
    //************************************
    
    //************************************
    //EL TEMPORAL MIENTRAS SE SABE Q PASA
    //************************************
    @GET
    @Path ("{id: \\d+}/citaspaciente")
    public List<CitaDTO>getCitasPaciente(@PathParam("id") long id) throws PatientLogicException, CitaLogicException{
        return patientLogic.getCitas(id);
    }

}
