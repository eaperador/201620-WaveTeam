package co.edu.uniandes.waveteam.sistemahospital.ejbs;

import co.edu.uniandes.waveteam.sistemahospital.api.IDoctorLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.PacienteEntity;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;
import co.edu.uniandes.waveteam.sistemahospital.persistence.DoctorPersistence;
import co.edu.uniandes.waveteam.sistemahospital.persistence.PacientePersistence;
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
    private PacientePersistence pacientePersistence;

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
     * Get doctors by their speciality
     * @return
     */
    @Override
    public List<DoctorEntity> getDoctorByEspecialidad(EspecialidadEntity especialidad) {
        return persistence.findByEspecialidad(especialidad);
    }

    /**
     * Create a new doctor instance
     * @param doctorEntity
     */
    @Override
    public void createDoctor(DoctorEntity doctorEntity) {
        persistence.create(doctorEntity);
    }

    /**
     * Update an existing doctor isntance
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
