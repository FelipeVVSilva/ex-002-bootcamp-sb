package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ObjectNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository repo;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
		List<City> list = repo.findAll(Sort.by("name"));
		List<CityDTO> listDto = list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
		return listDto;
	}
	
	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		entity.setName(dto.getName());
		entity = repo.save(entity);
		return new CityDTO(entity);
	}

	public void delete(Long id) {
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Não é possível deletear entidades que tenham relacionamentos");
		}
		catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException("Id not found: " + id);
		}
		
	}

}
