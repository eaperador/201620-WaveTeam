/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.ejbs;

import co.edu.uniandes.waveteam.sistemahospital.api.IConsultaHistoricaLogic;
import co.edu.uniandes.waveteam.sistemahospital.api.IEspecialidadLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultaHistoricaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import co.edu.uniandes.waveteam.sistemahospital.persistence.ConsultaHistoricaPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author d.marino10
 */
@Stateless
public class ConsultaHistoricaLogic implements IConsultaHistoricaLogic{

    @Inject
    private ConsultaHistoricaPersistence persistence;

    @Inject
    private IEspecialidadLogic espLogic;
    
    @Override
    public List<ConsultaHistoricaEntity> getTodasLasConsultasHistoricas() {
        return persistence.findAll();
    }

    @Override
    public List<ConsultaHistoricaEntity> getConsultasHistoricasPorEspecialidad(Long espId) {
        EspecialidadEntity esp = espLogic.getEspecialidad(espId);
        return esp.getConsultaActual();
    }
    
    @Override
    public List<ConsultaHistoricaEntity> getConsultasHistoricasPorFecha(String fecha) {
        return persistence.findAllInFecha(fecha);
    }

    @Override
    public ConsultaHistoricaEntity getConsultaHistorica(Long id) {
        return persistence.find(id);
    }

    @Override
    public ConsultaHistoricaEntity createConsultaHistorica(EspecialidadEntity esp) {
        ConsultaHistoricaEntity c = new ConsultaHistoricaEntity();
        c.setEspecialidad(esp);
        c.setNumeroDoctores(esp.getDoctores().size());
        return persistence.create(c);
    }

    @Override
    public void deleteConsultaHistorica(Long id) {
        persistence.delete(id);
    }
      
}
