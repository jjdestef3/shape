package com.smcpartners.shape.usecases.find_measures_by_id;

import com.smcpartners.shape.shared.dto.shape.MeasureDTO;
import com.smcpartners.shape.shared.dto.shape.OrganizationMeasureDTO;
import com.smcpartners.shape.shared.usecasecommon.UseCaseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Responsible:<br/>
 * 1. Support REST framework
 * <p>
 * Created by johndestefano on 11/4/15.
 * <p>
 * Changes:<b/>
 */
@Path("/admin")
public interface FindMeasuresByIdService {

    @POST
    @Path("/find/measureById")
    @Produces("application/json")
    @Consumes("application/json")
    List<MeasureDTO> findMeasuresById(List<OrganizationMeasureDTO> orgM) throws UseCaseException;
}
