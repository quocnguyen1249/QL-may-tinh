package com.qlmaytinh.mapper;

import com.qlmaytinh.dto.request.ProductCreationRequest;
import com.qlmaytinh.dto.request.ProductUpdateRequest;
import com.qlmaytinh.dto.response.ProductResponse;
import com.qlmaytinh.dto.response.ProductShortResponse;
import com.qlmaytinh.entity.ProductEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T14:11:04+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponse toProductResponse(ProductEntity one) {
        if ( one == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.brand( one.getBrand() );
        productResponse.description( one.getDescription() );
        productResponse.discount( one.getDiscount() );
        productResponse.id( one.getId() );
        productResponse.image( one.getImage() );
        productResponse.name( one.getName() );
        productResponse.price( one.getPrice() );
        productResponse.quantity( one.getQuantity() );
        productResponse.warrantytime( one.getWarrantytime() );

        return productResponse.build();
    }

    @Override
    public ProductEntity toProductEntity(ProductCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        ProductEntity.ProductEntityBuilder productEntity = ProductEntity.builder();

        productEntity.brand( request.getBrand() );
        productEntity.description( request.getDescription() );
        productEntity.discount( request.getDiscount() );
        productEntity.image( request.getImage() );
        productEntity.name( request.getName() );
        productEntity.price( request.getPrice() );
        productEntity.quantity( request.getQuantity() );
        productEntity.warrantytime( request.getWarrantytime() );

        return productEntity.build();
    }

    @Override
    public void updateProduct(ProductEntity productEntity, ProductUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        productEntity.setBrand( request.getBrand() );
        productEntity.setDescription( request.getDescription() );
        productEntity.setDiscount( request.getDiscount() );
        productEntity.setId( request.getId() );
        productEntity.setImage( request.getImage() );
        productEntity.setName( request.getName() );
        productEntity.setPrice( request.getPrice() );
        productEntity.setQuantity( request.getQuantity() );
        productEntity.setWarrantytime( request.getWarrantytime() );
    }

    @Override
    public List<ProductResponse> toProductResponseList(List<ProductEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProductResponse> list = new ArrayList<ProductResponse>( entities.size() );
        for ( ProductEntity productEntity : entities ) {
            list.add( toProductResponse( productEntity ) );
        }

        return list;
    }

    @Override
    public ProductShortResponse toProductShortResponse(ProductEntity product) {
        if ( product == null ) {
            return null;
        }

        ProductShortResponse.ProductShortResponseBuilder productShortResponse = ProductShortResponse.builder();

        productShortResponse.brand( product.getBrand() );
        productShortResponse.discount( product.getDiscount() );
        productShortResponse.id( product.getId() );
        productShortResponse.image( product.getImage() );
        productShortResponse.name( product.getName() );
        productShortResponse.price( product.getPrice() );

        return productShortResponse.build();
    }
}
