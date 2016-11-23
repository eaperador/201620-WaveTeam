/*
 * PatientDTO
 * Objeto de transferencia de datos de Patient.
 */
package co.edu.uniandes.rest.waveteam.dtos;
import co.edu.uniandes.waveteam.sistemahospital.entities.CitaEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.PacienteEntity;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author je.ardila1501
 */
@XmlRootElement
public class PatientDetailDTO extends PatientDTO{

   
    private List<CitaDTO> citas;
    
    /**
     * Constructor por defecto
     */
    public PatientDetailDTO() {

    }
    
    public PatientDetailDTO(PacienteEntity entity) {
        super(entity);
        List<CitaEntity> listaCitas = entity.getCitas();
        for (CitaEntity list : listaCitas) {
            this.citas.add(new CitaDTO(list));
        }
    }
    
    @Override
    public PacienteEntity toEntity() {
        PacienteEntity entity = super.toEntity();
         List<CitaDTO> citas = this.getCitas();
//        for (CitaDTO cita : this.citas) {         
//            entity.getCitas().add(cita.toEntity());
//        }
        return entity;
    }

    public List<CitaDTO> getCitas() {
        return citas;
    }

    public void setCitas(List<CitaDTO> citas) {
        this.citas = citas;
    }
    
 
}
