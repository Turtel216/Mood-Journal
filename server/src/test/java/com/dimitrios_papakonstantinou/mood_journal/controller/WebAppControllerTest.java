package com.dimitrios_papakonstantinou.mood_journal.controller;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.User;
import com.dimitrios_papakonstantinou.mood_journal.service.WebAppService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebAppController.class)
class WebAppControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WebAppService webAppService;
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
    }

    @Test
    void addEntry() {
    }

    //Test case Success
    @Test
    void testGetEntry_Success() throws Exception {
        when(webAppService.getEntry(entry.getUserId(), entry.getEntryDate())).thenReturn(entry);

        this.mockMvc.perform(get("/api/moodjournal/entry/1"))
                .andDo(print()).andExpect(status().isOk());
    }
}