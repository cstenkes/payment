package eu.brevissimus.payment.service;

import eu.brevissimus.payment.exception.FoundException;
import eu.brevissimus.payment.exception.NotFoundException;
import eu.brevissimus.payment.model.dto.CustomerDto;
import eu.brevissimus.payment.model.entity.Customer;
import eu.brevissimus.payment.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static eu.brevissimus.payment.exception.ErrorCode.CUSTOMER_FOUND;
import static eu.brevissimus.payment.exception.ErrorCode.CUSTOMER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(CustomerDto customerDto) {
        Optional<Customer> customerCheck = customerRepository.findByFirstNameAndLastName(customerDto.firstName(),customerDto.lastName());
        if (customerCheck.isPresent()) {
            throw new FoundException(CUSTOMER_FOUND, "With firstName: " + customerDto.firstName() + ", lastName:" + customerDto.lastName() + "a customer has already existed.");
        }


        Customer customer = new Customer();
        customer.setFirstName(customerDto.firstName());
        customer.setLastName(customerDto.lastName());
        customer.setAddress(customerDto.address());
        customer.setActive(true);
        return customerRepository.save(customer);
    }

    // modifing address only
    public Customer modifyCustomer(CustomerDto customerDto) {
        Customer customer = customerRepository.findByFirstNameAndLastName(customerDto.firstName(),customerDto.lastName())
                        .orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND, "firstName: " + customerDto.firstName() +
                ", lastName:" + customerDto.lastName()));

        customer.setAddress(customerDto.address());
        return customerRepository.save(customer);
    }

    //logical delete
    @Transactional
    public Customer deleteCustomer(CustomerDto customerDto) {
        Customer customer = customerRepository.findByFirstNameAndLastName(customerDto.firstName(), customerDto.lastName())
        .orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND, "firstName: " + customerDto.firstName() +
                ", lastName:" + customerDto.lastName()));
        customer.setActive(false);
        return customerRepository.save(customer);
    }

}
