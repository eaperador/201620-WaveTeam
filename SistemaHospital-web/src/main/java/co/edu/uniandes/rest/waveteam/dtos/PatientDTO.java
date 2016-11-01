/*
 * PatientDTO
 * Objeto de transferencia de datos de Patient.
 */
package co.edu.uniandes.rest.waveteam.dtos;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
//import SistemaHospital-back.target.classes.co.edu.uniandes.waveteam.sistemahospital.entities.PacienteEntity;
/**
 * @author je.ardila1501
 */
@XmlRootElement
public class PatientDTO {

    private Long id;
    private String name;
    private String sexo;
    private int edad;
    private String tipoSangre;
    private String eps;
    private List<CitaDTO> citas;
    /**
     * Constructor por defecto
     */
    public PatientDTO() {

    }
    
    /**
     * 
     * @param entity 
     */
//     public PatientDTO(PacienteEntity entity) {
//        if (entity != null) {
//        this.id = id;
//        this.name = name;
//        this.edad=edad;
//        this.sexo=sexo;
//        this.tipoSangre=tipoSangre;
//        this.eps=eps;
//        this.citas = null;
//        }
//    }

    /**
     * Constructor con parámetros.
     * @param id
     * @param name
     * @param sexo
     * @param edad
     * @param tipoSangre
     * @param eps 
     * @param pcorreo
     * @param pcitas 
     */
    public PatientDTO(Long id, String name,int edad, String sexo , String tipoSangre, String eps) {
        this.id = id;
        this.name = name;
        this.edad=edad;
        this.sexo=sexo;
        this.tipoSangre=tipoSangre;
        this.eps=eps;
        this.citas = null;
        
    }

    public List<CitaDTO> getCitas() {
        return citas;
    }

    public void setCitas(List<CitaDTO> citas) {
        this.citas = citas;
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
    
    /**
     * 
     * @return the sex
     */
    public String getSexo(){
        return sexo;
    }
    
    /**
     * 
     * @param sexo the sex to set
     */
     public void setSexo(String sexo){
        this.sexo=sexo;
    }
     
     /**
      * 
      * @return 
      */
     public int getEdad(){
         return edad;
     }
     
     /**
      * 
      * @param edad 
      */
     public void setEdad(int edad){
         this.edad=edad;
     }
     
     /**
      * 
      * @return 
      */
     public String getTipoSangre(){
         return tipoSangre;
     }
     
     /**
      * 
      * @param tipoSAngre 
      */
     public void setTipoSangre(String tipoSAngre){
         this.tipoSangre=tipoSAngre;
     }
     
     /**
      * 
      * @return 
      */
     public String getEps(){
         return eps;
     }
     
     /**
      * 
      * @param eps 
      */
     public void setEps(String eps){
         this.eps=eps;
     }
     
//     /**
//      * 
//      */
//     public String getCorreo(){
//         return this.correo;
//     }
//     
//     /**
//      * 
//      */
//     public void setCorreo(String pcorreo){
//         this.correo=pcorreo;
//     }
     
//     /**
//      * 
//      */
//     public List<CitaDTO> getCitas(){
//         return this.citas;
//     }
//     
//     /**
//      * 
//      */
//     public void setCitas(List<CitaDTO> pcitas){
//         this.citas=pcitas;
//     }
    
     /**
     * Convierte el objeto a una cadena
     */
    @Override
    public String toString() {
        return "{ id : " + getId() + ", name : \"" + getName() + ", edad : \"" + getEdad() + 
                ", sexo : \"" + getSexo() + ", tipoSangre : \""+ getTipoSangre()+ 
                ", eps : \""+ getEps()+ "\" }";
    }
     
     
//    /**
//     * Convierte el objeto a una cadena
//     */
//    @Override
//    public String toString() {
//        return "{ id : " + getId() + ", name : \"" + getName() + ", edad : \"" + getEdad() + 
//                ", sexo : \"" + getSexo() + ", tipoSangre : \""+ getTipoSangre()+ 
//                ", eps : \""+ getEps()+ ", correo : \""+ getCorreo()+ "\" }";
//    }
}
