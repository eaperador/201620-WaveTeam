
package co.edu.uniandes.waveteam.sistemahospital.ejbs;

import co.edu.uniandes.waveteam.sistemahospital.api.IConsultorioLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultorioEntity;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;
import co.edu.uniandes.waveteam.sistemahospital.exceptions.WaveTeamLogicException;
import co.edu.uniandes.waveteam.sistemahospital.persistence.ConsultorioPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author r.garcia11
 */
@Stateless
public class ConsultorioLogic implements IConsultorioLogic {
    @Inject
    private ConsultorioPersistence persistencia;

    //Implementacion de la interfaz
    @Override
    public List<ConsultorioEntity> getConsultorios() {
        return persistencia.findAll();
    }

    @Override
    public ConsultorioEntity getConsultorio(Long id) {
        return persistencia.find(id);
    }
    
    @Override
    public ConsultorioEntity findByName(String name){
        return persistencia.findByName(name);
    }

    @Override
    public ConsultorioEntity updateConsultorio(ConsultorioEntity consultorioActualizado) throws WaveTeamLogicException{
        ConsultorioEntity cons = persistencia.findByName(consultorioActualizado.getName());
        if (cons != null && cons.getId() != consultorioActualizado.getId())
            throw new WaveTeamLogicException("No se puede actualizar porque el nombre ya existe.");
        return persistencia.update(consultorioActualizado);
    }

    @Override
    public ConsultorioEntity createConsultorio(ConsultorioEntity consultorio) throws WaveTeamLogicException{
        ConsultorioEntity cons = persistencia.find(consultorio.getId());
        if (cons != null) {
            throw new WaveTeamLogicException("ya existe un consultorio con ese id");
        }
        cons = persistencia.findByName(consultorio.getName());
        if (cons != null){
            throw new WaveTeamLogicException("ya existe un consultorio con ese nombre");
        }
        return persistencia.create(consultorio);
    }

    @Override
    public void deleteConsultorio(Long id) throws WaveTeamLogicException {
        ConsultorioEntity cons = persistencia.find(id);
        if (cons == null){
            throw new WaveTeamLogicException("El consultorio que se quiere eliminar no existe");}
        persistencia.delete(id);
    }

    @Override
    public ConsultorioEntity unasignDoctors(Long id) throws WaveTeamLogicException {
        ConsultorioEntity cons = persistencia.find(id);
        if (cons == null){
            throw new WaveTeamLogicException("El consultorio cuyos doctores se quieren desasignar no existe");}
        return persistencia.unasignDoctors(id);
    }

    @Override
    public ConsultorioEntity unasignDoctor(Long idConsultorio, Long idDoctor) throws WaveTeamLogicException {
        ConsultorioEntity cons = persistencia.find(idConsultorio);
        if (cons == null){
            throw new WaveTeamLogicException("El consultorio cuyo doctor se quiere desasignar no existe");}
        return persistencia.unasignDoctor(idConsultorio, idDoctor);
    }

    @Override
    public ConsultorioEntity asignDoctor(Long idConsultorio, DoctorEntity doc) throws WaveTeamLogicException {
        ConsultorioEntity cons = persistencia.find(idConsultorio);
        if (cons == null){
            throw new WaveTeamLogicException("El consultorio al que se le quiere asignar un doctor no existe");}
        return persistencia.asignDoctor(idConsultorio, doc);
    }
}
