package ru.unisuite.methodicalcomplexservice.webservice;

import javax.jws.WebService;
import ru.unisuite.methodicalcomplexservice.McsCore;
import ru.unisuite.methodicalcomplexservice.exception.McsException;
@WebService(endpointInterface = "ru.unisuite.methodicalcomplexservice.webservice.IMcWebService", serviceName = "MethodicalComplexWebService", portName = "MethodicalComplexWebServicePort")
public class McWebServiceImpl implements IMcWebService {

	@Override
	public String findAuthors(String fio_mask) throws McsException {
		return new McsCore().findAuthors(fio_mask);
	}

	@Override
	public String findBooks(String bookMask) throws McsException {
		return new McsCore().findBooks(bookMask);
	}

	@Override
	public String getDisciplines(int disciplineTypeIndex, int semesterNum, int complexSpecialitiesId)
			throws McsException {
		return new McsCore().getDisciplines(disciplineTypeIndex, semesterNum, complexSpecialitiesId);
	}

	@Override
	public String getEducationalPlan(int divisionId, int disciplineSpecialitieId, int complexSpecialitiesId)
			throws McsException {
		return new McsCore().getEducationalPlan(divisionId, disciplineSpecialitieId, complexSpecialitiesId);
	}

	@Override
	public String getCurrentWorkloads(int methodicalComplexId) throws McsException {
		return new McsCore().getCurrentWorkloads(methodicalComplexId);
	}

	@Override
	public String getActualWorkloads(int disciplinePlanId, int complexSpecialitiesId)
			throws McsException {
		return new McsCore().getActualWorkloads(disciplinePlanId, complexSpecialitiesId);
	}

	@Override
	public String getActualCompetences(int disciplinePlanId, int complexSpecialitiesId) throws McsException {
		return new McsCore().getActualCompetences(disciplinePlanId, complexSpecialitiesId);
	}

	@Override
	public String getSpecialityCompetences(int complexSpecialitiesId, int disciplinePlanId) throws McsException {
		return new McsCore().getSpecialityCompetences(complexSpecialitiesId, disciplinePlanId);
	}

	@Override
	public String getXmlStructure(int methodicalComplexId, int complexSpecialitiesId) throws McsException {
		return new McsCore().getXmlStructure(methodicalComplexId, complexSpecialitiesId);
	}

	@Override
	public boolean actualizeWorkloads(int complexSpecialitiesId, int methodicalComplexId, int disciplinePlanId)
			throws McsException {
		return new McsCore().actualizeWorkloads(complexSpecialitiesId, methodicalComplexId, disciplinePlanId);
	}

	@Override
	public boolean addCompetence(int complexSpecialitiesId, int specialityCompetenceId) throws McsException {
		return new McsCore().addCompetence(complexSpecialitiesId, specialityCompetenceId);
	}

	@Override
	public boolean deleteCompetence(int methodicalComplexCompetenceId) throws McsException {
		return new McsCore().deleteCompetence(methodicalComplexCompetenceId);
	}

	@Override
	public boolean changeCompetence(int newSpecialityCompetenceId, int complexSpecialitiesId,
			int methodicalComplexCompetenceId) throws McsException {
		return new McsCore().changeCompetence(newSpecialityCompetenceId, complexSpecialitiesId,
				methodicalComplexCompetenceId);
	}

	@Override
	public boolean checkModuleUpdates(int moduleId, String currentModuleHash) throws McsException {
		return new McsCore().checkModuleUpdates(moduleId, currentModuleHash);
	}

	@Override
	public boolean checkServiceAvailability() {
		return true;
	}

}