package dev.neuralnexus.beenamegenerator.config.versions;

import dev.neuralnexus.beenamegenerator.config.BNGConfig;
import dev.neuralnexus.beenamegenerator.config.sections.APIConfig;
import dev.neuralnexus.beenamegenerator.config.sections.NamingConfig;

/** A class for TaterComms configuration. */
public class BNGConfig_V1 implements BNGConfig {
    private final int version;
    private final APIConfig api;
    private final NamingConfig naming;

    /**
     * Create a new instance of the configuration.
     *
     * @param version The version of the configuration.
     * @param api The API configuration.
     * @param naming The Naming configuration.
     */
    public BNGConfig_V1(int version, APIConfig api, NamingConfig naming) {
        this.version = version;
        this.api = api;
        this.naming = naming;
    }

    @Override
    public int version() {
        return version;
    }

    @Override
    public APIConfig api() {
        return api;
    }

    @Override
    public NamingConfig naming() {
        return naming;
    }
}
