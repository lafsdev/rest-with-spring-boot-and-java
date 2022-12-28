package io.github.com.lafsdev.apigateway.services;

import io.github.com.lafsdev.apigateway.exception.ResourceNotFoundException;
import io.github.com.lafsdev.apigateway.model.PersonVO;
import io.github.com.lafsdev.apigateway.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonVO> findAll() {

        logger.info("Finding all people!");

        return repository.findAll();
    }

    public PersonVO findById(Long id) {

        logger.info("Finding one person!");

        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
    }

    public PersonVO create(PersonVO personVO) {

        logger.info("Creating one person!");

        return repository.save(personVO);
    }

    public PersonVO update(PersonVO personVO) {

        logger.info("Updating one person!");

        PersonVO entity = repository.findById(personVO.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(personVO.getFirstName());
        entity.setLastName(personVO.getLastName());
        entity.setAddress(personVO.getAddress());
        entity.setGender(personVO.getGender());

        return repository.save(entity);
    }

    public void delete(Long id) {

        logger.info("Deleting one person!");
        PersonVO entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }
}
