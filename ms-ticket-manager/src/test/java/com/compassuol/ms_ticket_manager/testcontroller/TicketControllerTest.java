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
        // Criando o objeto DTO de entrada
        TicketDTO ticketDTO = new TicketDTO(
                "John Doe",
                "12345678901",
                "johndoe@example.com",
                "Concert",
                "completed",
                100.0,
                20.0,
                "1",
                null  // ticketId será gerado automaticamente pelo serviço
        );

        // Criando o objeto de resposta esperado com ticketId como ObjectId
        EventResponseDTO eventResponseDTO = new EventResponseDTO();
        eventResponseDTO.setTicketId(new ObjectId("6452d37fb1742e1a60d5f9d1").toString());  // Convertendo ObjectId para String

        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
        ticketResponseDTO.setTicketid(new ObjectId("6452d37fb1742e1a60d5f9d1"));  // Passando ObjectId diretamente
        ticketResponseDTO.setEvent(eventResponseDTO);  // Associando o EventResponseDTO ao TicketResponseDTO

        // Configurando o mock do serviço para retornar o ticketResponseDTO quando o método createTicket for chamado
        Mockito.when(ticketService.createTicket(Mockito.any(), Mockito.any()))
                .thenReturn(ticketResponseDTO);

        // Executando o teste
        mockMvc.perform(MockMvcRequestBuilders.post("/ticketmanagement/v1/create-ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ticketDTO)))  // Convertendo o ticketDTO para JSON
                .andExpect(MockMvcResultMatchers.status().isOk())  // Espera que o status seja 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticketId").value("6452d37fb1742e1a60d5f9d1"))  // Verifica se o ticketId retornado é o esperado
                .andExpect(MockMvcResultMatchers.jsonPath("$.event.ticketId").value("6452d37fb1742e1a60d5f9d1"))  // Verifica se o ticketId do evento também está correto
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("John Doe"));  // Verifica o nome do cliente
    }

    @Test
    void testGetTicketById() throws Exception {
        // Mock dos dados de saída
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

        // Simula o comportamento do service
        Mockito.when(ticketService.getTicketById(ticketId)).thenReturn(ticket);

        // Teste do endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/ticketmanagement/v1/get-ticket/" + ticketId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventName").value("Concert"));
    }

    @Test
    void testListTicketsByCpf() throws Exception {
        // Mock dos dados de saída
        String cpf = "12345678901";
        List<Ticket> tickets = List.of(
                Ticket.builder().ticketid(new ObjectId()).customerName("John Doe").cpf(cpf).build(),
                Ticket.builder().ticketid(new ObjectId()).customerName("Jane Doe").cpf(cpf).build()
        );

        // Simula o comportamento do service
        Mockito.when(ticketService.listTicketsByCpf(cpf)).thenReturn(tickets);

        // Teste do endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/ticketmanagement/v1/list-tickets-by-cpf/" + cpf))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    void testUpdateTicket() throws Exception {
        // Mock dos dados de entrada e saída
        ObjectId ticketId = new ObjectId("6452d37fb1742e1a60d5f9d1");
        TicketDTO ticketDTO = new TicketDTO("John Doe Updated", "12345678901", "johnupdated@example.com", "Concert Updated", "completed", 200.0, 40.0, "1", ticketId.toHexString());
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
        ticketResponseDTO.setTicketid(ticketId);
        ticketResponseDTO.setCustomerName("John Doe Updated");

        // Simula o comportamento do service
        Mockito.when(ticketService.updateTicket(ticketId, ticketDTO)).thenReturn(ticketResponseDTO);

        // Teste do endpoint
        mockMvc.perform(MockMvcRequestBuilders.put("/ticketmanagement/v1/update-ticket/" + ticketId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ticketDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("John Doe Updated"));
    }

    @Test
    void testCancelTicket() throws Exception {
        // Simula o comportamento do service
        ObjectId ticketId = new ObjectId("6452d37fb1742e1a60d5f9d1");
        Mockito.doNothing().when(ticketService).cancelTicket(ticketId);

        // Teste do endpoint
        mockMvc.perform(MockMvcRequestBuilders.delete("/ticketmanagement/v1/cancel-ticket/" + ticketId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}