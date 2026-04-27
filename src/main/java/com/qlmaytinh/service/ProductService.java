package com.qlmaytinh.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mapstruct.factory.Mappers;

import com.qlmaytinh.dto.request.ProductCreationRequest;
import com.qlmaytinh.dto.request.ProductUpdateRequest;
import com.qlmaytinh.dto.response.ProductResponse;
import com.qlmaytinh.entity.OrderEntity;
import com.qlmaytinh.entity.ProductEntity;
import com.qlmaytinh.mapper.ProductMapper;
import com.qlmaytinh.paging.Pageble;
import com.qlmaytinh.repository.OrderRepository;
import com.qlmaytinh.repository.ProductRepository;

public class ProductService {

	private ProductRepository productRepository = new ProductRepository();
	private OrderRepository orderRepository =  new OrderRepository();
	private ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

	public ProductResponse findOne(Long id) {
		ProductEntity productEntity = productRepository.findOne(id);
		if (productEntity == null)
			return null;
		return mapper.toProductResponse(productEntity);
	}

	public ProductResponse save(ProductCreationRequest request) {
		if(productRepository.existsByName(request.getName())) 
			throw new RuntimeException("Tên sản phẩm đã tồn tại!");
		Long id = productRepository.save(mapper.toProductEntity(request));
		return mapper.toProductResponse(productRepository.findOne(id));
	}

	public ProductResponse update(ProductUpdateRequest request) {
		ProductEntity productEntity = productRepository.findOne(request.getId());
		mapper.updateProduct(productEntity, request);
		productRepository.update(productEntity);
		return mapper.toProductResponse(productRepository.findOne(request.getId()));
	}
	
	public List<String> findAllBrands() {
		return productRepository.findAllBrands();
	}

	public List<ProductResponse> findAll(String keyword, List<String> brands,
	        Double minPrice, Double maxPrice,
	        Double minDiscount, Double maxDiscount,
	        List<String> filters,
	        Pageble pageble) {

	    boolean isFilter = StringUtils.isNotBlank(keyword)
	            || (brands != null && !brands.isEmpty())
	            || minPrice != null
	            || maxPrice != null
	            || minDiscount != null
	            || maxDiscount != null
	            || (filters != null && !filters.isEmpty());

	    if (!isFilter) {
	        return mapper.toProductResponseList(productRepository.findAll(pageble));
	    }

	    return mapper.toProductResponseList(
	            productRepository.filterProducts(
	                    keyword, brands, minPrice, maxPrice,
	                    minDiscount, maxDiscount,
	                    filters,
	                    pageble));
	}
	public Integer countFilterProducts(String keyword, List<String> brands,
	        Double minPrice, Double maxPrice,
	        Double minDiscount, Double maxDiscount,
	        List<String> filters) {

	    return productRepository.countFilterProducts(
	            keyword, brands, minPrice, maxPrice,
	            minDiscount, maxDiscount,
	            filters);
	}

	public void delete(Long[] ids) {
		for (long id : ids) {
			deleteOrdersByProductId(id);
			productRepository.delete(id);
		}
	}

	private void deleteOrdersByProductId(Long productId) {
	    List<OrderEntity> orders = orderRepository.findByProductId(productId);

	    for (OrderEntity order : orders) {
	        if ("PENDING".equals(order.getStatus())) {
	            ProductEntity product = productRepository.findOne(productId);
	            if (product != null) {
	                product.setQuantity(product.getQuantity() + order.getQuantity());
	                productRepository.update(product);
	            }
	        }
	    }

	    orderRepository.deletesByProductId(productId);
	}

}
