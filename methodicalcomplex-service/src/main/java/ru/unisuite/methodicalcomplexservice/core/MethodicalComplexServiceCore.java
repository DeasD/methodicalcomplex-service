package ru.unisuite.methodicalcomplexservice.core;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.unisuite.methodicalcomplexservice.jaxbentities.*;
import ru.unisuite.methodicalcomplexservice.exception.MethodicalComplexServiceException;

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

	public void setSQLParams(PreparedStatement ps, Object[] sqlQueryParams) {

		for (int i = 0; i < sqlQueryParams.length; i++) {
			try {
				ps.setObject(i + 1, sqlQueryParams[i]);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public String findAuthors(String fio_mask) throws MethodicalComplexServiceException {
		StringWriter writer = new StringWriter();
		try (Connection connection = getDataSource().getConnection();
				PreparedStatement ps = connection
						.prepareStatement(MethodicalComplexSqlQueriesController.GET_AUTHORS_SQL)) {

			setSQLParams(ps, new Object[] { fio_mask });

			try (ResultSet rs = ps.executeQuery()) {

				MethodicalComplex methodicalComplex = new MethodicalComplex();
				List<Authors> authorsList = new ArrayList<Authors>();

				while (rs.next()) {
					Authors author = new Authors(rs.getInt("ID_E"), rs.getString("FIO").toString(),
							rs.getDate("BIRTHDAY").toString());
					authorsList.add(author);
				}
				methodicalComplex.setAuthors(authorsList);
				getMarshaller(methodicalComplex).marshal(methodicalComplex, writer);
				return writer.toString();
			}

		} catch (SQLException | JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new MethodicalComplexServiceException(e.getMessage(), e);
		}

	}

	public String findBooks(String bookMask) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDisciplines(int disciplineTypeIndex, int semesterNum, int complexSpecialitiesID) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEducationalPlan(int disciplineID, int disciplineSpecialitieID, int complexSpecialitiesID) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCurrentWorkloads(int methodicalComplexID) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getActualWorkloads(int disciplinePlanID, int complexSpecialitiesID) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getActualCompetences(int disciplinePlanID, int complexSpecialitiesID) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSpecialityCompetences(int complexSpecialitiesID, int disciplinePlanID) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getXmlStructure(int methodicalComplexID, int complexSpecialitiesID) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean actualizeWorkloads(int complexSpecialitiesID, int methodicalComplexID, int disciplinePlanID) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean addCompetence(int complexSpecialitiesID, int specialityCompetenceID) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean deleteCompetence(int methodicalComplexCompetenceID) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean changeCompetence(int newSpecialityCompetenceID, int complexSpecialitiesID,
			int methodicalComplexCompetenceID) {
		// TODO Auto-generated method stub
		return null;
	}

}
