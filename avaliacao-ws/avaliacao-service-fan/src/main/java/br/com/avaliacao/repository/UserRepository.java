package br.com.avaliacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.avaliacao.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
