package eu.brevissimus.payment.controller;

import eu.brevissimus.payment.model.dto.AccountBalanceDto;
import eu.brevissimus.payment.model.dto.CustomerDto;
import eu.brevissimus.payment.model.entity.Customer;
import eu.brevissimus.payment.service.AccountService;
import eu.brevissimus.payment.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/payment/api/v${application.version}/customers")
@RestController
@RequiredArgsConstructor
@Tag(name = "Customer operations", description = "all customer related endpoints")
public class CustomerController {

    private final AccountService accountService;
    private final CustomerService customerService;

    @GetMapping("/balance")
    public List<AccountBalanceDto> getAllBalanceOfCustomer(@RequestParam("fname") String firstName, @RequestParam("lname") String lastName) {
        List<AccountBalanceDto> accountBalances = accountService.getAllBalanceOfCustomer(firstName, lastName);
        log.info("balance of customer {} {}: {}", firstName, lastName, accountBalances );
        return accountBalances;
    }

    // trivial controller methods:

    @PostMapping("/")
    public Customer createCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.createCustomer(customerDto);
        log.info("customer created: {}", customer );
        return customer;
    }

    @PutMapping("/")
    public Customer modifyCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.modifyCustomer(customerDto);
        log.info("customer modified: {}", customer );
        return customer;
    }

    @DeleteMapping("/")
    public Customer deleteCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.deleteCustomer(customerDto);
        log.info("customer deleted: {}", customer );
        return customer;
    }
}
