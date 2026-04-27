package com.qlmaytinh.mapper;

import com.qlmaytinh.dto.request.PagingRequest;
import com.qlmaytinh.dto.response.PagingResponse;
import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T14:11:04+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
public class PagingMapperImpl implements PagingMapper {

    @Override
    public void toPagingResponse(PagingResponse<?> response, PagingRequest request) {
        if ( request == null ) {
            return;
        }

        String[] brands = request.getBrands();
        if ( brands != null ) {
            response.setBrands( Arrays.copyOf( brands, brands.length ) );
        }
        else {
            response.setBrands( null );
        }
        String[] filters = request.getFilters();
        if ( filters != null ) {
            response.setFilters( Arrays.copyOf( filters, filters.length ) );
        }
        else {
            response.setFilters( null );
        }
        response.setMaxDiscount( request.getMaxDiscount() );
        response.setMaxPageItem( request.getMaxPageItem() );
        response.setMaxPrice( request.getMaxPrice() );
        response.setMinDiscount( request.getMinDiscount() );
        response.setMinPrice( request.getMinPrice() );
        response.setPage( request.getPage() );
        response.setSortBy( request.getSortBy() );
        response.setSortName( request.getSortName() );
        response.setTotalItem( request.getTotalItem() );
        response.setTotalPage( request.getTotalPage() );
    }
}
