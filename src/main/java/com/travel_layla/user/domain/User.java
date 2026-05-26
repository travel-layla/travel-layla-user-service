package com.travel_layla.user.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(schema = "app", name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}