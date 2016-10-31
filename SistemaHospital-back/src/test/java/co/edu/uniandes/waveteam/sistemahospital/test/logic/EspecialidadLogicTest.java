/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.test.logic;

import co.edu.uniandes.waveteam.sistemahospital.api.IDoctorLogic;
import co.edu.uniandes.waveteam.sistemahospital.api.IEspecialidadLogic;
import co.edu.uniandes.waveteam.sistemahospital.ejbs.DoctorLogic;
import co.edu.uniandes.waveteam.sistemahospital.ejbs.EspecialidadLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import co.edu.uniandes.waveteam.sistemahospital.persistence.DoctorPersistence;
import co.edu.uniandes.waveteam.sistemahospital.persistence.EspecialidadPersistence;
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author d.marino10
 */
@RunWith(Arquillian.class)
public class EspecialidadLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private IEspecialidadLogic logic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EspecialidadEntity> data = new ArrayList<EspecialidadEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EspecialidadEntity.class.getPackage())
                .addPackage(EspecialidadLogic.class.getPackage())
                .addPackage(IEspecialidadLogic.class.getPackage())
                .addPackage(EspecialidadPersistence.class.getPackage())
                .addPackage(DoctorPersistence.class.getPackage())
                .addPackage(DoctorEntity.class.getPackage())
                .addPackage(DoctorLogic.class.getPackage())
                .addPackage(IDoctorLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void setUp() {
        try {
            utx.begin();
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
        em.createQuery("delete from EspecialidadEntity").executeUpdate();
    }
    
    private void insertData() {
         for (int i = 0; i < 4; i++) {
            EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);
            for (DoctorEntity d : entity.getDoctores()) {
                d.setEspecialidad(entity);
            }
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Test of getEspecialidades method, of class EspecialidadLogic.
     */
    @Test
    public void testGetEspecialidades(){
        
        List<EspecialidadEntity> list = logic.getEspecialidades();
        Assert.assertEquals(data.size(), list.size());
        for (EspecialidadEntity entity : list) {
            boolean found = false;
            for (EspecialidadEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Test of getEspecialidad method, of class EspecialidadLogic.
     */
    @Test
    public void testGetEspecialidad1() {
        EspecialidadEntity entity = data.get(0);
        EspecialidadEntity resultEntity = logic.getEspecialidad(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }
    
    @Test
    public void testGetEspecialidad2(){
        
        EspecialidadEntity entity = logic.getEspecialidad(-100L);
        Assert.assertNull(entity);
    }

    /**
     * Test of getEspecialidadPorNombre method, of class EspecialidadLogic.
     */
    @Test
    public void testGetEspecialidadPorNombre1(){
        
        EspecialidadEntity entity = data.get(0);
        EspecialidadEntity resultEntity = logic.getEspecialidadPorNombre(entity.getName());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }
    
    @Test
    public void testGetEspecialidadPorNombre2(){
        
        EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);
        EspecialidadEntity resultEntity = logic.getEspecialidadPorNombre(entity.getName());
        Assert.assertNull(resultEntity);
        
    }

    
    /**
     * Test of createEspecialidad method, of class EspecialidadLogic.
     */
    @Test
    public void testCreateEspecialidad(){
    }

    /**
     * Test of updateEspecialidad method, of class EspecialidadLogic.
     */
    @Test
    public void testUpdateEspecialidad(){
        EspecialidadEntity entity = data.get(0);
        EspecialidadEntity pojoEntity = factory.manufacturePojo(EspecialidadEntity.class);

        pojoEntity.setId(entity.getId());
        logic.updateEspecialidad(pojoEntity);

        EspecialidadEntity resp = em.find(EspecialidadEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }

    /**
     * Test of deleteEspecialidad method, of class EspecialidadLogic.
     */
    @Test
    public void testDeleteEspecialidad(){
        
        EspecialidadEntity entity = data.get(1);
        logic.deleteEspecialidad(entity.getId());
        EspecialidadEntity deleted = em.find(EspecialidadEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
