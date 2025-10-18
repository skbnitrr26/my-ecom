package com.sumitcoder.model;

import java.util.Set;

import com.sumitcoder.domain.HomeCategorySection;
import com.sumitcoder.domain.PaymentMethod;
import com.sumitcoder.domain.PaymentOrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomeCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String image;
	
	private String categoryId;
	
	private HomeCategorySection section;
}
