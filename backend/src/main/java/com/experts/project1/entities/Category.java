package com.experts.project1.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_category")
public class Category implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	//Para que o id seja autoincrementado:
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")//Armazenando no BD no padrão UTC, sem especificação de timezone 
	private Instant createdAt; //Instante em que foi criado
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")//Armazenando no BD no padrão UTC, sem especificação de timezone 
	private Instant updatedAt; //Instante em que foi atualizado
	
	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Category() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}
	
	//Macete para, sempre que uma categoria for salva, o instante atual será armazenado no createdAt.
	//Quando for atualizado, será armazenado o instante atual no updatedAt.
	
	@PrePersist //Sempre que for chamado um save do JPA, o método será executado
	public void prePersist() {
		createdAt = Instant.now();
	}
	
	@PreUpdate //Sempre que der um save para atualizar, será chamado o método
	public void preUpdate() {
		updatedAt = Instant.now();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
}
