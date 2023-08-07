package dev.neuralnexus.beenamegenerator.common;

import dev.neuralnexus.taterlib.common.TemplatePlugin;

/**
 * The BeeNameGenerator plugin interface.
 */
public interface BeeNameGeneratorPlugin extends TemplatePlugin {
    /**
     * Starts the BeeNameGenerator plugin.
     */
    default void pluginStart() {
        try {
            useLogger("BeeNameGenerator is running on " + getServerType() + " " + getServerVersion() + "!");

            // Start the TaterAPI
            BeeNameGenerator.start(pluginConfigPath(), pluginLogger());

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
    }

    /**
     * Stops the TaterAPI plugin.
     */
    default void pluginStop() {
        try {
            BeeNameGenerator.stop();
            useLogger("BeeNameGenerator has been disabled!");
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
