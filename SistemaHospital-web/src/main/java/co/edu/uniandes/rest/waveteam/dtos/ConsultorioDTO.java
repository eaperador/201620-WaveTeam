/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author r.garcia11
 */
public class ConsultorioDTO {
    
    private Long id;
    private String nombre;
    private String horario;
    private boolean atencionUrgencias;
    private boolean unidadCuidadosIntensivos;
    
    // Un consultorio puede estar asignado a muchos doctores
    private List<MedicoDTO> doctoresAsignados = new ArrayList<MedicoDTO>();
    
    /**
     * Constructor vacío
     */
    public ConsultorioDTO()
    {        
       //Este constructor esta vacio porque es el predeterminado
    }
    
    /**
     * Constructor con parámetros
     */
    public ConsultorioDTO(Long id, String nombre, String horario, boolean atencionUrgencias, boolean unidadCuidadosIntensivos)
    {
        this.id = id;
        this.nombre = nombre;
        this.horario = horario;
        this.atencionUrgencias = atencionUrgencias;
        this.unidadCuidadosIntensivos = unidadCuidadosIntensivos;
    }
    
    /**
     * Devuelve el id del consultorio
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * Cambia el id por el id por parámetro
     * 
     * @param id 
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
        
    /**
     * Devuelve el horario
     */
    public String getHorario()
    {
        return horario;   
    }
    
    /**
     * Cambia el horario
     * @param horario 
     */
    public void setHorario(String horario)
    {
        this.horario = horario;
    }
    
    /**
     * Devuelve si atiende urgencias
     * @return 
     */
    public boolean getAtencionUrgencias()
    {
        return atencionUrgencias;
    }
    
    /**
     * Cambia el estado de atención a urgencias
     * 
     * @param atencionUrgencias 
     */
    public void setAtencionUrgencias(boolean atencionUrgencias)
    {
        this.atencionUrgencias = atencionUrgencias;
    }
    
    /**
     * Devuelve si tiene unidad de cuidados intensivos
     */
    public boolean getUnidadCuidadosIntensivos()
    {
        return unidadCuidadosIntensivos;
    }
    
    /**
     * Cambia si tiene UCI
     * @param unidadCuidadosIntensivos 
     */
    public void setUnidadCuidadosIntensivos(boolean unidadCuidadosIntensivos)
    {
        this.unidadCuidadosIntensivos = unidadCuidadosIntensivos;
    }
    
    
    //REQUERIMIENTO R8 - ASIGNAR CONSULTORIOS A DOCTORES
    
    /**
     * Retorna los doctores asignados a este consultorio
     */
    public List<MedicoDTO> getDoctoresAsignados(){
        return doctoresAsignados;
    }
    
    /**
     * Edita la lista de doctores asignados con la lista por parámetro
     * @param nuevosDoctores lista de doctores asignados
     */
    public void setDoctoresAsignados(List<MedicoDTO> nuevosDoctores){
        doctoresAsignados = nuevosDoctores;
    }
    
    public void agregarDoctorAsignado(MedicoDTO doc)
    {
        doctoresAsignados.add(doc);
    }
    
    public boolean eliminarDoctorAsignado(Long idDoctor)
    {
        for (MedicoDTO doc: doctoresAsignados)
        {
            if (Objects.equals(doc.getId(), idDoctor))
            {
                doctoresAsignados.remove(doc);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Devuelve un String con los parámetros del objeto
     */
    @Override
    public String toString()
    {
        return "Id: " + id + 
                ", nombre: " + nombre + 
                ", horario: " + horario + 
                ", atiende urgencias: " + (atencionUrgencias?"Si":"No") + 
                ", UCI: " + (unidadCuidadosIntensivos?"Si":"No");
    }
}
