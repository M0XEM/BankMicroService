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
import java.sql.Timestamp;

@Entity
@Table(name = "passport", schema = "profile")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Passport implements BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "series", nullable = false)
    private Integer series;

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "gender", length = 3, nullable = false)
    private String gender;

    @Column(name = "birth_date", nullable = false)
    private Timestamp birthDate;

    @Column(name = "birth_place", length = 480, nullable = false)
    private String birthPlace;

    @Column(name = "issued_by", nullable = false)
    private String issuedBy;

    @Column(name = "date_of_issue", nullable = false)
    private Timestamp dateOfIssue;

    @Column(name = "division_code", nullable = false)
    private Integer divisionCode;

    @Column(name = "expiration_date")
    private Timestamp expirationDate;

    @OneToOne
    @JoinColumn(name = "registration_id", referencedColumnName = "id")
    private Registration registrationId;

    @Override
    public String getNameEntity() {
        return "passport";
    }
}
