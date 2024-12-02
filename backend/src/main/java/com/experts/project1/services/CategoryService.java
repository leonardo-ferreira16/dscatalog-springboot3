package com.experts.project1.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.experts.project1.dto.CategoryDTO;
import com.experts.project1.entities.Category;
import com.experts.project1.repositories.CategoryRepository;
import com.experts.project1.services.exceptions.DatabaseException;
import com.experts.project1.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
		Page<Category> list = categoryRepository.findAll(pageRequest);
		Page<CategoryDTO> listDto = list.map(x -> new CategoryDTO(x));
		return listDto;
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id){
		Optional<Category> obj = categoryRepository.findById(id);
		Category category = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new CategoryDTO(category);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = categoryRepository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(CategoryDTO dto, Long id) {
		try {
		Category entity = categoryRepository.getReferenceById(id);
		entity.setName(dto.getName());
		entity = categoryRepository.save(entity);
		return new CategoryDTO(entity);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void deleteById(Long id) {
		if(!categoryRepository.existsById(id)) {
			throw new ResourceNotFoundException("Resource not found!");
		}
		try {
			categoryRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial.");
		}
		
	}
}
