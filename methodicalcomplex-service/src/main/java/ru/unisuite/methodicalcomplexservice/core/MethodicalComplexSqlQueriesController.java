package ru.unisuite.methodicalcomplexservice.core;

import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.helpers.IOUtils;

public class MethodicalComplexSqlQueriesController {

	public static final String GET_AUTHORS_SQL;
	public static final String GET_BOOKS_SQL;
	public static final String GET_DISCIPLINES_SQL;
	public static final String GET_CURRENT_WORKLOADS_SQL;
	public static final String GET_ACTUAL_WORKLOADS_SQL;
	public static final String GET_EDUCATIONAL_PLANS_SQL;
	public static final String GET_ACTUAL_COMPETENCES_SQL;
	public static final String GET_SPECIALITY_COMPETENCES_SQL;
	public static final String GET_XML_STRUCTURE_SQL;
	public static final String ACTUALIZE_WORKLOADS_SQL;
	public static final String ADD_COMPETENCE_SQL;
	public static final String DELETE_COMPETENCE_SQL;
	public static final String CHANGE_COMPETENCE_SQL;

	static {
		GET_AUTHORS_SQL = sqlResourceToString("getAuthors.sql");
		GET_BOOKS_SQL = sqlResourceToString("getBooks.sql");
		GET_DISCIPLINES_SQL = sqlResourceToString("getDisciplines.sql");
		GET_CURRENT_WORKLOADS_SQL = sqlResourceToString("getCurrentWorkloads.sql");
		GET_ACTUAL_WORKLOADS_SQL = sqlResourceToString("getActualWorkloads.sql");
		GET_EDUCATIONAL_PLANS_SQL = sqlResourceToString("getEducationalPlans.sql");
		GET_ACTUAL_COMPETENCES_SQL = sqlResourceToString("getActualCompetences.sql");
		GET_SPECIALITY_COMPETENCES_SQL = sqlResourceToString("getSpecialityCompetences.sql");
		GET_XML_STRUCTURE_SQL = sqlResourceToString("getXmlStructure.sql");
		ACTUALIZE_WORKLOADS_SQL = sqlResourceToString("actualizeWorkloads.sql");
		ADD_COMPETENCE_SQL = sqlResourceToString("addCompetence.sql");
		DELETE_COMPETENCE_SQL = sqlResourceToString("deleteCompetence.sql");
		CHANGE_COMPETENCE_SQL = sqlResourceToString("changeCompetence.sql");
	}

	public static String sqlResourceToString(String sqlFileName) {
		try (InputStream inputStream = MethodicalComplexSqlQueriesController.class.getClassLoader()
				.getResourceAsStream(sqlFileName)) {					
			return IOUtils.toString(inputStream, "UTF-8");
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
