package dev.neuralnexus.beenamegenerator.config.sections;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

/** The API config section. */
@ConfigSerializable
public class APIConfig {
    @Setting("baseUrl")
    private String baseUrl;

    @Setting("authToken")
    private String authToken;

    /**
     * Get the base URL.
     *
     * @return The base URL
     */
    public String baseUrl() {
        return baseUrl;
    }

    /**
     * Get the auth token.
     *
     * @return The auth token
     */
    public String authToken() {
        return authToken;
    }
}
