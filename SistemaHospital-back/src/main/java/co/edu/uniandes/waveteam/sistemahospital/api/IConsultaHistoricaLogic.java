/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.api;

import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultaHistoricaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import java.util.List;

/**
 *
 * @author d.marino10
 */
public interface IConsultaHistoricaLogic {
    
    public List<ConsultaHistoricaEntity> getTodasLasConsultasHistoricas();

    public List<ConsultaHistoricaEntity> getConsultasHistoricasPorEspecialidad(Long espId);

    public List<ConsultaHistoricaEntity> getConsultasHistoricasPorFecha(String fecha);

    public ConsultaHistoricaEntity getConsultaHistorica(Long id);

    public ConsultaHistoricaEntity createConsultaHistorica(String nombreEsp);

    public void deleteConsultaHistorica(Long id);

}
