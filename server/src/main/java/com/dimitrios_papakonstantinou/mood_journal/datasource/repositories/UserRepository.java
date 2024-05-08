package com.dimitrios_papakonstantinou.mood_journal.datasource.repositories;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
