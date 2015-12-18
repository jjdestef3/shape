package com.smcpartners.shape.usecases.show_app_hist_demographic;

import com.smcpartners.shape.crosscutting.security.RequestScopedUserId;
import com.smcpartners.shape.crosscutting.security.annotations.SecureRequireActiveLogAvtivity;
import com.smcpartners.shape.frameworks.data.dao.shape.MeasureDAO;
import com.smcpartners.shape.frameworks.data.dao.shape.OrganizationMeasureDAO;
import com.smcpartners.shape.frameworks.data.dao.shape.UserDAO;
import com.smcpartners.shape.shared.constants.SecurityRoleEnum;
import com.smcpartners.shape.shared.dto.shape.MeasureDTO;
import com.smcpartners.shape.shared.dto.shape.OrganizationMeasureDTO;
import com.smcpartners.shape.shared.dto.shape.response.AppHistDemographicsDTO;
import com.smcpartners.shape.shared.usecasecommon.UseCaseException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bryanhokanson on 12/17/15.
 */
@RequestScoped
public class ShowAppHistDemographicServiceAdapter implements ShowAppHistDemographicService {

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

    public ShowAppHistDemographicServiceAdapter() {
    }

    @Override
    @SecureRequireActiveLogAvtivity({SecurityRoleEnum.ADMIN, SecurityRoleEnum.ORG_ADMIN, SecurityRoleEnum.REGISTERED})
    public List<AppHistDemographicsDTO> showAppHistDemographic(@PathParam("orgId") int orgId,
                                                               @PathParam("measureId") int measureId,
                                                               @PathParam("year") int year) throws UseCaseException {
        try{

            List<AppHistDemographicsDTO> retLst = new ArrayList<>();

            MeasureDTO mdto = mDAO.findById(measureId);

            List<OrganizationMeasureDTO> orgMList = orgMDAO.findOrgMeasureByMeasureIdAndYear(measureId, year);

            for (OrganizationMeasureDTO om : orgMList) {
                AppHistDemographicsDTO ahdDTO = new AppHistDemographicsDTO();

                //add measure info to dto
                ahdDTO.setNqfId(mdto.getNqfId());
                ahdDTO.setName(mdto.getName());
                ahdDTO.setDescription(mdto.getDescription());
                ahdDTO.setReportPeriodQuarter(om.getReportPeriodQuarter());
                ahdDTO.setReportPeriodYear(om.getReportPeriodYear());


                List<Object> africanAmericanList = new ArrayList<>();
                List<Object> nativeAmericanList = new ArrayList<>();
                List<Object> asianList = new ArrayList<>();
                List<Object> nativeHawaiianList = new ArrayList<>();
                List<Object> whiteList = new ArrayList<>();
                List<Object> otherRaceList = new ArrayList<>();
                List<Object> under17List = new ArrayList<>();
                List<Object> age18to44List = new ArrayList<>();
                List<Object> age45to64List = new ArrayList<>();
                List<Object> age65PlusList = new ArrayList<>();
                List<Object> hispanicList = new ArrayList<>();
                List<Object> notHispanicList = new ArrayList<>();
                List<Object> femaleList = new ArrayList<>();
                List<Object> maleList = new ArrayList<>();
                List<Object> otherGenderList = new ArrayList<>();


                try {
                    //add to the race lists
                    africanAmericanList.add("African American");
                    africanAmericanList.add(convertToDoubles(om.getRaceAfricanAmericanNum(),
                            om.getRaceAmericanIndianDen()));

                    nativeAmericanList.add("Native American");
                    nativeAmericanList.add(convertToDoubles(om.getRaceAmericanIndianNum(),
                            om.getRaceAmericanIndianDen()));

                    asianList.add("Asian");
                    asianList.add(convertToDoubles(om.getRaceAsianNum(), om.getRaceAsianDen()));

                    nativeHawaiianList.add("Native Hawaiian");
                    nativeHawaiianList.add(convertToDoubles(om.getRaceNativeHawaiianNum(),
                            om.getRaceNativeHawaiianDen()));

                    whiteList.add("White");
                    whiteList.add(convertToDoubles(om.getRaceWhiteNum(), om.getRaceWhiteDen()));

                    otherRaceList.add("Other");
                    otherRaceList.add(convertToDoubles(om.getRaceOtherNum(), om.getRaceOtherDen()));

                    //add arrays to raceData double array
                    List<List<Object>> rdList = new ArrayList<>();
                    rdList.add(africanAmericanList);
                    rdList.add(nativeAmericanList);
                    rdList.add(asianList);
                    rdList.add(nativeHawaiianList);
                    rdList.add(whiteList);
                    rdList.add(otherRaceList);
                    //set gender data on dto
                    ahdDTO.setRaceData(rdList);
                } catch (Exception e) {
                    List<List<Object>> rdList = new ArrayList<>();
                    List<Object> errorList = new ArrayList<>();
                    errorList.add(false);
                    rdList.add(errorList);
                    ahdDTO.setRaceData(rdList);
                }

                try {

                    //add to age lists
                    under17List.add("Under 17");
                    under17List.add(convertToDoubles(om.getAgeUnder17Num(), om.getAgeUnder17Den()));

                    age18to44List.add("18-44");
                    age18to44List.add(convertToDoubles(om.getAge1844Num(), om.getAge1844Den()));

                    age45to64List.add("45-64");
                    age45to64List.add(convertToDoubles(om.getAge4564Num(), om.getAge4564Den()));

                    age65PlusList.add("65+");
                    age65PlusList.add(convertToDoubles(om.getAgeOver65Num(), om.getAgeOver65Den()));

                    //add arrays to ageData double array
                    List<List<Object>> adList = new ArrayList<>();
                    adList.add(under17List);
                    adList.add(age18to44List);
                    adList.add(age45to64List);
                    adList.add(age65PlusList);
                    //set age data on dto
                    ahdDTO.setAgeData(adList);

                } catch (Exception e) {
                    List<List<Object>> rdList = new ArrayList<>();
                    List<Object> errorList = new ArrayList<>();
                    errorList.add(false);
                    rdList.add(errorList);
                    ahdDTO.setAgeData(rdList);
                }


                try {
                    //add to ethnicity data lists
                    hispanicList.add("Hispanic");
                    hispanicList.add(convertToDoubles(om.getEthnicityHispanicLatinoNum(),
                            om.getEthnicityHispanicLatinoDen()));


                    notHispanicList.add("Not Hispanic");
                    notHispanicList.add(convertToDoubles(om.getEthnicityNotHispanicLatinoNum(),
                            om.getEthnicityNotHispanicLatinoDen()));

                    //add arrays to ethnicityData double array
                    List<List<Object>> ethList = new ArrayList<>();
                    ethList.add(hispanicList);
                    ethList.add(notHispanicList);
                    //set ethnicity data on dto
                    ahdDTO.setEthnicityData(ethList);
                } catch (Exception e) {
                    List<List<Object>> rdList = new ArrayList<>();
                    List<Object> errorList = new ArrayList<>();
                    errorList.add(false);
                    rdList.add(errorList);
                    ahdDTO.setEthnicityData(rdList);
                }

                try {
                    //add to gender list
                    femaleList.add("Female");
                    femaleList.add(convertToDoubles(om.getGenderFemaleNum(), om.getGenderFemaleDen()));

                    maleList.add("Male");
                    maleList.add(convertToDoubles(om.getGenderMaleNum(), om.getGenderMaleDen()));

                    otherGenderList.add("Other");
                    otherGenderList.add(convertToDoubles(om.getGenderOtherNum(), om.getGenderOtherDen()));

                    //add arrays to genderData double Array
                    List<List<Object>> gList = new ArrayList<>();
                    gList.add(femaleList);
                    gList.add(maleList);
                    gList.add(otherGenderList);
                    //set gender data on dto
                    ahdDTO.setGenderData(gList);
                } catch (Exception e) {
                    List<List<Object>> rdList = new ArrayList<>();
                    List<Object> errorList = new ArrayList<>();
                    errorList.add(false);
                    rdList.add(errorList);
                    ahdDTO.setGenderData(rdList);

                }


                //add arrays to genderData double Array
                List<List<Object>> gList = new ArrayList<>();
                gList.add(femaleList);
                gList.add(maleList);
                gList.add(otherGenderList);
                //set gender data on dto
                ahdDTO.setGenderData(gList);

                retLst.add(ahdDTO);

            }


            return retLst;

        } catch (Exception e) {
            log.logp(Level.SEVERE, this.getClass().getName(), "showAppHistDemographic", e.getMessage(), e);
            throw new UseCaseException(e.getMessage());
        }

    }

    public double convertToDoubles(int numVal, int denVal) {
        double num = (double)numVal;
        double den = (double)denVal;
        double sum = num/den;
        if (Double.isNaN(sum)){
            sum = 0.00;
        }
        return sum;
    }



}