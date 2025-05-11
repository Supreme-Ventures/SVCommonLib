package not.savage.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import not.savage.cereal.impl.mode.profile.CerealDataProfile;

@Getter @Setter
@NoArgsConstructor
public class DataProfile extends CerealDataProfile {

    // Data fields here
    private int playerLevel = 1;
    private double playerBalance = 0;

    /**
     * Called when the profile is first initialized. Is not called on each load.
     * Useful for initializing default values, and other one-time setup.
     */
    @Override
    public void initialize() {

    }

    /**
     * Called each time the profile is loaded from the database.
     * Useful to cache states when loading from the database, or initializing other services.
     */
    @Override
    public void load() {

    }
}
