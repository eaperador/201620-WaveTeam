/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.dtos;

import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultaHistoricaEntity;

/**
 *
 * @author d.marino10
 */
public class ConsultaHistoricaDTO {
    
    private EspecialidadDTO especialidad;
    private String fecha;
    private int numeroDoctores;
    private int numeroCitas;
    private long promedioDuracion;
    private int citasLibres;
    private int citasCanceladas;
    private int citasTerminadas;
    
    public ConsultaHistoricaDTO(){
        
    }
    
    public ConsultaHistoricaDTO(EspecialidadDTO es,int numDoc,int numCitas,int prom,int citasLi,int citasCan,int citasTer, String fecha){
        this.especialidad=es;
        this.numeroDoctores=numDoc;
        this.numeroCitas=numCitas;
        this.promedioDuracion=prom;
        this.citasLibres=citasLi;
        this.citasCanceladas=citasCan;
        this.citasTerminadas=citasTer;
        this.fecha=fecha;
    }
    
    public ConsultaHistoricaDTO(ConsultaHistoricaEntity entity){
        this.especialidad=new EspecialidadDTO(entity.getEspecialidad());
        this.numeroDoctores=entity.getNumeroDoctores();
        this.numeroCitas=entity.getNumeroCitas();
        this.promedioDuracion=entity.getPromedioDuracion();
        this.citasLibres=entity.getCitasLibres();
        this.citasCanceladas=entity.getCitasCanceladas();
        this.citasTerminadas=entity.getCitasTerminadas();
        this.fecha=entity.getFecha();
    }
    
    	public EspecialidadDTO getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(EspecialidadDTO especialidad) {
		this.especialidad = especialidad;
	}

	public int getNumeroDoctores() {
		return numeroDoctores;
	}

	public void setNumeroDoctores(int numeroDoctores) {
		this.numeroDoctores = numeroDoctores;
	}

	public int getNumeroCitas() {
		return numeroCitas;
	}

	public void setNumeroCitas(int numeroCitas) {
		this.numeroCitas = numeroCitas;
	}

	public long getPromedioDuracion() {
		return promedioDuracion;
	}

	public void setPromedioDuracion(long promedioDuracion) {
		this.promedioDuracion = promedioDuracion;
	}

	public int getCitasLibres() {
		return citasLibres;
	}

	public void setCitasLibres(int citasLibres) {
		this.citasLibres = citasLibres;
	}    
        
        public int getCitasCanceladas() {
		return citasCanceladas;
	}

	public void setCitasCanceladas(int citasCanceladas) {
		this.citasCanceladas = citasCanceladas;
	}    
        
        public int getCitasTerminadas() {
		return citasTerminadas;
	}

	public void setCitasTerminadas(int citasTerminadas) {
		this.citasTerminadas = citasTerminadas;
	}    
        
        public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha=fecha;
	}   
}
