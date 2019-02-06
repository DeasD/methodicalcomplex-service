package ru.unisuite.methodicalcomplexservice.entity;

import javax.xml.bind.annotation.XmlElement;

public class ActualWorkload {

	@XmlElement
	private int educationalLoadMcId;

	@XmlElement
	private String name;

	@XmlElement
	private int semester;

	@XmlElement
	private String hours;

	@XmlElement
	private String interactiveHours;

	@XmlElement
	private String action;

	@XmlElement
	private int lessonIdk;

	public ActualWorkload() {
	}

	public ActualWorkload(int educationalLoadMcId, String name, int semester, String hours, String interactiveHours,
			String action, int lessonIdk) {

		this.educationalLoadMcId = educationalLoadMcId;
		this.name = name;
		this.semester = semester;
		this.hours = hours;
		this.interactiveHours = interactiveHours;
		this.action = action;
		this.lessonIdk = lessonIdk;
	}

	public int getEducationalLoadMcId() {
		return educationalLoadMcId;
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

	public String getAction() {
		return action;
	}

	public int getLessonIdk() {
		return lessonIdk;
	}

}
