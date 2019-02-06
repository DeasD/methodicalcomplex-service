package ru.unisuite.methodicalcomplexservice.entity;

import javax.xml.bind.annotation.XmlElement;

public class ActualCompetence {

	@XmlElement
	private String abbreviation;

	@XmlElement
	private String name;

	@XmlElement
	private String knowledge;

	@XmlElement
	private String ability;

	@XmlElement
	private String skill;

	@XmlElement
	private String action;

	@XmlElement
	private int specialityCompetenceId;

	@XmlElement
	private int methodicalComplexCompetenceId;
	
	public ActualCompetence() {}

	public ActualCompetence(String abbreviation, String name, String knowledge, String ability, String skill,
			String action, int specialityCompetenceId, int methodicalComplexCompetenceId) {
		this.abbreviation = abbreviation;
		this.name = name;
		this.knowledge = knowledge;
		this.ability = ability;
		this.skill = skill;
		this.action = action;
		this.specialityCompetenceId = specialityCompetenceId;
		this.methodicalComplexCompetenceId = methodicalComplexCompetenceId;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getName() {
		return name;
	}

	public String getKnowledge() {
		return knowledge;
	}

	public String getAbility() {
		return ability;
	}

	public String getSkill() {
		return skill;
	}

	public String getAction() {
		return action;
	}

	public int getSpecialityCompetenceId() {
		return specialityCompetenceId;
	}

	public int getMethodicalComplexCompetenceId() {
		return methodicalComplexCompetenceId;
	}

}
