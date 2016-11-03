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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    
    private static final Logger LOGGER = Logger.getLogger(ConsultorioPersistence.class.getName());
    
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
        PodamFactory factory = new PodamFactoryImpl();
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
    public void testGetConsultorioExistente() {
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
     * Test of getConsultorio method, of class ConsultorioLogic.
     */
    @Test
    public void testGetConsultorioInexistente() {
        ConsultorioEntity cons = logic.getConsultorio(-1L);
        Assert.assertNull(cons);
    }

    /**
     * Test of findByName method, of class ConsultorioLogic.
     */
    @Test
    public void testFindByNameExistente() {
        ConsultorioEntity consultorioEntity = data.get(0);
        ConsultorioEntity encontrado = logic.findByName(consultorioEntity.getName());

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
     * Test of findByName method, of class ConsultorioLogic.
     */
    @Test
    public void testFindByNameInexistente() {
        PodamFactory factory = new PodamFactoryImpl();
        ConsultorioEntity cons = factory.manufacturePojo(ConsultorioEntity.class);
        boolean found = false;
        for(ConsultorioEntity consultorio : data){
            if (consultorio.getName().equals(cons.getName())){
                found = true;
            }
        }
        if (!found){
            ConsultorioEntity encontrado = logic.findByName(cons.getName());
            Assert.assertNull(encontrado);
        }
        else{
            Assert.assertTrue(true);
        }
    }

    /**
     * Test of updateConsultorio method, of class ConsultorioLogic.
     */
    @Test
    public void testUpdateConsultorioNuevo() {
        ConsultorioEntity consultorioEntity = data.get(0);
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
    }

    /**
     * Test of updateConsultorio method, of class ConsultorioLogic.
     */
    @Test
    public void testUpdateConsultorioExistente() {
        ConsultorioEntity consultorioEntity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ConsultorioEntity nuevo = factory.manufacturePojo(ConsultorioEntity.class);
        nuevo.setId(consultorioEntity.getId());
        nuevo.setName(data.get(1).getName());

        try {
            logic.updateConsultorio(nuevo);
            Assert.fail();
        } catch (WaveTeamLogicException e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * Test of createConsultorio method, of class ConsultorioLogic.
     */
    @Test
    public void testCreateConsultorioNuevo() {
        PodamFactory factory = new PodamFactoryImpl();
        ConsultorioEntity consultorioEntity = factory.manufacturePojo(ConsultorioEntity.class);

        boolean existe = false;
        for (ConsultorioEntity consultorio : data) {
            if (consultorio.getName().equals(consultorioEntity.getName())) {
                existe = true;
            }
        }
        if (!existe) {
            try {
                ConsultorioEntity result = logic.createConsultorio(consultorioEntity);
                Assert.assertNotNull(result);
                ConsultorioEntity consEntity = em.find(ConsultorioEntity.class, result.getId());
                Assert.assertNotNull(consEntity);
                Assert.assertEquals(consEntity.getName(), consultorioEntity.getName());
            } catch (WaveTeamLogicException e) {
                Assert.fail();
            }
        }
    }

    /**
     * Test of createConsultorio method, of class ConsultorioLogic.
     */
    @Test
    public void testCreateConsultorioExistente() {
        PodamFactory factory = new PodamFactoryImpl();
        ConsultorioEntity consultorioEntity = factory.manufacturePojo(ConsultorioEntity.class);

        consultorioEntity.setName(data.get(0).getName());
        try {
            logic.createConsultorio(consultorioEntity);
            Assert.fail();
        } catch (WaveTeamLogicException e) {
            Assert.assertTrue(true);
        }

        consultorioEntity = factory.manufacturePojo(ConsultorioEntity.class);
        consultorioEntity.setId(data.get(0).getId());
        try {
            logic.createConsultorio(consultorioEntity);
            Assert.fail();
        } catch (WaveTeamLogicException e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * Test of deleteConsultorio method, of class ConsultorioLogic.
     */
    @Test
    public void testDeleteConsultorioExistente() {
        try {
            ConsultorioEntity consultorioEntity = data.get(0);
            logic.deleteConsultorio(consultorioEntity.getId());
            ConsultorioEntity borrado = em.find(ConsultorioEntity.class, consultorioEntity.getId());
            Assert.assertNull(borrado);
        } catch (WaveTeamLogicException e) {
            Assert.fail();
        }
    }

    /**
     * Test of deleteConsultorio method, of class ConsultorioLogic.
     */
    @Test
    public void testDeleteConsultorioInexistente() {
        try {
            PodamFactory factory = new PodamFactoryImpl();
            ConsultorioEntity consultorioEntity = factory.manufacturePojo(ConsultorioEntity.class);
            logic.deleteConsultorio(consultorioEntity.getId());
            Assert.fail();
        } catch (WaveTeamLogicException e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * Test of unasignDoctors method, of class ConsultorioLogic.
     */
    @Test
    public void testUnasignDoctorsExistente() {
        ConsultorioEntity consultorio = data.get(0);

        try {
            logic.unasignDoctors(consultorio.getId());
            Assert.assertEquals(consultorio.getDoctoresAsignados().size(), 0);
        } catch (WaveTeamLogicException e) {
            Assert.fail();
        }

    }

    /**
     * Test of unasignDoctors method, of class ConsultorioLogic.
     */
    @Test
    public void testUnasignDoctorsInexistente() {

        try {
            logic.unasignDoctors(-1L);
            Assert.fail();
        } catch (WaveTeamLogicException e) {
            Assert.assertTrue(true);
        }

    }

    /**
     * Test of unasignDoctor method, of class ConsultorioLogic.
     */
    @Test
    public void testUnasignDoctorExistente() {
        PodamFactory factory = new PodamFactoryImpl();
        ConsultorioEntity consultorio = data.get(0);
        List<DoctorEntity> lista = consultorio.getDoctoresAsignados();
        int tam = lista.size();
        if (tam != 0) {
            DoctorEntity doc = lista.get(0);
            try {
                consultorio = logic.unasignDoctor(consultorio.getId(), doc.getId());
                lista = consultorio.getDoctoresAsignados();
                Assert.assertEquals(lista.size(), tam - 1);
                boolean encontrado = false;
                for (DoctorEntity doctor : lista) {
                    if (Objects.equals(doctor, doc)) {
                        encontrado = true;
                    }
                }
                Assert.assertFalse(encontrado);
            } catch (WaveTeamLogicException e) {
                Assert.fail();
            }
        }
    }

    /**
     * Test of unasignDoctor method, of class ConsultorioLogic.
     */
    @Test
    public void testUnasignDoctorInexistente() {
        try {
            ConsultorioEntity consultorio = logic.unasignDoctor(-1L, Long.MIN_VALUE);
            Assert.fail();
        } catch (WaveTeamLogicException e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * Test of asignDoctor method, of class ConsultorioLogic.
     */
    @Test
    public void testAsignDoctorExistente() {
        ConsultorioEntity consultorio = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        DoctorEntity doc = factory.manufacturePojo(DoctorEntity.class);
        List<DoctorEntity> docs = consultorio.getDoctoresAsignados();
        int tam = docs.size();
        doc.setConsultorio(consultorio.getId());
        try {
            consultorio = logic.asignDoctor(consultorio.getId(), doc);
            List<DoctorEntity> lista = consultorio.getDoctoresAsignados();
            Assert.assertEquals(lista.size(), tam+1);
            boolean encontrado = false;
                for (DoctorEntity doctor : lista) {
                    if (Objects.equals(doctor.getConsultorio(), doc.getConsultorio())
                            && Objects.equals(doctor.getName(), doc.getName())) {
                        encontrado = true;
                    }
                }
                Assert.assertTrue(encontrado);
        } catch (WaveTeamLogicException e) {
            Assert.fail();
        }
    }

    /**
     * Test of asignDoctor method, of class ConsultorioLogic.
     */
    @Test
    public void testAsignDoctorInexistente() {
        PodamFactory factory = new PodamFactoryImpl();
        DoctorEntity doc = factory.manufacturePojo(DoctorEntity.class);
        try{
            logic.asignDoctor(-1L, doc);
            Assert.fail();
        }catch(WaveTeamLogicException e){
            Assert.assertTrue(true);
        }
    }
}
