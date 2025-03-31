package eu.brevissimus.payment.service;

import eu.brevissimus.payment.exception.NotFoundException;
import eu.brevissimus.payment.model.dto.CustomerDto;
import eu.brevissimus.payment.model.entity.Customer;
import eu.brevissimus.payment.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock private CustomerRepository customerRepository;

    @InjectMocks private CustomerService customerService;

    private final CustomerDto customerDto1 = new CustomerDto(
            "Doe", "John", "NYC First avenue 11");

    private final Customer customer1 = new Customer(null,
            "Doe", "John", "NYC First avenue 11",true,
            Collections.emptyList(), Collections.emptyList());

    private final Customer customerOut = new Customer(1L,
            "Doe", "John", "NYC First avenue 11",true,
            Collections.emptyList(), Collections.emptyList());

    private final CustomerDto customerDto2 = new CustomerDto(
            "Doe", "John", "NYC First avenue 35");
    private final Customer customerOut2 = new Customer(1L,
            "Doe", "John", "NYC First avenue 35",true,
            Collections.emptyList(), Collections.emptyList());
    private final Customer customerMid = new Customer(1L,
            "Doe", "John", "NYC First avenue 35",true,
            Collections.emptyList(), Collections.emptyList());

    @Test
    void test_createCustomer(){
        when(customerRepository.save(customer1)).thenReturn(customerOut);

        Customer result = customerService.createCustomer(customerDto1);

        assertThat(result).isEqualTo(customerOut);
        verify(customerRepository).save(any());
    }

    @Test
    void test_modifyCustomer_ok(){
        when(customerRepository.findByFirstNameAndLastName("John","Doe")).thenReturn(Optional.of(customerOut));
        when(customerRepository.save(customerMid)).thenReturn(customerOut2);

        Customer result = customerService.modifyCustomer(customerDto2);

        assertThat(result).isEqualTo(customerOut2);
        verify(customerRepository).findByFirstNameAndLastName(any(),any());
        verify(customerRepository).save(any());
    }

    @Test
    void test_modifyCustomer_not_ok(){
        assertThrows(NotFoundException.class, () -> customerService.modifyCustomer(customerDto2));
        verify(customerRepository).findByFirstNameAndLastName(any(),any());
    }

// fixme

//    @Test
//    void test_deleteCustomer_ok(){
//        when(customerRepository.findByFirstNameAndLastName("John","Doe")).thenReturn(Optional.of(customerOut));
//        when(customerRepository.save(customerMid)).thenReturn(customerOut2);
//
//        Customer result = customerService.deleteCustomer(customerDto2);
//
//        assertThat(result).isEqualTo(customerOutDeleted);
//        verify(customerRepository).findByFirstNameAndLastName(any(),any());
//        verify(customerRepository).save(any());
//    }

    @Test
    void test_deleteCustomer_not_ok(){
        assertThrows(NotFoundException.class, () -> customerService.deleteCustomer(customerDto2));
        verify(customerRepository).findByFirstNameAndLastName(any(),any());
    }

}
