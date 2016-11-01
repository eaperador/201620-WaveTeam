package co.edu.uniandes.waveteam.sistemahospital.test.logic;

import co.edu.uniandes.waveteam.sistemahospital.api.IDoctorLogic;
import co.edu.uniandes.waveteam.sistemahospital.api.IEspecialidadLogic;
import co.edu.uniandes.waveteam.sistemahospital.ejbs.DoctorLogic;
import co.edu.uniandes.waveteam.sistemahospital.ejbs.EspecialidadLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;
import co.edu.uniandes.waveteam.sistemahospital.persistence.DoctorPersistence;
import co.edu.uniandes.waveteam.sistemahospital.persistence.EspecialidadPersistence;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.print.Doc;
import javax.transaction.UserTransaction;
import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by felipeplazas on 10/31/16.
 */
@RunWith(Arquillian.class)
public class DoctorLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private IDoctorLogic logic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<DoctorEntity> data = new ArrayList<>();
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DoctorPersistence.class.getPackage())
                .addPackage(DoctorEntity.class.getPackage())
                .addPackage(DoctorLogic.class.getPackage())
                .addPackage(IDoctorLogic.class.getPackage())
                .addPackage(EspecialidadEntity.class.getPackage())
                .addPackage(EspecialidadLogic.class.getPackage())
                .addPackage(IEspecialidadLogic.class.getPackage())
                .addPackage(EspecialidadPersistence.class.getPackage())
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
        em.createQuery("delete from DoctorEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 4; i++) {
            DoctorEntity entity = factory.manufacturePojo(DoctorEntity.class);
            EspecialidadEntity esp = entity.getEspecialidad();
            esp.getDoctores().add(entity);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Get all doctors test
     */
    @Test
    public void getDoctorsTest(){
        List<DoctorEntity> list = logic.getDoctores();
        Assert.assertEquals(data.size(), list.size());
        A: for (DoctorEntity entity : list) {
            for (DoctorEntity storedEntity : data) {
                if ( entity.getId().equals(storedEntity.getId() ) )
                    continue A;
            }
            Assert.fail();
        }
    }

    /**
     * Get a doctor by his id test
     */
    @Test
    public void getDoctorTest() {
        DoctorEntity entity = data.get(0);
        DoctorEntity resultEntity = logic.getDoctorById(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getEspecialidad().getName(), resultEntity.getEspecialidad().getName());
    }

    /**
     * Get a doctor by his name test
     */
    @Test
    public void getDoctorByNameTest(){
        try{
            DoctorEntity entity = data.get(0);
            DoctorEntity resultEntity = logic.getDoctorByName(entity.getName());
            Assert.assertNotNull(resultEntity);
            Assert.assertEquals(entity.getName(), resultEntity.getName());
            Assert.assertEquals(entity.getId(), resultEntity.getId());
            Assert.assertEquals(entity.getEspecialidad().getName(), resultEntity.getEspecialidad().getName());
        } catch (WaveTeamLogicException w){
            w.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Get all doctors of a given speciality test
     */
    @Test
    public void getDoctorsByEspecialidadTest(){
        EspecialidadEntity especialidadEntity = data.get(0).getEspecialidad();
        List<DoctorEntity> docs = logic.getDoctorByEspecialidad(especialidadEntity);

        Assert.assertNotNull(docs);
        for (DoctorEntity entity: docs) {
            Assert.assertEquals(entity.getEspecialidad().getName(), especialidadEntity.getName());
            Assert.assertEquals(entity.getEspecialidad().getTipo(), especialidadEntity.getTipo());
        }
    }

    @Test
    public void createDoctorTest(){
        DoctorEntity entity = factory.manufacturePojo(DoctorEntity.class);
        logic.createDoctor(entity);
        DoctorEntity result = logic.getDoctorById(entity.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(entity.getName(), result.getName());
        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getEspecialidad().getName(), result.getEspecialidad().getName());
    }

    
}
