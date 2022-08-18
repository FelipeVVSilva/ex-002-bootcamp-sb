package com.devsuperior.bds02.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.services.EventService;

@RestController
@RequestMapping("/events")
public class EventResource {

	@Autowired
	private EventService service;
	
	@GetMapping
	public ResponseEntity<Page<EventDTO>> findAll(Pageable Pageable){
		Page<EventDTO> listDto = service.findAll(Pageable);
		return ResponseEntity.ok().body(listDto);
	}
	
	@PostMapping
	public ResponseEntity<EventDTO> insert(@Valid @RequestBody EventDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EventDTO> update(@PathVariable Long id, @Valid @RequestBody EventDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
}
