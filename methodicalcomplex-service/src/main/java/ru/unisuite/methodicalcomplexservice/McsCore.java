package ru.unisuite.methodicalcomplexservice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.RowMapper;
import ru.unisuite.methodicalcomplexservice.entity.*;
import ru.unisuite.methodicalcomplexservice.exception.McsErrorCode;
import ru.unisuite.methodicalcomplexservice.exception.McsException;

public class McsCore {

	private String optToStr(Object obj) {
		return Objects.toString(obj, "");
	}

	public String findAuthors(String fio_mask) throws McsException {
		SqlParameterOccurrences[] params = { new SqlParameterOccurrences(fio_mask, new int[] { 1 }) };
		MethodicalComplex mc = new MethodicalComplex();
		mc.setAuthors(new McsHelper().executeSqlStatement(McsSqlQueries.GET_AUTHORS, params, new RowMapper<Author>() {
			public Author mapRow(ResultSet rs, int rownum) throws SQLException {
				return new Author(rs.getInt("ID_E"), optToStr(rs.getString("FIO")), optToStr(rs.getDate("BIRTHDAY")));
			}
		}));
		return new McsHelper().marshalObjToXml(mc);
	}

	public String findBooks(String bookMask) throws McsException {
		SqlParameterOccurrences[] params = { new SqlParameterOccurrences(bookMask, new int[] { 1, 2, 3, 4, 5, 6, 7 }) };
		MethodicalComplex mc = new MethodicalComplex();
		mc.setBooks(new McsHelper().executeSqlStatement(McsSqlQueries.GET_BOOKS, params, new RowMapper<Book>() {
			public Book mapRow(ResultSet rs, int rownum) throws SQLException {
				return new Book(optToStr(rs.getString("NAME")), optToStr(rs.getString("AUTHORS")),
						optToStr(rs.getInt("PUBLISH_YEAR")), optToStr(rs.getString("PUBLISHERS")),
						optToStr(rs.getString("KIND_EDITION")), optToStr(rs.getString("PLACE_NAME")),
						rs.getInt("ID_EDITION"));
			}
		}));
		return new McsHelper().marshalObjToXml(mc);
	}

	public String getDisciplines(int disciplineTypeIndex, int semesterNum, int complexSpecialitiesId)
			throws McsException {
		SqlParameterOccurrences[] params = {
				new SqlParameterOccurrences(disciplineTypeIndex,
						new int[] { 1, 2, 3, 4, 6, 10, 11, 12, 14, 18, 19, 20 }),
				new SqlParameterOccurrences(semesterNum, new int[] { 5, 13 }),
				new SqlParameterOccurrences(complexSpecialitiesId, new int[] { 7, 8, 9, 15, 16, 17 }) };
		MethodicalComplex mc = new MethodicalComplex();
		mc.setDisciplines(
				new McsHelper().executeSqlStatement(McsSqlQueries.GET_DISCIPLINES, params, new RowMapper<Discipline>() {
					public Discipline mapRow(ResultSet rs, int rownum) throws SQLException {
						return new Discipline(rs.getInt("SEM"), optToStr(rs.getString("NAME")),
								optToStr(rs.getString("KNOWLEDGE")), optToStr(rs.getString("ABILITY")),
								optToStr(rs.getString("SKILL")), rs.getInt("ID_DISCIPLINE"));
					}
				}));
		return new McsHelper().marshalObjToXml(mc);
	}

	public boolean checkModuleUpdates(int moduleId, String currentModuleHash) throws McsException {
//		SqlParameterOccurrences[] params = { new SqlParameterOccurrences(moduleId, new int[] { 1 }) };
//		boolean hasModuleUpdate = false;
//		List<MethodicalComplex> modulesList = new McsHelper().executeSqlStatement(McsSqlQueries.GET_XML_STRUCTURE,
//				params, new RowMapper<MethodicalComplex>() {
//					public MethodicalComplex mapRow(ResultSet rs, int rownum) throws SQLException {
//						return new MethodicalComplex(rs.getString("HASH"), rs.getBlob("MODULE_BINARY"));
//					}
//				});
//		if (!modulesList.isEmpty()) {
//			MethodicalComplex mcInfo = modulesList.get(0);
//			if (mcInfo.getMd5Hash() != currentModuleHash) {
//				byte[] moduleByteArray;
//				try {
//					moduleByteArray = mcInfo.getModuleBlob().getBytes(1, (int) mcInfo.getModuleBlob().length());
//					try (FileOutputStream fos = new FileOutputStream(System.getenv("AppData")
//							+ "\\UniSuite\\LocalMethodicalComplex\\MethodicalComplexApp.exe")) {
//						fos.write(moduleByteArray);
//					}
//					hasModuleUpdate = true;
//				} catch (SQLException | IOException e) {
//					throw new McsException(McsErrorCode.COULDNT_UPDATE_MODULE_VERSION.toString() + e.getMessage());
//				}
//			}
//		}
//		return hasModuleUpdate;
		return true;
	}

