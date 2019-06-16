import java.util.Date;

public interface CreditCardInterface {
    public void setId(int id);
    public int getId();
    public void setAccount(AccountInterface account);
    public AccountInterface getAccount();
    public void setLastUsed(Date lastUsed);
    public Date getLastUsed();
    public void setPinCode(int pinCode);
    public int getPinCode();
    public void setWrongPinCodeAttempts(int wrongPinCodeAttempts);
    public void addWrongPinCodeAttempt();
    public int getWrongPinCodeAttempts();
    public void resetWrongPinCodeAttempts();
    public void setBlocked(boolean blocked);
    public boolean isBlocked();
}
