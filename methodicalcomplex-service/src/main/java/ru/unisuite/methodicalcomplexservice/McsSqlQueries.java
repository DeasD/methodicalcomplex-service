package ru.unisuite.methodicalcomplexservice;

import java.io.IOException;
import java.io.InputStream;
import org.apache.cxf.helpers.IOUtils;

    class McsSqlQueries {

	static final String GET_AUTHORS = sqlResourceToString("getAuthors.sql"),
			GET_BOOKS = sqlResourceToString("getBooks.sql"),
			GET_DISCIPLINES = sqlResourceToString("getDisciplines.sql"),
			GET_CURRENT_WORKLOADS = sqlResourceToString("getCurrentWorkloads.sql"),
			GET_ACTUAL_WORKLOADS = sqlResourceToString("getActualWorkloads.sql"),
			GET_EDUCATIONAL_PLANS = sqlResourceToString("getEducationalPlans.sql"),
			GET_ACTUAL_COMPETENCES = sqlResourceToString("getActualCompetences.sql"),
			GET_SPECIALITY_COMPETENCES = sqlResourceToString("getSpecialityCompetences.sql"),
			GET_XML_STRUCTURE = sqlResourceToString("getXmlStructure.sql"),
			ACTUALIZE_WORKLOADS = sqlResourceToString("actualizeWorkloads.sql"),
			ADD_COMPETENCE = sqlResourceToString("addCompetence.sql"),
			DELETE_COMPETENCE = sqlResourceToString("deleteCompetence.sql"),
			CHANGE_COMPETENCE = sqlResourceToString("changeCompetence.sql");

	private static String sqlResourceToString(String sqlFileName) {
		try (InputStream inputStream = McsSqlQueries.class.getClassLoader()
				.getResourceAsStream(sqlFileName)) {
			return (inputStream != null) ? IOUtils.toString(inputStream, "UTF-8") : null;  
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
