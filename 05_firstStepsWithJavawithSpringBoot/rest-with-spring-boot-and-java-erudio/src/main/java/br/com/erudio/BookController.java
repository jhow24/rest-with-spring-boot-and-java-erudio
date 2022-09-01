package br.com.erudio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.services.BookServices;
import br.com.erudio.util.MediaType;

@RestController
@RequestMapping("/api/book/v1") 
public class BookController {
	
	@Autowired //ele faz o trabalho do new BookSercie();
	private BookServices service;
	//private BookServices service = new BookService();
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON, 
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML
			})
	public List<BookVO> findAll() {
			return service.findAll();
	}
	
	@GetMapping(value = "/{id}",
			produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML})
	public BookVO findById(@PathVariable(value = "id")Long id) {
		return service.findById(id);
	}
	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML},
			produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML})
	public BookVO create(@RequestBody BookVO book) {
			return service.create(book);
	}
	
	@PutMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML},
			produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML})
	public BookVO update(@RequestBody BookVO book) {
			return service.update(book);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id")Long id) {
			service.delete(id);
			return ResponseEntity.noContent().build();
	}
	
}
