package ru.unisuite.methodicalcomplexservice.entity;

import javax.xml.bind.annotation.XmlElement;

public class EducationalPlan {

	@XmlElement
	private int identifier;

	@XmlElement
	private String startDate;

	@XmlElement
	private String endDate;

	@XmlElement
	private String course;

	@XmlElement
	private String hours;

	@XmlElement
	private String lecHours;

	@XmlElement
	private String labHours;

	@XmlElement
	private String prHours;

	@XmlElement
	private String ksrHours;

	@XmlElement
	private String srHours;

	@XmlElement
	private String cert;

	@XmlElement
	private String competences;

	@XmlElement
	private String disciplineType;

	@XmlElement
	private String disciplineKind;

	@XmlElement
	private String fgos;

	@XmlElement
	private int educationalPlanId;

	@XmlElement
	private int disciplinePlanId;
	
	public EducationalPlan() {}

	public EducationalPlan(int identifier, String startDate, String endDate, String course, String hours, String lecHours,
			String labHours, String prHours, String ksrHours, String srHours, String cert, String competences,
			String disciplineType, String disciplineKind, String fgos, int educationalPlanId, int disciplinePlanId) {
		
		this.identifier = identifier;
		this.startDate = startDate;
		this.endDate = endDate;
		this.course = course;
		this.hours = hours;
		this.lecHours = lecHours;
		this.labHours = labHours;
		this.prHours = prHours;
		this.ksrHours = ksrHours;
		this.srHours = srHours;
		this.cert = cert;
		this.competences = competences;
		this.disciplineType = disciplineType;
		this.disciplineKind = disciplineKind;
		this.fgos = fgos;
		this.educationalPlanId = educationalPlanId;
		this.disciplinePlanId = disciplinePlanId;
	}

	public int getIdentifier() {
		return identifier;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getCourse() {
		return course;
	}

	public String getHours() {
		return hours;
	}

	public String getLecHours() {
		return lecHours;
	}

	public String getLabHours() {
		return labHours;
	}

	public String getPrHours() {
		return prHours;
	}

	public String getKsrHours() {
		return ksrHours;
	}

	public String getSrHours() {
		return srHours;
	}

	public String getCert() {
		return cert;
	}

	public String getCompetences() {
		return competences;
	}

	public String getDisciplineType() {
		return disciplineType;
	}

	public String getDisciplineKind() {
		return disciplineKind;
	}

	public String getFgos() {
		return fgos;
	}

	public int getEducationalPlanId() {
		return educationalPlanId;
	}

	public int getDisciplinePlanId() {
		return disciplinePlanId;
	}

}
