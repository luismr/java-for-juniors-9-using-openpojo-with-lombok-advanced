import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.PojoParameter;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import data.Country;
import data.Customer;

public class PojoComplexConstructorsTest {

    @Test
    public void shouldGetCustomerInstanceWithIdAndName() {
        final PojoClass pojoClass = PojoClassFactory.getPojoClass(Customer.class);
        Customer customer = (Customer) PojoComplexConstructorsTest.getInstance(pojoClass, new Object [] {1L, "John Doe"});
        Affirm.affirmNotNull("customer pojo class doesn't have a constructor for the provided parameters", customer);
    }

    @Test
    public void shouldGetCountryInstanceWithIdAndName() {
        final PojoClass pojoClass = PojoClassFactory.getPojoClass(Country.class);
        Country country = (Country) PojoComplexConstructorsTest.getInstance(pojoClass, new Object [] {1L, "Brasil"});
        Affirm.affirmNotNull("country pojo class doesn't have a constructor for the provided parameters", country);
    }

    @Test
    public void shouldTestAllPojoClassesWithCustomConstructors() {
        final Class [] clazzez = {Country.class, Customer.class};
        final Object [][][] constructors = {
                {
                        {1L, "Brasil"},
                        {2L, "Argentina"},
                        {3L, "Uruguai"},
                        {4L, "Paraguai"}
                },
                {
                        {1L, "John Snow"},
                        {2L, "Donald Duck"},
                        {3L, "Daffy Duck"}
                }
        };

        List<Object> instances = new ArrayList<>();
        for  (int i = 0; i < clazzez.length; i++) {
            Class clazz = clazzez[i];
            Object [][] classConstructors = constructors[i];
            for (int j = 0; j < classConstructors.length; j++) {
                Object instance = PojoComplexConstructorsTest.getInstance(clazz, classConstructors[j]);
                Affirm.affirmNotNull("The class [%s] cannot be instantiated", instance.getClass());
                instances.add(instance);
            }
        }

        Affirm.affirmNotNull("instances cannot be null", instances);
        Affirm.affirmTrue("no instances were added to the final result", instances.size() > 0);

    }

    private static Object getInstance(Class clazz, Object [] params) {
        final PojoClass pojoClass = PojoClassFactory.getPojoClass(clazz);
        return getInstance(pojoClass, params);
    }

    private static Object getInstance(PojoClass pojoClass, Object [] params) {
        if (pojoClass.isFinal() || pojoClass.isAbstract()) {
            throw ReflectionException.getInstance(("[%s] is not a concrete class and cannot be instantiated".formatted(pojoClass.getName())));
        }

        Object instance = null;
        final List<PojoMethod> constructors = pojoClass.getPojoConstructors();
        for (PojoMethod constructor : constructors) {
            if (constructor.getPojoParameters() != null && constructor.getPojoParameters().size() == params.length) {
                int i = 0;
                boolean found = true;
                for (PojoParameter pojoParameter : constructor.getPojoParameters()) {
                    if (!pojoParameter.getType().equals(params[i].getClass())) {
                        found = false;
                        break;
                    }
                    i++;
                }

                if (found) {
                    instance = constructor.invoke(null, params);
                    break;
                }
            }
        }

        return instance;
    }

}
