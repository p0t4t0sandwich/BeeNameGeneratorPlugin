package dev.neuralnexus.beenamegenerator.config;

import dev.neuralnexus.beenamegenerator.config.sections.APIConfig;
import dev.neuralnexus.beenamegenerator.config.sections.NamingConfig;

/** A class for BeeNameGenerator configuration. */
public interface BNGConfig {
    /**
     * Get the version of the configuration.
     *
     * @return The version of the configuration.
     */
    int version();

    /**
     * Get the API configuration.
     *
     * @return The API configuration.
     */
    APIConfig api();

    /**
     * Get the Naming configuration.
     *
     * @return The Naming configuration.
     */
    NamingConfig naming();
}
