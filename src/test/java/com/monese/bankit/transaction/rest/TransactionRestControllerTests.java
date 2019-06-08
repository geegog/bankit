package com.monese.bankit.transaction.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monese.bankit.BankitApplication;
import com.monese.bankit.transaction.application.dto.ResponseDTO;
import com.monese.bankit.transaction.application.dto.StatementDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BankitApplication.class)
@WebAppConfiguration
@Sql("/customers-dataset.sql")
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransactionRestControllerTests {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Autowired @Qualifier("_halObjectMapper")
    ObjectMapper mapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testSendReceiveAndRequestStatementWithOneTransactionMoney() throws Exception {

        String transferDTO = "{\n" +
                "    \"senderAccount\": \"053248946241\",\n" +
                "    \"receiverAccount\": \"051234562313\",\n" +
                "    \"amount\": 123675,\n" +
                "    \"receiverFirstName\": \"Onuche\",\n" +
                "    \"receiverLastName\": \"Idoko\"\n" +
                "}";

        MvcResult result = mockMvc.perform(
                post("http://localhost:8080/api/transactions/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transferDTO))
                .andExpect(status().isOk())
                .andReturn();

        ResponseDTO responseDTO =
                mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<ResponseDTO>() {
                });

        //System.out.println(result.getResponse().getContentAsString());
        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertEquals(responseDTO.getReceiver().getMoney().getBalance(), new BigDecimal(623675).setScale(2));
        Assert.assertEquals(responseDTO.getSender().getMoney().getBalance(), new BigDecimal(76325).setScale(2));
        Assert.assertEquals(responseDTO.getReceiver().getMoney().getCurrency(), Currency.getInstance("EUR"));
        Assert.assertEquals(responseDTO.getSender().getMoney().getCurrency(), Currency.getInstance("EUR"));


        MvcResult statementResult = mockMvc.perform(
                get("http://localhost:8080/api/transactions/history?accountNumber=051234562313"))
                .andExpect(status().isOk())
                .andReturn();

        StatementDTO statementDTO =
                mapper.readValue(statementResult.getResponse().getContentAsString(), new TypeReference<StatementDTO>() {
                });

        Assert.assertEquals(200, statementResult.getResponse().getStatus());
        Assert.assertEquals(statementDTO.getMoneyDTO().getBalance(), new BigDecimal(623675).setScale(2));
        Assert.assertEquals(statementDTO.getMoneyDTO().getCurrency(), Currency.getInstance("EUR"));
        Assert.assertEquals(statementDTO.getHistoryDTOList().size(), 1);

    }


}
