package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.UserDTO;
import com.devsuperior.bds02.entities.User;
import com.devsuperior.bds02.repositories.UserRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ObjectNotFoundException;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository repo;
	
	@Transactional(readOnly = true)
	public List<UserDTO> findAll() {
		List<User> list = repo.findAll(Sort.by("name"));
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return listDto;
	}
	
	@Transactional
	public UserDTO insert(UserDTO dto) {
		User entity = new User();
		entity.setName(dto.getName());
		entity = repo.save(entity);
		return new UserDTO(entity);
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

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = repo.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException("Email not found: " + email);
		}
		
		return user;
	}

}
