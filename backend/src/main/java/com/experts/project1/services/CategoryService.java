package com.experts.project1.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.experts.project1.dto.CategoryDTO;
import com.experts.project1.entities.Category;
import com.experts.project1.repositories.CategoryRepository;
import com.experts.project1.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = categoryRepository.findAll();
		List<CategoryDTO> listDto = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		return listDto;
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id){
		Optional<Category> obj = categoryRepository.findById(id);
		Category category = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return new CategoryDTO(category);
	}
}
