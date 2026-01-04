package Microservices.OrderService.dto;

import Microservices.OrderService.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OrderRequestDto {

    private Long id;
    private OrderStatus orderStatus;
    private Double price;
    private List<OrderRequestItemDto> items;

}
