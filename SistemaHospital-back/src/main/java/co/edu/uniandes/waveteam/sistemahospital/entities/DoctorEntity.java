/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author felipeplazas
 */
@Entity
public class DoctorEntity extends BaseEntity implements Serializable{
    
    @PodamExclude
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CitaEntity> disponibilidadCitas = new ArrayList<>();
    
    @ManyToOne(cascade = CascadeType.ALL)
    private EspecialidadEntity especialidad;
    
    private Long consultorio;

    public List<CitaEntity> getDisponibilidadCitas() {
        return disponibilidadCitas;
    }

    public void setDisponibilidadCitas(List<CitaEntity> disponibilidad) {
        this.disponibilidadCitas = disponibilidad;
    }

    public EspecialidadEntity getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadEntity especialidad) {
        this.especialidad = especialidad;
    }

    public Long getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Long consultorio) {
        this.consultorio = consultorio;
    }
    
}
