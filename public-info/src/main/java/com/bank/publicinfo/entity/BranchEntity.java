package com.bank.publicinfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;

@Entity
@Table(name = "branch", schema = "public_bank_information")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchEntity implements MainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "address", length = 370, nullable = false)
    String address;

    @Column(name = "phone_number", unique = true, nullable = false)
    Long phoneNumber;

    @Column(name = "city", length = 250, nullable = false)
    String city;

    @Column(name = "start_of_work", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    Time startOfWork;

    @Column(name = "end_of_work", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    Time endOfWork;
}
