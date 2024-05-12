package com.dimitrios_papakonstantinou.mood_journal.service.implementation;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.User;
import com.dimitrios_papakonstantinou.mood_journal.datasource.repositories.EntryRepository;
import com.dimitrios_papakonstantinou.mood_journal.datasource.repositories.UserRepository;
import com.dimitrios_papakonstantinou.mood_journal.service.WebAppService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WebAppServiceImplementationTest {

    @Mock
    private EntryRepository entryRepository;
    @Mock
    private UserRepository userRepository;
    private WebAppService webAppService;
    AutoCloseable autoCloseable;
    Entry entry;
    Mood mood;
    User user;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        webAppService = new WebAppServiceImplementation(entryRepository, userRepository);

        mood = Mood.GOOD;
        entry = new Entry(1L, 1L, "some text", mood, "05.05.2024");

        user = new User(1L, "stelios", "password", "email@mail.com");
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    // Test case Success
    @Test
    void testSaveEntry_Found() {
        mock(Entry.class);
        mock(EntryRepository.class);

        Long userId = entry.getUserId();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(entryRepository.save(entry)).thenReturn(entry);

        assertThat(webAppService.saveEntry(entry)).isEqualTo("Success");
    }

    // Test case User not found
    @Test
    void testSaveEntry_UserNotFound() {
        mock(Entry.class);
        mock(EntryRepository.class);

        Long userId = 2L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(entryRepository.save(entry)).thenReturn(entry);

        assertThat(webAppService.saveEntry(entry)).isEqualTo("User ID not found");
    }

    //TODO
    //Test case Success
    @Test
    void testGetEntry_Found() {
    }
}