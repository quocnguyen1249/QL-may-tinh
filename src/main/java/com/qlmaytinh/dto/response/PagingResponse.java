package com.qlmaytinh.dto.response;

import java.util.List;

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
public class PagingResponse<T> {
	List<T> listResult;
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
	String[] brands;
	String[] filters;
}
