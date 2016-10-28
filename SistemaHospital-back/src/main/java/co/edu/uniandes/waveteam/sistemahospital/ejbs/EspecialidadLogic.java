/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.ejbs;

import co.edu.uniandes.waveteam.sistemahospital.api.IEspecialidadLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import co.edu.uniandes.waveteam.sistemahospital.persistence.EspecialidadPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author d.marino10
 */
@Stateless
public class EspecialidadLogic implements IEspecialidadLogic{
    
    @Inject
    private EspecialidadPersistence persistence;

    @Override
    public List<EspecialidadEntity> getEspecialidades() {
        return persistence.findAll();
    }

    @Override
    public EspecialidadEntity getEspecialidad(Long id) {
        return persistence.find(id);
    }

    @Override
    public EspecialidadEntity getEspecialidadPorNombre(String name) {
        return persistence.findByName(name);
    }

    @Override
    public EspecialidadEntity createEspecialidad(EspecialidadEntity entity) {
        EspecialidadEntity bus = persistence.findByName(entity.getName());
        if(bus!=null)
        {
            
        }
        return persistence.create(entity);
    }

    @Override
    public EspecialidadEntity updateEspecialidad(EspecialidadEntity entity) {
        return persistence.update(entity);
    }

    @Override
    public void deleteEspecialidad(Long id) {
        persistence.delete(id);
    }
    
}
