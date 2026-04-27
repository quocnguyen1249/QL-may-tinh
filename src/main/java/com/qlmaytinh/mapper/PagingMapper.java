package com.qlmaytinh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.qlmaytinh.dto.request.PagingRequest;
import com.qlmaytinh.dto.response.PagingResponse;

@Mapper
public interface PagingMapper {
	@Mapping(target = "listResult", ignore = true)
    void toPagingResponse(@MappingTarget PagingResponse<?> response,PagingRequest request
    );
}
