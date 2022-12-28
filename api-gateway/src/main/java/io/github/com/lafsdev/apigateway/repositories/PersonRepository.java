package io.github.com.lafsdev.apigateway.repositories;

import io.github.com.lafsdev.apigateway.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
