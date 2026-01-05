package Microservices.InventoryService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name="OrderService",path="/api/v1/orders")
public interface OrdersClient {
    @GetMapping("/helloOrders")
    String helloOrders();

}
