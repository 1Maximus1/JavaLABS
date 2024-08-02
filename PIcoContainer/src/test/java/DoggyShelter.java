import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DoggyShelter {
    private final Dog puppy;

    @Inject
    public DoggyShelter(Dog puppy) {
        this.puppy = puppy;
    }

    public Dog getPuppy() {
        return this.puppy;
    }
}