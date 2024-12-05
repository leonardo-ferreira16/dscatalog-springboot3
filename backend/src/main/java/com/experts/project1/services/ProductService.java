package com.experts.project1.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.experts.project1.dto.CategoryDTO;
import com.experts.project1.dto.ProductDTO;
import com.experts.project1.entities.Category;
import com.experts.project1.entities.Product;
import com.experts.project1.repositories.CategoryRepository;
import com.experts.project1.repositories.ProductRepository;
import com.experts.project1.services.exceptions.DatabaseException;
import com.experts.project1.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		Page<Product> list = productRepository.findAll(pageRequest);
		Page<ProductDTO> listDto = list.map(x -> new ProductDTO(x));
		return listDto;
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id){
		Optional<Product> obj = productRepository.findById(id);
		Product product = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(product, product.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		//entity.setName(dto.getName());
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(ProductDTO dto, Long id) {
		try {
			Product entity = productRepository.getReferenceById(id); //instanciando a entidade com o id
			copyDtoToEntity(dto, entity); //copia os dados atualizados
			entity = productRepository.save(entity); //salva a entidade
			return new ProductDTO(entity);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void deleteById(Long id) {
		if(!productRepository.existsById(id)) {
			throw new ResourceNotFoundException("Resource not found!");
		}
		try {
			productRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial.");
		}
		
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();
		for(CategoryDTO catDTO : dto.getCategories()) { //percorrendo as categorias do dto
			Category category = categoryRepository.getReferenceById(catDTO.getId()); //pegando o id de cada categoria sem mexer diretamente no bd
			entity.getCategories().add(category); //adicionando as categorias na entidade
		}
	}
}
