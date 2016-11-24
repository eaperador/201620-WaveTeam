/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.dtos;


import co.edu.uniandes.waveteam.sistemahospital.entities.CitaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.persistence.CitaPersistence;
import java.util.ArrayList;
import java.util.logging.Logger;


/**
 *
 * @author jm.lizarazo10
 */
public class CitaDTO {
    
     private static final Logger LOGGER = Logger.getLogger(CitaPersistence.class.getName());

    public ArrayList a = new ArrayList();

    
    
    private Long id;
    private Long fecha;
    private Long hora;
    private int duracion;
    private MedicoDetailDTO doctorO;
    private PatientDetailDTO pacienteO;
    private Long doctor;
    private Long paciente;
    private String habilitada;
    
    
    public CitaDTO(){
        
    }
    
    //***********************************************************************
    //CAMBIOS PARA QUITAR ERRORES EN PACIENTDETAIL
    
    public CitaDTO(CitaEntity entity){
        if (entity != null) {
            this.id = id;
            this.fecha = fecha;
            this.hora = hora;
            this.duracion = duracion;
            this.doctor = doctor;
            this.paciente = paciente; 
            this.habilitada = habilitada;
        }
         LOGGER.info("Hay metodo de cita dto con parametro entity: " + entity.getId() + ", " + entity.getDuracion() + ", " + entity.getFecha());
    }
    
    public CitaDTO(CitaEntity entity, boolean alternate){
        if (entity != null) {
            this.id = entity.getId();
            this.fecha = entity.getFecha();
            this.fecha = entity.getFecha();
            this.hora = entity.getHora();
            this.duracion = entity.getDuracion();
            
            this.habilitada = entity.getHabilitada();
        }
        LOGGER.info("Hay metodo de cita DTO alterno, " +                 
                entity.getFecha()+ ", "+ 
                entity.getHabilitada());
    }
    
    
    public CitaEntity toEntity() {
        
        CitaEntity entity = new CitaEntity();
        entity.setDuracion(duracion);
        entity.setFecha(fecha);
        entity.setHabilitada(habilitada);
        entity.setHora(hora);
        entity.setId(id);
        entity.setDoctor(doctorO.toEntity());
        entity.setPaciente(pacienteO.toEntity());
        LOGGER.info("Hay metodo to Entity! ");
        LOGGER.info("Los valores de medico y paciente son: " + doctorO.toEntity().toString() +", " +pacienteO.toEntity().toString());
        
        return entity;
    }
   
     //***********************************************************************
    
    public CitaDTO(Long id, Long fecha, Long hora, int duracion, Long medico, Long paciente, String habilitada){
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.duracion = duracion;
        this.doctor = medico;
        this.paciente = paciente; 
        this.habilitada = habilitada;
        LOGGER.info("Hay metodo crear DTO con todos los parametros");

    }
    
    
    
    
    public Long getId(){
        return id;
    }
    
    
    
    public void setId(Long id){
        this.id = id;
    }
    
    public Long getFecha(){
        return fecha;
    }
    
    public void setFecha(Long fecha){
        this.fecha = fecha;
    }
    
    
    public Long getHora(){
        return hora;
    }
    
    
    public void setHora(Long hora){
        this.hora = hora;
    }
    
    
    public int getDuracion(){
        return duracion;
    }
    
    public void setDuracion(int duracion){
        this.duracion = duracion;
    }
    
    
    public Long getMedico(){
        return doctor;
    }
    
    public void setMedico(Long doctor){
        this.doctor = doctor;
    }
    
    
    public Long getPaciente(){
        return paciente;
    }
    
    
    public void setPaciente(Long paciente){
        this.paciente = paciente;
    }
    
    public void setPacienteO(PatientDetailDTO patient){
    this.pacienteO = patient;
    }
    
    public void setDoctorO(MedicoDetailDTO doctor){
        this.doctorO = doctor;
    }
    
    public PatientDetailDTO getPatientO(){
        return pacienteO;
    }
    
    public MedicoDetailDTO getDoctorO(){
        return doctorO;
    }
    
    
    public void desactivar(){
        this.habilitada = "false";
    }
    
    public String getHabilitada(){
        return this.habilitada;
    }

    public void setHabilitada(String habilitada){
        this.habilitada = habilitada;
    }

    public void addSm(Long t){
        a.add(t);
    }
    
    @Override
    public String toString(){

        return  "{ id : " + id +
                ", fecha : " + fecha + 
                ", hora : " +hora + 
                ", duracion : " + duracion + 
//                ", medico : " + doctor.toString() + 
                ", paciente : " + paciente.toString() +
                ", habilitada : " + habilitada 
                +" }";
    }
    
    
}
