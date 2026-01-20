// OrderService/src/main/java/Microservices/OrderService/service/OrderService.java
package Microservices.OrderService.service;

import Microservices.OrderService.clients.InventoryOpenFeignClient;
import Microservices.OrderService.dto.OrderRequestDto;
import Microservices.OrderService.entity.OrderItem;
import Microservices.OrderService.entity.OrderStatus;
import Microservices.OrderService.entity.Orders;
import Microservices.OrderService.repository.OrdersRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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
    private final InventoryOpenFeignClient FeignClient;


    @Transactional
   // @Retry(name= "createOrder", fallbackMethod = "createOrderFallback")
    @CircuitBreaker(name= "createOrder", fallbackMethod = "createOrderFallback")
    @RateLimiter(name= "createOrder", fallbackMethod = "createOrderFallback")
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        Double totalPrice = FeignClient.totalCartPrice(orderRequestDto);

       Orders orders = modelMapper.map(orderRequestDto, Orders.class);
       for(OrderItem orderItem: orders.getItems()){
           orderItem.setOrder(orders);

       }
       orders.setPrice(totalPrice);
       orders.setOrderStatus(OrderStatus.ORDER_ADDED);
       Orders placedOrder =  ordersRepository.save(orders);
       return modelMapper.map(placedOrder, OrderRequestDto.class);

    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto, Throwable throwable)
    {
        log.error("Fallback Occurred Due to : {}",throwable.getMessage());
        return new OrderRequestDto();

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
