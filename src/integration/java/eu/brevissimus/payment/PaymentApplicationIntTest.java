package eu.brevissimus.payment;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentApplicationIntTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void paymentServiceTest() throws Exception {
		// given - setup or precondition
		// the full spring will boot up with init data via liquibase

		// when - action
		ResultActions response = mockMvc.perform(get("/payment/api/v1/cards/{card_number}/balance","12345678-12345678-12345678"));

		// then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber", CoreMatchers.is("12345678A")));
		response.andExpect(MockMvcResultMatchers.jsonPath("$.balance", CoreMatchers.is(500)));
		response.andExpect(MockMvcResultMatchers.jsonPath("$.currency", CoreMatchers.is("EUR")));
	}
}