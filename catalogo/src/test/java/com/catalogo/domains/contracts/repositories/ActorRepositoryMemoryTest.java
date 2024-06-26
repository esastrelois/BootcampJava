package com.catalogo.domains.contracts.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.catalogo.domains.contrats.services.FilmService;
import com.catalogo.domains.entities.Actor;


@DataJpaTest
class ActorRepositoryMemoryTest {
	@Autowired
	private TestEntityManager em;

	@Autowired
	ActorRepository dao;
	
	@MockBean
	FilmService frv;
	
	@MockBean
	FilmRepository fr;

	@Nested
    @DisplayName("Test de ActorRepository")
    class TestActorRepository {

        @Test
        void testFindTop5ByLastNameStartingWithOrderByFirstNameDesc() {
            // Arrange
            dao.save(new Actor(1, "Luis", "Dominguez"));
            dao.save(new Actor(2, "Marcos", "Dominguez"));
            dao.save(new Actor(3, "Clara", "Dominguez"));
            dao.save(new Actor(4, "Sergio", "Lopez"));
            dao.save(new Actor(5, "Pablo", "Dominguez"));
            dao.save(new Actor(6, "Covadonga", "Dominguez"));
            dao.save(new Actor(6, "Maria", "Dominguez"));

            // Act
            List<Actor> result = dao.findTop5ByLastNameStartingWithOrderByFirstNameDesc("D");

            // Assert
            assertThat(result).hasSize(5);
        }
	}
}