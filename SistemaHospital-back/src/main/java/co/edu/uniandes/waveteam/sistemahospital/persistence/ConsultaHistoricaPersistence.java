/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.persistence;

import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultaHistoricaEntity;
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
public class ConsultaHistoricaPersistence {

        
    private static final Logger LOGGER = Logger.getLogger(EspecialidadPersistence.class.getName());

    @PersistenceContext(unitName = "WaveteamPU")
    protected EntityManager em;
 
    public ConsultaHistoricaEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando consultaHistorica con id={0}", id);
        return em.find(ConsultaHistoricaEntity.class, id);
    }
    
    public List<ConsultaHistoricaEntity> findAll() {
        LOGGER.info("Consultando todas las consultas historicas");
        Query q = em.createQuery("select u from ConsultaHistoricaEntity u");
        return q.getResultList();
    }

    public List<ConsultaHistoricaEntity> findAllInEspecialidad(Long espId) {
        LOGGER.log(Level.INFO, "Consultando todas las consultasHistoricas de la especialidad id={0}", espId);
        TypedQuery q = em.createQuery("select d from ConsultaHistoricaEntity d  where d.especialidad.id = :espId", ConsultaHistoricaEntity.class);
        q = q.setParameter("espId", espId);
        return q.getResultList();
    }
    
    public List<ConsultaHistoricaEntity> findAllInFecha(String fecha) {
        LOGGER.log(Level.INFO, "Consultando todas las consultasHistoricas en fecha={0} ", fecha);
        TypedQuery q = em.createQuery("select d from ConsultaHistoricaEntity d  where d.fecha = :f", ConsultaHistoricaEntity.class);
        q = q.setParameter("f", fecha);
        return q.getResultList();
    }

    public ConsultaHistoricaEntity create(ConsultaHistoricaEntity entity) {
        LOGGER.info("Creando una consulta historica nueva");
        em.persist(entity);
        LOGGER.info("Consulta historica creada");
        return entity;
    }

    /**
     *
     * @param id: corresponde a un id válido que existe el deptarment
     * crrespondiente en la base de datos.
     */
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando consulta historica con id={0}", id);
        ConsultaHistoricaEntity entity = em.find(ConsultaHistoricaEntity.class, id);
        assert entity != null;
        em.remove(entity);
    }
}
