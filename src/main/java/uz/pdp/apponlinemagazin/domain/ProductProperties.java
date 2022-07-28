package uz.pdp.apponlinemagazin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String key;

    @Column(nullable = false)
    private String value;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Products product;

    public ProductProperties(String key, String value, Products product) {
        this.key = key;
        this.value = value;
        this.product = product;
    }
}
