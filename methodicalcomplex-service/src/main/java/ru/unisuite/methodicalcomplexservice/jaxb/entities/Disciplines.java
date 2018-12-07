package ru.unisuite.methodicalcomplexservice.jaxb.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Disciplines {

	@XmlElement
	private int semester;

	@XmlElement
	private String name;

	@XmlElement
	private String knowledge;

	@XmlElement
	private String ability;

	@XmlElement
	private String skill;

	@XmlElement
	private int disciplineId;
	
	public Disciplines() {}

	public Disciplines(int semester, String name, String knowledge, String ability, String skill, int disciplineId) {
		this.semester = semester;
		this.name = name;
		this.knowledge = knowledge;
		this.ability = ability;
		this.skill = skill;
		this.disciplineId = disciplineId;
	}

	public int getSemester() {
		return semester;
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

	public int getDisciplineId() {
		return disciplineId;
	}
	
}
