/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.ejbs;

import co.edu.uniandes.waveteam.sistemahospital.api.IPacienteLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.PacienteEntity;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;
import co.edu.uniandes.waveteam.sistemahospital.persistence.PacientePersistence;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;


@Stateless
public class PacienteLogic implements IPacienteLogic{
    
    @Inject
     private PacientePersistence pacientePercistence;
    
   
    public PacienteEntity getPaciente (Long id){
        return pacientePercistence.find(id);
    }
    
    public void deletePaciente (Long id){
        pacientePercistence.delete(id);
    }
    
    public PacienteEntity updatePaciente(PacienteEntity paciente) throws WaveTeamLogicException{ 
        PacienteEntity entity = getPaciente(paciente.getId());
        if(entity==null){
            throw new WaveTeamLogicException("No existe un paciente con ese id");
        }
        else{
           return pacientePercistence.update(paciente);
        }
        
    }
    
    
    public PacienteEntity createPaciente(PacienteEntity paciente) throws WaveTeamLogicException{
        PacienteEntity entity = getPaciente(paciente.getId());
        if(entity!=null){
            throw new WaveTeamLogicException("ya existe un paciente con ese id");
        }
        else{
           return pacientePercistence.create(paciente); 
        }
        
    }
    
    public void findNumCitasPaciente(Long idPaciente)throws WaveTeamLogicException{
        List<PacienteEntity> lista = pacientePercistence.findAllCitasPaciente(idPaciente);
        if(lista.size()>10){
           throw new WaveTeamLogicException("Un paciente NO puede tener mas de 10 citas");
        }
    }
            
    
    public PacienteEntity findPacienteByName (String name){
        return pacientePercistence.findByName(name);
    }
    
    public PacienteEntity findPacienteByID(Long id){
        return pacientePercistence.find(id);
    }
    
    public List<PacienteEntity> findAllPacientes(){
        return pacientePercistence.findAll();
    }  
}
