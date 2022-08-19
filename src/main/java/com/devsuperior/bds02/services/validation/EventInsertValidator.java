package com.devsuperior.bds02.services.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.resources.exceptions.FieldMessage;

public class EventInsertValidator implements ConstraintValidator<EventInsertValid, EventDTO>{

	@Override
	public void initialize(EventInsertValid ann) {

	}

	@Override
	public boolean isValid(EventDTO dto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();
		
		if(dto.getDate().isBefore(LocalDate.now())) {
			list.add(new FieldMessage("date", "A data do evento não pode ser passada"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
				.addConstraintViolation();
		}

		return list.isEmpty();
	}

}
