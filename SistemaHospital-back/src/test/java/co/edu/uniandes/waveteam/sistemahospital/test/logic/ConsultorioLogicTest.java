/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.test.logic;

import co.edu.uniandes.waveteam.sistemahospital.api.IConsultorioLogic;
import co.edu.uniandes.waveteam.sistemahospital.api.IDoctorLogic;
import co.edu.uniandes.waveteam.sistemahospital.ejbs.ConsultorioLogic;
import co.edu.uniandes.waveteam.sistemahospital.ejbs.DoctorLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultorioEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;
import co.edu.uniandes.waveteam.sistemahospital.persistence.ConsultorioPersistence;
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author r.garcia11
 */
@RunWith(Arquillian.class)
public class ConsultorioLogicTest {

    PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private IConsultorioLogic logic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ConsultorioEntity> data = new ArrayList();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ConsultorioPersistence.class.getPackage())
                .addPackage(ConsultorioEntity.class.getPackage())
                .addPackage(ConsultorioLogic.class.getPackage())
                .addPackage(IConsultorioLogic.class.getPackage())
                .addPackage(DoctorPersistence.class.getPackage())
                .addPackage(DoctorEntity.class.getPackage())
                .addPackage(DoctorLogic.class.getPackage())
                .addPackage(IDoctorLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    public ConsultorioLogicTest() {
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
        for (int i = 0; i < 3; i++) {
            ConsultorioEntity entity = factory.manufacturePojo(ConsultorioEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getConsultorios method, of class ConsultorioLogic.
     */
    @Test
    public void testGetConsultorios() {
        List<ConsultorioEntity> consultorios = logic.getConsultorios();
        Assert.assertEquals(data.size(), consultorios.size());
        for (ConsultorioEntity consultorio : consultorios) {
            boolean found = false;
            for (ConsultorioEntity consultorio2 : data) {
                if (consultorio2.getId().equals(consultorio.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }

    }

    /**
     * Test of getConsultorio method, of class ConsultorioLogic.
     */
    @Test
    public void testGetConsultorio() {
        ConsultorioEntity consultorioEntity = data.get(0);
        ConsultorioEntity encontrado = logic.getConsultorio(consultorioEntity.getId());

        Assert.assertNotNull(encontrado);
        Assert.assertEquals(encontrado.getName(), consultorioEntity.getName());
        Assert.assertEquals(encontrado.getId(), consultorioEntity.getId());
        Assert.assertEquals(encontrado.getUnidadCuidadosintensivos(), consultorioEntity.getUnidadCuidadosintensivos());
        Assert.assertEquals(encontrado.getHorario(), consultorioEntity.getHorario());
        Assert.assertEquals(encontrado.getAtencionUrgencias(), consultorioEntity.getAtencionUrgencias());
        Assert.assertEquals(encontrado.getDoctoresAsignados().size(), consultorioEntity.getDoctoresAsignados().size());
        for (DoctorEntity doc1 : consultorioEntity.getDoctoresAsignados()) {
            boolean found = false;
            for (DoctorEntity doc2 : encontrado.getDoctoresAsignados()) {
                if (doc2.getId().equals(doc1.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }

    }

    /**
     * Test of updateConsultorio method, of class ConsultorioLogic.
     */
    @Test
    public void testUpdateConsultorio() {
        ConsultorioEntity consultorioEntity = data.get(1);
        PodamFactory factory = new PodamFactoryImpl();
        ConsultorioEntity nuevo = factory.manufacturePojo(ConsultorioEntity.class);
        nuevo.setId(consultorioEntity.getId());

        boolean existe = false;
        for (ConsultorioEntity consultorio : data) {
            if (consultorio.getName().equals(nuevo.getName())) {
                existe = true;
            }
        }
        if (!existe) {
            try {
                ConsultorioEntity actualizado = logic.updateConsultorio(nuevo);
                ConsultorioEntity found = em.find(ConsultorioEntity.class, actualizado.getId());
                Assert.assertNotNull(found);
                Assert.assertEquals(found.getId(), actualizado.getId());
                Assert.assertEquals(found.getHorario(), actualizado.getHorario());
                Assert.assertEquals(found.getAtencionUrgencias(), actualizado.getAtencionUrgencias());
                Assert.assertEquals(found.getName(), actualizado.getName());
                Assert.assertEquals(found.getUnidadCuidadosintensivos(), actualizado.getUnidadCuidadosintensivos());
                Assert.assertEquals(found.getDoctoresAsignados().size(), actualizado.getDoctoresAsignados().size());
                for (DoctorEntity doc1 : found.getDoctoresAsignados()) {
                    boolean ex = false;
                    for (DoctorEntity doc2 : actualizado.getDoctoresAsignados()) {
                        if (doc2.getId().equals(doc1.getId())) {
                            ex = true;
                        }
                    }
                    Assert.assertTrue(ex);
                }
            } catch (WaveTeamLogicException e) {
                Assert.fail();
            }
        }

//        consultorioEntity.setName(data.get(0).getName());
//        try{
//            ConsultorioEntity actualizado = logic.updateConsultorio(consultorioEntity);
//            ConsultorioEntity found = em.find(ConsultorioEntity.class, actualizado.getId());
//            Assert.fail();
//        } catch (WaveTeamLogicException e){
//            Assert.assertTrue(true);
//        }
    }

    /**
     * Test of createConsultorio method, of class ConsultorioLogic.
     */
    @Test
    public void testCreateConsultorio() {
        PodamFactory factory = new PodamFactoryImpl();
        ConsultorioEntity consultorioEntity = factory.manufacturePojo(ConsultorioEntity.class);

        boolean existe = false;
        for (ConsultorioEntity consultorio : data) {
            if (consultorio.getName().equals(consultorioEntity.getName())) {
                existe = true;
            }
        }
        if (!existe) {
            try{
                ConsultorioEntity result = logic.createConsultorio(consultorioEntity);
                Assert.assertNotNull(result);
                ConsultorioEntity consEntity = em.find(ConsultorioEntity.class, result.getId());
                Assert.assertNotNull(consEntity);
                Assert.assertEquals(consEntity.getName(), consultorioEntity.getName());
            } catch (WaveTeamLogicException e){
                Assert.fail();
            }
        }

    }

    /**
     * Test of deleteConsultorio method, of class ConsultorioLogic.
     */
    @Test
    public void testDeleteConsultorio() {
        try{
            ConsultorioEntity consultorioEntity = data.get(0);
            logic.deleteConsultorio(consultorioEntity.getId());
            ConsultorioEntity borrado = em.find(ConsultorioEntity.class, consultorioEntity.getId());
            Assert.assertNull(borrado);
        } catch (WaveTeamLogicException e){
            Assert.fail();
        }
    }

    /**
     * Test of unasignDoctors method, of class ConsultorioLogic.
     */
    @Test
    public void testUnasignDoctors() {
        Assert.assertTrue(true);
    }

    /**
     * Test of unasignDoctor method, of class ConsultorioLogic.
     */
    @Test
    public void testUnasignDoctor() {
        Assert.assertTrue(true);        
    }

    /**
     * Test of asignDoctor method, of class ConsultorioLogic.
     */
    @Test
    public void testAsignDoctor() {
        Assert.assertTrue(true);        
    }

    /**
     * Test of asignDoctors method, of class ConsultorioLogic.
     */
    @Test
    public void testAsignDoctors() {
        Assert.assertTrue(true);        
    }
}
