package com.dasa.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dasa.domain.Campanha;
import com.dasa.domain.DadoPopulacional;
import com.dasa.domain.ProporcaoAnoResponse;
import com.dasa.enumeration.TipoCampanha;
import com.dasa.enumeration.TipoSexo;
import com.dasa.repository.CampanhaRepository;
import com.dasa.repository.DadosPopulacionaisRepository;

@Service
public class DadosPopulacionaisServiceimpl implements DadosPopulacionaisService {

	@Autowired
	private DadosPopulacionaisRepository dadosPopulacionaisRepository;
	
	@Autowired
	private CampanhaRepository campanhaRepository;

	@Override
	public DadoPopulacional obterPopulacaoPorAno(final Optional<String> ano) {

		final String anoCenso = ano.get();

		if (!ano.isPresent()) {
			throw new IllegalArgumentException("Parametro Ano é obrigatorio.");
		}

		return dadosPopulacionaisRepository.findByAno(anoCenso);
	}

	@Override
	public DadoPopulacional obterProjecaoAno(String ano) {
		DadoPopulacional dadoPopulacional = new DadoPopulacional();
		dadoPopulacional.setAno(ano);
		
		// Obtendo as estatisticas dos anos anteriores.
		DadoPopulacional dadosPopulacionais2010 = obterPopulacaoPorAno(Optional.of("2010"));
		DadoPopulacional dadosPopulacionais2000 = obterPopulacaoPorAno(Optional.of("2000"));
		double populacaoTotal = Math.pow(
				dadosPopulacionais2010.getPopulacaoTotal()
						.divide(dadosPopulacionais2000.getPopulacaoTotal(), 5, RoundingMode.HALF_UP).doubleValue(),
				1.0 / 10);

		// Realizando a consulta para verificar o crescimento baseado neste ano.
		Integer anoAnterior = Integer.valueOf(ano)-1;
		DadoPopulacional dadosPopulacionais = obterPopulacaoPorAno(Optional.of(String.valueOf(anoAnterior)));
		BigDecimal porcCrescimento = new BigDecimal(populacaoTotal - 1);
		
		// Crescimento total
		BigDecimal crescimentoTotal = dadosPopulacionais.getPopulacaoTotal().multiply(porcCrescimento).setScale(0, RoundingMode.HALF_UP);
		BigDecimal totalCrescimento = dadosPopulacionais.getPopulacaoTotal().add(crescimentoTotal);
		dadoPopulacional.setPopulacaoTotal(totalCrescimento);
		
		// Crescimento homens
		BigDecimal crescimentoHomens = new BigDecimal(dadosPopulacionais.getTotalHomens()).multiply(porcCrescimento).setScale(0, RoundingMode.HALF_UP);
		BigDecimal homensCrescimento = new BigDecimal(dadosPopulacionais.getTotalHomens()).add(crescimentoHomens);
		dadoPopulacional.setTotalHomens(homensCrescimento.longValue());
		
		// Crescimento mulheres
		BigDecimal crescimentoMulheres = new BigDecimal(dadosPopulacionais.getTotalMulheres()).multiply(porcCrescimento).setScale(0, RoundingMode.HALF_UP);
		BigDecimal mulheresCrescimento = new BigDecimal(dadosPopulacionais.getTotalMulheres()).add(crescimentoMulheres);
		dadoPopulacional.setTotalMulheres(mulheresCrescimento.longValue());

		return dadoPopulacional;
	}

	@Override
	public Campanha participarCampanha(Campanha campanha) {

		if (campanha.getAno() == null) {
			throw new IllegalArgumentException("Parametro Ano é obrigatorio.");
		}

		if (campanha.getSexo() == null) {
			throw new IllegalArgumentException("Parametro Ano é obrigatorio.");
		}
		
		if (campanha.getCampanha() == null) {
			throw new IllegalArgumentException("Parametro Campanha é obrigatorio.");
		}

		// Obtendo por ano.
		DadoPopulacional dadoPopulacional = dadosPopulacionaisRepository.findByAno(campanha.getAno());
		dadoPopulacional.setPopulacaoTotal(dadoPopulacional.getPopulacaoTotal().add(new BigDecimal(1000000)));

		if (TipoSexo.M.equals(campanha.getSexo())) {
			dadoPopulacional.setTotalHomens(dadoPopulacional.getTotalHomens() + 1000000);
		} else if (TipoSexo.F.equals(campanha.getSexo())) {
			dadoPopulacional.setTotalMulheres(dadoPopulacional.getTotalMulheres() + 1);
		}

		// Persistindo a participacao nos dados populacionais.
		dadosPopulacionaisRepository.save(dadoPopulacional);
		
		// Persistindo a campanha.
		campanhaRepository.save(campanha);

		return campanha;
	}
	
	@Override
	public List<Campanha> obterCampanhaPorAno(final Optional<String> ano) {
		final String anoFiltro = ano.get();

		if (!ano.isPresent()) {
			throw new IllegalArgumentException("Parametro Ano é obrigatorio.");
		}
		
		return campanhaRepository.findByAno(anoFiltro);
	}
	
	@Override
	public ProporcaoAnoResponse obterProporcaoAno(final Optional<String> ano) {
		List<Campanha> campanhasAno = obterCampanhaPorAno(ano);
		if(CollectionUtils.isEmpty(campanhasAno)){
			return new ProporcaoAnoResponse(0D, 0D, 0D, 0D);
		}
		
		Integer qtdCancerMama = 0;
		List<Campanha> campanhasCancerMama = campanhasAno.stream().filter(c -> TipoCampanha.CANCER_MAMA.equals(c.getCampanha())).collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(campanhasCancerMama)){
			qtdCancerMama = campanhasCancerMama.size();
		}
		
		Integer qtdCancerProstata = 0;
		List<Campanha> campanhasCancerProstata = campanhasAno.stream().filter(c -> TipoCampanha.CANCER_PROSTATA.equals(c.getCampanha())).collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(campanhasCancerProstata)){
			qtdCancerProstata = campanhasCancerProstata.size();
		}
		
		Integer qtdSexoMasculino = 0;
		List<Campanha> campanhasSexoMasculino = campanhasAno.stream().filter(c -> TipoSexo.M.equals(c.getSexo())).collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(campanhasSexoMasculino)){ 
			qtdSexoMasculino = campanhasSexoMasculino.size();
		}
		
		Integer qtdSexoFeminino = 0;
		List<Campanha> campanhasSexoFeminino = campanhasAno.stream().filter(c -> TipoSexo.F.equals(c.getSexo())).collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(campanhasSexoFeminino)){
			qtdSexoFeminino = campanhasSexoFeminino.size();
		}
		
		Integer totalCampanhasAno = campanhasAno.size();
		
		Double porcCancerMama = (qtdCancerMama * 100) / Double.valueOf(totalCampanhasAno);
		Double porcCancerProstata = (qtdCancerProstata * 100) / Double.valueOf(totalCampanhasAno);
		Double porcSexoMasculino = (qtdSexoMasculino * 100) / Double.valueOf(totalCampanhasAno);
		Double porcSexoFeminino = (qtdSexoFeminino * 100) / Double.valueOf(totalCampanhasAno);
		return new ProporcaoAnoResponse(porcCancerMama, porcCancerProstata, porcSexoMasculino, porcSexoFeminino);
	}
}
