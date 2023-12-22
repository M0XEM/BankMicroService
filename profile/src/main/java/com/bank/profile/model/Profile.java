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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "profile", schema = "profile")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile implements BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "email", length = 264)
    private String email;

    @Column(name = "name_on_card", length = 370)
    private String nameOnCard;

    @Column(name = "inn", unique = true)
    private Long inn;

    @Column(name = "snils", unique = true)
    private Long snils;

    @JoinColumn(name = "passport_id", referencedColumnName = "id", nullable = false)
    @OneToOne
    private Passport passport;

    @JoinColumn(name = "actual_registration_id", referencedColumnName = "id")
    @OneToOne
    private ActualRegistration actualRegistration;

    @Override
    public String getNameEntity() {
        return "Profile";
    }
}