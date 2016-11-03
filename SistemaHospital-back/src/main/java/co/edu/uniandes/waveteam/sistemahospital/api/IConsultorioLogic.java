/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.api;

import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultorioEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;
import java.util.List;

/**
 *
 * @author r.garcia11
 */
public interface IConsultorioLogic {
    
    public List<ConsultorioEntity> getConsultorios();
            
    public ConsultorioEntity getConsultorio(Long id);

    public ConsultorioEntity findByName(String name);
    
    public ConsultorioEntity updateConsultorio(ConsultorioEntity consultorioActualizado) throws WaveTeamLogicException;

    public ConsultorioEntity createConsultorio(ConsultorioEntity consultorio) throws WaveTeamLogicException;

    public void deleteConsultorio(Long id) throws WaveTeamLogicException;

    public ConsultorioEntity unasignDoctors(Long id) throws WaveTeamLogicException;

    public ConsultorioEntity unasignDoctor(Long idConsultorio, Long idDoctor) throws WaveTeamLogicException;

    public ConsultorioEntity asignDoctor(Long idConsultorio, DoctorEntity doc) throws WaveTeamLogicException;
}
