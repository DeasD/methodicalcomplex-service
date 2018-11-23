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
	public String findBooks(String bookMask) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisciplines(int disciplineTypeIndex, int semesterNum, int complexSpecialitiesID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEducationalPlan(int disciplineID, int disciplineSpecialitieID, int complexSpecialitiesID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentWorkloads(int methodicalComplexID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getActualWorkloads(int disciplinePlanID, int complexSpecialitiesID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getActualCompetences(int disciplinePlanID, int complexSpecialitiesID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSpecialityCompetences(int complexSpecialitiesID, int disciplinePlanID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getXmlStructure(int methodicalComplexID, int complexSpecialitiesID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean actualizeWorkloads(int complexSpecialitiesID, int methodicalComplexID, int disciplinePlanID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addCompetence(int complexSpecialitiesID, int specialityCompetenceID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteCompetence(int methodicalComplexCompetenceID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean changeCompetence(int newSpecialityCompetenceID, int complexSpecialitiesID,
			int methodicalComplexCompetenceID) {
		// TODO Auto-generated method stub
		return null;
	}

}