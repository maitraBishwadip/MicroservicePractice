package Microservices.OrderService.clients;

import Microservices.OrderService.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="InventoryService",path="/api/v1/products")
public interface InventoryOpenFeignClient {
    @PutMapping("/reduce-stock")
    Double totalCartPrice(@RequestBody OrderRequestDto orderRequestDto);
}
