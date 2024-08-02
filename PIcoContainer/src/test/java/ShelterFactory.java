import javax.inject.Inject;

public class ShelterFactory {
    private final DoggyShelter Shelter;

    @Inject
    public ShelterFactory(DoggyShelter Shelter) {
        this.Shelter = Shelter;
    }

    public DoggyShelter getShelter() {
        return this.Shelter;
    }
}
