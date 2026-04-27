package com.qlmaytinh.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity {
	Long id;
	String name;
	String brand;
	String image;
	String description;
	Double price;
	Double discount;
	int quantity;
	int warrantytime;
}
