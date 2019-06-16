public interface AccountInterface {
    public void setId(int id);
    public int getId();
    public void setBalance(double balance);
    public double getBalance();
    public void deposit(double amount);
    public void withdraw(double amount);
}
