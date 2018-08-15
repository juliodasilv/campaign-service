package br.com.avaliacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.avaliacao.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

	Team findByNameIgnoreCase(String name);
}
