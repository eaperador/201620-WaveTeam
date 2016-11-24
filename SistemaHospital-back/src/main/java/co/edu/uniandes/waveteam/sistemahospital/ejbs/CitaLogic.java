package co.edu.uniandes.waveteam.sistemahospital.ejbs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import co.edu.uniandes.waveteam.sistemahospital.api.ICitaLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.CitaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.PacienteEntity;
import co.edu.uniandes.waveteam.sistemahospital.persistence.CitaPersistence;
import co.edu.uniandes.waveteam.sistemahospital.persistence.DoctorPersistence;
import co.edu.uniandes.waveteam.sistemahospital.persistence.PacientePersistence;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author jm.lizarazo10
 */
@Stateless 
public class CitaLogic implements ICitaLogic{
    
    private static final Logger LOGGER = Logger.getLogger(CitaPersistence.class.getName());
    @Inject private CitaPersistence persistence;
    

    @Override
    public List<CitaEntity> getCitas() {
        return persistence.findAll();
    }

    @Override
    public CitaEntity getCita(Long id) {
        return persistence.find(id);
    }

    @Override
    public CitaEntity createCita(CitaEntity entity) {
        persistence.create(entity);
        
        LOGGER.info("Método create cita en CitaLogic: Fecha=" +entity.getFecha());
        return entity;
    }

    @Override
    public CitaEntity updateCita(CitaEntity entity) {
        return persistence.update(entity);
        
    }

    @Override
    public void deleteCita(Long id) {
        persistence.delete(id);
    }
    
    @Override
    public List<CitaEntity> getCitasByPaciente(Long paciente){
        return persistence.findByPaciente(paciente);
    }
    
    @Override
    public List<CitaEntity> getCitasByDoctor(Long doctor){
        return persistence.findByDoctor(doctor);
    }
    
    @Override
    public List<CitaEntity> getCitasByDoctorEnFecha(Long doctor, Long fechaInicio, Long fechaFin){        
        return persistence.findByDoctorEnFecha(doctor, fechaInicio, fechaFin);
        
    }
    
    @Override
    public List<CitaEntity> getCitasByPacienteEnFecha(Long paciente, Long fechaInicio, Long fechaFin){
        return persistence.findByPacienteEnFecha(paciente, fechaInicio, fechaFin);
    }

}
