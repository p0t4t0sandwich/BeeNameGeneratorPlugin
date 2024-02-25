package dev.neuralnexus.beenamegenerator.config;

import dev.neuralnexus.beenamegenerator.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.config.sections.APIConfig;
import dev.neuralnexus.beenamegenerator.config.sections.NamingConfig;
import dev.neuralnexus.beenamegenerator.config.versions.BNGConfig_V1;
import dev.neuralnexus.taterlib.TaterLib;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;

import io.leangen.geantyref.TypeToken;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/** A class for loading BeeNameGenerator configuration. */
public class BNGConfigLoader {
    private static final Path configPath =
            Paths.get(
                    TaterAPIProvider.serverType().dataFolders().configFolder()
                            + File.separator
                            + BeeNameGenerator.Constants.PROJECT_NAME
                            + File.separator
                            + BeeNameGenerator.Constants.PROJECT_ID
                            + ".conf");
    private static final String defaultConfigPath =
            "source." + BeeNameGenerator.Constants.PROJECT_ID + ".conf";
    private static BNGConfig config;

    /** Copy the default configuration to the config folder. */
    public static void copyDefaults() {
        if (configPath.toFile().exists()) {
            return;
        }
        try {
            Files.createDirectories(configPath.getParent());
            Files.copy(
                    Objects.requireNonNull(
                            BNGConfigLoader.class
                                    .getClassLoader()
                                    .getResourceAsStream(defaultConfigPath)),
                    configPath);
        } catch (IOException e) {
            BeeNameGenerator.logger()
                    .error(
                            "An error occurred while copying the default configuration: "
                                    + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
    }

    /** Load the configuration from the file. */
    public static void load() {
        copyDefaults();

        final HoconConfigurationLoader loader =
                HoconConfigurationLoader.builder().path(configPath).build();
        CommentedConfigurationNode root;
        try {
            root = loader.load();
        } catch (ConfigurateException e) {
            BeeNameGenerator.logger()
                    .error("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
            return;
        }

        ConfigurationNode versionNode = root.node("version");
        int version = versionNode.getInt(1);

        APIConfig api = null;
        try {
            api = root.node("api").get(new TypeToken<APIConfig>() {});
        } catch (SerializationException e) {
            BeeNameGenerator.logger()
                    .error(
                            "An error occurred while loading the api configuration: "
                                    + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        NamingConfig naming = null;
        try {
            naming = root.node("naming").get(new TypeToken<NamingConfig>() {});
        } catch (SerializationException e) {
            BeeNameGenerator.logger()
                    .error(
                            "An error occurred while loading the naming configuration: "
                                    + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        switch (version) {
            case 1:
                config = new BNGConfig_V1(version, api, naming);
                break;
            default:
                BeeNameGenerator.logger().error("Unknown configuration version: " + version);
        }
    }

    /** Unload the configuration. */
    public static void unload() {
        config = null;
    }

    /** Save the configuration to the file. */
    public static void save() {
        if (config == null) {
            return;
        }
        final HoconConfigurationLoader loader =
                HoconConfigurationLoader.builder().path(configPath).build();
        CommentedConfigurationNode root;
        try {
            root = loader.load();
        } catch (ConfigurateException e) {
            TaterLib.logger()
                    .error("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
            return;
        }

        try {
            root.node("version").set(config.version());
        } catch (SerializationException e) {
            TaterLib.logger()
                    .error("An error occurred while saving this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        try {
            root.node("api").set(config.api());
        } catch (SerializationException e) {
            TaterLib.logger()
                    .error("An error occurred while saving this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        try {
            root.node("naming").set(config.naming());
        } catch (SerializationException e) {
            TaterLib.logger()
                    .error("An error occurred while saving this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        try {
            loader.save(root);
        } catch (ConfigurateException e) {
            TaterLib.logger()
                    .error("An error occurred while saving this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
    }

    /**
     * Get the loaded configuration.
     *
     * @return The loaded configuration.
     */
    public static BNGConfig config() {
        if (config == null) {
            load();
        }
        return config;
    }
}
