package not.savage.example.model;

import not.savage.cereal.annotation.Location;
import not.savage.cereal.impl.CerealObjectFactory;
import not.savage.cereal.impl.mode.profile.CerealProfileCache;

import java.util.UUID;

/**
 * Example implementation of a CerealProfileCache
 * A "CerealProfile" is a per-player profile that can be saved and loaded
 * from the dataset each time that player logs in or out. This profile is directly bound
 * to that single player (or its UUID). 1 per Player.
 */
@Location("data_profiles.json")
public class DataProfilesCache extends CerealProfileCache<DataProfile> {

    public DataProfilesCache() {
        super(
                "data_profiles", // table name | collection name.
                new CerealObjectFactory<>() {
                    @Override
                    protected DataProfile create(UUID uuid) {
                        // Can be used to do pre-creation logic
                        // In this example, we do nothing.
                        return new DataProfile();
                    }
                }
        );
    }
}
