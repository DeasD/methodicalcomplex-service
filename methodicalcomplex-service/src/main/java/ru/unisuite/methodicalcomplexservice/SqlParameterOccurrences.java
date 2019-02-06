package ru.unisuite.methodicalcomplexservice;

public class SqlParameterOccurrences {

	private Object param;

	private int[] occurrences;

	SqlParameterOccurrences(Object param, int[] occurrences) {
		this.param = param;
		this.occurrences = occurrences;
	}

	public Object getParam() {
		return param;
	}

	public int[] getOccurrences() {
		return occurrences;
	}

}
