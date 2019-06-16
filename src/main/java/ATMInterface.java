public interface ATMInterface {
    public void setDataMapper(DataMapperInterface dataMapper);
    public boolean insert(CreditCardInterface creditCard, int pincode) throws Exception;
    public void eject();
    public boolean deposit(double amount) throws Exception;
    public boolean withdraw(double amount) throws Exception;
    public double balance() throws Exception;
}
