package com.seafood.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "boat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Boat {

    @Id
    @SequenceGenerator(
            name = "boat_sequence",
            sequenceName = "boat_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "boat_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @Column(
            name = "registrationMark",
            nullable = false,
            columnDefinition = "VARCHAR(255)",
            unique = true
    )
    private String registrationMark;

    @Column(
            name = "name",
            columnDefinition = "VARCHAR(255)"
    )
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Seller owner;
}
