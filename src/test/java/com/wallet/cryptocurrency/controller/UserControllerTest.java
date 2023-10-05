package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.coingeko.client.CoinGeckoClient;
import com.wallet.cryptocurrency.config.ConfigApp;
import com.wallet.cryptocurrency.service.UserService;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ConfigApp configApp;

    String token;

    @BeforeEach
    void setUp() throws Exception {
        String username = "maciej";
        String password = "maciej123";

        MultiValueMap<String, String> loginParams = new LinkedMultiValueMap<>();
        loginParams.add("username", username);
        loginParams.add("password", password);

        ResultActions resultActions = this.mockMvc.perform(post("http://localhost:8080/auth/log-in").contentType(MediaType.APPLICATION_JSON)
                .params(loginParams));
        MvcResult mvcResult = resultActions.andDo(print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(contentAsString);
        this.token = "Bearer " + jsonObject.getJSONObject("token").getString("token");
    }

    @Test
    void testGetUserDashboard() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080//users").accept(MediaType.APPLICATION_JSON).header("Authorization", this.token))
                .andExpect(jsonPath("$.wallets", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.wishLists", Matchers.hasSize(2)));
    }

}