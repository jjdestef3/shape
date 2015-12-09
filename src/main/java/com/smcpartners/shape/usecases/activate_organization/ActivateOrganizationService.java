package com.smcpartners.shape.usecases.activate_organization;

import com.smcpartners.shape.shared.dto.common.BooleanValueDTO;
import com.smcpartners.shape.shared.dto.shape.request.IntEntityIdRequestDTO;
import com.smcpartners.shape.shared.usecasecommon.UseCaseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Responsible:<br/>
 * 1.
 * <p>
 * Created by johndestefano on 11/4/15.
 * <p>
 * Changes:<b/>
 */
@Path("admin")
public interface ActivateOrganizationService {

    @POST
    @Path("/organization/activate")
    @Produces("application/json")
    @Consumes("application/json")
    BooleanValueDTO activateOrganization(IntEntityIdRequestDTO id) throws UseCaseException;
}
