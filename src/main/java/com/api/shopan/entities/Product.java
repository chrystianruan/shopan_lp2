package com.api.shopan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    /*
     * Produto não terá metodo delete, pois geralmente para update e delete se usa cascade. Produto será usado lá na frente nos pedidos.
     * Como pedidos não pode ser apagado, então produtos também não.
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Double unit_price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
