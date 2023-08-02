package dev.neuralnexus.beenamegenerator.common.api;

import dev.neuralnexus.beenamegenerator.common.BeeNameGenerator;

/**
 * BeeNameGenerator API Provider
 */
public class BeeNameGeneratorAPIProvider {
    private static BeeNameGenerator instance = null;

    /**
     * Get the instance of BeeNameGenerator
     * @return The instance of BeeNameGenerator
     */
    public static BeeNameGenerator get() {
        if (instance == null) {
            throw new NotLoadedException();
        }
        return instance;
    }

    /**
     * DO NOT USE THIS METHOD, IT IS FOR INTERNAL USE ONLY
     * @param instance: The instance of BeeNameGenerator
     */
    public static void register(BeeNameGenerator instance) {
        BeeNameGeneratorAPIProvider.instance = instance;
    }

    /**
     * DO NOT USE THIS METHOD, IT IS FOR INTERNAL USE ONLY
     */
    public static void unregister() {
        BeeNameGeneratorAPIProvider.instance = null;
    }

    /**
     * Throw this exception when the API hasn't loaded yet, or you don't have the BeeNameGenerator plugin installed.
     */
    private static final class NotLoadedException extends IllegalStateException {
        private static final String MESSAGE = "The API hasn't loaded yet, or you don't have the BeeNameGenerator plugin installed.";

        NotLoadedException() {
            super(MESSAGE);
        }
    }
}
