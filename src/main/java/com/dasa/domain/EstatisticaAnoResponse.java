package com.dasa.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Data;

@Data
public class EstatisticaAnoResponse {
	private final String ano;
	private final BigDecimal total;
	private final Double porcentagemHomens;
	private final Double porcentagemMulheres;

	public EstatisticaAnoResponse(DadoPopulacional pop) {
		this.ano = pop.getAno();
		this.total = pop.getPopulacaoTotal();
		this.porcentagemHomens = calcularPorcentagem(total, pop.getTotalHomens());
		this.porcentagemMulheres = calcularPorcentagem(total, pop.getTotalMulheres());
	}

	private Double calcularPorcentagem(BigDecimal total, Long amostra) {
		BigDecimal result = new BigDecimal(0);
		result = new BigDecimal(amostra).multiply(new BigDecimal(100));
		result = result.divide(total, 2, RoundingMode.HALF_UP);
		return result.doubleValue();
	}

	public String getAno() {
		return ano;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public Double getPorcentagemHomens() {
		return porcentagemHomens;
	}

	public Double getPorcentagemMulheres() {
		return porcentagemMulheres;
	}

}
