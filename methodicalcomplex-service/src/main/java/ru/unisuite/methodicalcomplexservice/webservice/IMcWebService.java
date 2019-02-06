package ru.unisuite.methodicalcomplexservice.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import ru.unisuite.methodicalcomplexservice.exception.McsException;
@WebService(name = "MethodicalComplexWebService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface IMcWebService {

	@WebMethod(action = "urn:CheckServiceAvailability")
	public boolean checkServiceAvailability();

	@WebMethod(action = "urn:FindAuthors")
	public String findAuthors(@WebParam(name = "fioMask") String fioMask) throws McsException;

	@WebMethod(action = "urn:FindBooks")
	public String findBooks(@WebParam(name = "bookMask") String bookMask) throws McsException;

	@WebMethod(action = "urn:GetDisciplines")
	public String getDisciplines(@WebParam(name = "disciplineTypeIndex") int disciplineTypeIndex,
			@WebParam(name = "semesterNum") int semesterNum,
			@WebParam(name = "complexSpecialitiesId") int complexSpecialitiesId) throws McsException;

	@WebMethod(action = "urn:GetEducationalPlan")
	public String getEducationalPlan(@WebParam(name = "divisionId") int divisionId,
			@WebParam(name = "disciplineSpecialitieId") int disciplineSpecialitieId,
			@WebParam(name = "complexSpecialitiesId") int complexSpecialitiesId) throws McsException;

	@WebMethod(action = "urn:GetCurrentWorkloads")
	public String getCurrentWorkloads(@WebParam(name = "methodicalComplexId") int methodicalComplexId)
			throws McsException;

	@WebMethod(action = "urn:GetActualWorkloads")
	public String getActualWorkloads(@WebParam(name = "disciplinePlanId") int disciplinePlanId,
			@WebParam(name = "complexSpecialitiesId") int complexSpecialitiesId) throws McsException;

	@WebMethod(action = "urn:GetActualCompetences")
	public String getActualCompetences(@WebParam(name = "disciplinePlanId") int disciplinePlanId,
			@WebParam(name = "complexSpecialitiesId") int complexSpecialitiesId) throws McsException;

	@WebMethod(action = "urn:GetSpecialityCompetences")
	public String getSpecialityCompetences(@WebParam(name = "complexSpecialitiesId") int complexSpecialitiesId,
			@WebParam(name = "disciplinePlanId") int disciplinePlanId) throws McsException;

	@WebMethod(action = "urn:GetXmlStructure")
	public String getXmlStructure(@WebParam(name = "methodicalComplexId") int methodicalComplexId,
			@WebParam(name = "complexSpecialitiesId") int complexSpecialitiesId) throws McsException;

	@WebMethod(action = "urn:ActualizeWorkloads")
	public boolean actualizeWorkloads(@WebParam(name = "complexSpecialitiesId") int complexSpecialitiesId,
			@WebParam(name = "methodicalComplexId") int methodicalComplexId,
			@WebParam(name = "disciplinePlanId") int disciplinePlanId) throws McsException;

	@WebMethod(action = "urn:AddCompetence")
	public boolean addCompetence(@WebParam(name = "complexSpecialitiesId") int complexSpecialitiesId,
			@WebParam(name = "specialityCompetenceId") int specialityCompetenceId) throws McsException;

	@WebMethod(action = "urn:DeleteCompetence")
	public boolean deleteCompetence(@WebParam(name = "methodicalComplexCompetenceId") int methodicalComplexCompetenceId)
			throws McsException;

	@WebMethod(action = "urn:ChangeCompetence")
	public boolean changeCompetence(@WebParam(name = "newSpecialityCompetenceId") int newSpecialityCompetenceId,
			@WebParam(name = "complexSpecialitiesId") int complexSpecialitiesId,
			@WebParam(name = "methodicalComplexCompetenceId") int methodicalComplexCompetenceId) throws McsException;

	@WebMethod(action = "urn:CheckModuleUpdates")
	public boolean checkModuleUpdates(@WebParam(name = "moduleId") int moduleId,
			@WebParam(name = "currentModuleHash") String currentModuleHash) throws McsException;

}