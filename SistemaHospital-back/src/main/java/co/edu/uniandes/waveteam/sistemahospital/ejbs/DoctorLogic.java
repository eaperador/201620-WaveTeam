package co.edu.uniandes.waveteam.sistemahospital.ejbs;

import co.edu.uniandes.waveteam.sistemahospital.api.IDoctorLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;

import java.util.List;

/**
 * Created by felipeplazas on 10/27/16.
 */
public class DoctorLogic implements IDoctorLogic {

    @Override
    public List<DoctorEntity> getDoctores() {
        return null;
    }

    @Override
    public DoctorEntity getDoctorById() {
        return null;
    }

    @Override
    public DoctorEntity getDoctorByName() {
        return null;
    }

    @Override
    public List<DoctorEntity> getDoctorByEspecialidad() {
        return null;
    }

    @Override
    public void createDoctor(DoctorEntity doctorEntity) {

    }

    @Override
    public void updateDoctor(DoctorEntity doctorEntity) {

    }

    @Override
    public void deleteDoctor(Long doctorId) {

    }
}
