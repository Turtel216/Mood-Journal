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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    Mood mood; //default mood, used in most tests
    Entry entry; //default entry, used for most tests
    // entry 1-4, used for tests that require a list of entries
    Entry entry1;
    Entry entry2;
    Entry entry3;
    Entry entry4;
    User user; //default user, used in most tests
    List<Entry> entryList;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        webAppService = new WebAppServiceImplementation(entryRepository, userRepository);

        mood = Mood.GOOD;

        entry = new Entry(1L, 1L, "some text", mood, "05.05.2024");

        entry1 = new Entry(1L, 1L, "some text", Mood.GOOD, "05.05.2024");
        entry2 = new Entry(1L, 2L, "some text", Mood.GOOD, "05.05.2024");
        entry3 = new Entry(1L, 3L, "some text", Mood.MEH, "05.05.2024");
        entry4 = new Entry(1L, 4L, "some text", Mood.HORRIBLE, "05.05.2024");

        entryList = List.of(entry1, entry2, entry3, entry4);

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

        assertThat(webAppService.saveEntry(entry)).isEqualTo("Entry saved successfully");
    }

    // Test case failure, User not found
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

    // Test case failure, couldn't save entry
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

    //Test case Success
    @Test
    void testGetUser_Found() {
        mock(User.class);
        mock(UserRepository.class);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThat(webAppService.getUser(user.getId()).equals(user)).isTrue();
    }

    //Test case Failure
    @Test
    void testGetUser_NotFound() {
        mock(User.class);
        mock(UserRepository.class);

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        UserIdNotFoundException exception = assertThrows(UserIdNotFoundException.class,
                () -> webAppService.getUser(user.getId()));

        assertEquals(exception.getMessage(), "Provided user id does not match any user in the data base");
    }

    //Test case Success
    @Test
    void testGetMood_Found() {
        mock(Entry.class);
        mock(EntryRepository.class);

        List<Entry> entries = List.of(entry);

        when(entryRepository.findByUserId(user.getId())).thenReturn(Optional.of(entries));

        assertThat(webAppService.getMood(user.getId())).isEqualTo(entries.stream().map(Entry::getMood).collect(Collectors.toList()));
    }

    //Test case user not found
    @Test
    void testGetMood_UserNotFound() {
        mock(Entry.class);
        mock(EntryRepository.class);

        List<Entry> entries = List.of(entry);

        when(entryRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        UserIdNotFoundException exception = assertThrows(UserIdNotFoundException.class,
                () -> webAppService.getMood(user.getId()));
        assertEquals(exception.getMessage(), "Provided user id does not match any mood entry in the data base");
    }

    // Test case Success
    @Test
    void testGetMean_Success() {
        mock(Entry.class);
        mock(EntryRepository.class);

        when(entryRepository.findByUserId(user.getId())).thenReturn(Optional.of(entryList));

        assertThat(webAppService.getMean(user.getId()).equals(Mood.MEH)).isTrue();
    }

    // Test case Success
    @Test
    void testGetMean_UserNotFound() {
        mock(Entry.class);
        mock(EntryRepository.class);

        when(entryRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        UserIdNotFoundException exception = assertThrows(UserIdNotFoundException.class,
                () -> webAppService.getMean(user.getId()));
        assertEquals(exception.getMessage(), "Provided user id does not match any user in the data base");
    }

    // Test case Success
    @Test
    void testGetMode_Success() {
        mock(Entry.class);
        mock(EntryRepository.class);

        when(entryRepository.findByUserId(user.getId())).thenReturn(Optional.of(entryList));

        assertThat(webAppService.getMode(user.getId()).equals(Mood.GOOD)).isTrue();
    }

    // Test case Failure
    @Test
    void testGetMode_UserNotFound() {
        mock(Entry.class);
        mock(EntryRepository.class);

        when(entryRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        UserIdNotFoundException exception = assertThrows(UserIdNotFoundException.class,
                () -> webAppService.getMode(user.getId()));
        assertEquals(exception.getMessage(), "Provided user id does not match any user in the data base");
    }

    // Test case Success
    @Test
    void testGetMedian_Success() {
        mock(Entry.class);
        mock(EntryRepository.class);

        when(entryRepository.findByUserId(user.getId())).thenReturn(Optional.of(entryList));

        assertThat(webAppService.getMedian(user.getId()).equals(Mood.GOOD)).isTrue();
    }

    // Test case Failure
    @Test
    void testGetMedian_UserNotFound() {
        mock(Entry.class);
        mock(EntryRepository.class);

        when(entryRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        UserIdNotFoundException exception = assertThrows(UserIdNotFoundException.class,
                () -> webAppService.getMedian(user.getId()));
        assertEquals(exception.getMessage(), "Provided user id does not match any user in the data base");
    }
}