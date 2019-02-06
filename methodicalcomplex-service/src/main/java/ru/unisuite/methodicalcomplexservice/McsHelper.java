package ru.unisuite.methodicalcomplexservice;

import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import ru.unisuite.methodicalcomplexservice.exception.McsException;
import ru.unisuite.methodicalcomplexservice.exception.McsErrorCode;

public class McsHelper {

	private static final String JNDI_DATASOURCE_NAME = "jdbc/ds_basic";
	private static final Logger logger = LoggerFactory.getLogger(McsHelper.class);

	public JdbcTemplate getJdbcTemplate() throws McsException {

		Context initContext = null;
		try {
			initContext = new InitialContext();
			DataSource dataSource = (DataSource) initContext.lookup(JNDI_DATASOURCE_NAME);
			// dataSource.getConnection().setAutoCommit(true);
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			return jdbcTemplate;
		} catch (NamingException e) {
			logger.error("Couldn't initialize JNDI data source. " + e.toString());
			throw new McsException(e.getMessage(), e);
		} finally {
			if (initContext != null) {
				try {
					initContext.close();
				} catch (NamingException e) {
					logger.warn("Initial context of JNDI data source can't be closed. " + e.toString());
					throw new McsException(e.getMessage(), e);
				}
			}
		}
	}

	public <T> List<T> executeSqlStatement(String sqlQuery, SqlParameterOccurrences[] params, RowMapper<T> rowMap)
			throws McsException {
		try {
			if (params != null) {
				return getJdbcTemplate().query(sqlQuery, new PreparedStatementSetter() {
					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						for (int i = 0; i < params.length; i++) {
							Object param = params[i].getParam();
							int[] paramOccurences = params[i].getOccurrences();
							for (int j = 0; j < paramOccurences.length; j++)
								preparedStatement.setObject(paramOccurences[j], param);
						}
					}
				}, rowMap);
			} else
				return getJdbcTemplate().query(sqlQuery, rowMap);
		} catch (DataAccessException e) {
			logger.error(e.toString());
			throw new McsException(McsErrorCode.COULDNT_GET_DATA.toString() + e.getMessage());
		}
	}

	public boolean executeSqlCommand(String sqlQuery, SqlParameterOccurrences[] params) throws McsException {
		try {
			if (params != null) {
				getJdbcTemplate().update(sqlQuery, new PreparedStatementSetter() {
					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						for (int i = 0; i < params.length; i++) {
							Object param = params[i].getParam();
							int[] paramOccurences = params[i].getOccurrences();
							for (int j = 0; j < paramOccurences.length; j++)
								preparedStatement.setObject(paramOccurences[j], param);
						}
					}
				});
			} else
				getJdbcTemplate().update(sqlQuery);
			return true;
		} catch (DataAccessException e) {
			logger.error(e.toString());
			throw new McsException(McsErrorCode.COULDNT_EXECUTE_SQL_COMMAND.toString() + e.getMessage());
		}
	}

	public String marshalObjToXml(Object marshalizedObj) throws McsException {
		StringWriter writer = new StringWriter();
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(marshalizedObj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(marshalizedObj, writer);
			return writer.toString();
		} catch (JAXBException e) {
			logger.error(e.toString());
			throw new McsException(e.getMessage(), e);
		}
	}
}
