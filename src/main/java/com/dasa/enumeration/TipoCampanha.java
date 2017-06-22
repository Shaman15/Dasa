package com.dasa.enumeration;

public enum TipoCampanha {
	CANCER_PROSTATA	("Câncer de Próstata"), 
	CANCER_MAMA		("Câncer de Mama");
	
	private String campanha;
	
	TipoCampanha(String campanha){
		this.campanha = campanha;
	}

	public String getCampanha() {
		return campanha;
	}
}
