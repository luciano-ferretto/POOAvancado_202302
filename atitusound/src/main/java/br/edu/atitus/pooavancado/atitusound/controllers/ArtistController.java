package br.edu.atitus.pooavancado.atitusound.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.pooavancado.atitusound.entities.ArtistEntity;
import br.edu.atitus.pooavancado.atitusound.entities.dtos.ArtistDTO;
import br.edu.atitus.pooavancado.atitusound.services.ArtistService;

@RestController
@RequestMapping("/artists")
public class ArtistController {
	
	//Spring, Injeção de Dependências
	//Aqui o Spring Framework é responsável pela implementação, 
	//instanciação e injeção dentro do objeto ArtistController
	
	//@Autowired >> Spring recomenda utilizar métodos construtores
	
	private ArtistService artistService;
	
	public ArtistController(ArtistService artistService) {
		super();
		this.artistService = artistService;
	}
	
	protected ArtistEntity convertDTO2Entity(ArtistDTO dto) {
		ArtistEntity entidade = new ArtistEntity();
		entidade.setName(dto.getName());
		entidade.setImage(dto.getImage());
		return entidade;
	}
	
	@DeleteMapping("/{uuid}")
	public ResponseEntity<?> delete(@PathVariable UUID uuid) {
		try {
			artistService.deleteById(uuid);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{uuid}")
	public ResponseEntity<ArtistEntity> put(@PathVariable UUID uuid, @RequestBody ArtistDTO dto) {
		ArtistEntity entidade = convertDTO2Entity(dto);
		entidade.setUuid(uuid);
		try {
			artistService.save(entidade);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		return ResponseEntity.ok(entidade);
	}
	
	@GetMapping("/{uuid}")
	public ResponseEntity<ArtistEntity> getByUuid(@PathVariable UUID uuid) {
		Optional<ArtistEntity> entidade;
		try {
			entidade = artistService.findById(uuid);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		if (entidade.isEmpty())
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(entidade.get());
	}
	
	@GetMapping
	public ResponseEntity<Page<List<ArtistEntity>>> getAll(@PageableDefault(page = 0, size = 10, sort = "name", direction = Direction.ASC) Pageable pageable,
							@RequestParam String name) {
		Page<List<ArtistEntity>> entidades;
		try {
			entidades = artistService.findByNameContainingIgnoreCase(pageable, name);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		return ResponseEntity.ok(entidades);
	}

	@PostMapping
	public ResponseEntity<ArtistEntity> save(@RequestBody ArtistDTO dto) {
		ArtistEntity entidade = convertDTO2Entity(dto);
		try {
			artistService.save(entidade);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(entidade);
	}

}
