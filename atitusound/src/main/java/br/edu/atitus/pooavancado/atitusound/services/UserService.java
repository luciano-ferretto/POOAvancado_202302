package br.edu.atitus.pooavancado.atitusound.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.edu.atitus.pooavancado.atitusound.entities.UserEntity;

public interface UserService extends GenericService<UserEntity>, UserDetailsService{

}
