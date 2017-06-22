package com.dasa.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dasa.domain.Campanha;
import com.dasa.domain.DadoPopulacional;
import com.dasa.domain.EstatisticaAnoResponse;
import com.dasa.domain.ProporcaoAnoResponse;
import com.dasa.service.DadosPopulacionaisService;

@RestController
public class SampleController {

	@Autowired
	DadosPopulacionaisService service;

	@RequestMapping("/hello")
	public String helloWorld() {
		return "Hello =)";
	}

	@RequestMapping("/2010")
	public EstatisticaAnoResponse get2010data() {
		DadoPopulacional pop = service.obterPopulacaoPorAno(Optional.of("2010"));
		return new EstatisticaAnoResponse(pop);
	}

	@RequestMapping("/projecao/{ano}")
	public EstatisticaAnoResponse projecaoAno(@PathVariable("ano") String ano) {
		DadoPopulacional pop = service.obterProjecaoAno(ano);
		return new EstatisticaAnoResponse(pop);
	}

	@RequestMapping(value = "/participar", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Campanha participarCampanha(@RequestBody Campanha request) {
		return service.participarCampanha(request);
	}
	
	@RequestMapping("/dadosCampanha/{ano}")
	public List<Campanha> dadosCampanhaAno(@PathVariable("ano") String ano) {
		return service.obterCampanhaPorAno(Optional.of(ano));
	}
	
	@RequestMapping("/proporcao/{ano}")
	public ProporcaoAnoResponse proporcao(@PathVariable("ano") String ano) {
		return service.obterProporcaoAno(Optional.of(ano));
	}

}
