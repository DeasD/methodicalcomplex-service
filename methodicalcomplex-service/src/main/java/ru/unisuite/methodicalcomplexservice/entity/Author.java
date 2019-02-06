package ru.unisuite.methodicalcomplexservice.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Author {
	@XmlElement
	private int idEmployee;

	@XmlElement
	private String name;

	@XmlElement
	private String birthday;

	public Author() {}

	public Author(int idEmployee, String name, String birthday) {
		this.idEmployee = idEmployee;
		this.name = name;
		this.birthday = birthday;
	}

	public int getIdEmployee() {
		return idEmployee;
	}

	public String getName() {
		return name;
	}

	public String getBirthday() {
		return birthday;
	}

}
