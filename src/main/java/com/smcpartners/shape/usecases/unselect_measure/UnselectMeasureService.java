package com.smcpartners.shape.usecases.unselect_measure;

import com.smcpartners.shape.shared.dto.common.BooleanValueDTO;
import com.smcpartners.shape.shared.dto.shape.request.IntEntityIdRequestDTO;
import com.smcpartners.shape.shared.usecasecommon.UseCaseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Responsible:</br>
 * 1. Support REST framework </br>
 * <p>
 * Created by johndestefano on 3/15/16.
 * </p>
 * <p>
 * Changes:</br>
 * 1. </br>
 * </p>
 */
@Path("/admin")
public interface UnselectMeasureService {
    /**
     * Inactivate the user
     *
     * @throws UseCaseException
     */
    @POST
    @Path("/measure/unselect")
    @Produces("application/json")
    @Consumes("application/json")
    BooleanValueDTO unselectMeasure(IntEntityIdRequestDTO id) throws UseCaseException;
}
