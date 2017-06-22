package com.dasa.domain;

import lombok.Data;

@Data
public class ProporcaoAnoResponse {
	private Double cancerMama;
	private Double cancerProstata;
	private Double sexoMasculino;
	private Double sexoFeminino;

	public ProporcaoAnoResponse(Double cancerMama, Double cancerProstata, Double sexoMasculino, Double sexoFeminino) {
		super();
		this.cancerMama = cancerMama;
		this.cancerProstata = cancerProstata;
		this.sexoMasculino = sexoMasculino;
		this.sexoFeminino = sexoFeminino;
	}

	public Double getCancerMama() {
		return cancerMama;
	}

	public void setCancerMama(Double cancerMama) {
		this.cancerMama = cancerMama;
	}

	public Double getCancerProstata() {
		return cancerProstata;
	}

	public void setCancerProstata(Double cancerProstata) {
		this.cancerProstata = cancerProstata;
	}

	public Double getSexoMasculino() {
		return sexoMasculino;
	}

	public void setSexoMasculino(Double sexoMasculino) {
		this.sexoMasculino = sexoMasculino;
	}

	public Double getSexoFeminino() {
		return sexoFeminino;
	}

	public void setSexoFeminino(Double sexoFeminino) {
		this.sexoFeminino = sexoFeminino;
	}

}
