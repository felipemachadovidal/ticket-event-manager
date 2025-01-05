package com.compassuol.ms_ticket_manager.testcontroller;

import com.compassuol.ms_ticket_manager.dto.*;
import com.compassuol.ms_ticket_manager.model.Ticket;
import com.compassuol.ms_ticket_manager.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Test
    void testCreateTicket() throws Exception {
        TicketDTO ticketDTO = new TicketDTO(
                "John Doe",  // customerName
                "12345678901",
                "johndoe@example.com",
                "Concert",
                "completed",
                100.0,
                20.0,
                "1",
                null
        );

        EventResponseDTO eventResponseDTO = new EventResponseDTO();
        eventResponseDTO.setTicketId(new ObjectId("6452d37fb1742e1a60d5f9d1").toString());

        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
        ticketResponseDTO.setTicketid(new ObjectId("6452d37fb1742e1a60d5f9d1"));
        ticketResponseDTO.setCustomerName("John Doe");
        ticketResponseDTO.setEvent(eventResponseDTO);

        Mockito.when(ticketService.createTicket(Mockito.any(), Mockito.any()))
                .thenReturn(ticketResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/ticketmanagement/v1/create-ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ticketDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticketid.timestamp").value(1683149695))
                .andExpect(MockMvcResultMatchers.jsonPath("$.event.ticketId").value("6452d37fb1742e1a60d5f9d1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    void testGetTicketById() throws Exception {
        ObjectId ticketId = new ObjectId("6452d37fb1742e1a60d5f9d1");
        Ticket ticket = Ticket.builder()
                .ticketid(ticketId)
                .customerName("John Doe")
                .cpf("12345678901")
                .customerMail("johndoe@example.com")
                .eventName("Concert")
                .status("completed")
                .brlAmount(100.0)
                .usdAmount(20.0)
                .build();

        Mockito.when(ticketService.getTicketById(ticketId)).thenReturn(ticket);

        mockMvc.perform(MockMvcRequestBuilders.get("/ticketmanagement/v1/get-ticket/" + ticketId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventName").value("Concert"));
    }

    @Test
    void testListTicketsByCpf() throws Exception {

        String cpf = "12345678901";
        List<Ticket> tickets = List.of(
                Ticket.builder().ticketid(new ObjectId()).customerName("John Doe").cpf(cpf).build(),
                Ticket.builder().ticketid(new ObjectId()).customerName("Jane Doe").cpf(cpf).build()
        );

        Mockito.when(ticketService.listTicketsByCpf(cpf)).thenReturn(tickets);

        mockMvc.perform(MockMvcRequestBuilders.get("/ticketmanagement/v1/list-tickets-by-cpf/" + cpf))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    void testUpdateTicket() throws Exception {

        ObjectId ticketId = new ObjectId("6452d37fb1742e1a60d5f9d1");
        TicketDTO ticketDTO = new TicketDTO("John Doe Updated", "12345678901", "johnupdated@example.com", "Concert Updated", "completed", 200.0, 40.0, "1", ticketId.toHexString());
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
        ticketResponseDTO.setTicketid(ticketId);
        ticketResponseDTO.setCustomerName("John Doe Updated");


        Mockito.when(ticketService.updateTicket(ticketId, ticketDTO)).thenReturn(ticketResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/ticketmanagement/v1/update-ticket/" + ticketId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ticketDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("John Doe Updated"));
    }

    @Test
    void testCancelTicket() throws Exception {
        ObjectId ticketId = new ObjectId("6452d37fb1742e1a60d5f9d1");
        Mockito.doNothing().when(ticketService).cancelTicket(ticketId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/ticketmanagement/v1/cancel-ticket/" + ticketId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}