/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.test.logic;

import co.edu.uniandes.waveteam.sistemahospital.api.IConsultaHistoricaLogic;
import co.edu.uniandes.waveteam.sistemahospital.ejbs.ConsultaHistoricaLogic;
import co.edu.uniandes.waveteam.sistemahospital.ejbs.ConsultaHistoricaLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultaHistoricaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import co.edu.uniandes.waveteam.sistemahospital.persistence.ConsultaHistoricaPersistence;
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
 * @author d.marino10
 */
@RunWith(Arquillian.class)
public class ConsultaHistoricaLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    EspecialidadEntity padre;

    @Inject
    private IConsultaHistoricaLogic logic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ConsultaHistoricaEntity> data = new ArrayList<ConsultaHistoricaEntity>();
    
     @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ConsultaHistoricaEntity.class.getPackage())
                .addPackage(ConsultaHistoricaLogic.class.getPackage())
                .addPackage(IConsultaHistoricaLogic.class.getPackage())
                .addPackage(ConsultaHistoricaPersistence.class.getPackage())
                .addPackage(EspecialidadEntity.class.getPackage())
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
        em.createQuery("delete from ConsultaHistoricaEntity").executeUpdate();
        em.createQuery("delete from EspecialidadEntity").executeUpdate();
    }
     
    private void insertData() {
        padre = factory.manufacturePojo(EspecialidadEntity.class);
        padre.setId(1L);
        em.persist(padre);
        for (int i = 0; i < 4; i++) {
            ConsultaHistoricaEntity entity = factory.manufacturePojo(ConsultaHistoricaEntity.class);
            entity.setEspecialidad(padre);
            entity.setFecha("07/23/2016");
            em.persist(entity);
            data.add(entity);
        }
    }
    

    /**
     * Test of getTodasLasConsultasHistoricas method, of class ConsultaHistoricaLogic.
     */
    @Test
    public void testGetTodasLasConsultasHistoricas() {
        List<ConsultaHistoricaEntity> list = logic.getTodasLasConsultasHistoricas();
        Assert.assertEquals(data.size(), list.size());
        for (ConsultaHistoricaEntity entity : list) {
            boolean found = false;
            for (ConsultaHistoricaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Test of getConsultasHistoricasPorEspecialidad method, of class ConsultaHistoricaLogic.
     */
    @Test
    public void testGetConsultasHistoricasPorEspecialidad() {
        
        List<ConsultaHistoricaEntity> list = logic.getConsultasHistoricasPorEspecialidad(data.get(0).getEspecialidad().getId());
        Assert.assertEquals(data.size(), list.size());
         for (ConsultaHistoricaEntity entity : list) {
            boolean found = false;
            for (ConsultaHistoricaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
        
    }

    /**
     * Test of getConsultasHistoricasPorFecha method, of class ConsultaHistoricaLogic.
     */
    @Test
    public void testGetConsultasHistoricasPorFecha() {
        
        List<ConsultaHistoricaEntity> list = logic.getConsultasHistoricasPorFecha(data.get(0).getFecha());
        Assert.assertEquals(data.size(), list.size());
         for (ConsultaHistoricaEntity entity : list) {
            boolean found = false;
            for (ConsultaHistoricaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Test of getConsultaHistorica method, of class ConsultaHistoricaLogic.
     */
    @Test
    public void testGetConsultaHistorica() {
        
        ConsultaHistoricaEntity entity = data.get(0);
        ConsultaHistoricaEntity resultEntity = logic.getConsultaHistorica(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }

    /**
     * Test of createConsultaHistorica methsod, of class ConsultaHistoricaLogic.
     */
    @Test
    public void testCreateConsultaHistorica() {
        ConsultaHistoricaEntity entity = logic.createConsultaHistorica(padre.getName());
 
        Assert.assertNotNull(entity);
        Assert.assertEquals(entity.getEspecialidad().getName(),padre.getName());
        Assert.assertEquals(entity.getNumeroDoctores(), padre.getDoctores().size());
        
    }

    /**
     * Test of deleteConsultaHistorica method, of class ConsultaHistoricaLogic.
     */
    @Test
    public void testDeleteConsultaHistorica() {
        ConsultaHistoricaEntity entity = data.get(1);
        logic.deleteConsultaHistorica(entity.getId());
        ConsultaHistoricaEntity deleted = em.find(ConsultaHistoricaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
