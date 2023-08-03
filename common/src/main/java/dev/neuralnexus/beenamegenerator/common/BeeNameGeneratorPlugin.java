package dev.neuralnexus.beenamegenerator.common;

import dev.neuralnexus.taterlib.common.Utils;

/**
 * The TaterAPI plugin interface.
 */
public interface BeeNameGeneratorPlugin {
    /**
     * Gets the config path.
     */
    String pluginConfigPath();

    /**
     * Use whatever logger is being used.
     * @param message The message to log
     */
    static void useLogger(String message) {
        System.out.println(message);
    }

    /**
     * Gets the server type.
     * @return The server type
     */
    default String getServerType() {
        return "unknown";
    }

    /**
     * Gets the server version.
     * @return The server version
     */
    default String getServerVersion() {
        return "unknown";
    }

    /**
     * Register hooks.
     */
    void registerHooks();


    /**
     * Registers event listeners.
     */
    void registerEventListeners();

    /**
     * Registers commands.
     */
    void registerCommands();

    /**
     * Starts the BeeNameGenerator plugin.
     */
    default void pluginStart() {
        Utils.runTaskAsync(() -> {
            try {
                useLogger("BeeNameGenerator is running on " + getServerType() + " " + getServerVersion() + "!");

                // Start the TaterAPI
                BeeNameGenerator.start(pluginConfigPath());

                // Register hooks
                registerHooks();

                // Register event listeners
                registerEventListeners();

                // Register commands
                registerCommands();

                useLogger("BeeNameGenerator has been enabled!");

            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }

    /**
     * Stops the TaterAPI plugin.
     */
    default void pluginStop() {
        Utils.runTaskAsync(() -> {
            try {
                BeeNameGenerator.stop();
                useLogger("BeeNameGenerator has been disabled!");
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
