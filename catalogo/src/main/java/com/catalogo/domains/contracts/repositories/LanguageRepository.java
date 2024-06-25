package com.catalogo.domains.contracts.repositories;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.catalogo.domains.core.contrats.repositories.RepositoryWithProjections;
import com.catalogo.domains.entities.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer>, JpaSpecificationExecutor<Language>,
RepositoryWithProjections{
	List<Language>findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
	@Query(value = "SELECT count(*) FROM Language l")
	int cuentaLenguajes();
}
