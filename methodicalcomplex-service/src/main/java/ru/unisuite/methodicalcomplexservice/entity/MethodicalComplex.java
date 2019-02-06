package ru.unisuite.methodicalcomplexservice.entity;

import java.sql.Blob;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MethodicalComplex {

	@XmlElementWrapper(name = "authors")
	@XmlElement(name = "author")
	private List<Author> authors;

	@XmlElementWrapper(name = "books")
	@XmlElement(name = "book")
	private List<Book> books;

	@XmlElementWrapper(name = "disciplines")
	@XmlElement(name = "discipline")
	private List<Discipline> disciplines;

	@XmlElementWrapper(name = "educationalPlans")
	@XmlElement(name = "educationalPlan")
	private List<EducationalPlan> educationalPlans;

	@XmlElementWrapper(name = "workloads")
	@XmlElement(name = "workload")
	private List<Workload> workloads;

	@XmlElementWrapper(name = "actualWorkloads")
	@XmlElement(name = "actualWorkload")
	private List<ActualWorkload> actualWorkloads;

	@XmlElementWrapper(name = "actualCompetences")
	@XmlElement(name = "actualCompetence")
	private List<ActualCompetence> actualCompetences;

	@XmlElementWrapper(name = "specialityCompetences")
	@XmlElement(name = "specialityCompetence")
	private List<SpecialityCompetence> specialityCompetences;

	private String md5Hash;

	private Blob moduleBlob;
	
	public MethodicalComplex() {}

	public MethodicalComplex(String md5Hash, Blob moduleBlob) {
		this.md5Hash = md5Hash;
		this.moduleBlob = moduleBlob;
	}

	public String getMd5Hash() {
		return md5Hash;
	}

	public Blob getModuleBlob() {
		return moduleBlob;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public void setDisciplines(List<Discipline> disciplines) {
		this.disciplines = disciplines;
	}

	public void setEducationalPlans(List<EducationalPlan> educationalPlans) {
		this.educationalPlans = educationalPlans;
	}

	public void setWorkloads(List<Workload> workloads) {
		this.workloads = workloads;
	}

	public void setActualWorkloads(List<ActualWorkload> actualWorkload) {
		this.actualWorkloads = actualWorkload;
	}

	public void setActualCompetences(List<ActualCompetence> actualCompetences) {
		this.actualCompetences = actualCompetences;
	}

	public void setSpecialityCompetences(List<SpecialityCompetence> specialityCompetences) {
		this.specialityCompetences = specialityCompetences;
	}
}
