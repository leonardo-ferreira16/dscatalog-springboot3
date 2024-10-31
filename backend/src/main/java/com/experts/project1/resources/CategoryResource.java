package com.experts.project1.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.experts.project1.entities.Category;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	/*
	 * @Autowired
	 * private CategoryService;
	 */
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		List<Category> categories = new ArrayList<>();
		categories.add(new Category(1L, "Books"));
		categories.add(new Category(2L, "Electronics"));
		return ResponseEntity.ok().body(categories);
	}
}
