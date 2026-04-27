package com.qlmaytinh.dto.request;

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
public class OrderCreateRequest {
	Long userId;
    Long productId;
    @NotNull(message = "Số lượng không hợp lệ")
    @Positive(message = "Số lượng không hợp lệ")
    Integer quantity;
}
