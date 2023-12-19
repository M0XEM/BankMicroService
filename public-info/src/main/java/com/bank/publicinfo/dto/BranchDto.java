package com.bank.publicinfo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class BranchDto {
    Long id;

    String address;

    Long phoneNumber;

    String city;

    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    Time startOfWork;

    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    Time endOfWork;
}
