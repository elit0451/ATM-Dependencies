import java.util.Date;

public class CreditCard implements CreditCardInterface {

    private int _id;
    private AccountInterface _account;
    private Date _dateLastUsed;
    private int _pinCode;
    private int _wrongPinCodeAttempts;
    private boolean _blocked;

    public CreditCard() {
        _id = 0;
        _pinCode = 0;
        _wrongPinCodeAttempts = 0;
        _blocked = false;
    }

    public void setId(int id) {
        _id = id;
    }

    public int getId() {
        return _id;
    }

    public void setAccount(AccountInterface account) {
        _account = account;
    }

    public AccountInterface getAccount() {
        return _account;
    }

    public void setLastUsed(Date lastUsed) {
        _dateLastUsed = lastUsed;
    }

    public Date getLastUsed() {
        return _dateLastUsed;
    }

    public void setPinCode(int pinCode) {
        _pinCode = pinCode;
    }

    public int getPinCode() {
        return _pinCode;
    }

    public void setWrongPinCodeAttempts(int wrongPinCodeAttempts) {
        _wrongPinCodeAttempts = wrongPinCodeAttempts;
    }

    public void addWrongPinCodeAttempt() {
        _wrongPinCodeAttempts++;
    }

    public int getWrongPinCodeAttempts() {
        return _wrongPinCodeAttempts;
    }

    public void resetWrongPinCodeAttempts() {
        _wrongPinCodeAttempts = 0;
    }

    public void setBlocked(boolean blocked) {
        _blocked = blocked;
    }

    public boolean isBlocked() {
        return _blocked;
    }
}
