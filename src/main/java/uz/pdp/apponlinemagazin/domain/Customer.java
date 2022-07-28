package uz.pdp.apponlinemagazin.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import uz.pdp.apponlinemagazin.domain.enums.CustomerStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String telegramNumber;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    private boolean paymentType;

    @Column(nullable = false)
    private Long orderTime  = new Timestamp(System.currentTimeMillis()).getTime();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerProduct> customerProducts;

    public Customer(String fullName, String phoneNumber, String telegramNumber, CustomerStatus status, boolean paymentType) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.telegramNumber = telegramNumber;
        this.status = status;
        this.paymentType = paymentType;
    }
}
