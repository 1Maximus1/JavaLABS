import com.di.implementation.BasicEnvironment;
import com.di.util.BindException;
import com.di.util.CircularInjectException;
import com.di.util.UnregisteredComponentException;
import org.di.Container;
import org.di.Environment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MyTest {
    private final Environment environment = new BasicEnvironment();

    public MyTest() {
    }

    @Test
    public void shouldThrowBindExceptionWhenTryingToRebindWithClass() {
        Assertions.assertThrows(BindException.class, () -> {
            this.environment.configure((binder) -> {
                binder.bind(Pets.class, Dog.class);
                binder.bind(Pets.class, Cat.class);
            });
        });
    }

    @Test
    public void shouldThrowBindExceptionWhenTryingToRebindWithInstance() {
        Assertions.assertThrows(BindException.class, () -> {
            this.environment.configure((binder) -> {
                binder.bind(Pets.class, Dog.class);
                binder.bind(Pets.class, new Cat());
            });
        });
    }

    @Test
    public void shouldThrowBindExceptionWhenTryingToRegisterAbstractClass() {
        Assertions.assertThrows(BindException.class, () -> {
            this.environment.configure((binder) -> {
                binder.bind(Pets.class);
            });
        });
    }

    @Test
    public void shouldThrowBindExceptionWhenTryingToRegisterInterface() {
        Assertions.assertThrows(BindException.class, () -> {
            this.environment.configure((binder) -> {
                binder.bind(Animal.class);
            });
        });
    }

    @Test
    public void shouldThrowBindExceptionWhenTryingToRegisterNull() {
        Assertions.assertThrows(BindException.class, () -> {
            this.environment.configure((binder) -> {
                binder.bind((Class) null);
            });
        });
    }

    @Test
    public void shouldThrowBindExceptionWhenTryingToRegisterClassWithMoreThanOneInjectionConstructor() {
        Assertions.assertThrows(BindException.class, () -> {
            this.environment.configure((binder) -> {
                binder.bind(TwoConstructorInjection.class);
            });
        });
    }

    @Test
    public void shouldThrowUnregisteredComponentExceptionWhenTryingToGetUnregisteredComponent() {
        Container container = this.environment.configure((binder) -> {
        });
        Assertions.assertThrows(UnregisteredComponentException.class, () -> {
            container.getComponent(Dog.class);
        });
    }

    @Test
    public void shouldThrowUnregisteredComponentExceptionWhenTryingToGetComponentWhichDependenciesNotRegistered() {
        Container container = this.environment.configure((binder) -> {
            binder.bind(DoggyShelter.class);
        });
        Assertions.assertThrows(UnregisteredComponentException.class, () -> {
            container.getComponent(DoggyShelter.class);
        });
    }

    @Test
    public void shouldThrowCircularInjectExceptionWhenTryingToRegisterComponentWithCircularInjectDependency() {
        Assertions.assertThrows(CircularInjectException.class, () -> {
            this.environment.configure((binder) -> {
                binder.bind(A.class);
                binder.bind(B.class);
                binder.bind(C.class);
            });
        });
    }

    @Test
    public void shouldResolveSingletonWithInjection() {
        Container container = this.environment.configure((binder) -> {
            binder.bind(Dog.class);
            binder.bind(DoggyShelter.class);
        });
        DoggyShelter puppy1 = (DoggyShelter) container.getComponent(DoggyShelter.class);
        DoggyShelter puppy2 = (DoggyShelter) container.getComponent(DoggyShelter.class);
        Assertions.assertSame(puppy1, puppy2);
        Assertions.assertSame(puppy1.getPuppy(), puppy2.getPuppy());
    }

    @Test
    public void shouldResolveNestedInjectDependency() {
        Container container = this.environment.configure((binder) -> {
            binder.bind(ShelterFactory.class);
            binder.bind(Dog.class);
            binder.bind(DoggyShelter.class);
        });
        ShelterFactory c = (ShelterFactory) container.getComponent(ShelterFactory.class);
        Assertions.assertSame(c.getShelter(), container.getComponent(DoggyShelter.class));
    }
}