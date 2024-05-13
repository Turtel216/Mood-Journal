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
    @Column(nullable = false)
    private Long userId;
    private String text;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Mood mood;
    @Column(nullable = false)
    //Todo refactor to add actual date
    private String entryDate;

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        if(obj.getClass() != this.getClass())
            return false;

        final Entry other = (Entry) obj;

        if((this.id == null) ? (other.id != null) : !this.id.equals(other.id))
            return false;
        if((this.userId == null) ? (other.userId != null) : !this.userId.equals(other.userId))
            return false;
        if((this.text == null) ? (other.text != null) : !this.text.equals(other.text))
            return false;
        if((this.mood == null) ? (other.mood != null) : !this.mood.equals(other.mood))
            return false;
        if((this.entryDate == null) ? (other.entryDate != null) : !this.entryDate.equals(other.entryDate))
            return false;

        return true;
    }
}
