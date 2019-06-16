public class Account implements AccountInterface {

    private int _id;
    private double _balance;

    public Account() {
        _id = 0;
        _balance = 0;
    }

    public void setId(int id) {
        _id = id;
    }

    public int getId() {
        return _id;
    }

    public void setBalance(double balance) {
        _balance = balance;
    }

    public double getBalance() {
        return _balance;
    }

    public void deposit(double amount) {
        _balance += amount;
    }

    public void withdraw(double amount) {
        if(_balance - amount < 0)
            throw new ArithmeticException();

        _balance -= amount;
    }
}
