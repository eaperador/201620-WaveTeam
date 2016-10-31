/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.test.persistence;

import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import co.edu.uniandes.waveteam.sistemahospital.persistence.DoctorPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author felipeplazas
 */
@RunWith(Arquillian.class)
public class DoctorPersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DoctorEntity.class.getPackage())
                .addPackage(EspecialidadEntity.class.getPackage())
                .addPackage(DoctorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Inject
    private DoctorPersistence doctorPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    /**
     * Initial test configuration
     */
    @Before
    public void setUp() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    private void clearData() {
        em.createQuery("delete from DoctorEntity").executeUpdate();
    }

    private List<DoctorEntity> data = new ArrayList<>();
    
    /**
     * Inserts initial values
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            DoctorEntity entity = factory.manufacturePojo(DoctorEntity.class);

            EspecialidadEntity e = entity.getEspecialidad();
            e.getDoctores().add(entity);

            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void findByEspecialidadTest(){
        EspecialidadEntity especialidad = data.get(0).getEspecialidad();
        List<DoctorEntity> doctorsByEntities = doctorPersistence.findByEspecialidad(especialidad);
        for (DoctorEntity doctor : doctorsByEntities){
            Assert.assertEquals(especialidad.getId(), doctor.getEspecialidad().getId());
        }

//        especialidad = data.get(1).getEspecialidad();
//        doctorsByEntities = doctorPersistence.findByEspecialidad(especialidad);
//        for (DoctorEntity doctor : doctorsByEntities){
//            Assert.assertEquals(especialidad.getId(), doctor.getEspecialidad().getId());
//        }
//
//        especialidad = data.get(2).getEspecialidad();
//        doctorsByEntities = doctorPersistence.findByEspecialidad(especialidad);
//        for (DoctorEntity doctor : doctorsByEntities){
//            Assert.assertEquals(especialidad.getId(), doctor.getEspecialidad().getId());
//        }
    }
    
    @Test
    public void createDoctorTest(){
        PodamFactory factory = new PodamFactoryImpl();
        DoctorEntity docEntity = factory.manufacturePojo(DoctorEntity.class);
        DoctorEntity result = doctorPersistence.create(docEntity);
        Assert.assertNotNull(result);

        DoctorEntity entity = em.find(DoctorEntity.class, result.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(docEntity.getName(), entity.getName());
        Assert.assertEquals(docEntity.getConsultorio(), entity.getConsultorio());
        Assert.assertEquals(docEntity.getDisponibilidadCitas().size(), entity.getDisponibilidadCitas().size());
        Assert.assertEquals(docEntity.getEspecialidad(), entity.getEspecialidad());
    }
    
    @Test
    public void updateDoctorTest(){
        DoctorEntity entity = data.get(1);
        PodamFactory factory = new PodamFactoryImpl();
        DoctorEntity newEntity = factory.manufacturePojo(DoctorEntity.class);
        newEntity.setId(entity.getId());
        doctorPersistence.update(newEntity);
        
        DoctorEntity result = em.find(DoctorEntity.class, entity.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(newEntity.getName(), result.getName());
        Assert.assertEquals(newEntity.getConsultorio(), result.getConsultorio());
        Assert.assertEquals(newEntity.getEspecialidad().getName(), result.getEspecialidad().getName());
    }
    
    @Test
    public void getDoctorsTest(){
        List<DoctorEntity> list = doctorPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (DoctorEntity entity: list){
            boolean found = false;
            for (DoctorEntity entity2: data){
                if ( entity.getId().equals(entity2.getId() ) ){ 
                    found = true;
                    Assert.assertEquals(entity2.getName(), entity.getName());
                    Assert.assertEquals(entity2.getConsultorio(), entity.getConsultorio());
                    Assert.assertEquals(entity2.getDisponibilidadCitas().size(), entity.getDisponibilidadCitas().size());
                    Assert.assertEquals(entity2.getEspecialidad(), entity.getEspecialidad());
                    break;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getDoctorTest(){
        DoctorEntity entity = data.get(2);
        DoctorEntity newEntity = em.find(DoctorEntity.class, entity.getId());
        DoctorEntity newEntity2 = doctorPersistence.find(entity.getId());
        
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
        Assert.assertEquals(entity.getConsultorio(), newEntity.getConsultorio());
        Assert.assertEquals(entity.getDisponibilidadCitas().size(), newEntity.getDisponibilidadCitas().size());
        Assert.assertEquals(entity.getEspecialidad(), newEntity.getEspecialidad());
        
        Assert.assertNotNull(newEntity2);
        Assert.assertEquals(entity.getName(), newEntity2.getName());
        Assert.assertEquals(entity.getConsultorio(), newEntity2.getConsultorio());
        Assert.assertEquals(entity.getDisponibilidadCitas().size(), newEntity2.getDisponibilidadCitas().size());
        Assert.assertEquals(entity.getEspecialidad(), newEntity2.getEspecialidad());
    }
    
    @Test
    public void getDoctorByNameTest(){
        DoctorEntity entity = data.get(0);
        DoctorEntity newEntity = doctorPersistence.findByName(entity.getName());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
        Assert.assertEquals(entity.getConsultorio(), newEntity.getConsultorio());
        Assert.assertEquals(entity.getDisponibilidadCitas().size(), newEntity.getDisponibilidadCitas().size());
        Assert.assertEquals(entity.getEspecialidad(), newEntity.getEspecialidad());
    }
    
    @Test
    public void deleteDoctorTest(){
        DoctorEntity entity = data.get(data.size()-1);
        doctorPersistence.delete(entity.getId());
        DoctorEntity deleted = doctorPersistence.find(entity.getId());
        DoctorEntity deleted2 = em.find(DoctorEntity.class, entity.getId());
        
        Assert.assertNull(deleted);
        Assert.assertNull(deleted2);
    }

}