	public String getEducationalPlan(int divisionId, int disciplineSpecialitieId, int complexSpecialitiesId)
			throws McsException {
		SqlParameterOccurrences[] params = {
				new SqlParameterOccurrences(complexSpecialitiesId, new int[] { 1, 5, 6, 7 }),
				new SqlParameterOccurrences(divisionId, new int[] { 2, 3 }),
				new SqlParameterOccurrences(disciplineSpecialitieId, new int[] { 4 }) };
		MethodicalComplex mc = new MethodicalComplex();
		mc.setEducationalPlans(new McsHelper().executeSqlStatement(McsSqlQueries.GET_EDUCATIONAL_PLANS, params,
				new RowMapper<EducationalPlan>() {
					public EducationalPlan mapRow(ResultSet rs, int rownum) throws SQLException {
						return new EducationalPlan(rs.getInt("IDENTIFIER"), optToStr(rs.getDate("D_START")),
								optToStr(rs.getDate("D_END")), optToStr(rs.getString("COURSE")),
								optToStr(rs.getInt("HOURS")), optToStr(rs.getInt("LEC_HOURS")),
								optToStr(rs.getInt("LAB_HOURS")), optToStr(rs.getInt("PR_HOURS")),
								optToStr(rs.getInt("KSR_HOURS")), optToStr(rs.getInt("SR_HOURS")),
								optToStr(rs.getString("CERT")), optToStr(rs.getString("COMPS")),
								optToStr(rs.getString("DIS_TYPE")), optToStr(rs.getString("DIS_KIND")),
								optToStr(rs.getString("FGOS")), rs.getInt("ID_EDUCATIONAL_PLAN"),
								rs.getInt("ID_DISCIPLINE_PLAN"));
					}
				}));
		return new McsHelper().marshalObjToXml(mc);
	}

	public String getCurrentWorkloads(int methodicalComplexId) throws McsException {
		SqlParameterOccurrences[] params = { new SqlParameterOccurrences(methodicalComplexId, new int[] { 1 }) };
		MethodicalComplex mc = new MethodicalComplex();
		mc.setWorkloads(new McsHelper().executeSqlStatement(McsSqlQueries.GET_CURRENT_WORKLOADS, params,
				new RowMapper<Workload>() {
					public Workload mapRow(ResultSet rs, int rownum) throws SQLException {
						return new Workload(optToStr(rs.getString("NAME")), rs.getInt("SEMESTER"),
								optToStr(rs.getInt("HOURS")), optToStr(rs.getInt("I_HOURS")), rs.getInt("IDK_LESSON"),
								rs.getInt("ID_EDUCATIONAL_LOAD_UMK"), rs.getInt("ID_METHODICAL_COMPLEX"));
					}
				}));
		return new McsHelper().marshalObjToXml(mc);
	}

	public String getActualWorkloads(int disciplinePlanId, int complexSpecialitiesId) throws McsException {
		SqlParameterOccurrences[] params = { new SqlParameterOccurrences(disciplinePlanId, new int[] { 1, 2 }),
				new SqlParameterOccurrences(complexSpecialitiesId, new int[] { 3 }) };
		MethodicalComplex mc = new MethodicalComplex();
		mc.setActualWorkloads(new McsHelper().executeSqlStatement(McsSqlQueries.GET_ACTUAL_WORKLOADS, params,
				new RowMapper<ActualWorkload>() {
					public ActualWorkload mapRow(ResultSet rs, int rownum) throws SQLException {
						return new ActualWorkload(rs.getInt("ID_EDUCATIONAL_LOAD_UMK"), optToStr(rs.getString("NAME")),
								rs.getInt("SEMESTER"), optToStr(rs.getInt("HOURS")),
								optToStr(rs.getInt("HOURS_INTERACTIVE")), optToStr(rs.getString("ACTION")),
								rs.getInt("IDK_LESSON"));
					}
				}));
		return new McsHelper().marshalObjToXml(mc);
	}

