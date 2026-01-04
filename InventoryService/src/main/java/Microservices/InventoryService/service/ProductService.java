package Microservices.InventoryService.service;

import Microservices.InventoryService.dto.ProductDto;
import Microservices.InventoryService.entity.ProductEntity;
import Microservices.InventoryService.repository.ProductRepository;
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

}
