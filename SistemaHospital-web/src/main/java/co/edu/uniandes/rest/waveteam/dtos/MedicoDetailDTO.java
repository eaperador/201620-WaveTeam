/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.dtos;

import co.edu.uniandes.rest.waveteam.mocks.CitaLogicMock;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author felipeplazas
 */
@XmlRootElement
public class MedicoDetailDTO extends MedicoDTO {
    
    private ArrayList<CitaDTO> disponibilidad;
    
    public MedicoDetailDTO(){
        
    }
    
    public MedicoDetailDTO(DoctorEntity entity){
        super(entity);
    }
    
    public MedicoDetailDTO(Long id, String name, String especialidad, Long consultorio, ArrayList<CitaDTO> dispo) {
        super(id, name, especialidad, consultorio, dispo);
        this.disponibilidad = dispo;
    }
    
    public ArrayList<CitaDTO> getDisponibilidad(){
        return disponibilidad;
    }
    
    @Override
    public DoctorEntity toEntity() {
        DoctorEntity entity = super.toEntity();
         List<CitaDTO> citas = this.disponibilidad; 
//        for (CitaDTO cita : this.disponibilidad) {         
//            entity.getDisponibilidadCitas().add(cita.toEntity());
//        }
        return entity;
    }
    
    public void setDisponibilidad(ArrayList<LinkedHashMap> diasDisponible){
        for (int j = 0; j < diasDisponible.size() ; j++) {
            LinkedHashMap lhm = diasDisponible.get(j);
            Long inicio = (Long)lhm.get("value");
            Calendar n = new GregorianCalendar();
            n.setTimeInMillis(inicio);
            int fromDay = n.get(Calendar.DAY_OF_WEEK);
            int i = 0;
            if(CitaLogicMock.getCityArray() == null) { CitaLogicMock clm = new CitaLogicMock();}
            while ((fromDay == n.get(Calendar.DAY_OF_WEEK)) && (n.get(Calendar.HOUR_OF_DAY) <= 24)){
                if (i == 4) break;
                CitaDTO cita = new CitaDTO();
                cita.setHora(inicio+(i*900000));
                n.setTimeInMillis(n.getTimeInMillis()+(i*900000));
                cita.setDuracion(15);
                cita.setMedico(this.id);
                cita.desactivar();
                cita.setId(1L);
                cita.setPaciente(-1L);
                System.out.println(cita.toString());
//                disponibilidad.add(cita);
                CitaLogicMock.getCityArray().add(cita);
                i++;
            }
        }
    }
    
}