	public String getActualCompetences(int disciplinePlanId, int complexSpecialitiesId) throws McsException {
		SqlParameterOccurrences[] params = { new SqlParameterOccurrences(complexSpecialitiesId, new int[] { 1 }),
				new SqlParameterOccurrences(disciplinePlanId, new int[] { 2, 3 }) };
		MethodicalComplex mc = new MethodicalComplex();
		mc.setActualCompetences(new McsHelper().executeSqlStatement(McsSqlQueries.GET_ACTUAL_COMPETENCES, params,
				new RowMapper<ActualCompetence>() {
					public ActualCompetence mapRow(ResultSet rs, int rownum) throws SQLException {
						return new ActualCompetence(optToStr(rs.getString("ABBREVIATION")),
								optToStr(rs.getString("NAME")), optToStr(rs.getString("KNOWLEDGE")),
								optToStr(rs.getString("ABILITY")), optToStr(rs.getString("SKILL")),
								optToStr(rs.getString("ACTION")), rs.getInt("ID_S_COMPETENCE"),
								rs.getInt("ID_MC_COMPETENCES"));
					}
				}));
		return new McsHelper().marshalObjToXml(mc);
	}

	public String getSpecialityCompetences(int complexSpecialitiesId, int disciplinePlanId) throws McsException {
		SqlParameterOccurrences[] params = { new SqlParameterOccurrences(complexSpecialitiesId, new int[] { 1, 2 }),
				new SqlParameterOccurrences(disciplinePlanId, new int[] { 3 }) };
		MethodicalComplex mc = new MethodicalComplex();
		mc.setSpecialityCompetences(new McsHelper().executeSqlStatement(McsSqlQueries.GET_SPECIALITY_COMPETENCES,
				params, new RowMapper<SpecialityCompetence>() {
					public SpecialityCompetence mapRow(ResultSet rs, int rownum) throws SQLException {
						return new SpecialityCompetence(optToStr(rs.getString("ABBREVIATION")),
								optToStr(rs.getString("NAME")), rs.getInt("ID_S_COMPETENCE"));
					}
				}));
		return new McsHelper().marshalObjToXml(mc);
	}

	public String getXmlStructure(int methodicalComplexId, int complexSpecialitiesId) throws McsException {
		SqlParameterOccurrences[] params = { new SqlParameterOccurrences(methodicalComplexId, new int[] { 1 }),
				new SqlParameterOccurrences(complexSpecialitiesId, new int[] { 2 }) };
		String xml = new McsHelper()
				.executeSqlStatement(McsSqlQueries.GET_XML_STRUCTURE, params, new RowMapper<String>() {
					public String mapRow(ResultSet rs, int rownum) throws SQLException {
						return rs.getString("XML");
					}
				}).toString();
		if (!xml.isEmpty()) {
			return xml;
		} else
			throw new McsException(McsErrorCode.COULDNT_EXPORT_MC_XML.toString());
	}

	public boolean actualizeWorkloads(int complexSpecialitiesId, int methodicalComplexId, int disciplinePlanId)
			throws McsException {
		SqlParameterOccurrences[] params = { new SqlParameterOccurrences(complexSpecialitiesId, new int[] { 1, 4 }),
				new SqlParameterOccurrences(methodicalComplexId, new int[] { 7, 8, 9, 10, 11 }),
				new SqlParameterOccurrences(disciplinePlanId, new int[] { 2, 3, 5, 6 }) };
		return new McsHelper().executeSqlCommand(McsSqlQueries.ACTUALIZE_WORKLOADS, params);
	}

	public boolean addCompetence(int complexSpecialitiesId, int specialityCompetenceId) throws McsException {
		SqlParameterOccurrences[] params = { new SqlParameterOccurrences(complexSpecialitiesId, new int[] { 1 }),
				new SqlParameterOccurrences(specialityCompetenceId, new int[] { 2 }) };
		return new McsHelper().executeSqlCommand(McsSqlQueries.ADD_COMPETENCE, params);
	}

	public boolean deleteCompetence(int methodicalComplexCompetenceId) throws McsException {
		SqlParameterOccurrences[] params = {
				new SqlParameterOccurrences(methodicalComplexCompetenceId, new int[] { 1 }) };
		return new McsHelper().executeSqlCommand(McsSqlQueries.DELETE_COMPETENCE, params);
	}

	public boolean changeCompetence(int newSpecialityCompetenceId, int complexSpecialitiesId,
			int methodicalComplexCompetenceId) throws McsException {
		SqlParameterOccurrences[] params = { new SqlParameterOccurrences(newSpecialityCompetenceId, new int[] { 1 }),
				new SqlParameterOccurrences(complexSpecialitiesId, new int[] { 2 }),
				new SqlParameterOccurrences(methodicalComplexCompetenceId, new int[] { 3 }) };
		return new McsHelper().executeSqlCommand(McsSqlQueries.CHANGE_COMPETENCE, params);
	}
}
