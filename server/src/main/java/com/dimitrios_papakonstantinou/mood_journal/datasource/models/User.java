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

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        if(obj.getClass() != this.getClass())
            return false;

        final User other = (User) obj;

        if((this.id == null) ? (other.id != null) : !this.id.equals(other.id))
            return false;
        if((this.username == null) ? (other.username != null) : !this.username.equals(other.username))
            return false;
        if((this.password == null) ? (other.password != null) : !this.password.equals(other.password))
            return false;
        if((this.email == null) ? (other.email != null) : !this.email.equals(other.email))
            return false;

        return true;
    }
}
