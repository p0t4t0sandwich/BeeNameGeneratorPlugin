package dev.neuralnexus.beenamegenerator;

import com.google.common.collect.ImmutableMap;

import dev.neuralnexus.beenamegenerator.api.BNGAPIHandler;
import dev.neuralnexus.beenamegenerator.api.BNGAPIProvider;
import dev.neuralnexus.beenamegenerator.commands.BNGCommand;
import dev.neuralnexus.beenamegenerator.config.BNGConfigLoader;
import dev.neuralnexus.taterlib.api.info.ServerType;
import dev.neuralnexus.taterlib.bstats.MetricsAdapter;
import dev.neuralnexus.taterlib.event.api.CommandEvents;
import dev.neuralnexus.taterlib.logger.AbstractLogger;

/** The BeeNameGenerator class. */
public class BeeNameGenerator {
    private static final BeeNameGenerator instance = new BeeNameGenerator();
    private static boolean STARTED = false;
    private static boolean RELOADED = false;
    private Object plugin;
    private Object pluginServer;
    private Object pluginLogger;
    private AbstractLogger logger;

    /**
     * Getter for the singleton instance of the class.
     *
     * @return The singleton instance
     */
    public static BeeNameGenerator instance() {
        return instance;
    }

    /**
     * Get the plugin
     *
     * @return The plugin
     */
    public static Object plugin() {
        return instance.plugin;
    }

    /**
     * Set the plugin
     *
     * @param plugin The plugin
     */
    private static void setPlugin(Object plugin) {
        instance.plugin = plugin;
    }

    /**
     * Set the plugin server
     *
     * @param pluginServer The plugin server
     */
    private static void setPluginServer(Object pluginServer) {
        instance.pluginServer = pluginServer;
    }

    /**
     * Set the plugin logger
     *
     * @param pluginLogger The plugin logger
     */
    private static void setPluginLogger(Object pluginLogger) {
        instance.pluginLogger = pluginLogger;
    }

    /**
     * Get the logger
     *
     * @return The logger
     */
    public static AbstractLogger logger() {
        return instance.logger;
    }

    /**
     * Set the logger
     *
     * @param logger The logger
     */
    private static void setLogger(AbstractLogger logger) {
        instance.logger = logger;
    }

    /**
     * Start
     *
     * @param plugin The plugin
     * @param pluginServer The plugin server
     * @param pluginLogger The plugin logger
     * @param logger The logger
     */
    public static void start(
            Object plugin, Object pluginServer, Object pluginLogger, AbstractLogger logger) {
        if (pluginServer != null) {
            setPluginServer(pluginServer);
        }
        if (pluginLogger != null) {
            setPluginLogger(pluginLogger);
        }
        setPlugin(plugin);
        setLogger(logger);

        // Set up bStats
        MetricsAdapter.setupMetrics(
                plugin,
                pluginServer,
                pluginLogger,
                ImmutableMap.<ServerType, Integer>builder()
                        .put(ServerType.BUKKIT, 21121)
                        .put(ServerType.SPONGE, 21122)
                        .build());

        if (STARTED) {
            logger.info(Constants.PROJECT_NAME + " has already started!");
            return;
        }
        STARTED = true;

        if (!RELOADED) {
            CommandEvents.REGISTER_COMMAND.register(
                    event -> event.registerCommand(plugin, new BNGCommand()));
        }

        // Config
        BNGConfigLoader.load();

        // Register API
        String baseURL = BNGConfigLoader.config().api().baseUrl();
        String authToken = BNGConfigLoader.config().api().authToken();

        BNGAPIHandler bngapiHandler;
        if (authToken == null || authToken.isEmpty() || authToken.equals("YOUR_AUTH_TOKEN")) {
            bngapiHandler = new BNGAPIHandler(baseURL);
        } else {
            logger().info("Using auth token: " + authToken);
            bngapiHandler = new BNGAPIHandler(baseURL, authToken);
        }
        BNGAPIProvider.register(bngapiHandler);

        logger().info(Constants.PROJECT_NAME + " has been started!");
    }

    /** Start */
    public static void start() {
        start(instance.plugin, instance.pluginServer, instance.pluginLogger, instance.logger);
    }

    /** Stop */
    public static void stop() {
        if (!STARTED) {
            logger().info(Constants.PROJECT_NAME + " has already stopped!");
            return;
        }
        STARTED = false;
        RELOADED = true;

        // Remove references to objects
        BNGConfigLoader.unload();

        // Unregister API
        BNGAPIProvider.unregister();

        logger().info(Constants.PROJECT_NAME + " has been stopped!");
    }

    /** Reload */
    public static void reload() {
        if (!STARTED) {
            logger().info(Constants.PROJECT_NAME + " has not been started!");
            return;
        }
        RELOADED = true;

        // Stop
        stop();

        // Start
        start();

        logger().info(Constants.PROJECT_NAME + " has been reloaded!");
    }

    /** Constants used throughout the plugin. */
    public static class Constants {
        public static final String PROJECT_NAME = "BeeNameGenerator";
        public static final String PROJECT_ID = "beenamegenerator";
        public static final String PROJECT_VERSION = "1.0.2-SNAPSHOT";
        public static final String PROJECT_AUTHORS = "p0t4t0sandwich";
        public static final String PROJECT_DESCRIPTION =
                "A Minecraft plugin that implements the bee-name-generator API to generate bee names automagically.";
        public static final String PROJECT_URL =
                "https://github.com/p0t4t0sandwich/BeeNameGeneratorPlugin";
    }
}
