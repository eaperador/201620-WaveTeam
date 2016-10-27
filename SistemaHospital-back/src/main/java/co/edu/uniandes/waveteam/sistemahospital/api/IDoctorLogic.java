package co.edu.uniandes.waveteam.sistemahospital.api;

import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;

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
     * @return
     */
    public DoctorEntity getDoctorById();

    /**
     * Get a doctor by his/her name.
     * @return
     */
    public DoctorEntity getDoctorByName();

    /**
     * Get all doctors with the given speciality.
     * @return
     */
    public List<DoctorEntity> getDoctorByEspecialidad();

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
