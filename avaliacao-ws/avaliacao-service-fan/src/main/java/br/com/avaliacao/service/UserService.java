package br.com.avaliacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avaliacao.error.ResourceNotFoundException;
import br.com.avaliacao.error.UserAlreadyAddedException;
import br.com.avaliacao.model.Team;
import br.com.avaliacao.model.User;
import br.com.avaliacao.repository.TeamRepository;
import br.com.avaliacao.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userDAO;

	@Autowired
	private TeamRepository teamDAO;


	public User save(User user) {
		//Verifica se o usuario ja existe pelo email
		if(userDAO.findByEmail(user.getEmail()) != null)
			throw new UserAlreadyAddedException("User already added!");
		
		//Verifica se o time existe, caso contrario lanca excecao
		Team team = teamDAO.findByNameIgnoreCase(user.getTeam().getName());
		if(team==null)
			throw new ResourceNotFoundException("Team not found!");
		else
			user.setTeam(team);
		
		//persiste o usuario
		user = userDAO.save(user);
		return user; 
	}
}
