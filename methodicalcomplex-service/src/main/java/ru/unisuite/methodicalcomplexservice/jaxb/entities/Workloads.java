package ru.unisuite.methodicalcomplexservice.jaxb.entities;

import javax.xml.bind.annotation.XmlElement;

public class Workloads {
	
	@XmlElement
	private String name;
	
	@XmlElement
	private int semester;
	
	@XmlElement
	private String hours;
	
	@XmlElement
	private String interactiveHours;
	
	@XmlElement
	private int lessonIdk;
	
	@XmlElement
	private int educationalLoadMcId;
	
	@XmlElement
	private int methodicalComplexId;
	
	public Workloads() {}
	
	public Workloads(String name, int semester, String hours, String interactiveHours, int lessonIdk,
			int educationalLoadMcId, int methodicalComplexId) {
		this.name = name;
		this.semester = semester;
		this.hours = hours;
		this.interactiveHours = interactiveHours;
		this.lessonIdk = lessonIdk;
		this.educationalLoadMcId = educationalLoadMcId;
		this.methodicalComplexId = methodicalComplexId;
	}
	
	public String getName() {
		return name;
	}

	public int getSemester() {
		return semester;
	}

	public String getHours() {
		return hours;
	}

	public String getInteractiveHours() {
		return interactiveHours;
	}

	public int getLessonIdk() {
		return lessonIdk;
	}

	public int getEducationalLoadMcId() {
		return educationalLoadMcId;
	}

	public int getMethodicalComplexId() {
		return methodicalComplexId;
	}
	

}
