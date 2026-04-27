package com.qlmaytinh.dto.request;

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
public class PagingRequest {
	Long id;
	Integer page;
	Integer maxPageItem;
	Integer totalPage;
	Integer totalItem;
	String sortName;
	String sortBy;
	Double minPrice; 
	Double maxPrice;
	Double minDiscount;
	Double maxDiscount;
	Long[] ids;
	String[] brands;
	String[] filters;
	String keyword;
}
