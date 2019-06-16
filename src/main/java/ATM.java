import java.util.Date;

public class ATM implements ATMInterface {

    private DataMapperInterface _dataMapper;
    private boolean _inserted = false;
    private CreditCardInterface _currentCreditCard;

    public void setDataMapper(DataMapperInterface dataMapper) {
        _dataMapper = dataMapper;
    }

    public boolean insert(CreditCardInterface creditCard, int pincode) throws Exception {
        CreditCardInterface verifiedCard = _dataMapper.getCreditCard(creditCard.getId());

        if(verifiedCard == null) {
            System.out.println("Wrong card");
            throw new Exception("Card does not exist in the system, ejecting.");
        }
        else if(verifiedCard.isBlocked()) {
            System.out.println("Card blocked");
            throw new Exception("The card is currently blocked, ejecting.");
        }
        else if(verifiedCard.getPinCode() != pincode) {
            verifiedCard.addWrongPinCodeAttempt();
            throw new Exception("The pincode is incorrect, ejecting.");
        }

        _inserted = true;

        verifiedCard.resetWrongPinCodeAttempts();
        verifiedCard.setLastUsed(new Date(System.currentTimeMillis()));
        _currentCreditCard = verifiedCard;

        System.out.println("Balance: " + balance());

        return _inserted;
    }

    public void eject() {
        if(_inserted) {
            _dataMapper.setAccount(_currentCreditCard.getAccount());
            _dataMapper.setCreditCard(_currentCreditCard);

            _inserted = false;
        }
    }

    public boolean deposit(double amount) throws Exception {
        if(!_inserted)
            throw new Exception("Credit card not inserted.");

        _currentCreditCard.getAccount().deposit(amount);
        return true;
    }

    public boolean withdraw(double amount) throws Exception {
        if(!_inserted)
            throw new Exception("Credit card not inserted.");

        _currentCreditCard.getAccount().withdraw(amount);
        return true;
    }

    public double balance() throws Exception {
        if(!_inserted)
            throw new Exception("Credit card not inserted.");

        return _currentCreditCard.getAccount().getBalance();
    }
}
