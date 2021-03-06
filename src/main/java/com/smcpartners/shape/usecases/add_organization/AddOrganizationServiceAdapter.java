package com.smcpartners.shape.usecases.add_organization;

import com.smcpartners.shape.crosscutting.security.RequestScopedUserId;
import com.smcpartners.shape.crosscutting.security.annotations.SecureRequireActiveLogActivity;
import com.smcpartners.shape.frameworks.data.dao.shape.OrganizationDAO;
import com.smcpartners.shape.frameworks.data.dao.shape.UserDAO;
import com.smcpartners.shape.shared.constants.SecurityRoleEnum;
import com.smcpartners.shape.shared.dto.shape.OrganizationDTO;
import com.smcpartners.shape.shared.dto.shape.response.IntEntityResponseDTO;
import com.smcpartners.shape.shared.usecasecommon.UseCaseException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible:<br/>
 * 1. Only ADMIN can add an organization
 * <p>
 * Created by johndestefano on 11/4/15.
 * <p>
 * Changes:<b/>
 */
@RequestScoped
public class AddOrganizationServiceAdapter implements AddOrganizationService {

    @Inject
    private Logger log;

    @EJB
    private UserDAO userDAO;

    @EJB
    private OrganizationDAO organizationDAO;

    @Inject
    private RequestScopedUserId requestScopedUserId;


    public AddOrganizationServiceAdapter() {
    }

    @Override
    @SecureRequireActiveLogActivity({SecurityRoleEnum.ADMIN})
    public IntEntityResponseDTO addOrganization(OrganizationDTO org) throws UseCaseException {
        try {
            // Only ADMIN can add organizations
            OrganizationDTO orgDTO = organizationDAO.create(org);
            return IntEntityResponseDTO.makeNew(orgDTO.getId());
        } catch (Exception e) {
            log.logp(Level.SEVERE, this.getClass().getName(), "addOrganization", e.getMessage(), e);
            throw new UseCaseException(e.getMessage());
        }
    }
}
