package com.example.addressbook.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.example.addressbook.model.request.AddressBookRequest;
import com.example.addressbook.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AddressBookController.class)
@AutoConfigureMockMvc
class AddressBookControllerTest {

  @MockBean
  AddressService addressService;

  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @BeforeEach
  public void initAll() {
    this.objectMapper = new ObjectMapper();
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void createInvalidAge() throws Exception {
    // given
    AddressBookRequest addressBookRequest =
        AddressBookRequest.builder().name("김호박").age(21).phone("01012345678")
            .build();

    // then
    mockMvc.perform(post("/api/v1/addresses")
            .content(objectMapper.writeValueAsString(addressBookRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void createInvalidName() throws Exception {
    // given
    AddressBookRequest addressBookRequest =
        AddressBookRequest.builder().name("김호박A").age(19).phone("01012345678")
            .build();

    // then
    mockMvc.perform(post("/api/v1/addresses")
            .content(objectMapper.writeValueAsString(addressBookRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void createInvalidPhone() throws Exception {
    // given
    AddressBookRequest addressBookRequest =
        AddressBookRequest.builder().name("김호박").age(19).phone("01012345678123")
            .build();

    // then
    mockMvc.perform(post("/api/v1/addresses")
            .content(objectMapper.writeValueAsString(addressBookRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void retrieve() throws Exception {
    // then
    mockMvc.perform(get("/api/v1/addresses/{id}", UUID.randomUUID())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void list() throws Exception {
    // then
    mockMvc.perform(get("/api/v1/addresses")
            .queryParam("page", "1")
            .queryParam("sort", "name,desc")
            .queryParam("size", "5")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void update() throws Exception {
    // given
    AddressBookRequest addressBookRequest =
        AddressBookRequest.builder().id(UUID.randomUUID()).name("김호박").age(19).phone("01012345678")
            .build();

    // then
    mockMvc.perform(put("/api/v1/addresses/{id}", addressBookRequest.getId())
            .content(objectMapper.writeValueAsString(addressBookRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void delete() throws Exception {
    // then
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/addresses/{id}", UUID.randomUUID()))
        .andExpect(status().isNoContent());
  }
}
