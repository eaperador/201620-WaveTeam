package co.edu.uniandes.waveteam.sistemahospital.api;

import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;

import java.util.List;

/**
 * Created by felipeplazas on 10/27/16.
 */
public interface IDoctorLogic {

    /**
     * List all doctors
     * @return
     */
    public List<DoctorEntity> getDoctores();

    /**
     * Get a doctor by id.
     * @param doctorId
     * @return
     */
    public DoctorEntity getDoctorById(Long doctorId);

    /**
     * Get a doctor by his/her name.
     * @param doctorName
     * @return
     */
    public DoctorEntity getDoctorByName(String doctorName) throws WaveTeamLogicException;

    /**
     * Get all doctors with the given speciality.
     * @param especialidad
     * @return
     */
    public List<DoctorEntity> getDoctorByEspecialidad(EspecialidadEntity especialidad);

    /**
     * Create a new doctor
     * @param doctorEntity
     */
    public void createDoctor(DoctorEntity doctorEntity);

    /**
     * Update a doctor.
     * @param doctorEntity
     */
    public void updateDoctor(DoctorEntity doctorEntity);

    /**
     * Delete the doctor with the given id.
     * @param doctorId
     */
    public void deleteDoctor(Long doctorId);


}
