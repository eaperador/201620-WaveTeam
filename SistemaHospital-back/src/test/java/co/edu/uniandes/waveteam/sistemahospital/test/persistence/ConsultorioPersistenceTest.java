/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.test.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultorioEntity;
import co.edu.uniandes.waveteam.sistemahospital.persistence.ConsultorioPersistence;
import org.jboss.arquillian.container.test.api.Deployment;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author r.garcia11
 */
@RunWith(Arquillian.class)
public class ConsultorioPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ConsultorioEntity.class.getPackage())
                .addPackage(ConsultorioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    private ConsultorioPersistence consultorioPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ConsultorioEntity> data = new ArrayList<ConsultorioEntity>();

    public ConsultorioPersistenceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void clearData() {
        em.createQuery("delete from ConsultorioEntity").executeUpdate();
    }

    public void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i=0; i<3; i++){
            ConsultorioEntity entity = factory.manufacturePojo(ConsultorioEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of find method, of class ConsultorioPersistence.
     */
    @Test
    public void testFind(){
        ConsultorioEntity consultorioEntity = data.get(0);
        ConsultorioEntity encontrado = consultorioPersistence.find(consultorioEntity.getId());
        
        Assert.assertNotNull(encontrado);
        Assert.assertEquals(encontrado.getName(), consultorioEntity.getName());
        Assert.assertEquals(encontrado.getId(), consultorioEntity.getId());
        Assert.assertEquals(encontrado.getUnidadCuidadosintensivos(), consultorioEntity.getUnidadCuidadosintensivos());
        Assert.assertEquals(encontrado.getHorario(), consultorioEntity.getHorario());
        Assert.assertEquals(encontrado.getAtencionUrgencias(), consultorioEntity.getAtencionUrgencias());
    }

    /**
     * Test of findByName method, of class ConsultorioPersistence.
     */
    @Test
    public void testFindByName(){
         String nombre = data.get(2).getName();
         ConsultorioEntity encontrado = consultorioPersistence.findByName(nombre);
         
         Assert.assertNotNull(encontrado);
         Assert.assertEquals(encontrado.getName(), nombre);
         
    }

    /**
     * Test of findAll method, of class ConsultorioPersistence.
     */
    @Test
    public void testFindAll(){
        List<ConsultorioEntity> consultorios = consultorioPersistence.findAll();
        
        Assert.assertEquals(consultorios.size(), data.size());
        for (ConsultorioEntity consultorio : consultorios){
            boolean found = false;
            for (ConsultorioEntity consultorio2 : data){
                if (consultorio2.getId().equals(consultorio.getId()))
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Test of create method, of class ConsultorioPersistence.
     */
    @Test
    public void testCreate(){
        PodamFactory factory = new PodamFactoryImpl();
        ConsultorioEntity consultorioEntity = factory.manufacturePojo(ConsultorioEntity.class);
        ConsultorioEntity result = consultorioPersistence.create(consultorioEntity);
        Assert.assertNotNull(result);
        ConsultorioEntity consEntity = em.find(ConsultorioEntity.class, result.getId());
        Assert.assertNotNull(consEntity);
        Assert.assertEquals(consEntity.getName(), consultorioEntity.getName());
    }

    /**
     * Test of delete method, of class ConsultorioPersistence.
     */
    @Test
    public void testDelete(){
        ConsultorioEntity consultorioEntity = data.get(0);
        consultorioPersistence.delete(consultorioEntity.getId());
        ConsultorioEntity borrado = em.find(ConsultorioEntity.class, consultorioEntity.getId());
        Assert.assertNull(borrado);
    }

    /**
     * Test of update method, of class ConsultorioPersistence.
     */
    @Test
    public void testUpdate(){
        ConsultorioEntity consultorioEntity = data.get(1);
        PodamFactory factory = new PodamFactoryImpl();
        ConsultorioEntity nuevo = factory.manufacturePojo(ConsultorioEntity.class);
        nuevo.setId(consultorioEntity.getId());
        ConsultorioEntity actualizado = consultorioPersistence.update(nuevo);
        
        ConsultorioEntity found = em.find(ConsultorioEntity.class, actualizado.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(found.getId(), actualizado.getId());
        Assert.assertEquals(found.getHorario(), actualizado.getHorario());
        Assert.assertEquals(found.getAtencionUrgencias(), actualizado.getAtencionUrgencias());
        Assert.assertEquals(found.getName(), actualizado.getName());
        Assert.assertEquals(found.getUnidadCuidadosintensivos(), actualizado.getUnidadCuidadosintensivos());
    }

}
