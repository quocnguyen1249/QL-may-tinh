package com.qlmaytinh.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductShortResponse {
    Long id;
    String name;
    String brand;
    Double price;
    Double discount;
    String image;
}
