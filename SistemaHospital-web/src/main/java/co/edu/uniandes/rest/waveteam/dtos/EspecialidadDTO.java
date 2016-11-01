/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.dtos;

import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author d.marino10
 */
public class EspecialidadDTO {
    
    private Long id;
    private String nombre;
    private String gruposEdad;
    private String tipo;
    private ArrayList<MedicoDTO> doctores;
    private ArrayList<CitaDTO> citas;
    
    public EspecialidadDTO(){
        
    }
    
    public EspecialidadDTO(Long id,String nombre,String gruposEdad,String tipo, ArrayList<MedicoDTO> doctores,ArrayList<CitaDTO> citas){
        this.id=id;
        this.nombre=nombre;
        this.gruposEdad=gruposEdad;
        this.tipo=tipo;
        this.doctores=doctores;
        this.citas=citas;
    }
    
    public EspecialidadDTO(EspecialidadEntity entity){
        this.id=entity.getId();
        this.nombre=entity.getName();
        this.gruposEdad=entity.getGruposEdad();
        this.tipo=entity.getTipo();
        
    }
    
    	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGruposEdad() {
		return gruposEdad;
	}

	public void setGruposEdad(String gruposEdad) {
		this.gruposEdad = gruposEdad;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
        
        public ArrayList<MedicoDTO> getDoctores() {
		return doctores;
	}

	public void setDoctores(ArrayList<MedicoDTO> doctores) {
		this.doctores = doctores;
	}
        
        public ArrayList<CitaDTO> getCitas() {
		return citas;
	}

	public void setCitas(ArrayList<CitaDTO> citas) {
		this.citas = citas;
	}
        
        
    public EspecialidadEntity toEntity() {
        
        EspecialidadEntity entity = new EspecialidadEntity();
        entity.setName(this.getNombre());
        entity.setId(this.getId());
        entity.setTipo(this.getTipo());
        entity.setGruposEdad(this.getGruposEdad());
        return entity;

    }
     
    @Override
    public String toString() {
        return "{ id : " + getId() + ", nombre : \"" + getNombre() + "\" }";
    }
}
