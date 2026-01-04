package Microservices.InventoryService.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {
    private Long id;

    private String title;
    private Double price;
    private Integer stock;
}
