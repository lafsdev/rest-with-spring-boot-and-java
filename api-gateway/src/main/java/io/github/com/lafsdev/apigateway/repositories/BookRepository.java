package io.github.com.lafsdev.apigateway.repositories;

import io.github.com.lafsdev.apigateway.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
