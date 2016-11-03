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
public class ConsultaHistoricaDetailDTO extends ConsultaHistoricaDTO {
    
    private EspecialidadDTO especialidad;
    
    public ConsultaHistoricaDetailDTO(){
        super();
    }
    
    public ConsultaHistoricaDetailDTO(ConsultaHistoricaEntity entity) {
        super(entity);
        if (entity.getEspecialidad() != null) {
            this.especialidad = new EspecialidadDTO(entity.getEspecialidad());
        }

    }
    
    public ConsultaHistoricaEntity toEntity() {
        ConsultaHistoricaEntity entity = super.toEntity();
        if (this.especialidad != null) {
            entity.setEspecialidad(this.especialidad.toEntity());
        }
        return entity;
    }
    
    public EspecialidadDTO getEspecialidad() {
        return especialidad;
    }
    
     public void setEspecialidad(EspecialidadDTO esp) {
        this.especialidad = esp;
    }
}
