package ru.unisuite.methodicalcomplexservice.webservice;

import javax.jws.WebService;

import ru.unisuite.methodicalcomplexservice.core.MethodicalComplexServiceCore;
import ru.unisuite.methodicalcomplexservice.exception.MethodicalComplexServiceException;

@WebService(endpointInterface = "ru.unisuite.methodicalcomplexservice.webservice.IMethodicalComplexWebService", serviceName = "MethodicalComplexWebService", portName = "MethodicalComplexWebServicePort")
public class MethodicalComplexWebServiceImpl implements IMethodicalComplexWebService {

	@Override
	public String findAuthors(String fio_mask) throws MethodicalComplexServiceException {
		return new MethodicalComplexServiceCore().findAuthors(fio_mask);
	}

	@Override
	public String findBooks(String bookMask) throws MethodicalComplexServiceException {
		return new MethodicalComplexServiceCore().findBooks(bookMask);
	}

	@Override
	public String getDisciplines(int disciplineTypeIndex, int semesterNum, int complexSpecialitiesId)
			throws MethodicalComplexServiceException {
		return new MethodicalComplexServiceCore().getDisciplines(disciplineTypeIndex, semesterNum,
				complexSpecialitiesId);
	}

	@Override
	public String getEducationalPlan(int divisionId, int disciplineSpecialitieId, int complexSpecialitiesId) throws MethodicalComplexServiceException {
		return new MethodicalComplexServiceCore().getEducationalPlan(divisionId, disciplineSpecialitieId,
				complexSpecialitiesId);
	}

	@Override
	public String getCurrentWorkloads(int methodicalComplexId) throws MethodicalComplexServiceException {
		return new MethodicalComplexServiceCore().getCurrentWorkloads(methodicalComplexId);
	}

	@Override
	public String getActualWorkloads(int disciplinePlanId, int complexSpecialitiesId) throws MethodicalComplexServiceException {
		return new MethodicalComplexServiceCore().getActualWorkloads(disciplinePlanId, complexSpecialitiesId);
	}

	@Override
	public String getActualCompetences(int disciplinePlanId, int complexSpecialitiesId) throws MethodicalComplexServiceException {
		return new MethodicalComplexServiceCore().getActualCompetences(disciplinePlanId, complexSpecialitiesId);
	}

	@Override
	public String getSpecialityCompetences(int complexSpecialitiesId, int disciplinePlanId) throws MethodicalComplexServiceException {
		return new MethodicalComplexServiceCore().getSpecialityCompetences(complexSpecialitiesId, disciplinePlanId);
	}

	@Override
	public String getXmlStructure(int methodicalComplexId, int complexSpecialitiesId) throws MethodicalComplexServiceException {
		return new MethodicalComplexServiceCore().getXmlStructure(methodicalComplexId, complexSpecialitiesId);
	}

	@Override
	public boolean actualizeWorkloads(int complexSpecialitiesId, int methodicalComplexId, int disciplinePlanId) throws MethodicalComplexServiceException {
		return new MethodicalComplexServiceCore().actualizeWorkloads(complexSpecialitiesId, methodicalComplexId,
				disciplinePlanId);
	}

	@Override
	public boolean addCompetence(int complexSpecialitiesId, int specialityCompetenceId) throws MethodicalComplexServiceException {
		return new MethodicalComplexServiceCore().addCompetence(complexSpecialitiesId, specialityCompetenceId);
	}

	@Override
	public boolean deleteCompetence(int methodicalComplexCompetenceId) throws MethodicalComplexServiceException {
		return new MethodicalComplexServiceCore().deleteCompetence(methodicalComplexCompetenceId);
	}

	@Override
	public boolean changeCompetence(int newSpecialityCompetenceId, int complexSpecialitiesId,
			int methodicalComplexCompetenceId) throws MethodicalComplexServiceException {
		return new MethodicalComplexServiceCore().changeCompetence(newSpecialityCompetenceId, complexSpecialitiesId,
				methodicalComplexCompetenceId);
	}

}