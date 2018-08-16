package br.com.avaliacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.avaliacao.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	Customer findByEmail(String email);
}
