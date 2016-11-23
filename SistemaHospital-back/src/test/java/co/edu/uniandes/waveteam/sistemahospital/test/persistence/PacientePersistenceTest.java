/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.test.persistence;

import co.edu.uniandes.waveteam.sistemahospital.entities.PacienteEntity;
import co.edu.uniandes.waveteam.sistemahospital.persistence.PacientePersistence;
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
import java.util.Random;


@RunWith(Arquillian.class)
public class PacientePersistenceTest {
   
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PacienteEntity.class.getPackage())
                .addPackage(PacientePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    private PacientePersistence pacientePersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<PacienteEntity> data = new ArrayList<PacienteEntity>();
    
    
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
    
    /**
     * 
     */
    private void clearData() {
        em.createQuery("delete from PacienteEntity").executeUpdate();
    }
    
    /**
     * 
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 8; i++) {
            PacienteEntity entity = factory.manufacturePojo(PacienteEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * 
     */
    @Test
    public void createPacienteTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PacienteEntity newEntity = factory.manufacturePojo(PacienteEntity.class);

        PacienteEntity result = pacientePersistence.create(newEntity);
        Assert.assertNotNull(result);
        System.out.println("=====================================================================");
        System.out.println("CREO BN EL NUEVO PACIENTE");
        PacienteEntity entity = em.find(PacienteEntity.class, result.getId());
        Assert.assertNotNull(entity);
        System.out.println("=====================================================================");
        System.out.println("ENCONTRO EL PACIENTE");
        Assert.assertEquals(newEntity.getName(), entity.getName());
        System.out.println("=====================================================================");
        System.out.println("EL NOMBRE CORRESPONDE");
        Assert.assertEquals(newEntity.getTipoDocumento(), entity.getTipoDocumento());
        System.out.println("=====================================================================");
        System.out.println("EL TIPO DE DOCUMENTO CORRESPONDE");
        // ASSERT DE LA CANTIDAD
    }
    
    /**
     * 
     */
    @Test
    public void deletePacienteTest(){
        Random random = new Random();
        int number = random.nextInt(8);
        
        PacienteEntity entitity = data.get(number);
        pacientePersistence.delete(entitity.getId());
        
        PacienteEntity deleted = em.find(PacienteEntity.class, entitity.getId());
        Assert.assertNull(deleted);
        System.out.println("=====================================================================");
        System.out.println("VERIFICACION QUE EL ELEMENTO SEA NULL" + deleted); 
    }
    
    /**
     * 
     */
    @Test
    public void updatePacienteTest(){
      Random random = new Random();
      int number = random.nextInt(8);
        
      PacienteEntity entitity = data.get(number);
      
      PodamFactory factory = new PodamFactoryImpl();
      
      PacienteEntity newEntity = factory.manufacturePojo(PacienteEntity.class);
      
      // cambio el id del la nueva entidad por una entidad conocida
      
      newEntity.setId(entitity.getId());
      // actualizo el paciente con el id del entity del arreglo pero con la info de la entity generada por el podam
      pacientePersistence.update(newEntity);
              
      PacienteEntity resp = em.find(PacienteEntity.class, entitity.getId());
      
      // pruebas de existencia de un paciente con ID de la entidad del arreglo
      Assert.assertNotNull(resp);
      // pruebas de las actualizaciones
      Assert.assertEquals("nombre: ",newEntity.getName(), resp.getName());
      Assert.assertEquals("tipo documento: ",newEntity.getTipoDocumento(), resp.getTipoDocumento());  
      Assert.assertEquals("eps: ",newEntity.getEps(), resp.getEps());
      Assert.assertEquals("sexo: ",newEntity.getSexo(), resp.getSexo());
      Assert.assertEquals("edad: ",newEntity.getEdad(), resp.getEdad());  
    }
    
    /**
     * 
     */
    
    @Test
    public void findPacienteTest(){
        Random random= new Random();
        int number = random.nextInt(8);
        PacienteEntity entity = data.get(number);
        
        PacienteEntity resp=em.find(PacienteEntity.class, entity.getId());
        Assert.assertNotNull(resp);
    }
    
    /**
     * 
     */
    @Test
    public void findPacienteByNameTest(){
       Random random= new Random();
        int number = random.nextInt(8);
        PacienteEntity entity = data.get(0);
        System.out.println("****************************************");
        System.out.println("NOMBRE ENTIDAD : "+ entity.getName());
        PacienteEntity resp=pacientePersistence.findByName(entity.getName());
        System.out.println("****************************************");
        System.out.println("NOMBRE RESP : "+resp.getName());
        Assert.assertNotNull(resp);
        Assert.assertEquals("comprobando igualdad de nombres", entity.getName(), resp.getName());
    }
    
    
    
    
    /**
     * 
     */
    
    @Test
    public void findAllPacientesTest(){
        List<PacienteEntity> lista = pacientePersistence.findAll();
        Assert.assertNotNull("la lista no puede estar vacia", lista);
        Assert.assertEquals("el tamanio corrresponde", data.size(),lista.size());
        
        for (PacienteEntity entityLista : lista){
            boolean existe = false;
            
            for (PacienteEntity entityData : data){
                if(entityLista.getId()==entityData.getId()){
                    existe = true;
                }
            }
            
            // verificacion que los id de los entities sean los mismos (PK), mismos objetos
            Assert.assertTrue(existe);
        }
    }
    
}
