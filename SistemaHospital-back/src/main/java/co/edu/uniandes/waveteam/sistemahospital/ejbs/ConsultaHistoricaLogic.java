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
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 *
 * @author d.marino10
 */
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ConsultaHistoricaEntity getConsultaHistorica(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ConsultaHistoricaEntity createConsultaHistorica(EspecialidadEntity esp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteEspecialidad(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
      
}
