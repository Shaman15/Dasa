package com.dasa.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dasa.enumeration.TipoCampanha;
import com.dasa.enumeration.TipoSexo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "dados_campanhas")
public class Campanha implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Long id;

	private String ano;

	@Enumerated(EnumType.STRING)
	private TipoSexo sexo;

	@Enumerated(EnumType.STRING)
	private TipoCampanha campanha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public TipoSexo getSexo() {
		return sexo;
	}

	public void setSexo(TipoSexo sexo) {
		this.sexo = sexo;
	}

	public TipoCampanha getCampanha() {
		return campanha;
	}

	public void setCampanha(TipoCampanha campanha) {
		this.campanha = campanha;
	}

}