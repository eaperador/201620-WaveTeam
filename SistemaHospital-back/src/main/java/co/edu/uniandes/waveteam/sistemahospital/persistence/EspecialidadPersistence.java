/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.persistence;

import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author d.marino10
 */

@Stateless
public class EspecialidadPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(EspecialidadPersistence.class.getName());

    @PersistenceContext(unitName = "WaveteamPU")
    protected EntityManager em;
 
    public EspecialidadEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando especialidad con id={0}", id);
        return em.find(EspecialidadEntity.class, id);
    }
    
    public List<EspecialidadEntity> findAll() {
        LOGGER.info("Consultando todas las especialidades");
        Query q = em.createQuery("select u from EspecialidadEntity u");
        return q.getResultList();
    }
    
    public EspecialidadEntity create(EspecialidadEntity entity) {
        LOGGER.info("Creando una especialidad nueva");
        em.persist(entity);
        LOGGER.info("Especialidad creada");
        return entity;
    }
     
    public EspecialidadEntity update(EspecialidadEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando especialidad con id={0}", entity.getId());
        return em.merge(entity);
    }

    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando especialidad con id={0}", id);
        EspecialidadEntity entity = em.find(EspecialidadEntity.class, id);
        em.remove(entity);
    }
    
    public EspecialidadEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando una especialidad con name = {0}", name);
        TypedQuery<EspecialidadEntity> q 
            = em.createQuery("select u from EspecialidadEntity u where u.name = :name", EspecialidadEntity.class);
        q = q.setParameter("name", name); 
        List<EspecialidadEntity> lista = q.getResultList();
        if(lista.size()==0)
        {
            return null;
        }
        else
        return lista.get(0);
        
    }
}
