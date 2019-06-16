import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import javax.sql.DataSource;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class TestATM {
    DataMapperInterface mapper;
    AccountInterface account;
    CreditCardInterface creditCard;
    ATMInterface atm;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mapper = Mockito.mock(DataMapper.class);
        atm = Mockito.spy(ATM.class);

        atm.setDataMapper(mapper);

        account = new Account();
        account.setId(1);
        account.setBalance(100);

        creditCard = Mockito.spy(CreditCard.class);
        creditCard.setId(1);
        creditCard.setAccount(account);
        creditCard.setPinCode(1234);
    }

    @Test
    public void checkInitialBalanceTest() {
        Mockito.when(mapper.getCreditCard(1)).thenReturn(creditCard);

        try {
            atm.insert(creditCard, 1234);

            assertEquals(100, atm.balance());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void checkBalanceAfterDepositTest() {
        Mockito.when(mapper.getCreditCard(1)).thenReturn(creditCard);

        try {
            atm.insert(creditCard, 1234);
            atm.deposit(50);

            assertEquals(150, atm.balance());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void checkBalanceAfterWithdrawTest() {
        Mockito.when(mapper.getCreditCard(1)).thenReturn(creditCard);

        try {
            atm.insert(creditCard, 1234);
            atm.withdraw(50);
            Mockito.verify(atm).withdraw(50);

            assertEquals(50, atm.balance());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void insertUnexistentCardTest(){
        Mockito.when(mapper.getCreditCard(1)).thenReturn(null);
        Exception exception =
                assertThrows(Exception.class, () -> {
            atm.insert(creditCard, 1234);
        });

        assertEquals("Card does not exist in the system, ejecting.", exception.getMessage());
    }

    @Test
    public void insertCardWithWrongPinCodeTest(){
        Mockito.when(mapper.getCreditCard(1)).thenReturn(creditCard);
        Mockito.when(creditCard.getPinCode()).thenReturn(4321);

        Exception exception =
                assertThrows(Exception.class, () -> {
                    atm.insert(creditCard, 1234);
                });


        assertEquals("The pincode is incorrect, ejecting.", exception.getMessage());
    }

    @Test
    public void insertBlockedCardTest(){
        Mockito.when(mapper.getCreditCard(1)).thenReturn(creditCard);
        Mockito.when(creditCard.isBlocked()).thenReturn(true);

        Exception exception =
                assertThrows(Exception.class, () -> {
                    atm.insert(creditCard, 1234);
                });


        assertEquals("The card is currently blocked, ejecting.", exception.getMessage());
    }
}
