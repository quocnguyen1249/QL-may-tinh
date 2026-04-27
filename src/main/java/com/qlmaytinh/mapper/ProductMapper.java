package com.qlmaytinh.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.qlmaytinh.dto.request.ProductCreationRequest;
import com.qlmaytinh.dto.request.ProductUpdateRequest;
import com.qlmaytinh.dto.response.ProductResponse;
import com.qlmaytinh.dto.response.ProductShortResponse;
import com.qlmaytinh.entity.ProductEntity;

@Mapper
public interface ProductMapper {
	
	ProductResponse toProductResponse(ProductEntity one);
	@Mapping(target = "id", ignore = true)
	ProductEntity toProductEntity(ProductCreationRequest request);

	void updateProduct(@MappingTarget ProductEntity productEntity, ProductUpdateRequest request);
	List<ProductResponse> toProductResponseList(List<ProductEntity> entities);
	
	ProductShortResponse toProductShortResponse(ProductEntity product);

}
