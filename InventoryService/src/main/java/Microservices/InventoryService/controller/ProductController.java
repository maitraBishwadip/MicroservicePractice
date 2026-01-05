package Microservices.InventoryService.controller;

import Microservices.InventoryService.clients.OrdersClient;
import Microservices.InventoryService.dto.OrderRequestDto;
import Microservices.InventoryService.dto.OrderRequestItemDto;
import Microservices.InventoryService.dto.ProductDto;
import Microservices.InventoryService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final OrdersClient ordersClient;


    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));


    }

    @GetMapping("/fetchOrders")

        public String fetchFromOrdersService()
        {
//            ServiceInstance orderService = discoveryClient.getInstances("OrderService").getLast();
//
//          return   restClient.get()
//                    .uri(orderService.getUri()+"/api/v1/orders/helloOrders")
//                    .retrieve()
//                    .body(String.class);

            return ordersClient.helloOrders();



        }
        @PutMapping("reduce-stock")
       public ResponseEntity<Double> reduceStock(@RequestBody OrderRequestDto orderRequestDto)
        {
            Double totalPrice = productService.reduceStocks(orderRequestDto);
            return ResponseEntity.ok(totalPrice);
        }


}