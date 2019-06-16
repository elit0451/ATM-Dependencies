import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;

public class DataMapper implements DataMapperInterface {
    DataSource _data;
    AccountInterface _account;
    CreditCardInterface _creditCard;
    Connection _connection;

    public void setDataSource(DataSource dataSource) {
        _data = dataSource;

        try {
            _connection = _data.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void setCreditCard(CreditCardInterface creditCard) {
        _creditCard = creditCard;
        setAccount(_creditCard.getAccount());

        PreparedStatement statement;

        try {
            if(!_connection.isClosed()) {
                String query = "UPDATE creditCard SET last_used = ?, wrong_pin_code_attempts = ?, blocked = ? WHERE id = " + creditCard.getId();
                statement = _connection.prepareStatement(query);
                statement.setDate(1,new java.sql.Date(creditCard.getLastUsed().getTime()));
                statement.setInt(2, creditCard.getWrongPinCodeAttempts());
                statement.setBoolean(3, creditCard.isBlocked());

                statement.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CreditCardInterface getCreditCard(int id) {
        Statement statement;
        ResultSet result;

        try {
            if(!_connection.isClosed()) {
                statement = _connection.createStatement();
                result = statement.executeQuery("SELECT * FROM creditCard, bankAccount WHERE creditCard.id = " + id + " AND creditCard.account_id = bankAccount.id LIMIT 1;");


                if(result.next()){
                    int account_id = result.getInt("account_id");
                    double balance = result.getDouble("balance");

                    int creditCard_id = result.getInt(1);
                    Date last_used = result.getDate("last_used");
                    int pin_code = result.getInt("pin_code");
                    int wrong_pin_code_attempts = result.getInt("wrong_pin_code_attempts");
                    boolean blocked = result.getBoolean("blocked");

                    AccountInterface account = new Account();
                    account.setId(account_id);
                    account.setBalance(balance);

                    CreditCardInterface creditCard = new CreditCard();
                    creditCard.setId(creditCard_id);
                    creditCard.setAccount(account);
                    creditCard.setLastUsed(last_used);
                    creditCard.setPinCode(pin_code);
                    creditCard.setWrongPinCodeAttempts(wrong_pin_code_attempts);
                    creditCard.setBlocked(blocked);

                    setCreditCard(creditCard);
                }
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return _creditCard;
    }

    public void setAccount(AccountInterface account) {
        _account = account;

        PreparedStatement statement;

        try {
            if(!_connection.isClosed()) {
                String query = "UPDATE bankAccount SET balance = ? WHERE id = " + account.getId();
                statement = _connection.prepareStatement(query);
                statement.setDouble(1, account.getBalance());

                statement.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public AccountInterface getAccount(int id) {
        Statement statement;
        ResultSet result;

        try {
            if(!_connection.isClosed()) {
                statement = _connection.createStatement();
                result = statement.executeQuery("SELECT * FROM bankAccount WHERE id = " + id + " LIMIT 1;");


                if(result.next()){
                    int account_id = result.getInt("id");
                    double balance = result.getDouble("balance");

                    AccountInterface account = new Account();
                    account.setId(account_id);
                    account.setBalance(balance);

                    setAccount(account);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return _account;
    }
}
