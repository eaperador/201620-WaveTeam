/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.persistence;

import co.edu.uniandes.waveteam.sistemahospital.entities.CitaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.PacienteEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class PacientePersistence {

    private static final Logger LOGGER = Logger.getLogger(PacientePersistence.class.getName());
    @PersistenceContext(unitName = "WaveteamPU")
    protected EntityManager em;
    
    public PacienteEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando paciente con id={0}", id);
        return em.find(PacienteEntity.class, id);
    }
    
    public PacienteEntity findByName(String pname) {
        LOGGER.log(Level.INFO, "Consultando paciente con name={}", pname);
        TypedQuery<PacienteEntity> query = em.createQuery("select u from PacienteEntity u where u.name = :pname", PacienteEntity.class);
        query = query.setParameter("pname", pname);
        return query.getSingleResult();
    }
    
    public List<PacienteEntity> findAll()
    {
      LOGGER.info("Consultando todos los pacientes");
      Query q = em.createQuery("select u from PacienteEntity u");
      return q.getResultList();
    }
    public PacienteEntity create(PacienteEntity entity)
    {
        LOGGER.info("Creando un paciente nuevo");
        em.persist(entity);
        LOGGER.info("el Paciente ha sido creado");
        return entity;
    }
    public PacienteEntity update(PacienteEntity entity)
    {
        LOGGER.log(Level.INFO,"Actualizando Paciente con id={0}", entity.getId());
        return em.merge(entity);
    }
    public void delete(Long id)
    {
        LOGGER.log(Level.INFO,"Borrando paciente con id={0}",id);
        PacienteEntity entity = em.find(PacienteEntity.class,id);
        System.out.println("**********************************************************el paciente q encontro : " + entity);
        em.remove(entity);
    }
    
    /**
     * 
     * @param Id del paciente.
     * @return 
     */
    public List<PacienteEntity> findAllCitasPaciente(Long Idpaciente) {
        LOGGER.log(Level.INFO, "consultandi todas las citas del paciente con id={0}", Idpaciente);
        TypedQuery<PacienteEntity> query = em.createQuery("select u.paciente from CitaEntity u where u.paciente.id:pId", PacienteEntity.class);
        query = query.setParameter("pId", Idpaciente);
        return query.getResultList();
    }
    
    public void asignarCitaPaciente (Long IdCita, Long IdPaciente){
        LOGGER.log(Level.INFO, "asignando al paciente con id={0}", IdPaciente);
        
    }
}
