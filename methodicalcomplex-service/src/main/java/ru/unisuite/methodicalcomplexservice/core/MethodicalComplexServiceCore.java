package ru.unisuite.methodicalcomplexservice.core;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.unisuite.methodicalcomplexservice.exception.MethodicalComplexServiceException;
import ru.unisuite.methodicalcomplexservice.jaxb.entities.*;

public class MethodicalComplexServiceCore {

	private static final String JNDI_DATASOURCE_NAME = "jdbc/ds_basic";
	private static final Logger logger = LoggerFactory.getLogger(MethodicalComplexServiceCore.class);

	public DataSource getDataSource() throws MethodicalComplexServiceException {

		Context initContext = null;
		try {
			initContext = new InitialContext();
			DataSource dataSource = (DataSource) initContext.lookup(JNDI_DATASOURCE_NAME);
			return dataSource;
		} catch (NamingException e) {
			logger.error("Couldn't initialize JNDI data source. " + e.getMessage(), e);
			throw new MethodicalComplexServiceException(e.getMessage(), e);
		} finally {
			if (initContext != null) {
				try {
					initContext.close();
				} catch (NamingException e) {
					logger.warn("Initial context of JNDI data source can't be closed. " + e.getMessage(), e);
					throw new MethodicalComplexServiceException(e.getMessage(), e);
				}
			}
		}
	}

