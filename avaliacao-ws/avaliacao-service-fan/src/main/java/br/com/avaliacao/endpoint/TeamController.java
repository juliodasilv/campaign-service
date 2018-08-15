package br.com.avaliacao.endpoint;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avaliacao.model.User;
import br.com.avaliacao.service.UserService;

@RestController
@RequestMapping("v1")
public class TeamController {

	@Autowired
	private UserService userService;

	@PostMapping(path="users")
	public ResponseEntity<?> saveUser(@Valid @RequestBody User user){
		return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
	}

}