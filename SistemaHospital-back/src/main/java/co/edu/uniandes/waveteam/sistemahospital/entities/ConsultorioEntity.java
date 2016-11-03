/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.waveteam.sistemahospital.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author r.garcia11
 */
@Entity
public class ConsultorioEntity extends BaseEntity implements Serializable{
    
    @PodamExclude
    @OneToMany
    private List<DoctorEntity> doctoresAsignados = new ArrayList();
    
    private String nombre;
    private String horario;
    private boolean atencionUrgencias;
    private boolean unidadCuidadosIntensivos;
    
    public String getHorario(){
        return horario;
    }
    
    public void setHorario(String horario){
        this.horario = horario;
    }
    
    public boolean getAtencionUrgencias(){
        return atencionUrgencias;
    }
    
    public void setAtencionUrgencias(boolean atencionUrgencias){
        this.atencionUrgencias = atencionUrgencias;
    }
    
    public boolean getUnidadCuidadosintensivos(){
        return unidadCuidadosIntensivos;
    }
    
    public void setUnidadCuidadosIntensivos(boolean unidadCuidadosIntensivos){
        this.unidadCuidadosIntensivos = unidadCuidadosIntensivos;
    }
    
    public List<DoctorEntity> getDoctoresAsignados(){
        return doctoresAsignados;
    }
    
    public void setDoctoresAsignados(List<DoctorEntity> doctoresAsignados){
        this.doctoresAsignados = doctoresAsignados;
    }
    
    public void agregarDoctorAsignado(DoctorEntity doc){
        doctoresAsignados.add(doc);
    }
    
    public boolean eliminarDoctorAsignado(Long idDoctor)
    {
        for (DoctorEntity doc: doctoresAsignados)
        {
            if (Objects.equals(doc.getId(), idDoctor))
            {
                doctoresAsignados.remove(doc);
                return true;
            }
        }
        return false;
    }

    public void elminarDoctores() {
        doctoresAsignados.clear();
    }
}
