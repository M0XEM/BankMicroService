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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Time;

@Entity
@Table(name = "atm", schema = "public_bank_information")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AtmEntity implements MainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "address", length = 370, nullable = false)
    String address;

    @Column(name = "start_of_work", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    Time startOfWork;

    @Column(name = "end_of_work", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    Time endOfWork;

    @Column(name = "all_hours", nullable = false)
    Boolean allHours;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    BranchEntity branch;
}
