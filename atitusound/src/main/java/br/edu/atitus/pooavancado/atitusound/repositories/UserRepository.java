package br.edu.atitus.pooavancado.atitusound.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.edu.atitus.pooavancado.atitusound.entities.UserEntity;

@Repository
public interface UserRepository extends GenericRepository<UserEntity>{

	Optional<UserEntity> findByUsername(String username);
}