	public boolean executeSqlCommand(String sqlQuery, Object[] sqlQueryParams)
			throws MethodicalComplexServiceException {
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
			connection.setAutoCommit(true);
			if (sqlQueryParams != null) {
				for (int i = 0; i < sqlQueryParams.length; i++)
					ps.setObject(i + 1, sqlQueryParams[i]);
			}
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new MethodicalComplexServiceException(e.getMessage(), e);
		}
	}

	public Marshaller getMarshaller(Object obj) throws MethodicalComplexServiceException {

		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			return marshaller;
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new MethodicalComplexServiceException(e.getMessage(), e);
		}

	}

	// используется для передачи in-параметра и порядкового номера его вхождения в
	// запрос
	public void setSQLParam(PreparedStatement ps, Object sqlQueryParam, int[] paramEntries) {
		if (paramEntries != null) {
			try {
				for (int i = 0; i < paramEntries.length; i++)
					ps.setObject(paramEntries[i], sqlQueryParam);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	// используется в случае, если передается единственный in-параметр, который
	// в запросе может встречаться несколько раз
	public void setSQLParam(PreparedStatement ps, Object sqlQueryParam, int paramsCount) {
		if (paramsCount > 0) {
			try {
				for (int i = 0; i < paramsCount; i++)
					ps.setObject(i + 1, sqlQueryParam);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else
			try {
				ps.setObject(1, sqlQueryParam);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public String optToStr(Object obj) {
		return Objects.toString(obj, "");
	}

	public String findAuthors(String fio_mask) throws MethodicalComplexServiceException {
		StringWriter writer = new StringWriter();
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement ps = connection
						.prepareStatement(MethodicalComplexSqlQueriesController.GET_AUTHORS_SQL)) {

			setSQLParam(ps, fio_mask, 1);

			try (ResultSet rs = ps.executeQuery()) {

				MethodicalComplex methodicalComplex = new MethodicalComplex();
				List<Authors> authorsList = new ArrayList<Authors>();

				while (rs.next())
					authorsList.add(new Authors(rs.getInt("ID_E"), optToStr(rs.getString("FIO")),
							optToStr(rs.getDate("BIRTHDAY"))));
				methodicalComplex.setAuthors(authorsList);
				getMarshaller(methodicalComplex).marshal(methodicalComplex, writer);
				return writer.toString();
			}

		} catch (SQLException | JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new MethodicalComplexServiceException(e.getMessage(), e);
		}

	}

	public String findBooks(String bookMask) throws MethodicalComplexServiceException {
		StringWriter writer = new StringWriter();
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement ps = connection
						.prepareStatement(MethodicalComplexSqlQueriesController.GET_BOOKS_SQL)) {
			setSQLParam(ps, bookMask, 7);
			try (ResultSet rs = ps.executeQuery()) {

				MethodicalComplex methodicalComplex = new MethodicalComplex();
				List<Books> booksList = new ArrayList<Books>();
				while (rs.next()) {
					booksList.add(new Books(optToStr(rs.getString("NAME")), optToStr(rs.getString("AUTHORS")),
							optToStr(rs.getString("PUBLISH_YEAR")), optToStr(rs.getString("PUBLISHERS")),
							optToStr(rs.getString("KIND_EDITION")), optToStr(rs.getString("PLACE_NAME")),
							rs.getInt("ID_EDITION")));
				}
				methodicalComplex.setBooks(booksList);
				getMarshaller(methodicalComplex).marshal(methodicalComplex, writer);
				return writer.toString();
			}

		} catch (SQLException | JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new MethodicalComplexServiceException(e.getMessage(), e);
		}
	}

	public String getDisciplines(int disciplineTypeIndex, int semesterNum, int complexSpecialitiesId)
			throws MethodicalComplexServiceException {
		StringWriter writer = new StringWriter();
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement ps = connection
						.prepareStatement(MethodicalComplexSqlQueriesController.GET_DISCIPLINES_SQL)) {
			setSQLParam(ps, disciplineTypeIndex, new int[] { 1, 2, 3, 4, 6, 10, 11, 12, 14, 18, 19, 20 });
			setSQLParam(ps, semesterNum, new int[] { 5, 13 });
			setSQLParam(ps, complexSpecialitiesId, new int[] { 7, 8, 9, 15, 16, 17 });
			try (ResultSet rs = ps.executeQuery()) {

				MethodicalComplex methodicalComplex = new MethodicalComplex();
				List<Disciplines> disciplinesList = new ArrayList<Disciplines>();
				while (rs.next()) {
					disciplinesList.add(new Disciplines(rs.getInt("SEM"), optToStr(rs.getString("NAME")),
							optToStr(rs.getString("KNOWLEDGE")), optToStr(rs.getString("ABILITY")),
							optToStr(rs.getString("SKILL")), rs.getInt("ID_DISCIPLINE")));
				}
				methodicalComplex.setDisciplines(disciplinesList);
				getMarshaller(methodicalComplex).marshal(methodicalComplex, writer);
				return writer.toString();
			}

		} catch (SQLException | JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new MethodicalComplexServiceException(e.getMessage(), e);
		}
	}

	public String getEducationalPlan(int divisionId, int disciplineSpecialitieId, int complexSpecialitiesId)
			throws MethodicalComplexServiceException {
		StringWriter writer = new StringWriter();
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement ps = connection
						.prepareStatement(MethodicalComplexSqlQueriesController.GET_EDUCATIONAL_PLANS_SQL)) {

			setSQLParam(ps, complexSpecialitiesId, new int[] { 1, 5, 6, 7 });
			setSQLParam(ps, divisionId, new int[] { 2, 3 });
			setSQLParam(ps, disciplineSpecialitieId, new int[] { 4 });
			try (ResultSet rs = ps.executeQuery()) {

				MethodicalComplex methodicalComplex = new MethodicalComplex();
				List<EducationalPlan> educationalPlansList = new ArrayList<EducationalPlan>();
				while (rs.next()) {
					educationalPlansList.add(new EducationalPlan(rs.getInt("IDENTIFIER"),
							optToStr(rs.getDate("D_START")), optToStr(rs.getDate("D_END")),
							optToStr(rs.getString("COURSE")), optToStr(rs.getInt("HOURS")),
							optToStr(rs.getInt("LEC_HOURS")), optToStr(rs.getInt("LAB_HOURS")),
							optToStr(rs.getInt("PR_HOURS")), optToStr(rs.getInt("KSR_HOURS")),
							optToStr(rs.getInt("SR_HOURS")), optToStr(rs.getString("CERT")),
							optToStr(rs.getString("COMPS")), optToStr(rs.getString("DIS_TYPE")),
							optToStr(rs.getString("DIS_KIND")), optToStr(rs.getString("FGOS")),
							rs.getInt("ID_EDUCATIONAL_PLAN"), rs.getInt("ID_DISCIPLINE_PLAN")));
				}
				methodicalComplex.setEducationalPlans(educationalPlansList);
				getMarshaller(methodicalComplex).marshal(methodicalComplex, writer);
				return writer.toString();
			}

		} catch (SQLException | JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new MethodicalComplexServiceException(e.getMessage(), e);
		}
	}

	public String getCurrentWorkloads(int methodicalComplexId) throws MethodicalComplexServiceException {
		StringWriter writer = new StringWriter();
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement ps = connection
						.prepareStatement(MethodicalComplexSqlQueriesController.GET_CURRENT_WORKLOADS_SQL)) {

			setSQLParam(ps, methodicalComplexId, new int[] { 1 });
			try (ResultSet rs = ps.executeQuery()) {

				MethodicalComplex methodicalComplex = new MethodicalComplex();
				List<Workloads> workloadsList = new ArrayList<Workloads>();
				while (rs.next()) {
					workloadsList.add(new Workloads(optToStr(rs.getString("NAME")), rs.getInt("SEMESTER"),
							optToStr(rs.getInt("HOURS")), optToStr(rs.getInt("I_HOURS")), rs.getInt("IDK_LESSON"),
							rs.getInt("ID_EDUCATIONAL_LOAD_UMK"), rs.getInt("ID_METHODICAL_COMPLEX")));
				}
				methodicalComplex.setWorkloads(workloadsList);
				getMarshaller(methodicalComplex).marshal(methodicalComplex, writer);
				return writer.toString();
			}

		} catch (SQLException | JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new MethodicalComplexServiceException(e.getMessage(), e);
		}
	}

	public String getActualWorkloads(int disciplinePlanId, int complexSpecialitiesId)
			throws MethodicalComplexServiceException {
		StringWriter writer = new StringWriter();
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement ps = connection
						.prepareStatement(MethodicalComplexSqlQueriesController.GET_ACTUAL_WORKLOADS_SQL)) {

			setSQLParam(ps, disciplinePlanId, new int[] { 1, 2 });
			setSQLParam(ps, complexSpecialitiesId, new int[] { 3 });
			try (ResultSet rs = ps.executeQuery()) {

				MethodicalComplex methodicalComplex = new MethodicalComplex();
				List<ActualWorkloads> actualWorkloadsList = new ArrayList<ActualWorkloads>();
				while (rs.next()) {
					actualWorkloadsList.add(new ActualWorkloads(rs.getInt("ID_EDUCATIONAL_LOAD_UMK"),
							optToStr(rs.getString("NAME")), rs.getInt("SEMESTER"), optToStr(rs.getInt("HOURS")),
							optToStr(rs.getInt("HOURS_INTERACTIVE")), optToStr(rs.getString("ACTION")),
							rs.getInt("IDK_LESSON")));
				}
				methodicalComplex.setActualWorkloads(actualWorkloadsList);
				getMarshaller(methodicalComplex).marshal(methodicalComplex, writer);
				return writer.toString();
			}

		} catch (SQLException | JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new MethodicalComplexServiceException(e.getMessage(), e);
		}
	}

	public String getActualCompetences(int disciplinePlanId, int complexSpecialitiesId)
			throws MethodicalComplexServiceException {
		StringWriter writer = new StringWriter();
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement ps = connection
						.prepareStatement(MethodicalComplexSqlQueriesController.GET_ACTUAL_COMPETENCES_SQL)) {

			setSQLParam(ps, complexSpecialitiesId, new int[] { 1 });
			setSQLParam(ps, disciplinePlanId, new int[] { 2, 3 });

			try (ResultSet rs = ps.executeQuery()) {

				MethodicalComplex methodicalComplex = new MethodicalComplex();
				List<ActualCompetences> actualComptencesList = new ArrayList<ActualCompetences>();
				while (rs.next()) {
					actualComptencesList.add(new ActualCompetences(optToStr(rs.getString("ABBREVIATION")),
							optToStr(rs.getString("NAME")), optToStr(rs.getString("KNOWLEDGE")),
							optToStr(rs.getString("ABILITY")), optToStr(rs.getString("SKILL")),
							optToStr(rs.getString("ACTION")), rs.getInt("ID_S_COMPETENCE"),
							rs.getInt("ID_MC_COMPETENCES")));
				}
				methodicalComplex.setActualCompetences(actualComptencesList);
				getMarshaller(methodicalComplex).marshal(methodicalComplex, writer);
				return writer.toString();
			}

		} catch (SQLException | JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new MethodicalComplexServiceException(e.getMessage(), e);
		}
	}

	public String getSpecialityCompetences(int complexSpecialitiesId, int disciplinePlanId)
			throws MethodicalComplexServiceException {
		StringWriter writer = new StringWriter();
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement ps = connection
						.prepareStatement(MethodicalComplexSqlQueriesController.GET_SPECIALITY_COMPETENCES_SQL)) {

			setSQLParam(ps, complexSpecialitiesId, new int[] { 1, 2 });
			setSQLParam(ps, disciplinePlanId, new int[] { 3 });

			try (ResultSet rs = ps.executeQuery()) {

				MethodicalComplex methodicalComplex = new MethodicalComplex();
				List<SpecialityCompetences> specialityComptencesList = new ArrayList<SpecialityCompetences>();
				while (rs.next()) {
					specialityComptencesList.add(new SpecialityCompetences(optToStr(rs.getString("ABBREVIATION")),
							optToStr(rs.getString("NAME")), rs.getInt("ID_S_COMPETENCE")));
				}
				methodicalComplex.setSpecialityCompetences(specialityComptencesList);
				getMarshaller(methodicalComplex).marshal(methodicalComplex, writer);
				return writer.toString();
			}

		} catch (SQLException | JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new MethodicalComplexServiceException(e.getMessage(), e);
		}
	}

	public String getXmlStructure(int methodicalComplexId, int complexSpecialitiesId)
			throws MethodicalComplexServiceException {
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement ps = connection
						.prepareStatement(MethodicalComplexSqlQueriesController.GET_XML_STRUCTURE_SQL)) {
			setSQLParam(ps, methodicalComplexId, new int[] { 1 });
			setSQLParam(ps, complexSpecialitiesId, new int[] { 2 });
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String xml = optToStr(rs.getString("XML"));
					if (!xml.isEmpty()) {
						return xml;
					} else
						throw new MethodicalComplexServiceException("Methodical complex XML is empty.");
				} else
					throw new MethodicalComplexServiceException("Couldn't get methodical complex XML.");
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new MethodicalComplexServiceException(e.getMessage(), e);
		}
	}

	public boolean actualizeWorkloads(int complexSpecialitiesId, int methodicalComplexId, int disciplinePlanId)
			throws MethodicalComplexServiceException {
		return executeSqlCommand(MethodicalComplexSqlQueriesController.ACTUALIZE_WORKLOADS_SQL,
				new Object[] { complexSpecialitiesId, disciplinePlanId, disciplinePlanId, complexSpecialitiesId,
						disciplinePlanId, disciplinePlanId, methodicalComplexId, methodicalComplexId,
						methodicalComplexId, methodicalComplexId, methodicalComplexId });
	}

	public boolean addCompetence(int complexSpecialitiesId, int specialityCompetenceId)
			throws MethodicalComplexServiceException {
		return executeSqlCommand(MethodicalComplexSqlQueriesController.ADD_COMPETENCE_SQL,
				new Object[] { complexSpecialitiesId, specialityCompetenceId });
	}

	public boolean deleteCompetence(int methodicalComplexCompetenceId) throws MethodicalComplexServiceException {
		return executeSqlCommand(MethodicalComplexSqlQueriesController.DELETE_COMPETENCE_SQL,
				new Object[] { methodicalComplexCompetenceId });
	}

	public boolean changeCompetence(int newSpecialityCompetenceId, int complexSpecialitiesId,
			int methodicalComplexCompetenceId) throws MethodicalComplexServiceException {
		return executeSqlCommand(MethodicalComplexSqlQueriesController.CHANGE_COMPETENCE_SQL,
				new Object[] { newSpecialityCompetenceId, complexSpecialitiesId, methodicalComplexCompetenceId });
	}

}
