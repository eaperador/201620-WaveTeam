/*
 * MedicoDTO
 * Objeto de transferencia de datos de Médicos.
 */
package co.edu.uniandes.rest.waveteam.dtos;

import co.edu.uniandes.rest.waveteam.mocks.CitaLogicMock;
import co.edu.uniandes.waveteam.sistemahospital.entities.CitaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
/**
 * Objeto de transferencia de datos de Ciudades.
 *
 * @author Asistente
 */
@XmlRootElement
public class MedicoDTO {
    Long id; 
    private String name;
    private String especialidad;
    private Long consultorio;
    
    private static Long index = 1L;

    /**  
     * Constructor por defecto
     */
    public MedicoDTO() {

    }

    public MedicoDTO(DoctorEntity entity){
        this.id = entity.getId();
        this.name = entity.getName();
        if (entity.getEspecialidad() != null)
            this.especialidad = entity.getEspecialidad().getName();
        this.consultorio = entity.getConsultorio();
    }

    public MedicoDTO(Long id, String name, String especialidad, Long consultorio, ArrayList<CitaDTO> dispo) {
        this.id = id;
        this.name = name;
        this.especialidad = especialidad;
        this.consultorio = consultorio;
    }
    
    public DoctorEntity toEntity(){
        DoctorEntity entity = new DoctorEntity();
        entity.setId(this.getId());
        
//        System.out.println("JOJO: "+entity.getId());
        entity.setConsultorio(this.consultorio);
        entity.setName(this.name);
        
        EspecialidadEntity ent = new EspecialidadEntity();
//        ent.setId(5L);
        ent.setName(this.getEspecialidad());
        entity.setEspecialidad(ent);
        return entity;
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
//    public void setDisponibilidad(ArrayList<LinkedHashMap> diasDisponible){
//        for (int j = 0; j < diasDisponible.size() ; j++) {
//            LinkedHashMap lhm = diasDisponible.get(j);
//            Long inicio = (Long)lhm.get("value");
//            Calendar n = new GregorianCalendar();
//            n.setTimeInMillis(inicio);
//            int fromDay = n.get(Calendar.DAY_OF_WEEK);
//            int i = 0;
//            if(CitaLogicMock.getCityArray() == null) { CitaLogicMock clm = new CitaLogicMock();}
//            while ((fromDay == n.get(Calendar.DAY_OF_WEEK)) && (n.get(Calendar.HOUR_OF_DAY) <= 24)){
//                if (i == 4) break;
//                CitaDTO cita = new CitaDTO();
//                cita.setHora(inicio+(i*900000));
//                n.setTimeInMillis(n.getTimeInMillis()+(i*900000));
//                cita.setDuracion(15);
//                cita.setMedico(this.id);
//                cita.desactivar();
//                cita.setId(1L);
//                cita.setPaciente(-1L);
//                System.out.println(cita.toString());
////                disponibilidad.add(cita);
//                CitaLogicMock.getCityArray().add(cita);
//                i++;
//            }
//        }
//    }
    
//    public ArrayList<CitaDTO> getDisponibilidad(){
//        return disponibilidad;
//    }
   
//    /**
//     * Convierte el objeto a una cadena
//     */
//    @Override
//    public String toString() {
//        return "{ id : " + getId() + ", name : \"" + getName() + "\" }";
//    }
}
