/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.test.persistence;

import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultaHistoricaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import co.edu.uniandes.waveteam.sistemahospital.persistence.ConsultaHistoricaPersistence;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import static org.junit.Assert.fail;
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
public class ConsultaHistoricaPersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ConsultaHistoricaEntity.class.getPackage())
                .addPackage(ConsultaHistoricaPersistence.class.getPackage())
                .addPackage(EspecialidadEntity.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    EspecialidadEntity padre;
    
    @Inject
    private ConsultaHistoricaPersistence persistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ConsultaHistoricaEntity> data = new ArrayList<>();
    
    
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
        em.createQuery("delete from ConsultaHistoricaEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        padre = factory.manufacturePojo(EspecialidadEntity.class);
        padre.setId(1L);
        em.persist(padre);
        for (int i = 0; i < 4; i++) {
            ConsultaHistoricaEntity entity = factory.manufacturePojo(ConsultaHistoricaEntity.class);
            
            entity.setEspecialidad(padre);
            
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            entity.setFecha(df.format(new Date()));
            
            data.add(entity);
            em.persist(entity);
        }
    }


    /**
     * Test of find method, of class ConsultaHistoricaPersistence.
     */
    @Test
    public void testFind() throws Exception {
        ConsultaHistoricaEntity entity = data.get(0);
        ConsultaHistoricaEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
    }
    
    @Test
    public void testFindAll() throws Exception {
        
        List<ConsultaHistoricaEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ConsultaHistoricaEntity ent : list) {
            boolean found = false;
            for (ConsultaHistoricaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void testCreate() {
        PodamFactory factory = new PodamFactoryImpl();
        ConsultaHistoricaEntity newEntity = factory.manufacturePojo(ConsultaHistoricaEntity.class);
        newEntity.setEspecialidad(padre);
        ConsultaHistoricaEntity result = persistence.create(newEntity);
        Assert.assertNotNull(result);

        ConsultaHistoricaEntity entity = em.find(ConsultaHistoricaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }
    
    @Test
    public void testFindAllInEspeciaildad() {
        List<ConsultaHistoricaEntity> list = persistence.findAllInEspecialidad(padre.getId());
        Assert.assertEquals(data.size(), list.size());
        for (ConsultaHistoricaEntity ent : list) {
            boolean found = false;
            for (ConsultaHistoricaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    } 
    
     @Test
    public void testFindAllInFecha() {
        List<ConsultaHistoricaEntity> list = persistence.findAllInFecha(data.get(0).getFecha());
        Assert.assertEquals(data.size(), list.size());
        for (ConsultaHistoricaEntity ent : list) {
            boolean found = false;
            for (ConsultaHistoricaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    } 
    
    @Test
    public void testDelete() {
        ConsultaHistoricaEntity entity = data.get(0);
        persistence.delete(entity.getId());
        ConsultaHistoricaEntity deleted = em.find(ConsultaHistoricaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}
