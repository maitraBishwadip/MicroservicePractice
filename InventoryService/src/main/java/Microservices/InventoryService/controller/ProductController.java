package Microservices.InventoryService.controller;

import Microservices.InventoryService.dto.ProductDto;
import Microservices.InventoryService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;


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
            ServiceInstance orderService = discoveryClient.getInstances("OrderService").getLast();

          return   restClient.get()
                    .uri(orderService.getUri()+"/api/v1/orders/helloOrders")
                    .retrieve()
                    .body(String.class);



        }

}