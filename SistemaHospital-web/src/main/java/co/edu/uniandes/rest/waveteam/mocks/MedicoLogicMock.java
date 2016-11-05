package co.edu.uniandes.rest.waveteam.mocks;

/**
 * Mock del recurso Medicos (Mock del servicio REST)
 *
 * @author Asistente
 */

import co.edu.uniandes.rest.waveteam.dtos.CitaDTO;
import co.edu.uniandes.rest.waveteam.dtos.MedicoDTO;
import co.edu.uniandes.rest.waveteam.exceptions.MedicoLogicException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * CityLogicMock
 * Mock del recurso Medicos (Mock del servicio REST)
 */
public class MedicoLogicMock {

    // objeto para presentar logs de las operaciones
    private final static Logger logger = Logger.getLogger(MedicoLogicMock.class.getName());

    private static List<MedicoDTO> doctors;
    
    /**
     * Constructor. Crea los datos de ejemplo.
     */
    public MedicoLogicMock() {

        if (doctors == null) {
            logger.info("Inicializa la lista de doctores");
            doctors = new ArrayList<>();
            CitaDTO cita = new CitaDTO();
            cita.setHora(1474382871288L);
            cita.setDuracion(15);
            cita.setMedico(8L);
            cita.desactivar();
            cita.setId(1L);
            cita.setPaciente(1L);
            cita.setHabilitada("Habilitada");
            ArrayList <CitaDTO> d = new ArrayList();
            cita.addSm(532532L);
            cita.addSm(42353L);
            cita.addSm(263564L);
            d.add(cita);
            doctors.add(new MedicoDTO(1L, "Pedro Pablo Jaramillo", "Cardiologia", 301L, d));
            doctors.add(new MedicoDTO(5L, "Jairo Aristizabal", "Neumologia", 305L, new ArrayList<CitaDTO>()));
            doctors.add(new MedicoDTO(6L, "Carlos Diaz", "Pediatría", 320L, new ArrayList<CitaDTO>()));
            doctors.add(new MedicoDTO(3L, "Fernando Vallejo", "Traumatologia", 320L, new ArrayList<CitaDTO>()));
        
        }

        // indica que se muestren todos los mensajes
        logger.setLevel(Level.INFO);

        // muestra información
        logger.info("Doctores: " + doctors);
    }

    /**
     * Obtiene el listado de doctores.
     *
     * @return lista de doctores
     * @throws MedicoLogicException cuando no existe la lista en memoria
     */
    public List<MedicoDTO> getDoctors() throws MedicoLogicException {
        if (doctors == null) {
            logger.severe("Error interno: lista de doctores no existe.");
            throw new MedicoLogicException("Error interno: lista de doctores no existe.");
        }

        logger.info("Retornando todos los doctores");
        return doctors;
    }

    public List<CitaDTO> getDoctorSchedule(Long id) throws MedicoLogicException {
        if (doctors == null) {
            logger.severe("Error interno: lista de doctores no existe.");
            throw new MedicoLogicException("Error interno: lista de doctores no existe.");
        }

        for (MedicoDTO doctor : doctors) {
            if (Objects.equals(id, doctor.getId())) {
                logger.info("Retornando la disponibilidad del doctor con ID " + id);
//                return doctor.getDisponibilidad();
                return null;
            }
        }
        logger.info("No se encontro doctor con ese ID");
        throw new MedicoLogicException("No existe doctor con ese ID");
    }

    public MedicoDTO getDoctor(Long id) throws MedicoLogicException {
        if (doctors == null) {
            logger.severe("Error interno: lista de doctores no existe.");
            throw new MedicoLogicException("Error interno: lista de doctores no existe.");
        }

        for (MedicoDTO doctor : doctors) {
            if (Objects.equals(id, doctor.getId())) {
                logger.info("Retornando al doctor con ID " + id);
                return doctor;
            }
        }
        logger.info("No se encontro doctor con ese ID");
        throw new MedicoLogicException("No existe doctor con ese ID");
    }

