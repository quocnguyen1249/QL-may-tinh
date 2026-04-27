package com.qlmaytinh.dto.request;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
public class ProductUpdateRequest {

    Long id;

    @NotBlank(message = "Tên sản phẩm không hợp lệ")
    String name;

    @NotBlank(message = "Thương hiệu không hợp lệ")
    String brand;

    @NotBlank(message = "Hình ảnh không hợp lệ")
    String image;

    String description;

    @NotNull(message = "Giá sản phẩm không hợp lệ")
    @Positive(message = "Giá sản phẩm phải lớn hơn 0")
    Double price;

    @NotNull(message = "Số lượng không hợp lệ")
    @Positive(message = "Số lượng không hợp lệ")
    int quantity;

    @NotNull(message = "Thời gian bảo không hợp lệ")
    @Positive(message = "Thời gian bảo hành không hợp lệ")
    int warrantytime;

    @DecimalMin(value = "0.0", message = "Giảm giá phải >= 0")
    @DecimalMax(value = "1.0", message = "Giảm giá phải <= 1 (tối đa 100%)")
    Double discount;
}
