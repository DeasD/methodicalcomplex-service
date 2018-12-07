package ru.unisuite.methodicalcomplexservice.jaxb.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Books {

	@XmlElement
	private String name;

	@XmlElement
	private String authors;

	@XmlElement
	private String publishYear;

	@XmlElement
	private String publishers;

	@XmlElement
	private String kindEdition;

	@XmlElement
	private String placeName;

	@XmlElement
	private int editionId;

	public Books() {}

	public Books(String name, String authors, String publishYear, String publishers, String kindEdition,
			String placeName, int editionId) {
		this.name = name;
		this.authors = authors;
		this.publishYear = publishYear;
		this.publishers = publishers;
		this.kindEdition = kindEdition;
		this.placeName = placeName;
		this.editionId = editionId;
	}

	public String getName() {
		return name;
	}

	public String getAuthors() {
		return authors;
	}

	public String getPublishYear() {
		return publishYear;
	}

	public String getPublishers() {
		return publishers;
	}

	public String getKindEdition() {
		return kindEdition;
	}

	public String getPlaceName() {
		return placeName;
	}

	public int getEditionId() {
		return editionId;
	}

}
