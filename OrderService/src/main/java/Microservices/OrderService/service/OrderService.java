// OrderService/src/main/java/Microservices/OrderService/service/OrderService.java
package Microservices.OrderService.service;

import Microservices.OrderService.dto.OrderRequestDto;
import Microservices.OrderService.entity.OrderItem;
import Microservices.OrderService.entity.Orders;
import Microservices.OrderService.repository.OrdersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        Orders order = mapToOrderEntity(orderRequestDto);

        // Establish the bidirectional link
        order.getItems().forEach(item -> item.setOrder(order));

        Orders savedOrder = ordersRepository.save(order);
        return mapToOrderRequestDto(savedOrder);
    }

    private Orders mapToOrderEntity(OrderRequestDto orderRequestDto) {
        Orders order = modelMapper.map(orderRequestDto, Orders.class);
        // ModelMapper can map the nested list of items as well
        return order;
    }

    public List<OrderRequestDto> getAllOrders() {
        List<Orders> orders = ordersRepository.findAll();
        return orders.stream().map(this::mapToOrderRequestDto).toList();
    }

    public OrderRequestDto getOrderById(Long id) {
        Optional<Orders> order = ordersRepository.findById(id);
        return order.map(this::mapToOrderRequestDto)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    private OrderRequestDto mapToOrderRequestDto(Orders orders) {
        return modelMapper.map(orders, OrderRequestDto.class);
    }
}
