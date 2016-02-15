package com.smcpartners.shape.usecases.add_measure;

import com.smcpartners.shape.crosscutting.security.RequestScopedUserId;
import com.smcpartners.shape.crosscutting.security.annotations.SecureRequireActiveLogAvtivity;
import com.smcpartners.shape.frameworks.data.dao.shape.MeasureDAO;
import com.smcpartners.shape.frameworks.data.dao.shape.UserDAO;
import com.smcpartners.shape.shared.dto.shape.MeasureDTO;
import com.smcpartners.shape.shared.dto.shape.UserDTO;
import com.smcpartners.shape.shared.dto.shape.response.IntEntityResponseDTO;
import com.smcpartners.shape.shared.constants.SecurityRoleEnum;
import com.smcpartners.shape.shared.usecasecommon.UseCaseException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible:<br/>
 * 1.
 * <p>
 * Created by johndestefano on 11/4/15.
 * <p>
 * Changes:<b/>
 */
@RequestScoped
public class AddMeasureServiceAdapter implements AddMeasureService {

    @Inject
    private Logger log;

    @EJB
    private UserDAO userDAO;

    @EJB
    private MeasureDAO measureDAO;

    @Inject
    private RequestScopedUserId requestScopedUserId;


    public AddMeasureServiceAdapter() {
    }

    @Override
    @SecureRequireActiveLogAvtivity({SecurityRoleEnum.ADMIN,
            SecurityRoleEnum.ORG_ADMIN, SecurityRoleEnum.REGISTERED})
    public IntEntityResponseDTO addMeasure(MeasureDTO org) throws UseCaseException {
        try {
            MeasureDTO orgDTO = measureDAO.create(org);
            return IntEntityResponseDTO.makeNew(orgDTO.getId());

        } catch (Exception e) {
            log.logp(Level.SEVERE, this.getClass().getName(), "addMeasure", e.getMessage(), e);
            throw new UseCaseException(e.getMessage());
        }
    }
}
