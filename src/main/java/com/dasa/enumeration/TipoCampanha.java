package com.dasa.enumeration;

public enum TipoCampanha {
	CANCER_PROSTATA	("C�ncer de Pr�stata"), 
	CANCER_MAMA		("C�ncer de Mama");
	
	private String campanha;
	
	TipoCampanha(String campanha){
		this.campanha = campanha;
	}

	public String getCampanha() {
		return campanha;
	}
}
