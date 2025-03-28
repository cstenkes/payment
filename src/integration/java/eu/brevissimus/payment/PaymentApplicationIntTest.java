package eu.brevissimus.payment;

import eu.brevissimus.payment.service.TransactionService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//@SpringBootTest
//@AutoConfigureMockMvc
class PaymentApplicationIntTest {

	//@Autowired
	private TransactionService transactionService;

	//@Autowired
	private MockMvc mockMvc;

	//@Test
	void calendarServiceFreeTest() throws Exception {
		// given - setup or precondition
        //paymentService.initDay(); // add fix day for test

		// when - action
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/payment/something"));

		// then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		//response.andExpect(MockMvcResultMatchers.jsonPath("$.calendarDays[0].day", CoreMatchers.is("2023-09-08")));
		// ...

	}
}