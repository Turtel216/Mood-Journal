package com.dimitrios_papakonstantinou.mood_journal.service.implementation;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;
import com.dimitrios_papakonstantinou.mood_journal.datasource.repositories.EntryRepository;
import com.dimitrios_papakonstantinou.mood_journal.datasource.repositories.UserRepository;
import com.dimitrios_papakonstantinou.mood_journal.service.WebAppService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class WebAppServiceImplementationTest {

    @Mock
    private EntryRepository entryRepository;
    @Mock
    private UserRepository userRepository;
    private WebAppService webAppService;
    AutoCloseable autoCloseable;
    Entry entry;
    Mood mood;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        webAppService = new WebAppServiceImplementation(entryRepository, userRepository);
        mood = Mood.GOOD;
        entry = new Entry(1L, 1L, "some text", mood, "05.05.2024");
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void saveEntry() {
    }

    @Test
    void getEntry() {
    }
}