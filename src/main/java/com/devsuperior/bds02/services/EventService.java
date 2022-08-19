package com.devsuperior.bds02.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ObjectNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repo;
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional(readOnly = true)
	public Page<EventDTO> findAll(Pageable Pageable) {
		Page<Event> list = repo.findAll(Pageable);
		Page<EventDTO> listDto = list.map(x -> new EventDTO(x));
		return listDto;
	}
	
	@Transactional
	public EventDTO insert(EventDTO dto) {
		Event entity = new Event();
		updateData(entity, dto);
		entity = repo.save(entity);
		return new EventDTO(entity);
	}
	
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
		
		Optional<City> obj = cityRepository.findById(dto.getCityId());
		
		City city = obj.get();
		
		entity.setCity(city);
	}

}
