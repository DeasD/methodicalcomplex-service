package ru.unisuite.methodicalcomplexservice.entity;

import javax.xml.bind.annotation.XmlElement;

public class SpecialityCompetence {
	
	@XmlElement
	private String abbreviation;

	@XmlElement
	private String name;

	@XmlElement
	private int specialityCompetenceId;
	
	public SpecialityCompetence() {}
	
	public SpecialityCompetence(String abbreviation, String name, int specialityCompetenceId) {
		this.abbreviation = abbreviation;
		this.name = name;
		this.specialityCompetenceId = specialityCompetenceId;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getName() {
		return name;
	}

	public int getSpecialityCompetenceId() {
		return specialityCompetenceId;
	}
	
}
