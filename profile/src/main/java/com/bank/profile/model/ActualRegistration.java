package com.bank.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "actual_registration", schema = "profile")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActualRegistration implements BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country", length = 40, nullable = false)
    private String country;

    @Column(name = "region", length = 160)
    private String region;

    @Column(name = "city", length = 160)
    private String city;

    @Column(name = "district", length = 160)
    private String district;

    @Column(name = "locality", length = 230)
    private String locality;

    @Column(name = "street", length = 230)
    private String street;

    @Column(name = "house_number", length = 20)
    private String houseNumber;

    @Column(name = "house_block", length = 20)
    private String houseBlock;

    @Column(name = "flat_number", length = 40)
    private String flatNumber;

    @Column(name = "index", nullable = false)
    private Long index;

    @Override
    public String getNameEntity() {
        return "ActualRegistration";
    }
}
