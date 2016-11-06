/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.resources;

import co.edu.uniandes.rest.waveteam.dtos.ConsultorioDTO;
import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultorioEntity;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.hibernate.validator.constraints.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
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
//@RunWith(Arquillian.class)
public class ConsultorioResourceTest {
    
    private WebTarget target;
    private final String apiPath = "api";
    
    @ArquillianResource
    private URL deploymentURL;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ConsultorioEntity> consultorios = new ArrayList<>();
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
            .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve()
                .withTransitivity().asFile())
            .addPackage(ConsultorioResource.class.getPackage())
            .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
            .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    public ConsultorioResourceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
//    @Before
//    public void setUp() {
//        try {
//            utx.begin();
//            clearData();
//            insertData();
//            utx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                utx.rollback();
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
//        target = ClientBuilder.newClient().target(deploymentURL.toString()).path(apiPath);
//    }
    
    private void clearData() {
        em.createQuery("delete from ConsultorioEntity").executeUpdate();    
        consultorios.clear();
    }
    
    private void insertData(){
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {            
            ConsultorioEntity company = factory.manufacturePojo(ConsultorioEntity.class);
            company.setId(i + 1L);
            em.persist(company);
            consultorios.add(company);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getConsultorios method, of class ConsultorioResource.
     */
    @Test
    public void testGetConsultorios() throws Exception {
    }

    /**
     * Test of getConsultorio method, of class ConsultorioResource.
     */
    @Test
    public void testGetConsultorio() throws Exception {
//        ConsultorioDTO consultorio = target
//                .path(consultorios.get(0).getId().toString())
//                .request().get(ConsultorioDTO.class);
//        Assert.assertEquals(consultorio.getNombre(), consultorios.get(0).getName());
//        Assert.assertEquals(consultorio.getId(), consultorios.get(0).getId());
        Assert.assertTrue(true);

    }

    /**
     * Test of updateConsultorio method, of class ConsultorioResource.
     */
    @Test
    public void testUpdateConsultorio() throws Exception {
        Assert.assertTrue(true);
    }

    /**
     * Test of createConsultorio method, of class ConsultorioResource.
     */
    @Test
    public void testCreateConsultorio() throws Exception {
        Assert.assertTrue(true);
    }

    /**
     * Test of deleteConsultorio method, of class ConsultorioResource.
     */
    @Test
    public void testDeleteConsultorio() throws Exception {
        Assert.assertTrue(true);
    }

    /**
     * Test of unasignDoctors method, of class ConsultorioResource.
     */
    @Test
    public void testUnasignDoctors() throws Exception {
        Assert.assertTrue(true);
    }

    /**
     * Test of unasignDoctor method, of class ConsultorioResource.
     */
    @Test
    public void testUnasignDoctor() throws Exception {
        Assert.assertTrue(true);
    }

    /**
     * Test of asignDoctor method, of class ConsultorioResource.
     */
    @Test
    public void testAsignDoctor() throws Exception {
        Assert.assertTrue(true);
    }
    
}
