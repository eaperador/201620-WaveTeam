/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.api;

import co.edu.uniandes.waveteam.sistemahospital.entities.CitaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.PacienteEntity;
import java.util.List;

/**
 *
 * @author jm.lizarazo10
 */
public interface ICitaLogic {
    
    public List<CitaEntity> getCitas();
    public CitaEntity getCita(Long id);
    public CitaEntity createCita(CitaEntity entity);
    public CitaEntity updateCita(CitaEntity entity);
    public void deleteCita(Long id);
    public List<CitaEntity> getCitasByPaciente(PacienteEntity paciente);
    public List<CitaEntity> getCitasByDoctorEnFecha(DoctorEntity doctor, Long fechaInicio, Long fechaFin);
    public List<CitaEntity> getCitasByPacienteEnFecha(PacienteEntity paciente, Long fechaInicio, Long fechaFin);
    
}
