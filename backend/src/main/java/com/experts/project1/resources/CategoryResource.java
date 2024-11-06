package com.experts.project1.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.experts.project1.dto.CategoryDTO;
import com.experts.project1.entities.Category;
import com.experts.project1.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService categoryService;
	 
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<CategoryDTO> categories = categoryService.findAll();
		return ResponseEntity.ok().body(categories);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		CategoryDTO dto = categoryService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
}
