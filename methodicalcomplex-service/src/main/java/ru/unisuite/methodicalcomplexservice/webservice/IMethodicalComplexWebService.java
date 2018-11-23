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

	@WebMethod
	public String findAuthors(@WebParam(name = "fioMask") String fioMask) throws MethodicalComplexServiceException;

	@WebMethod
	public String findBooks(@WebParam(name = "bookMask") String bookMask);

	@WebMethod
	public String getDisciplines(@WebParam(name = "disciplineTypeIndex") int disciplineTypeIndex,
			@WebParam(name = "semesterNum") int semesterNum,
			@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID);

	@WebMethod
	public String getEducationalPlan(@WebParam(name = "disciplineID") int disciplineID,
			@WebParam(name = "disciplineSpecialitieID") int disciplineSpecialitieID,
			@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID);

	@WebMethod
	public String getCurrentWorkloads(@WebParam(name = "methodicalComplexID") int methodicalComplexID);

	@WebMethod
	public String getActualWorkloads(@WebParam(name = "disciplinePlanID") int disciplinePlanID,
			@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID);

	@WebMethod
	public String getActualCompetences(@WebParam(name = "disciplinePlanID") int disciplinePlanID,
			@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID);

	@WebMethod
	public String getSpecialityCompetences(@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID,
			@WebParam(name = "disciplinePlanID") int disciplinePlanID);

	@WebMethod
	public String getXmlStructure(@WebParam(name = "methodicalComplexID") int methodicalComplexID,
			@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID);

	@WebMethod
	public Boolean actualizeWorkloads(@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID,
			@WebParam(name = "methodicalComplexID") int methodicalComplexID,
			@WebParam(name = "disciplinePlanID") int disciplinePlanID);

	@WebMethod
	public Boolean addCompetence(@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID,
			@WebParam(name = "specialityCompetenceID") int specialityCompetenceID);

	@WebMethod
	public Boolean deleteCompetence(@WebParam(name = "methodicalComplexCompetenceID") int methodicalComplexCompetenceID);

	@WebMethod
	public Boolean changeCompetence(@WebParam(name = "newSpecialityCompetenceID") int newSpecialityCompetenceID,
			@WebParam(name = "complexSpecialitiesID") int complexSpecialitiesID,
			@WebParam(name = "methodicalComplexCompetenceID") int methodicalComplexCompetenceID);

}