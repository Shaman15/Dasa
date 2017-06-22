package com.dasa.service;

import java.util.List;
import java.util.Optional;

import com.dasa.domain.Campanha;
import com.dasa.domain.DadoPopulacional;
import com.dasa.domain.ProporcaoAnoResponse;


public interface DadosPopulacionaisService {

    DadoPopulacional obterPopulacaoPorAno(final Optional<String>  ano);
    
    DadoPopulacional obterProjecaoAno(final String ano);
    
    Campanha participarCampanha(Campanha request);

	List<Campanha> obterCampanhaPorAno(Optional<String> ano);

	ProporcaoAnoResponse obterProporcaoAno(Optional<String> ano);

}
