import data.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerTest {

    @Test
    public void testCustomer() {
        Customer c = new Customer();
        assertNotNull(c);

        c.setId(1L);
        assertNotNull(c.getId());
        assertEquals(1L, c.getId());

        c.setName("Luis");
        assertNotNull(c.getName());
        assertEquals("Luis", c.getName());
    }

    @Test
    public void testCustomerAllArgsConstructor() {
        Customer c = new Customer(1L, "Luis");
        assertNotNull(c);

        assertNotNull(c.getId());
        assertEquals(1L, c.getId());

        assertNotNull(c.getName());
        assertEquals("Luis", c.getName());
    }

}
