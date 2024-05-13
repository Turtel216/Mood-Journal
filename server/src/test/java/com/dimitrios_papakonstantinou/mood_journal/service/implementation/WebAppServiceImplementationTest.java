package com.dimitrios_papakonstantinou.mood_journal.service.implementation;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.User;
import com.dimitrios_papakonstantinou.mood_journal.datasource.repositories.EntryRepository;
import com.dimitrios_papakonstantinou.mood_journal.datasource.repositories.UserRepository;
import com.dimitrios_papakonstantinou.mood_journal.exceptions.EntryCouldNotBeSavedException;
import com.dimitrios_papakonstantinou.mood_journal.exceptions.EntryIdAndEntryDateNotFoundException;
import com.dimitrios_papakonstantinou.mood_journal.exceptions.UserIdNotFoundException;
import com.dimitrios_papakonstantinou.mood_journal.service.WebAppService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        UserIdNotFoundException exception = assertThrows(UserIdNotFoundException.class, () -> webAppService.saveEntry(entry));
        assertEquals(exception.getMessage(), "Provided entity does not match any user");
    }

    @Test
    void testSaveEntry_CouldNotBeSaved() {
        mock(Entry.class);
        mock(EntryRepository.class);

        Long userId = entry.getUserId();
        Throwable throwable = new Throwable();
        Exception exception = new RuntimeException(throwable.getCause());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(entryRepository.save(entry)).thenThrow(exception);

        EntryCouldNotBeSavedException e = assertThrows(EntryCouldNotBeSavedException.class,
                () -> webAppService.saveEntry(entry));
        assertEquals(e.getMessage(), "The entity could not be saved");
        assertEquals(e.getCause(), exception.getCause());
    }

    //Test case Success
    @Test
    void testGetEntry_Found() {
        mock(Entry.class);
        mock(EntryRepository.class);

        when(entryRepository.findByEntryDateAndUserId("05.05.2024", 1L)).thenReturn(Optional.ofNullable(entry));
        assertThat(webAppService.getEntry(1L, "05.05.2024").equals(entry)).isTrue();
    }

    //Test case Failure
    @Test
    void testGetEntry_NotFound() {
        mock(Entry.class);
        mock(EntryRepository.class);

        when(entryRepository.findByEntryDateAndUserId("05.05.2024", 2L)).thenReturn(Optional.empty());

        EntryIdAndEntryDateNotFoundException exception = assertThrows(EntryIdAndEntryDateNotFoundException.class,
                () -> webAppService.getEntry(2L, "05.05.2024"));
        assertEquals(exception.getMessage(), "Request entry not found");
    }
}