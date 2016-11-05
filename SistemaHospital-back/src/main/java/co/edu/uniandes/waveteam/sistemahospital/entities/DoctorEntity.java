/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
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
    
    public void setDisponibilidad(ArrayList<LinkedHashMap> diasDisponible){
        for (int j = 0; j < diasDisponible.size() ; j++) {
            LinkedHashMap lhm = diasDisponible.get(j);
            Long inicio = (Long)lhm.get("value");
            Calendar n = new GregorianCalendar();
            n.setTimeInMillis(inicio);
            int fromDay = n.get(Calendar.DAY_OF_WEEK);
            int i = 0;
            while ((fromDay == n.get(Calendar.DAY_OF_WEEK)) && (n.get(Calendar.HOUR_OF_DAY) <= 24)){
                if (i == 4) break;
                CitaEntity cita = new CitaEntity();
//                CitaDTO cita = new CitaDTO();
                cita.setHora(inicio+(i*900000));
                n.setTimeInMillis(n.getTimeInMillis()+(i*900000));
                cita.setDuracion(15);
                cita.setDoctor(this);
//                cita.desactivar();
                disponibilidadCitas.add(cita);
                i++;
            }
        }
    }
    
}
