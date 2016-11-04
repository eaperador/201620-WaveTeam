/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author jm.lizarazo10
 */
@Entity
public class CitaEntity extends BaseEntity implements Serializable{
    
    @PodamExclude
    @ManyToOne
    private DoctorEntity doctor;
    
    @PodamExclude
    @ManyToOne
    private PacienteEntity paciente;

    private String fecha;
    private Long hora;
    private int duracion;
    private String habilitada;
    

    
    public String getFecha(){
        return fecha;
    }
    
    public void setFecha(String fecha){
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
    
    public DoctorEntity getDoctor(){
        return doctor;
    }
    
    public void setDoctor(DoctorEntity medico){
        this.doctor = medico; 
    }
    
    public PacienteEntity getPaciente(){
        return paciente;
    }
    
    public void setPaciente(PacienteEntity paciente){
        this.paciente = paciente;
    }
    
    public String getHabilitada(){
        return habilitada;
    }
    
    public void setHabilitada(String habilitada){
        this.habilitada = habilitada;
    }
    
}
