package uz.pdp.apponlinemagazin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerProduct {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne(optional = false)
    private Products products;

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = true)
    private Double amount;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer customer;

    public CustomerProduct(Products products, Integer count, Double amount, Customer customer) {
        this.products = products;
        this.count = count;
        this.amount = amount;
        this.customer = customer;
    }
}
