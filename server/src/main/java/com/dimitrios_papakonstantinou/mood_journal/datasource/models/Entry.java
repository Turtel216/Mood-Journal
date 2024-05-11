package com.dimitrios_papakonstantinou.mood_journal.datasource.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "entry")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Id
    private Long userId;
    private String text;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Mood mood;
    @Column(nullable = false)
    //Todo refactor to add actual date
    private String entryDate;

}
