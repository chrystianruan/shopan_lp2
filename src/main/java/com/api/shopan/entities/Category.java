package com.api.shopan.entities;

import com.api.shopan.dtos.CategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    /*
    * Categoria não terá metodo delete, pois geralmente para update e delete se usa cascade. Categoria será usado em produto e produto será usado lá na frente nos pedidos.
    * Como pedidos não pode ser apagado, então categorias também não.
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public CategoryDTO parseToDTO() {
        return new CategoryDTO(this.getName());
    }
}
