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
        if (doctorName.split("\\s+").length == 1)
            throw new WaveTeamLogicException("You must specify a longer name, only a name is too general");
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
     *
     * @param doctorEntity
     */
    @Override
    public void createDoctor(DoctorEntity doctorEntity) {
//        if (doctorEntity.)
        persistence.create(doctorEntity);
    }

    /**
     *
     * @param doctorEntity
     */
    @Override
    public void updateDoctor(DoctorEntity doctorEntity) {
        persistence.update(doctorEntity);
    }

    /**
     *
     * @param doctorId
     */
    @Override
    public void deleteDoctor(Long doctorId) {
        persistence.delete(doctorId);
    }
}
