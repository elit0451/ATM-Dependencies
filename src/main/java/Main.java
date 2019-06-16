import com.mysql.cj.jdbc.MysqlDataSource;

public class Main {
    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("bank");

        DataMapperInterface mapperInterface = new DataMapper();
        mapperInterface.setDataSource(dataSource);

        ATMInterface atm = new ATM();

        try{
            CreditCardInterface cc = new CreditCard();
            cc.setId(1);
            atm.setDataMapper(mapperInterface);
            atm.insert(cc, 1234);
            System.out.println("BALANCE HERE:");
            System.out.println(atm.balance());
            atm.deposit(150);
            atm.withdraw(100);
            atm.eject();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
