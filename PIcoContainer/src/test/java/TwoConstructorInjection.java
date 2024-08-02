import javax.inject.Inject;

public class TwoConstructorInjection {
    private Dog puppy;
    private DoggyShelter Shelter;

    @Inject
    public TwoConstructorInjection(Dog puppy) {
        this.puppy = puppy;
    }

    @Inject
    public TwoConstructorInjection(DoggyShelter Shelter) {
        this.Shelter = Shelter;
    }
}
