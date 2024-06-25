package com.catalogo.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.catalogo.domains.core.contrats.repositories.RepositoryWithProjections;
import com.catalogo.domains.entities.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer>, JpaSpecificationExecutor<Language>,
RepositoryWithProjections{

}
