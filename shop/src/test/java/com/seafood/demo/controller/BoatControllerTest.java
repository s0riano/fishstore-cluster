/*
package com.seafood.demo.controller;

import com.seafood.demo.model.entity.Boat;
import com.seafood.demo.service.BoatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BoatControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BoatService boatService;

    @InjectMocks
    private BoatController boatController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(boatController).build();
    }

    @Test
    public void testUpdateBoat_BoatExists() throws Exception {
        Boat existingBoat = new Boat();
        existingBoat.setId(1L);
        existingBoat.setRegistrationMark("ABC123");
        existingBoat.setName("Old Boat");

        when(boatService.getBoatById(1L)).thenReturn(Optional.of(existingBoat));

        mockMvc.perform(put("/api/boats/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"registrationMark\":\"XYZ789\",\"name\":\"New Boat\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registrationMark").value("XYZ789"))
                .andExpect(jsonPath("$.name").value("New Boat"));

        verify(boatService, times(1)).saveBoat(any(Boat.class));
    }

    @Test
    public void testUpdateBoat_BoatDoesNotExist() throws Exception {
        when(boatService.getBoatById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/boats/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"registrationMark\":\"XYZ789\",\"name\":\"New Boat\"}"))
                .andExpect(status().isNotFound());

        verify(boatService, never()).saveBoat(any(Boat.class));
    }
}
*/

