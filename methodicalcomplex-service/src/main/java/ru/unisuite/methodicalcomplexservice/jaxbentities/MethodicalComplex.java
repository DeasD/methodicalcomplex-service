package ru.unisuite.methodicalcomplexservice.jaxbentities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MethodicalComplex {

	@XmlElementWrapper(name = "authors")
	@XmlElement(name = "author")
	private List<Authors> authors;

	public void setAuthors(List<Authors> authors) {
		this.authors = authors;
	}

}
