package com.dasa.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.dasa.domain.Campanha;

@Transactional
public interface CampanhaRepository extends CrudRepository<Campanha, Long> {

	List<Campanha> findByAno(final String ano);

}
