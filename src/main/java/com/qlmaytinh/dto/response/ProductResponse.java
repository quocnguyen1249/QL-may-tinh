package com.qlmaytinh.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
	Long id;
	String name;
	String brand;
	String description;
	String image;
	Double price;
	Double discount;
	int quantity;
	int warrantytime;
}
