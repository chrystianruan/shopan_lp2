package com.api.shopan.entities;

import com.api.shopan.dtos.ProductDTO;
import com.api.shopan.utils.HashUtils;
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
    @Column(name = "unit_price")
    private Double unitPrice;
    @Column(name = "link_img")
    private String linkImg;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;

    public ProductDTO parseToDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setHashId(HashUtils.encodeBase64(this.id.toString()));
        productDTO.setName(this.name);
        productDTO.setDescription(this.description);
        productDTO.setUnitPrice(this.unitPrice);
        productDTO.setLinkImg(this.linkImg);
        productDTO.setCategory(this.category.parseToDTO());

        return productDTO;
    }

}