    //REQUERIMIENTOS R4 Y R7 - MEDICO Y SUS DISPONIBILIDADES
    public List<CitaDTO> definirHorarioMedico(Long id, ArrayList<LinkedHashMap> diasDisponible) throws MedicoLogicException {
        if (doctors == null) {
            logger.severe("Error interno: lista de doctores no existe.");
            throw new MedicoLogicException("Error interno: lista de doctores no existe.");
        }

        for (MedicoDTO doctor : doctors) {
            if (Objects.equals(id, doctor.getId())) {
                logger.info("Agregando disponibilidad al doctor con id: " + id);
                logger.info("Agregando disponibilidad al doctor con id: " + id);
//                doctor.setDisponibilidad(diasDisponible);
//                return doctor.getDisponibilidad();
            return null;
            }
        }
        logger.info("No se encontro doctor con ese ID");
        throw new MedicoLogicException("No existe doctor con ese ID");
    }

    public MedicoDTO updateDoctor(Long id, MedicoDTO updatedCity) throws MedicoLogicException {
        if (doctors == null) {
            logger.severe("Error interno: lista de doctores no existe.");
            throw new MedicoLogicException("Error interno: lista de doctores no existe.");
        }

        boolean found = false;
        if (updatedCity.getId() != null) {
            for (MedicoDTO doctor : doctors) {
                if (Objects.equals(id, doctor.getId())) {
                    logger.info("Actualizando informacion del doctor con");
//                    doctor.setId(updatedCity.getId());
                    doctor.setName(updatedCity.getName());
                    doctor.setConsultorio(updatedCity.getConsultorio());
                    doctor.setEspecialidad(updatedCity.getEspecialidad());
                    found = true;
                    break;
                }
            }
            if (!found) {
                logger.severe("No se encontro doctor con ese ID");
                throw new MedicoLogicException("No existe doctor con ese ID");
            }

        } else {
            logger.severe("El ID dado es nulo");
            throw new MedicoLogicException("El id es nulo");

        }
        logger.info("Retornando el doctor actualizado");
        return updatedCity;
    }
    
    public MedicoDTO asignConsultorio(Long idMedico, Long idConsultorio) throws MedicoLogicException {
        if (doctors == null) {
            logger.severe("Error interno: lista de doctores no existe.");
            throw new MedicoLogicException("Error interno: lista de doctores no existe.");
        }
        MedicoDTO updatedDoctor = null;
        boolean found = false;
        if ( idMedico != null) {
            for (MedicoDTO doctor : doctors) {
                if (Objects.equals(idMedico, doctor.getId())) {
                    logger.info("Actualizando informacion del doctor con");
                    updatedDoctor = doctor;
                    doctor.setConsultorio(idConsultorio);
                    found = true;
                    break;
                }
            }
            if (!found) {
                logger.severe("No se encontro doctor con ese ID");
                throw new MedicoLogicException("No existe doctor con ese ID");
            }

        } else {
            logger.severe("El ID dado es nulo");
            throw new MedicoLogicException("El id es nulo");

        }
        logger.info("Retornando el doctor actualizado");
        return updatedDoctor;
    }

    /**
     * Agrega un medico a la lista.
     *
     * @param newDoctor doctor a adicionar
     * @throws MedicoLogicException cuando ya existe un doctor con el id
     * suministrado
     * @return doctor agregado
     */
    public MedicoDTO createDoctor(MedicoDTO newDoctor) throws MedicoLogicException {
        logger.info("Recibiendo solicitud de agregar doctor " + newDoctor);

        if (newDoctor.getId() != null) {
            for (MedicoDTO doctor : doctors) {
                if (Objects.equals(doctor.getId(), newDoctor.getId())) {
                    logger.severe("Ya existe un doctor con ese id");
                    throw new MedicoLogicException("Ya existe un doctor con ese id");
                }
            }

        } else {
            logger.severe("El ID suministrado es nulo");
            throw new MedicoLogicException("Debe suministrar un ID válido");
        }

        logger.info("Agregando doctor " + newDoctor);
        EspecialidadLogicMock esp = new EspecialidadLogicMock();
        esp.agregarDoctorEspecialidad(newDoctor);
        doctors.add(newDoctor);
        return newDoctor;
    }
    
    public void deleteDoctor(Long id) throws MedicoLogicException{
        if (doctors == null) {
            logger.severe("Error interno: lista de doctores no existe.");
            throw new MedicoLogicException("Error interno: lista de doctores no existe.");
        }
        for (MedicoDTO doctor : doctors) {
            if (Objects.equals(id, doctor.getId())) {
                logger.info("Borrando el doctor con ID "+id);
                doctors.remove(doctor);
                return;
            }
        }
        logger.info("No se encontro doctor con ese ID");
        throw new MedicoLogicException("No existe doctor con ese ID");
    }
}
