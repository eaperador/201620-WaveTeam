package co.edu.uniandes.waveteam.sistemahospital.persistence;

import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Created by felipeplazas on 10/11/16.
 */
@Stateless
public class DoctorPersistence {

    private static final Logger LOGGER = Logger.getLogger(DoctorPersistence.class.getName());

    @PersistenceContext(unitName = "WaveteamPU")
    protected EntityManager em;

    public DoctorEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando doctor con id={0}", id);
        return em.find(DoctorEntity.class, id);
    }
        
    public List<DoctorEntity> findByEspecialidad(EspecialidadEntity especialidad){
        String name = especialidad.getName();
        LOGGER.log(Level.INFO, "Consultando doctores con la dada especialidad");
        
        Query q = em.createQuery("select d from DoctorEntity d where d.especialidad.name = :name");
        q.setParameter("name", name);
        LOGGER.log(Level.INFO, "Retornando doctores de la dada especialidad");
        
        return q.getResultList();
    }
    
    public DoctorEntity findByName(String name){
        LOGGER.log(Level.INFO, "Consultando doctor con name= ", name);
        TypedQuery<DoctorEntity> q
                = em.createQuery("select d from DoctorEntity d where d.name = :name", DoctorEntity.class);
        q = q.setParameter("name", name);
        return q.getSingleResult();
    }
    
    public List<DoctorEntity> findAll() {
        LOGGER.info("Consultando todos los doctores");
        Query q = em.createQuery("select d from DoctorEntity d");
        return q.getResultList();
    }
    
    public DoctorEntity create(DoctorEntity entity) {
        LOGGER.info("Creando un doctor nuevo");
        em.persist(entity);
        LOGGER.info("Doctor creado");
        return entity;
    }
    
    public DoctorEntity update(DoctorEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando doctor con id={0}", entity.getId());
        return em.merge(entity);
    }
    
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando doctor con id={0}", id);
        DoctorEntity entity = em.find(DoctorEntity.class, id);
        em.remove(entity);
    }

}
