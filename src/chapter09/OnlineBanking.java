package chapter09;

abstract class OnlineBanking {
    public void ProcessCustomer(int id) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy(c);
    }

    abstract void makeCustomerHappy(Customer c);

    static private class Customer {}

    static private class Database {
        static Customer getCustomerWithId(int id) { return new Customer(); }
    }
}
