package io.github.techbank.creditcardservice.services;

import io.github.techbank.creditcardservice.entities.CreditCard;
import io.github.techbank.creditcardservice.exceptions.ExistsObjectInDBException;
import io.github.techbank.creditcardservice.repositories.CreditCardRepository;
import io.github.techbank.creditcardservice.services.implementations.CreditCardServiceImpl;
import io.github.techbank.creditcardservice.utils.CreditCardFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CreditCardServiceTest {

    CreditCardService creditCardService;

    @MockBean
    CreditCardRepository creditCardRepository;

    @BeforeEach
    public void setUp() {
        creditCardService = new CreditCardServiceImpl(creditCardRepository);
    }

    @Test
    void GivenACreditCard_WhenCallRegister_ThenSaveAndReturnCreditCard() {
        // Scenary
        var request = CreditCardFactory.createNewCredicCard(null);
        var creditCard = CreditCardFactory.createNewCredicCard();
        Mockito.when(creditCardRepository.existsByCardBrandAndCardLimitAndIncame(request.getCardBrand(), request.getCardLimit(), request.getIncame())).thenReturn(false);
        Mockito.when(creditCardRepository.save(any(CreditCard.class))).thenReturn(creditCard);

        // Execution
        CreditCard registeredCreditCard = creditCardService.register(request);

        // Verification
        verify(creditCardRepository, times(1)).save(request);
        Assertions.assertNotNull(registeredCreditCard.getId());
    }

    @Test
    void GivenAnExistingCreditCard_WhenCallRegister_ThenThrowAnException() {
        // Scenary
        var request = CreditCardFactory.createNewCredicCard(null);
        Mockito.when(creditCardRepository.existsByCardBrandAndCardLimitAndIncame(request.getCardBrand(), request.getCardLimit(), request.getIncame())).thenReturn(true);

        // Execution and Verification
        var exception = assertThrows(ExistsObjectInDBException.class, () -> creditCardService.register(request));
        verify(creditCardRepository, never()).save(request);
        String expectedMessage = "There is already a credit card with this properties.";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void GivenANullCreditCard_WhenCallRegister_ThenThrowAnException() {
        CreditCard request = null;

        // Execution and Verification
        var exception = assertThrows(IllegalArgumentException.class, () -> creditCardService.register(request));
        var expectedMessage = "Invalid object.";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
