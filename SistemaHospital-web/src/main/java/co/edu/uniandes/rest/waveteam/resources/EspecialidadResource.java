/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.waveteam.resources;

import co.edu.uniandes.rest.waveteam.dtos.EspecialidadDTO;
import co.edu.uniandes.rest.waveteam.dtos.MedicoDTO;
import co.edu.uniandes.waveteam.sistemahospital.api.IEspecialidadLogic;
import co.edu.uniandes.waveteam.sistemahospital.entities.EspecialidadEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author d.marino10
 */
@Path("especialidades")
@Produces("application/json")
@Consumes("application/json")
public class EspecialidadResource {
    
    
    @Inject
    private IEspecialidadLogic espLogic;
 
    private List<EspecialidadDTO> listEntity2DTO(List<EspecialidadEntity> entityList) {
        List<EspecialidadDTO> list = new ArrayList<>();
        for (EspecialidadEntity entity : entityList) {
            list.add(new EspecialidadDTO(entity));
        }
        return list;
    }

    /**
     * Obtiene el listado de especialidades.
     *
     * @return lista de especialidades
     * @throws EspecialidadLogicException excepción retornada por la lógica
     */
    @GET
    public List<EspecialidadDTO> getEspecialidades() throws Exception {
        return listEntity2DTO(espLogic.getEspecialidades());
    }

    @GET
    @Path("{id: \\d+}")
    public EspecialidadDTO getEspecialidad(@PathParam("id") Long id) throws Exception {
        return new EspecialidadDTO(espLogic.getEspecialidad(id));
    }
    
    @GET
    @Path("name")
    public EspecialidadDTO getEspecialidadByName(@QueryParam("name") String name) {
        EspecialidadEntity entity = espLogic.getEspecialidadPorNombre(name);
        if (entity == null) {
            throw new WebApplicationException("La compañía no existe", 404);
        } else {
            return new EspecialidadDTO(entity);
        }
    }

    /**
     * 
     * @param id
     * @param especialidad
     * @return
     */
    @PUT
    @Path("{id: \\d+}")
    public EspecialidadDTO updateEspecialidad(@PathParam("id") Long id, EspecialidadDTO especialidad) throws Exception {
        
        EspecialidadEntity entity =especialidad.toEntity();
        entity.setId(id);
        return new EspecialidadDTO(espLogic.updateEspecialidad(entity));
    }

    /**
     * Agrega una especialidad
     *
     * @param especialidad
     * @return especialidad2 Datos del médico agregado
     * @throws EspecialidadLogicException cuando ya existe una especialidad con el id 
     * suministrado
     */
    @POST
    public EspecialidadDTO createEspecialidad(EspecialidadDTO especialidad) throws Exception {
        return new EspecialidadDTO(espLogic.createEspecialidad(especialidad.toEntity()));
    }

    /**
     * 
     * @param id
     * @throws EspecialidadLogicException 
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteEspecialidad(@PathParam("id") Long id) throws Exception {
        espLogic.deleteEspecialidad(id);
    }
 
}
