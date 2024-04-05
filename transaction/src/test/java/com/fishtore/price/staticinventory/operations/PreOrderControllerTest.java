package com.fishtore.price.staticinventory.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishtore.transaction.operations.PreOrderControllerr;
import com.fishtore.transaction.transaction.TransactionService;
import dto.TransactionItemDTO;
import dto.preorder.PreOrderDTO;
import enums.SeafoodType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PreOrderControllerr.class)
public class PreOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private PreOrderDTO preOrderDTO;

    @BeforeEach
    void setUp() {
        TransactionItemDTO item1 = new TransactionItemDTO(SeafoodType.COD, new BigDecimal("0.5"), new BigDecimal("22"));
        TransactionItemDTO item2 = new TransactionItemDTO(SeafoodType.TUNA, new BigDecimal("2"), new BigDecimal("25.75"));
        preOrderDTO = new PreOrderDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Arrays.asList(item1, item2));
    }

    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        mockMvc.perform(post("/api/v1/transactions/preorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(preOrderDTO)))
                .andExpect(status().isOk()); // or `.isAccepted()` based on your implementation
    }

}