package Microservices.OrderService.dto;

import Microservices.OrderService.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestItemDto {
    private Long id;
    private Long productId;
    private Integer quantity;


}
