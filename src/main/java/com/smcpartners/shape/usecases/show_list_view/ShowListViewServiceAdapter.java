package com.smcpartners.shape.usecases.show_list_view;

import com.smcpartners.shape.crosscutting.security.RequestScopedUserId;
import com.smcpartners.shape.crosscutting.security.annotations.SecureRequireActiveLogActivity;
import com.smcpartners.shape.frameworks.data.dao.shape.MeasureDAO;
import com.smcpartners.shape.frameworks.data.dao.shape.OrganizationMeasureDAO;
import com.smcpartners.shape.frameworks.data.dao.shape.UserDAO;
import com.smcpartners.shape.shared.constants.SecurityRoleEnum;
import com.smcpartners.shape.shared.dto.shape.MeasureDTO;
import com.smcpartners.shape.shared.dto.shape.OrganizationMeasureDTO;
import com.smcpartners.shape.shared.dto.shape.response.ChartOptionsDTO;
import com.smcpartners.shape.shared.dto.shape.response.ListViewDTO;
import com.smcpartners.shape.shared.usecasecommon.UseCaseException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible:</br>
 * 1.  Formats measure information for a specific organization in a list format. Will only display the measures from
 * the most recent reporting year. For example, if the most recent measure information is from 2016,
 * but there is measure information from 2014 and 2015, it will display the list for 2016 reports. </br>
 * <p>
 * Created by bryanhokanson on 12/14/15.
 * </p>
 * <p>
 * Changes:</br>
 * 1. </br>
 * </p>
 */
@RequestScoped
public class ShowListViewServiceAdapter implements ShowListViewService {

    @Inject
    private Logger log;

    @EJB
    private OrganizationMeasureDAO organizationMeasureDAO;

    @EJB
    private UserDAO userDAO;

    @EJB
    private MeasureDAO mDAO;

    @EJB
    private OrganizationMeasureDAO orgMDAO;

    @Inject
    private RequestScopedUserId requestScopedUserId;

    public ShowListViewServiceAdapter() {
    }

    @Override
    @SecureRequireActiveLogActivity({SecurityRoleEnum.ADMIN, SecurityRoleEnum.DPH_USER, SecurityRoleEnum.ORG_ADMIN, SecurityRoleEnum.REGISTERED})
    public List<ListViewDTO> showListView(int orgId, int measureId, int year) throws UseCaseException {
        try {

            //set return list
            List<ListViewDTO> retLst = new ArrayList<>();

            //set variables
            String month = "Month";
            String day = "Day";
            String pieHole = ".6";
            String pieSliceText = "none";
            String numerator = "Numerator";
            String remOfPatients = "Remainder of Patients";

            //get the org measures that match the orgId, put them in a list
            List<OrganizationMeasureDTO> orgMList = orgMDAO.findAllOrganizationMeasureByOrgId(orgId);

            //loop through list, for each measure, do something
            if (orgMList != null) {
                for (OrganizationMeasureDTO om : orgMList) {
                    //if the org measure reporting year matches year passed in, continue
                    if (om.getReportPeriodYear() == year && om.getMeasureId() == measureId
                            && om.getReportPeriodYear() != null) {
                        //find measure
                        MeasureDTO mdto = mDAO.findById(measureId);
                        //make ListViewDTO'

                        try {

                            ListViewDTO lvDTO = new ListViewDTO();
                            lvDTO.setNqfId(mdto.getNqfId());
                            lvDTO.setName(mdto.getName());
                            lvDTO.setDesciption(mdto.getDescription());
                            lvDTO.setNumeratorValue(om.getNumeratorValue());
                            lvDTO.setDenominatorValue(om.getDenominatorValue());

                            //make arrayLists for chartData and chartOptions
                            List<Object> dateList = new ArrayList<>();
                            List<Object> wcList = new ArrayList<>();
                            List<Object> pcList = new ArrayList<>();

                            //add month and day to dateList
                            dateList.add(month);
                            dateList.add(day);

                            //add the well controlled array list data
                            wcList.add(numerator);
                            wcList.add(om.getNumeratorValue());

                            //add the poorly controlled array list data
                            pcList.add(remOfPatients);
                            pcList.add(om.getDenominatorValue() - om.getNumeratorValue());

                            //add all to an embedded ArrayList for formatting
                            List<List<Object>> newList = new ArrayList<>();
                            newList.add(dateList);
                            newList.add(wcList);
                            newList.add(pcList);

                            //set chartData on dto
                            lvDTO.setChartData(newList);

                            //add chartOptions portion to DTO
                            ChartOptionsDTO cod = new ChartOptionsDTO();
                            cod.setPieHole(pieHole);
                            cod.setPieSliceText(pieSliceText);
                            lvDTO.setChartOptions(cod);

                            //add dto to list
                            retLst.add(lvDTO);

                        } catch (Exception e) {
                            return retLst;
                        }
                    }
                }
            }

            //return list of DTOs
            return retLst;

        } catch (Exception e) {
            log.logp(Level.SEVERE, this.getClass().getName(), "showListView", e.getMessage(), e);
            throw new UseCaseException(e.getMessage());
        }

    }

}