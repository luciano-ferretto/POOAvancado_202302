package br.edu.atitus.pooavancado.atitusound.servicesimpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.atitus.pooavancado.atitusound.entities.UserEntity;
import br.edu.atitus.pooavancado.atitusound.repositories.GenericRepository;
import br.edu.atitus.pooavancado.atitusound.repositories.UserRepository;
import br.edu.atitus.pooavancado.atitusound.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
		super();
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public GenericRepository<UserEntity> getRepository() {
		return repository;
	}
	
	@Override
	public void validate(UserEntity entidade) throws Exception {
		UserService.super.validate(entidade);
		entidade.setPassword(passwordEncoder.encode(entidade.getPassword()));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username não encontrado!"));
		// var user = repository.findByUsername(username);
		// if (user.isEmpty())
		// throw new UsernameNotFoundException("Username não encontrado!")
		return user;
	}

}
