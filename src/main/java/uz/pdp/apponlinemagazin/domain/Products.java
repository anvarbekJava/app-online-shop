package uz.pdp.apponlinemagazin.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Category category;

    //Product olchov birligi
    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private Double cashPrice;

    @OneToOne
    private Attachment attachment;

    @Column(nullable = false)
    private Double transferPrice;

    @Column(nullable = false)
    private boolean enabled = true;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductProperties> productProperties;

    public Products(String name, Category category, String size, Double cashPrice, Double transferPrice) {
        this.name = name;
        this.category = category;
        this.size = size;
        this.cashPrice = cashPrice;
        this.transferPrice = transferPrice;
    }
}
