package br.com.avaliacao.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avaliacao.model.User;
import br.com.avaliacao.service.FanService;

@RestController
@RequestMapping("v1")
public class FanController {

	@Autowired
	private FanService userService;

	@PostMapping(path="users")
	public ResponseEntity<?> saveUser(@Valid @RequestBody User user){
		return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
	}

}