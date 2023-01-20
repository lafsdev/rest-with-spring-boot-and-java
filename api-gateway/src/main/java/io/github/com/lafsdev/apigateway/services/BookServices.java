package io.github.com.lafsdev.apigateway.services;

import io.github.com.lafsdev.apigateway.controller.BookController;
import io.github.com.lafsdev.apigateway.data.vo.v1.BookVO;
import io.github.com.lafsdev.apigateway.exception.RequiredObjectIsNullException;
import io.github.com.lafsdev.apigateway.exception.ResourceNotFoundException;
import io.github.com.lafsdev.apigateway.mapper.DozerMapper;
import io.github.com.lafsdev.apigateway.model.Book;
import io.github.com.lafsdev.apigateway.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class BookServices {

    private Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repository;

    public List<BookVO> findAll() {

        logger.info("Finding all book!");

        var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
        books
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
        return books;
    }

    public BookVO findById(Long id) {

        logger.info("Finding one book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO book) {

        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one book!");
        var entity = DozerMapper.parseObject(book, Book.class);
        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) {

        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one book!");

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {

        logger.info("Deleting one book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}
