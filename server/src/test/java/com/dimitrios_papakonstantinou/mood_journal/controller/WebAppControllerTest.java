package com.dimitrios_papakonstantinou.mood_journal.controller;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.User;
import com.dimitrios_papakonstantinou.mood_journal.exceptions.UserIdNotFoundException;
import com.dimitrios_papakonstantinou.mood_journal.service.implementation.WebAppServiceImplementation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO Implement tests
@WebMvcTest(WebAppController.class)
class WebAppControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WebAppServiceImplementation webAppService;
    Entry entry;
    User user;
    Mood mood;

    @BeforeEach
    void setUp() {
        mood = Mood.GOOD;
        entry = new Entry(1L,1L, "some text", mood, "05.05.2024");
        user = new User(1L, "Alex", "password", "alexis@mail.com");
    }

    @AfterEach
    void tearDown() {
        mood = null;
        entry = null;
        user = null;
    }

    @Test
    void addEntry() throws Exception {
        when(webAppService.saveEntry(entry)).thenReturn("Entry saved successfully");

        this.mockMvc.perform(post("/api/moodjournal/entry", entry))
                .andDo(print()).andExpect(status().isOk());
    }

    //Test case Success
    @Test
    void testGetEntry_Success() throws Exception {
        when(webAppService.getEntry(entry.getUserId(), entry.getEntryDate()))
                .thenReturn(entry);

        this.mockMvc.perform(get("/api/moodjournal/entry/1"))
                .andDo(print()).andExpect(status().isOk());
    }

    //Test case Failure
    @Test
    void testGetEntry_ThrowsException() throws Exception {
        when(webAppService.getEntry(entry.getUserId(), entry.getEntryDate()))
                .thenThrow(UserIdNotFoundException.class);

        this.mockMvc.perform(get("/api/moodjournal/entry/1"))
                .andDo(print()).andExpect(status().isNotFound());
    }
}