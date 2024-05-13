package com.dimitrios_papakonstantinou.mood_journal.datasource.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String email;

    public boolean equals(User user) {
        assert user != null;
        return user.getId().equals(id)
                && user.getUsername().equals(username)
                && user.getPassword().equals(password)
                && user.getEmail().equals(email);

    }
}
