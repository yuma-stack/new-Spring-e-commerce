package co.istad.ecommerce.features.category;

import co.istad.ecommerce.features.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false)
    private Boolean isDeleted;

    private String description;
    private String icon;

    //set join (join self relationship)
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    //one category have many product, Use Bidirectional (khang Product kr mean)
    @OneToMany (mappedBy = "category")
    private List<Product> products;

    @OneToMany(mappedBy = "parentCategory",cascade = CascadeType.REMOVE)
    private List<Category> childCategories;
}
