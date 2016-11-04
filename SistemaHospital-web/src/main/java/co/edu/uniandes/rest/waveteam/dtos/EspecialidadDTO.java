/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.dtos;

import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author d.marino10
 */
@XmlRootElement
public class EspecialidadDTO {
    
    private Long id;
    private String nombre;
    private String gruposEdad;
    private String tipo;
    
    public EspecialidadDTO(){
        
    }
    
    public EspecialidadDTO(Long id,String nombre,String gruposEdad,String tipo){
        this.id=id;
        this.nombre=nombre;
        this.gruposEdad=gruposEdad;
        this.tipo=tipo;

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
