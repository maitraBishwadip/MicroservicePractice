package Microservices.InventoryService.service;

import Microservices.InventoryService.dto.OrderRequestDto;
import Microservices.InventoryService.dto.OrderRequestItemDto;
import Microservices.InventoryService.dto.ProductDto;
import Microservices.InventoryService.entity.ProductEntity;
import Microservices.InventoryService.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor

public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllProducts() {
        List<ProductEntity> inventories = productRepository.findAll();
        return inventories.stream().map(this::mapToProductDto).toList();

    }

    public ProductDto getProductById(Long id)
    {
        Optional<ProductEntity> inventory = productRepository.findById(id);
        return inventory.map(this::mapToProductDto)
        .orElseThrow(() -> new RuntimeException("Product not found"));

    }

    private ProductDto mapToProductDto(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDto.class);
    }

    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {

        Double totalPrice= 0.0;

        for(OrderRequestItemDto item: orderRequestDto.getItems())
        {
            Long productId = item.getProductId();
            Integer quantity = item.getQuantity();

            ProductEntity product = productRepository.findById(productId).orElseThrow(()->
                    new RuntimeException("Product not found"));
            if(product.getStock() < quantity){
                throw new RuntimeException("Insufficient stock for product:");
            }

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            totalPrice += product.getPrice() * quantity;

        }
        return totalPrice;

    }
}
