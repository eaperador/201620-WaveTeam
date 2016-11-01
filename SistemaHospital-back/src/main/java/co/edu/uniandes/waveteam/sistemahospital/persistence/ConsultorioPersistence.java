/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.persistence;

import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultorioEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
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
 * @author r.garcia11eeeeeeeeeee y6
 */
@Stateless
public class ConsultorioPersistence {

    private static final Logger LOGGER = Logger.getLogger(ConsultorioPersistence.class.getName());

    @PersistenceContext(unitName = "WaveteamPU")
    protected EntityManager em;

    public ConsultorioEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando consultorio con id={0}", id);
        return em.find(ConsultorioEntity.class, id);
    }

    public ConsultorioEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando consultorio con nombre={}", name);
        TypedQuery<ConsultorioEntity> q
                = em.createQuery("select u from ConsultorioEntity u where u.name = :name", ConsultorioEntity.class);
        q = q.setParameter("name", name);
        return q.getSingleResult();
    }

    public List<ConsultorioEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los consultorios");
        Query q = em.createQuery("select u from ConsultorioEntity u");
        return q.getResultList();
    }

    public ConsultorioEntity create(ConsultorioEntity nuevoConsultorio) {
        LOGGER.log(Level.INFO, "Creando un consultorio");
        em.persist(nuevoConsultorio);
        LOGGER.log(Level.INFO, "Consultorio creado");
        return nuevoConsultorio;
    }

    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Eliminando un consultorio con id={0}", id);
        ConsultorioEntity entity = em.find(ConsultorioEntity.class, id);
        em.remove(entity);
    }

    public ConsultorioEntity update(ConsultorioEntity consultorioActualizado) {
        LOGGER.log(Level.INFO, "Actualizando el consultorio con id={0}", consultorioActualizado.getId());
        return em.merge(consultorioActualizado);
    }

    public ConsultorioEntity unasignDoctors(Long id) {
        LOGGER.log(Level.INFO, "Desasignando doctores de un consultorio");
        ConsultorioEntity cons = em.find(ConsultorioEntity.class, id);
        cons.elminarDoctores();
        return em.merge(cons);
    }

    public ConsultorioEntity unasignDoctor(Long idConsultorio, Long idDoctor) {
        LOGGER.log(Level.INFO, "Desasignando un doctor de un consultorio");
        ConsultorioEntity cons = em.find(ConsultorioEntity.class, idConsultorio);
        cons.eliminarDoctorAsignado(idDoctor);
        return em.merge(cons);
    }

    public ConsultorioEntity asignDoctor(Long idConsultorio, DoctorEntity doc) {
        LOGGER.log(Level.INFO, "Asignando un doctor a un consultorio");
        ConsultorioEntity cons = em.find(ConsultorioEntity.class, idConsultorio);
        cons.agregarDoctorAsignado(doc);
        return em.merge(cons);
    }
    
    public ConsultorioEntity asignDoctors(Long idConsultorio, List<DoctorEntity> docs){
        LOGGER.log(Level.INFO, "Asignando una lista de doctores a un consultorio");
        ConsultorioEntity cons = em.find(ConsultorioEntity.class, idConsultorio);
        cons.setDoctoresAsignados(docs);
        return em.merge(cons);
    }
}
