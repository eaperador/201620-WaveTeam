/*
 * MedicoDTO
 * Objeto de transferencia de datos de Médicos.
 */
package co.edu.uniandes.rest.waveteam.dtos;

import co.edu.uniandes.rest.waveteam.mocks.MedicoLogicMock;

import java.util.*;
import java.util.logging.Logger;
/**
 * Objeto de transferencia de datos de Ciudades.
 *
 * @author Asistente
 */
public class MedicoDTO {
    private Long id;
    private String name;
    private String especialidad;
    private Long consultorio;
    private List<CitaDTO> disponibilidad;

    /**
     * Constructor por defecto
     */
    public MedicoDTO() {

    }

    public MedicoDTO(Long id, String name, String especialidad, Long consultorio) {
        this.id = id;
        this.name = name;
        this.especialidad = especialidad;
        this.consultorio = consultorio;
        this.disponibilidad = new ArrayList<>();
        
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Long getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Long consultorio) {
        this.consultorio = consultorio;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    //REQUERIMIENTOS R4 Y R7 - MEDICO Y SUS DISPONIBILIDADES
    public void setDisponibilidad(Long... diasDisponible){
        for (int j = 0; j < diasDisponible.length ; j++) {
            Long inicio = diasDisponible[j];
            Calendar n = new GregorianCalendar();
            n.setTimeInMillis(inicio);
            int fromDay = n.get(Calendar.DAY_OF_WEEK);
            int i = 1;
            while (fromDay == n.get(Calendar.DAY_OF_WEEK) && n.get(Calendar.HOUR_OF_DAY) <= 20){
                CitaDTO cita = new CitaDTO();
                cita.setHora(inicio+i*900000);
                cita.setDuracion(15);
                cita.setMedico(this.id);
                cita.desactivar();
                cita.setId(1L);
                cita.setPaciente(1L);
                disponibilidad.add(cita);
                i++;
            }
        }
    }
    
    public List<CitaDTO> getDisponibilidad()
    {
        return disponibilidad;
    }
   
    /**
     * Convierte el objeto a una cadena
     */
    public String toString() {
        return "{ id : " + getId() + ", name : \"" + getName() + "\" }";
    }
}
