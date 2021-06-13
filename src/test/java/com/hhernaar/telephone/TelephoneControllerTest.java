package com.hhernaar.telephone;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhernaar.telephone.collection.Telephone;
import com.hhernaar.telephone.controller.TelephoneController;
import com.hhernaar.telephone.service.TelephoneService;
import com.hhernaar.telephone.util.ValidatorUtil;

@WebMvcTest(controllers = TelephoneController.class)
public class TelephoneControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TelephoneService telephoneService;

  @MockBean
  private ValidatorUtil validator;

  @MockBean
  private RabbitTemplate rabbitTemplate;

  private static final String BASE_URI = "/telephone";

  private static final String DATA =
      "fb48b5619accfdb6ef9315560e0fff8a9c927ad39986241b55558f57efbd3529";

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void save() throws Exception {
    mockMvc
        .perform(post(BASE_URI).contentType(MediaType.APPLICATION_JSON).header("DATA", DATA)
            .content(mapper.writeValueAsString(this.getTelephoneTest())))
        .andExpect(status().isCreated()).andDo(print());
  }

  @Test
  public void update() throws Exception {
    Telephone telephone = this.getTelephoneTest();
    mockMvc
        .perform(put(BASE_URI.concat("/{id}"), "60c5067b8b27b25abab77798")
            .contentType(MediaType.APPLICATION_JSON).header("DATA", DATA)
            .content(mapper.writeValueAsString(telephone)))
        .andExpect(status().isOk()).andDo(print());
  }


  @Test
  public void findById() throws Exception {
    mockMvc
        .perform(get(BASE_URI.concat("/{id}"), "60c5067b8b27b25abab77798")
            .accept(MediaType.APPLICATION_JSON).header("DATA", DATA))
        .andDo(print()).andExpect(status().isOk());
  }

  @Test
  public void findByImei() throws Exception {
    mockMvc
        .perform(get(BASE_URI.concat("/imei/{imei}"), "123456789012345")
            .accept(MediaType.APPLICATION_JSON).header("DATA", DATA))
        .andDo(print()).andExpect(status().isOk());
  }

  @Test
  public void list() throws Exception {
    mockMvc.perform(get(BASE_URI).accept(MediaType.APPLICATION_JSON).header("DATA", DATA))
        .andDo(print()).andExpect(status().isOk());
  }

  @Test
  public void delete() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URI.concat("/{id}"), "123456789012345")
        .header("DATA", DATA)).andDo(print()).andExpect(status().isOk());

  }


  private Telephone getTelephoneTest() {
    Telephone telephone = new Telephone();
    telephone.setBrand("Nokia");
    telephone.setModel("Nokia");
    telephone.setShortName("nokia");
    telephone.setImei("74125896325874123");
    telephone.setCellphoneNumber("525555555555");
    telephone.setEmail("example@example.com");
    telephone.setCreationDate(new Date());
    return telephone;
  }

}
