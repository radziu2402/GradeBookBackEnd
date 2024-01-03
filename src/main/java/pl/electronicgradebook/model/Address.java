package pl.electronicgradebook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "street", nullable = false, length = 50)
    private String street;

    @Column(name = "\"post code\"", nullable = false, length = 10)
    private String postCode;

    @Column(name = "\"street number\"", nullable = false)
    private Integer streetNumber;

    @Column(name = "\"flat number\"", nullable = false)
    private Integer flatNumber;

}