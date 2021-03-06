package com.nnk.springboot.itTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BidListIT {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  BidListService bidListService;

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc =
            MockMvcBuilders.webAppContextSetup(this.wac)
                    .apply(springSecurity())
                    .build();
  }

  @Test
  void bidListValidateWorkForAdmin() throws Exception {

    BidList bidList = new BidList();
    bidList.setAccount("account");
    bidList.setType("type");
    bidList.setBidQuantity(20.1);

    MvcResult result = this.mockMvc.perform(post("/bidList/validate")
            .flashAttr("bidList", bidList)
            .content(objectMapper.writeValueAsString(bidList))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/bidList/list"))
            .andReturn();
    BidList savedBidList = bidListService.findAll().get(0);
    assertEquals(savedBidList.getAccount(), "account");
    assertEquals(savedBidList.getType(), "type");
    Assertions.assertNull(result.getResponse().getErrorMessage());

    //Deleting user created  for this test using direct call to user Service
    bidListService.delete(savedBidList);

  }


  @Test
  void bidListValidateForAdminNotValid() throws Exception {

    BidList bidList = new BidList();
    //bidList.setAccount("account");

    System.out.println(objectMapper.writeValueAsString(bidList));
    MvcResult mvcResult = this.mockMvc.perform(post("/bidList/validate")
            .flashAttr("bidList", bidList)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();

    Assertions.assertTrue(bidListService.findAll().isEmpty());

  }


  @Test
  void getBidList() throws Exception {
    BidList bidList = new BidList();
    bidList.setAccount("Baccount");
    bidList.setType("Btype");
    bidList.setBidQuantity(20.1);
    bidListService.save(bidList);
    MvcResult mvcResult = this.mockMvc.perform(get("/bidList/list").with(user("Geff").roles(
            "ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();

    assertTrue(mvcResult.getResponse().getContentAsString().contains("Baccount"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Btype"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("20.1"));

    bidListService.delete(bidListService.findAll().get(0));


  }

  @Test
  void bidListUpdate() throws Exception {
    BidList bidList = new BidList();
    bidList.setAccount("account");
    bidList.setType("type");
    bidList.setBidQuantity(20.1);
    bidListService.save(bidList);

    BidList updated = bidListService.findAll().get(0);
    updated.setAccount("updated");

    MvcResult result = this.mockMvc.perform(post("/bidList/update/" + updated.getId())
            .flashAttr("bidList", updated)
            .content(objectMapper.writeValueAsString(updated))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/bidList/list"))
            .andReturn();

    assertTrue(bidListService.findById(updated.getId()).get().getAccount().equals("updated"));
    Assertions.assertNull(result.getResponse().getErrorMessage());


    // ReSet to original values
    bidListService.delete(updated);

  }

  @Test
  void bidListUpdateFail() throws Exception {

    this.mockMvc.perform(get("/bidList/update/{id}}", 1)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(400))
            .andReturn();

  }

  @Test
  void deleteBidList() throws Exception {
    BidList bidList = new BidList();
    bidList.setAccount("account");
    bidList.setType("type");
    bidList.setBidQuantity(20.1);
    bidListService.save(bidList);

    BidList updated = bidListService.findAll().get(0);

    int id = bidListService.findAll().get(0).getId();


    MvcResult result = this.mockMvc.perform(get("/bidList/delete/" + id)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/bidList/list"))
            .andReturn();

    assertFalse(bidListService.findById(id).isPresent());
    assertNull(result.getResponse().getErrorMessage());


  }

  @Test
  void addForm() throws Exception {
    MvcResult mvcResult = this.mockMvc.perform(get("/bidList/add")
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Account"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Type"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Bid Quantity"));

  }

  @Test
  void UpdateForm() throws Exception {
    BidList bidList = new BidList();
    bidList.setAccount("account");
    bidList.setType("type");
    bidList.setBidQuantity(20.1);
    bidListService.save(bidList);

    int id = bidListService.findAll().get(0).getId();
    MvcResult mvcResult = this.mockMvc.perform(get("/bidList/update/" + id)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Account"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Type"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Bid Quantity"));

    bidListService.delete(bidListService.findById(id).get());
  }

  @Test
  void UpdateFail() throws Exception {
    BidList bidList = new BidList();
    bidList.setAccount("Baccount");
    bidList.setType("Btype");
    bidList.setBidQuantity(20.1);
    bidList.setId(1);


    MvcResult result = this.mockMvc.perform(post("/bidList/update/1")
            .flashAttr("bidList", bidList)
            .content(objectMapper.writeValueAsString(bidList))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/bidList/list"))
            .andReturn();

    assertTrue(bidListService.findAll().isEmpty());


    // ReSet to original values
    bidListService.delete(bidList);

  }

}
