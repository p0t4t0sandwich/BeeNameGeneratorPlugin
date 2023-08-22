package dev.neuralnexus.beenamegenerator.common;

import dev.neuralnexus.beenamegenerator.common.api.BeeNameGeneratorAPIProvider;
import dev.neuralnexus.beenamegenerator.common.bngapi.BNGAPIHandler;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.lib.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The BeeNameGenerator class.
 */
public class BeeNameGenerator {
    private static final BeeNameGenerator instance = new BeeNameGenerator();
    private static YamlDocument config;
    private static String configPath;
    public static AbstractLogger logger;
    private static boolean STARTED = false;
    private static final ArrayList<Object> hooks = new ArrayList<>();
    private static BNGAPIHandler bngapiHandler = null;

    /**
     * Constructor for the BeeNameGenerator class.
     */
    public BeeNameGenerator() {}

    /**
     * Getter for the singleton instance of the BeeNameGenerator class.
     * @return The singleton instance
     */
    public static BeeNameGenerator getInstance() {
        return instance;
    }

    /**
     * Add a hook to the hooks list
     * @param hook The hook to add
     */
    public static void addHook(Object hook) {
        hooks.add(hook);
    }

    /**
     * Use the Logger
     * @param message The message to log
     */
    public static void useLogger(String message) {
        logger.info(message);
    }

    /**
     * Start
     * @param configPath The path to the config file
     * @param logger The logger
     */
    public static void start(String configPath, AbstractLogger logger) {
        BeeNameGenerator.configPath = configPath;
        BeeNameGenerator.logger = logger;

        // Config
        try {
            config = YamlDocument.create(new File("." + File.separator + configPath + File.separator + "BeeNameGenerator", "beenamegenerator.config.yml"),
                    Objects.requireNonNull(BeeNameGenerator.class.getClassLoader().getResourceAsStream("beenamegenerator.config.yml"))
            );
            config.reload();
        } catch (IOException | NullPointerException e) {
            useLogger("Failed to load beenamegenerator.config.yml!\n" + e.getMessage());
            e.printStackTrace();
        }

        if (STARTED) {
            useLogger("BeeNameGenerator has already started!");
            return;
        }
        STARTED = true;

        String baseURL = config.getString("api.base_url");
        String authToken = config.getString("api.auth_token");

        if (authToken == null || authToken.equals("") || authToken.equals("YOUR_AUTH_TOKEN")) {
            bngapiHandler = new BNGAPIHandler(baseURL);
        } else {
            useLogger("Using auth token: " + authToken);
            bngapiHandler = new BNGAPIHandler(baseURL, authToken);
        }

        useLogger("BeeNameGenerator has been started!");
        BeeNameGeneratorAPIProvider.register(instance);
    }

    /**
     * Start
     */
    public static void start() {
        start(configPath, logger);
    }

    /**
     * Stop
     */
    public static void stop() {
        if (!STARTED) {
            useLogger("BeeNameGenerator has already stopped!");
            return;
        }
        STARTED = false;

        // Remove references to objects
        config = null;
        bngapiHandler = null;

        useLogger("BeeNameGenerator has been stopped!");
        BeeNameGeneratorAPIProvider.unregister();
    }

    /**
     * Reload
     */
    public static void reload() {
        if (!STARTED) {
            useLogger("BeeNameGenerator has not been started!");
            return;
        }

        // Stop
        stop();

        // Start
        start(configPath, logger);

        useLogger("BeeNameGenerator has been reloaded!");
    }

    /**
     * Get the radius from the config.
     * @return The radius
     */
    public static int getRadius() {
        return config.getInt("naming.radius");
    }

    /**
     * Get the payment item from the config.
     */
    public static String getPaymentItem() {
        return config.getString("naming.payment_item");
    }

    /**
     * Getter for the BeeNameGenerator API Handler
     * @return The BeeNameGenerator API Handler
     */
    public static BNGAPIHandler getBNGAPIHandler() {
        return bngapiHandler;
    }
}
