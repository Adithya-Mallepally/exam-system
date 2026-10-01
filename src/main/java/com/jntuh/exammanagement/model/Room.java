package com.jntuh.exammanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String building;
    private Integer floor;

    @Column(nullable = false)
    private Integer capacity;

    @Builder.Default
    private boolean hasProjector = false;

    @Builder.Default
    private boolean available = true;
}
