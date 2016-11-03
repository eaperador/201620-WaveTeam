/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.dtos;

import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultaHistoricaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author d.marino10
 */
public class EspecialidadDetailDTO extends EspecialidadDTO{
     
    private List<MedicoDTO> doctores = new ArrayList<>();
    
    private List<ConsultaHistoricaDTO> consultas = new ArrayList<>();
    
    //private List<CitaDTO> citas = new ArrayList<>();
    
    public EspecialidadDetailDTO() {
        super();
    }
    
    public EspecialidadDetailDTO(EspecialidadEntity entity) {
        super(entity);
        
        List<DoctorEntity> list = entity.getDoctores();
        for (DoctorEntity d : list) {
            this.doctores.add(new   MedicoDTO(d));
        }
        
        List<ConsultaHistoricaEntity> lista = entity.getConsultaActual();
        for (ConsultaHistoricaEntity c : lista) {
            this.consultas.add(new ConsultaHistoricaDTO(c));
        }
    }
    
    
    public EspecialidadEntity toEntity() {
        EspecialidadEntity entity = super.toEntity();

        for (MedicoDTO d : this.doctores) {         
            entity.getDoctores().add(d.toEntity());
        }
        
        for (ConsultaHistoricaDTO c : this.consultas) {         
            entity.getConsultaActual().add(c.toEntity());
        }
        
        return entity;
    }
    
    public List<MedicoDTO> getDoctores() {
	return doctores;
    }

    public void setDoctores(List<MedicoDTO> doctores) {
	this.doctores = doctores;
    }
        
    public List<ConsultaHistoricaDTO> getConsultas() {
	return consultas;
    }

    public void setConsultasHistoricas(List<ConsultaHistoricaDTO> consultas){
	this.consultas = consultas;
    }
        
       // public ArrayList<CitaDTO> getCitas() {
	//	return citas;
	//}

	//public void setCitas(ArrayList<CitaDTO> citas) {
	//	this.citas = citas;
	//}
        
}
