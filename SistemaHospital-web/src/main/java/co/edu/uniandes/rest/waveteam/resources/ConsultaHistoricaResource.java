/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.resources;

import co.edu.uniandes.rest.waveteam.dtos.ConsultaHistoricaDTO;
import co.edu.uniandes.rest.waveteam.dtos.ConsultaHistoricaDetailDTO;
import co.edu.uniandes.rest.waveteam.exceptions.ConsultaHistoricaLogicException;
import co.edu.uniandes.waveteam.sistemahospital.api.IConsultaHistoricaLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.ConsultaHistoricaEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author d.marino10
 */
@Path("consultasHistoricas")
@Produces("application/json")
@Consumes("application/json")
public class ConsultaHistoricaResource {
    
    @Inject
    private IConsultaHistoricaLogic logic;
 
    private List<ConsultaHistoricaDetailDTO> listEntity2DTO(List<ConsultaHistoricaEntity> entityList) {
        List<ConsultaHistoricaDetailDTO> list = new ArrayList<>();
        for (ConsultaHistoricaEntity entity : entityList) {
            list.add(new ConsultaHistoricaDetailDTO(entity));
        }
        return list;
    }
    

    /**
     * Obtiene el listado de especialidades.
     *
     * @return lista de especialidades
     */
    @GET
    public List<ConsultaHistoricaDetailDTO> getConsultasHistoricas() throws Exception {
        return listEntity2DTO(logic.getTodasLasConsultasHistoricas());
    }
    
    @GET
    @Path("{id: \\d+}")
    public List<ConsultaHistoricaDetailDTO> getConsultasHistoricasPorEspecialidad(@PathParam("id") Long espId) throws Exception {
        return listEntity2DTO(logic.getConsultasHistoricasPorEspecialidad(espId));
    }
    
    @GET
    @Path("{fecha: }")
    public List<ConsultaHistoricaDetailDTO> getConsultasHistoricasPorFecha(@PathParam("fecha") String fecha) throws Exception {
        return listEntity2DTO(logic.getConsultasHistoricasPorFecha(fecha));
    }
    
    //@GET
    //@Path("{id: \\d+}")
    //public ConsultaHistoricaDetailDTO getConsultaHistorica(@PathParam("id") Long id) throws Exception {
        //return new ConsultaHistoricaDetailDTO(logic.getConsultaHistorica(id));
    //}


    @POST
    @Path("{nombre: }")
    public ConsultaHistoricaDetailDTO createConsultaHistorica(@PathParam("nombre") String nombreEsp) throws ConsultaHistoricaLogicException {
        return new ConsultaHistoricaDetailDTO(logic.createConsultaHistorica(nombreEsp));
    }

    @DELETE
    @Path("{id: \\d+}")
    public void deleteConsultaHistorica(@PathParam("id") Long id) throws ConsultaHistoricaLogicException {
        logic.deleteConsultaHistorica(id);
    }
}
