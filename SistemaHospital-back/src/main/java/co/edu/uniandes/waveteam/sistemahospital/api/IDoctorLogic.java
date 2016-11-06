package co.edu.uniandes.waveteam.sistemahospital.api;

import co.edu.uniandes.waveteam.sistemahospital.entities.CitaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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
     * Get the last inserted doctor
     * @return Doctor
     * @throws WaveTeamLogicException 
     */
    public DoctorEntity getLastInsertedDoctor() throws WaveTeamLogicException;

    /**
     * Get all doctors with the given specialty.
     * @param especialidad
     * @return
     */
    public List<DoctorEntity> getDoctorByEspecialidad(EspecialidadEntity especialidad);

    /**
     * Create a new doctor instance, if the given specialty does not exist, it is created.
     * @param doctorEntity
     * @return Created Doctor ID.
     */
    public DoctorEntity createDoctor(DoctorEntity doctorEntity) throws WaveTeamLogicException;

    /**
     * Update a doctor.
     * @param doctorEntity
     * @throws WaveTeamLogicException 
     */
    public void updateDoctor(DoctorEntity doctorEntity) throws WaveTeamLogicException;

    /**
     * Delete the doctor with the given id.
     * @param doctorId
     */
    public void deleteDoctor(Long doctorId) throws WaveTeamLogicException;
    
    /**
     * Set this doctor availability
     */
    public void setDisponibilidad(Long doctorID, ArrayList<LinkedHashMap> days);
    
    /**
     * Get this doctor availability
     */
    public List<CitaEntity> getDisponibilidad(Long doctorID);
}
