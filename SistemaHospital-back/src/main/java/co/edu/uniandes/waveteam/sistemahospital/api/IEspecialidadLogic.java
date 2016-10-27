/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.api;

import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import java.util.List;

/**
 *
 * @author d.marino10
 */
public interface IEspecialidadLogic {
    
    public List<EspecialidadEntity> getEspecialidades();

    public EspecialidadEntity getEspecialidad(Long id);

    public EspecialidadEntity getEspecialidadPorNombre(String name);

    public EspecialidadEntity createEspecialidad(EspecialidadEntity entity);

    public EspecialidadEntity updateEspecialidad(EspecialidadEntity entity);

    public void deleteEspecialidad(Long id);

    public Integer getNumeroDoctoresEspecialidad(Long id);
}
