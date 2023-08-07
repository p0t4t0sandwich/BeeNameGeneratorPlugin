package dev.neuralnexus.beenamegenerator.common;

import dev.neuralnexus.beenamegenerator.common.api.BeeNameGeneratorAPIProvider;
import dev.neuralnexus.beenamegenerator.common.bngapi.BNGAPIHandler;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BeeNameGenerator {
    private static final BeeNameGenerator instance = new BeeNameGenerator();
    private static YamlDocument config;
    private static String configPath;
    private static AbstractLogger logger;
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
     * Start BeeNameGenerator
     * @param configPath The path to the config file
     */
    public static void start(String configPath, AbstractLogger logger) {
        BeeNameGenerator.configPath = configPath;
        TaterLib.logger = logger;

        // Config
        try {
            config = YamlDocument.create(new File("." + File.separator + configPath + File.separator + "BeeNameGenerator", "config.yml"),
                    Objects.requireNonNull(BeeNameGenerator.class.getClassLoader().getResourceAsStream("config.yml"))
            );
            config.reload();
        } catch (IOException | NullPointerException e) {
            logger.info("Failed to load config.yml!\n" + e.getMessage());
            e.printStackTrace();
        }

        if (STARTED) {
            logger.info("BeeNameGenerator has already started!");
            return;
        }
        STARTED = true;

        String baseURL = config.getString("api.base_url");
        String authToken = config.getString("api.auth_token");

        if (authToken == null || authToken.equals("") || authToken.equals("YOUR_AUTH_TOKEN")) {
            bngapiHandler = new BNGAPIHandler(baseURL);
        } else {
            logger.info("Using auth token: " + authToken);
            bngapiHandler = new BNGAPIHandler(baseURL, authToken);
        }

        logger.info("BeeNameGenerator has been started!");
        BeeNameGeneratorAPIProvider.register(instance);
    }

    /**
     * Start TaterAPI
     */
    public static void start() {
        start(configPath, logger);
    }

    /**
     * Stop BeeNameGenerator
     */
    public static void stop() {
        if (!STARTED) {
            logger.info("BeeNameGenerator has already stopped!");
            return;
        }
        STARTED = false;

        logger.info("BeeNameGenerator has been stopped!");
        BeeNameGeneratorAPIProvider.unregister();
    }

    /**
     * Reload BeeNameGenerator
     */
    public static void reload() {
        if (!STARTED) {
            logger.info("BeeNameGenerator has not been started!");
            return;
        }

        // Remove references to config and apiHandler
        config = null;
        bngapiHandler = null;

        // Stop BeeNameGenerator
        stop();

        // Start BeeNameGenerator
        start(configPath, logger);

        logger.info("BeeNameGenerator has been reloaded!");
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
