import data.Customer;

public final class HelloWorld {

    private HelloWorld() {
    }

    public static void main(String[] args) {
        Customer c = new Customer(1L, "Luis");
        System.out.println("Hello %s! You are the customer number #%d".formatted(c.getName(), c.getId()));
    }

}
