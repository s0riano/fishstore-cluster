package com.fishstore.fishstoreservice.model.entity;

import com.fishstore.fishstoreservice.model.dto.SellerRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    @SequenceGenerator(
            name = "seller_sequence",
            sequenceName = "seller_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "seller_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "seller_id")
    private Long sellerId;  // or Long ownerId; depending on the identifier type

    public Long getSellerId() {
        return this.sellerId;
    }

}

