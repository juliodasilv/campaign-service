package br.com.avaliacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avaliacao.model.User;
import br.com.avaliacao.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userDAO;


	public User save(User user) {
		if(userDAO.findByEmail(user.getEmail()) != null)
			
			
		user = userDAO.save(user);
		return user; 
	}
}
