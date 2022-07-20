package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ObjectNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repo;
	
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event entity = repo.getOne(id);
			updateData(entity, dto);
			entity = repo.save(entity);
			return new EventDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ObjectNotFoundException("id not found: " + id);
		}
	}
	
	private void updateData(Event entity, EventDTO dto) {
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());
		entity.getCity().setId(dto.getCityId());
	}

}
