package co.edu.uniandes.waveteam.sistemahospital.api;

import co.edu.uniandes.waveteam.sistemahospital.entities.DoctorEntity;

import java.util.List;

/**
 * Created by felipeplazas on 10/27/16.
 */
public interface IDoctorLogic {

    public List<DoctorEntity> getCitas();
    public DoctorEntity getDoctorById();
    public DoctorEntity getDoctorByName();
}
