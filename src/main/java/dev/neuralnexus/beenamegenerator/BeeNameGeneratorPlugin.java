package dev.neuralnexus.beenamegenerator;

import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.api.info.MinecraftVersion;
import dev.neuralnexus.taterlib.api.info.ServerType;
import dev.neuralnexus.taterlib.event.api.PluginEvents;
import dev.neuralnexus.taterlib.logger.AbstractLogger;
import dev.neuralnexus.taterlib.plugin.Plugin;

/** The main plugin interface. */
public interface BeeNameGeneratorPlugin extends Plugin {
    @Override
    default String name() {
        return BeeNameGenerator.Constants.PROJECT_NAME;
    }

    @Override
    default String id() {
        return BeeNameGenerator.Constants.PROJECT_ID;
    }

    @Override
    default void pluginStart(
            Object plugin, Object pluginServer, Object pluginLogger, AbstractLogger logger) {
        ServerType serverType = TaterAPIProvider.serverType();
        MinecraftVersion minecraftVersion = TaterAPIProvider.minecraftVersion();
        if (serverType.isBungeeCordBased()
                || serverType.isVelocityBased()
                || minecraftVersion.isOlderThan(MinecraftVersion.V1_15)) {
            logger.error(
                    BeeNameGenerator.Constants.PROJECT_NAME
                            + " is not supported on "
                            + serverType
                            + " "
                            + minecraftVersion
                            + "!");
            return;
        }
        logger.info(
                BeeNameGenerator.Constants.PROJECT_NAME
                        + " is running on "
                        + serverType
                        + " "
                        + minecraftVersion
                        + "!");

        PluginEvents.DISABLED.register(event -> pluginStop());
        BeeNameGenerator.start(plugin, pluginServer, pluginLogger, logger);
    }

    @Override
    default void pluginStop() {
        BeeNameGenerator.stop();
    }
}
