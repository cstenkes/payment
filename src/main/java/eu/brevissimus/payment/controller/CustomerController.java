package eu.brevissimus.payment.controller;

import eu.brevissimus.payment.model.dto.AccountBalanceDto;
import eu.brevissimus.payment.model.dto.CustomerDto;
import eu.brevissimus.payment.model.entity.Customer;
import eu.brevissimus.payment.service.AccountService;
import eu.brevissimus.payment.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get balance of customer by firstname,lastname",
            description = "Retrieves balance of customer by firstname,lastname")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rerievng balance of customer by firstname,lastname was successful")
            })
    @GetMapping("/balance")
    public List<AccountBalanceDto> getAllBalanceOfCustomer(
            @Parameter(description = "First name", example = "John")
            @RequestParam("fname") String firstName,
            @Parameter(description = "Last name", example = "Doe")
            @RequestParam("lname") String lastName) {
        List<AccountBalanceDto> accountBalancesDto = accountService.getAllBalanceOfCustomer(firstName, lastName);
        log.info("balance of customer {} {}: {}", firstName, lastName, accountBalancesDto );
        return accountBalancesDto;
    }

    // CRUD write controller methods:

    @Operation(
            summary = "Customer creation",
            description = "Customer creation")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Customer creation was successful")
            })
    @PostMapping("/")
    public Customer createCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.createCustomer(customerDto);
        log.info("customer created: {}", customer );
        return customer;
    }

    @Operation(
            summary = "Existing customer modification",
            description = "Existing customer modification")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Existing customer modification was successful")
            })
    @PutMapping("/")
    public Customer modifyCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.modifyCustomer(customerDto);
        log.info("customer modified: {}", customer );
        return customer;
    }

    @Operation(
            summary = "Existing customer deletion (only logical)",
            description = "Existing customer deletion (only logical)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Existing customer deletion (only logical) was successful")
            })
    @DeleteMapping("/")
    public Customer deleteCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.deleteCustomer(customerDto);
        log.info("customer deleted: {}", customer );
        return customer;
    }
}
