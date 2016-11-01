/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.test.logic;

import co.edu.uniandes.waveteam.sistemahospital.api.IPacienteLogic;
import co.edu.uniandes.waveteam.sistemahospital.ejbs.PacienteLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.CitaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.PacienteEntity;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;
import co.edu.uniandes.waveteam.sistemahospital.persistence.PacientePersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
 * @author je.ardila1501
 */
@RunWith(Arquillian.class)
public class PacienteLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private IPacienteLogic pacienteLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<PacienteEntity> data = new ArrayList();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PacienteEntity.class.getPackage())
                .addPackage(PacienteLogic.class.getPackage())
                .addPackage(IPacienteLogic.class.getPackage())
                .addPackage(PacientePersistence.class.getPackage())
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
        em.createQuery("delete from PacienteEntity").executeUpdate();
    }
     
    private void insertData() {

        for (int i = 0; i < 8; i++) {
            PacienteEntity pacienteEntity = factory.manufacturePojo(PacienteEntity.class);
            em.persist(pacienteEntity);
            data.add(pacienteEntity);
        }
    }
    
    /**
     * Prueba: crea un nuevo paciente, con Id y tipo de Documento 
     * No existentes en la base de datos.
     */
    
    @Test
    public void createPacienteDiferenteTest()throws WaveTeamLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        PacienteEntity newPacienteEntity = factory.manufacturePojo(PacienteEntity.class);
        
        PacienteEntity result = pacienteLogic.createPaciente(newPacienteEntity);
        Assert.assertNotNull(result);
        
        PacienteEntity entity = em.find(PacienteEntity.class, result.getId());
        Assert.assertNotNull(entity);
        System.out.println("=====================================================================");
        System.out.println("ENCONTRO EL PACIENTE");
        Assert.assertEquals(newPacienteEntity.getName(), entity.getName());
        System.out.println("=====================================================================");
        System.out.println("EL NOMBRE CORRESPONDE");
        Assert.assertEquals(newPacienteEntity.getTipoDocumento(), entity.getTipoDocumento());
        System.out.println("=====================================================================");
        System.out.println("EL TIPO DE DOCUMENTO CORRESPONDE");
    }
    
    /**
     * Prueba: crea un nuevo paciente, con Id y tipo de Documento 
     * existentes en la base de datos
     * @throws WaveTeamLogicException 
     */
    @Test(expected = WaveTeamLogicException.class)
    public void createPacienteIgualTest() throws WaveTeamLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        PacienteEntity newPacienteEntity = factory.manufacturePojo(PacienteEntity.class);
        newPacienteEntity.setId(data.get(3).getId());
        newPacienteEntity.setTipoDocumento(data.get(3).getTipoDocumento());
        
        PacienteEntity result = pacienteLogic.createPaciente(newPacienteEntity);
    }
    
    /**
     * Prueba: Eliminando un paciente existente.
     * La NO existencia se verifica en el resource.
     */
    @Test
    public void deletePacienteTest(){
        Random random = new Random();
        int number = random.nextInt(8);
        
        PacienteEntity entitity = data.get(number);
        pacienteLogic.deletePaciente(entitity.getId());
        
        PacienteEntity deleted = em.find(PacienteEntity.class, entitity.getId());
        Assert.assertNull(deleted);
        System.out.println("=====================================================================");
        System.out.println("VERIFICACION QUE EL ELEMENTO SEA NULL" + deleted); 
    }
    
    /**
     * Prueba: Actualiza la informacion de un paciente, con Id y tipo de Documento 
     * existentes en la base de datos
     * @throws WaveTeamLogicException 
     */
    @Test
    public void updatePacienteExistenteTest() throws WaveTeamLogicException{
      Random random = new Random();
      int number = random.nextInt(8);
        
      PacienteEntity entitity = data.get(number);
      
      PodamFactory factory = new PodamFactoryImpl();
      
      PacienteEntity newEntity = factory.manufacturePojo(PacienteEntity.class);
      
      // cambio el id del la nueva entidad por una entidad conocida
      
      newEntity.setId(entitity.getId());
      // actualizo el paciente con el id del entity del arreglo pero con la info de la entity generada por el podam
      pacienteLogic.updatePaciente(newEntity);
              
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
     * Prueba: Actualiza la informacion de un paciente, con Id y tipo de Documento 
     * INEXISTENTES en la base de datos
     * @throws WaveTeamLogicException 
     */
    @Test(expected = WaveTeamLogicException.class)
    public void updatePacienteInexistenteTest() throws WaveTeamLogicException{
      PodamFactory factory = new PodamFactoryImpl();
      
      PacienteEntity newEntity = factory.manufacturePojo(PacienteEntity.class);
      
      newEntity.setId(9517538521L);
      // actualizo el paciente con el id iNEXISTENTE pero con la info de la entity generada por el podam
      pacienteLogic.updatePaciente(newEntity);
      
    }
    
    /**
     * 
     * @throws WaveTeamLogicException 
     */
//    @Test(expected = WaveTeamLogicException.class)
//    public void findNumCitasPacienteErrorTest() throws WaveTeamLogicException{
//        Random random= new Random();
//        int number = random.nextInt(8);
//        PacienteEntity entity = data.get(number);
//        
//        for (int i = 0; i < 15; i++) {
//            CitaEntity citaEntity = factory.manufacturePojo(CitaEntity.class);
//            entity.addCita(citaEntity);
//        }
//        
//        pacienteLogic.findNumCitasPaciente(entity.getId());
//    }
    
    /**
     * 
     * @throws WaveTeamLogicException 
     */
//    @Test
//    public void findNumCitasPacienteTest() throws WaveTeamLogicException{
//        Random random= new Random();
//        int number = random.nextInt(8);
//        PacienteEntity entity = data.get(number);
//        
//        for (int i = 0; i < 10; i++) {
//            CitaEntity citaEntity = factory.manufacturePojo(CitaEntity.class);
//            entity.addCita(citaEntity);
//        }
//        
//        pacienteLogic.findNumCitasPaciente(entity.getId());
//    }
    
    /**
     * Prueba: Busca un Paciente con Id existente en la base de datos.
     */
    @Test
    public void findPacienteExisteTest(){
        Random random= new Random();
        int number = random.nextInt(8);
        PacienteEntity entity = data.get(number);
        
        PacienteEntity resp=em.find(PacienteEntity.class, entity.getId());
        Assert.assertNotNull(resp);
    }
    
    /**
     * Prueba: Busca un Paciente con Id INEXISTENTE en la base de datos.
     */
    @Test
    public void findPacienteInexistenteTest(){
        PacienteEntity resp=em.find(PacienteEntity.class, 951753154L);
        Assert.assertNull(resp);
    }
    
    /**
     * Prueba: Busca un Paciente con nombre existente en la base de datos.
     * Verificacion de Existencia se hace en el Resource
     */
    @Test
    public void findPacienteByNameExisteTest(){
        Random random= new Random();
        int number = random.nextInt(8);
        PacienteEntity entity = data.get(number);
        System.out.println("****************************************");
        System.out.println("NOMBRE ENTIDAD : "+entity.getName());
        PacienteEntity resp=pacienteLogic.findPacienteByName(entity.getName());
        System.out.println("****************************************");
        System.out.println("NOMBRE RESP : "+resp.getName());
        Assert.assertNotNull(resp);
        Assert.assertEquals("comprobando igualdad de nombres", entity.getName(), resp.getName());
    }
    
    @Test
    public void findAllPacientesTest(){
        List<PacienteEntity> lista = pacienteLogic.findAllPacientes();
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
