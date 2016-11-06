package co.edu.uniandes.waveteam.sistemahospital.ejbs;

import co.edu.uniandes.waveteam.sistemahospital.api.IDoctorLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.CitaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultorioEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.PacienteEntity;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;
import co.edu.uniandes.waveteam.sistemahospital.persistence.ConsultorioPersistence;
import co.edu.uniandes.waveteam.sistemahospital.persistence.DoctorPersistence;
import co.edu.uniandes.waveteam.sistemahospital.persistence.EspecialidadPersistence;
import co.edu.uniandes.waveteam.sistemahospital.persistence.PacientePersistence;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Session;
import java.util.List;

/**
 * Created by felipeplazas on 10/27/16.
 */
@Stateless
public class DoctorLogic implements IDoctorLogic {

    @Inject
    private DoctorPersistence persistence;
    
    @Inject
    private EspecialidadPersistence specialtyPersistence;

    @Inject
    private PacientePersistence pacientePersistence;
    
    @Inject
    private ConsultorioPersistence consultorioPersistence;

    /**
     * List all the doctors
     * @return List of {@link DoctorEntity}
     */
    @Override
    public List<DoctorEntity> getDoctores() {
        return persistence.findAll();
    }

    /**
     * Get a doctor by his ID.
     * @param doctorId
     * @return
     */
    @Override
    public DoctorEntity getDoctorById(Long doctorId) {
        return persistence.find(doctorId);
    }

    /**
     * Get a doctor by his name
     * @return
     */
    @Override
    public DoctorEntity getDoctorByName(String doctorName) throws WaveTeamLogicException {
        return persistence.findByName(doctorName);
    }
    
    /**
     * Get a doctor by his name
     * @return
     */
    @Override
    public DoctorEntity getLastInsertedDoctor() throws WaveTeamLogicException {
        try{
            return persistence.getLastInsertedDoctor();
        } catch (Exception e){
            e.printStackTrace();
            throw new WaveTeamLogicException("There are no doctors");
        }
    }

    /**
     * Get doctors by their speciality
     * @return
     */
    @Override
    public List<DoctorEntity> getDoctorByEspecialidad(EspecialidadEntity especialidad) {
        return persistence.findByEspecialidad(especialidad);
    }

    /**
     * Create a new doctor instance, if the given specialty does not exist, it is created.
     * @param doctorEntity
     * @throws WaveTeamLogicException 
     * @return Created Doctor ID.
     */
    @Override
    public DoctorEntity createDoctor(DoctorEntity doctorEntity) throws WaveTeamLogicException {
        EspecialidadEntity specialty = specialtyPersistence.findByName(doctorEntity.getEspecialidad().getName());
        if (specialty == null){
            specialty = new EspecialidadEntity();
            specialty.setName(doctorEntity.getEspecialidad().getName());
            specialty.setTipo("Clinica");
            specialty.setGruposEdad("8-80");
        }
        doctorEntity.setEspecialidad(specialty);
        specialty.getDoctores().add(doctorEntity);
        
        if (doctorEntity.getConsultorio() != null){
            ConsultorioEntity consultorioEnt = consultorioPersistence.find(doctorEntity.getConsultorio());
            if (consultorioEnt == null);
                /**
                 * Uncomment as soon as consultorio api is working
                 */
    //            throw new WaveTeamLogicException("ERROR: The given consulting room does not exist");
        }

        if (doctorEntity.getId() == null){
            persistence.create(doctorEntity);
        }
        else{
            if (getDoctorById(doctorEntity.getId() ) != null)
                throw new WaveTeamLogicException("There already exists a doctor with that id");
            persistence.create(doctorEntity);
        }
        return doctorEntity;
    }
    
    /**
     * Set availability
     * @param doctorID
     * @param days 
     */
    @Override
    public void setDisponibilidad(Long doctorID, ArrayList<LinkedHashMap> days){
        try{
            persistence.find(doctorID).setDisponibilidad(days);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Get availability
     * @param doctorID
     * @return 
     */
    @Override
    public List<CitaEntity> getDisponibilidad(Long doctorID){
        try{
            return persistence.find(doctorID).getDisponibilidadCitas();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update an existing doctor instance
     * @param doctorEntity
     */
    @Override
    public void updateDoctor(DoctorEntity doctorEntity) throws WaveTeamLogicException {
        if (persistence.find(doctorEntity.getId()) == null)
            throw new WaveTeamLogicException("The instance you are trying to update does not exist");
        persistence.update(doctorEntity);
    }

    /**
     * Delete an existing doctor instance
     * @param doctorId
     */
    @Override
    public void deleteDoctor(Long doctorId) throws WaveTeamLogicException {
        if (persistence.find(doctorId) == null)
            throw new WaveTeamLogicException("The instance you are trying to delete does not exist");
        persistence.delete(doctorId);
    }
}
