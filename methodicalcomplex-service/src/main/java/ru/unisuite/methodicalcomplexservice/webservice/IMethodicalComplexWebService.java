package ru.unisuite.methodicalcomplexservice.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import ru.unisuite.methodicalcomplexservice.exception.MethodicalComplexServiceException;

@WebService(name = "MethodicalComplexWebService")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public interface IMethodicalComplexWebService {

	@WebMethod(action = "urn:FindAuthors")
	public String findAuthors(@WebParam(name = "fioMask") String fioMask) throws MethodicalComplexServiceException;

	@WebMethod(action = "urn:FindBooks")
	public String findBooks(@WebParam(name = "bookMask") String bookMask);

	@WebMethod(action = "urn:GetDisciplines")
	public String getDisciplines(@WebParam(name = "disciplineTypeIndex") int disciplineTypeIndex,
			@WebParam(name = "semesterNum") int semesterNum,
			@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID);

	@WebMethod(action = "urn:GetEducationalPlan")
	public String getEducationalPlan(@WebParam(name = "disciplineID") int disciplineID,
			@WebParam(name = "disciplineSpecialitieID") int disciplineSpecialitieID,
			@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID);

	@WebMethod(action = "urn:GetCurrentWorkloads")
	public String getCurrentWorkloads(@WebParam(name = "methodicalComplexID") int methodicalComplexID);

	@WebMethod(action = "urn:GetActualWorkloads")
	public String getActualWorkloads(@WebParam(name = "disciplinePlanID") int disciplinePlanID,
			@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID);

	@WebMethod(action = "urn:GetActualCompetences")
	public String getActualCompetences(@WebParam(name = "disciplinePlanID") int disciplinePlanID,
			@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID);

	@WebMethod(action = "urn:GetSpecialityCompetences")
	public String getSpecialityCompetences(@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID,
			@WebParam(name = "disciplinePlanID") int disciplinePlanID);

	@WebMethod(action = "urn:GetXmlStructure")
	public String getXmlStructure(@WebParam(name = "methodicalComplexID") int methodicalComplexID,
			@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID);

	@WebMethod(action = "urn:ActualizeWorkloads")
	public Boolean actualizeWorkloads(@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID,
			@WebParam(name = "methodicalComplexID") int methodicalComplexID,
			@WebParam(name = "disciplinePlanID") int disciplinePlanID);

	@WebMethod(action = "urn:AddCompetence")
	public Boolean addCompetence(@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID,
			@WebParam(name = "specialityCompetenceID") int specialityCompetenceID);

	@WebMethod(action = "urn:DeleteCompetence")
	public Boolean deleteCompetence(@WebParam(name = "methodicalComplexCompetenceID") int methodicalComplexCompetenceID);

	@WebMethod(action = "urn:ChangeCompetence")
	public Boolean changeCompetence(@WebParam(name = "newSpecialityCompetenceID") int newSpecialityCompetenceID,
			@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID,
			@WebParam(name = "methodicalComplexCompetenceID") int methodicalComplexCompetenceID);

}