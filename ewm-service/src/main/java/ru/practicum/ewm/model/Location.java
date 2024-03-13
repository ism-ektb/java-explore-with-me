package ru.practicum.ewm.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "locations")
@NoArgsConstructor
@Getter
@SuperBuilder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Float lat;
    private Float lon;
}
