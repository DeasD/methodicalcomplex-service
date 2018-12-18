package ru.unisuite.methodicalcomplexservice.jaxb.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MethodicalComplex {

	@XmlElementWrapper(name = "authors")
	@XmlElement(name = "author")
	private List<Authors> authors;

	@XmlElementWrapper(name = "books")
	@XmlElement(name = "book")
	private List<Books> books;

	@XmlElementWrapper(name = "disciplines")
	@XmlElement(name = "discipline")
	private List<Disciplines> disciplines;

	@XmlElementWrapper(name = "educationalPlans")
	@XmlElement(name = "educationalPlan")
	private List<EducationalPlan> educationalPlans;

	@XmlElementWrapper(name = "workloads")
	@XmlElement(name = "workload")
	private List<Workloads> workloads;

	@XmlElementWrapper(name = "actualWorkloads")
	@XmlElement(name = "actualWorkload")
	private List<ActualWorkloads> actualWorkloads;

	@XmlElementWrapper(name = "actualCompetences")
	@XmlElement(name = "actualCompetences")
	private List<ActualCompetences> actualCompetences;

	@XmlElementWrapper(name = "specialityCompetences")
	@XmlElement(name = "specialityCompetences")
	private List<SpecialityCompetences> specialityCompetences;

	public void setAuthors(List<Authors> authors) {
		this.authors = authors;
	}

	public void setBooks(List<Books> books) {
		this.books = books;
	}

	public void setDisciplines(List<Disciplines> disciplines) {
		this.disciplines = disciplines;
	}

	public void setEducationalPlans(List<EducationalPlan> educationalPlans) {
		this.educationalPlans = educationalPlans;
	}

	public void setWorkloads(List<Workloads> workloads) {
		this.workloads = workloads;
	}

	public void setActualWorkloads(List<ActualWorkloads> actualWorkloads) {
		this.actualWorkloads = actualWorkloads;
	}

	public void setActualCompetences(List<ActualCompetences> actualCompetences) {
		this.actualCompetences = actualCompetences;
	}

	public void setSpecialityCompetences(List<SpecialityCompetences> specialityCompetences) {
		this.specialityCompetences = specialityCompetences;
	}
}
