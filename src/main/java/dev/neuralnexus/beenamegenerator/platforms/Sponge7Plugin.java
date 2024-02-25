package dev.neuralnexus.beenamegenerator.platforms;

import com.google.inject.Inject;

import dev.neuralnexus.beenamegenerator.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.BeeNameGeneratorPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import org.slf4j.Logger;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

/** Sponge entry point. */
@Plugin(
        id = BeeNameGenerator.Constants.PROJECT_ID,
        name = BeeNameGenerator.Constants.PROJECT_NAME,
        version = BeeNameGenerator.Constants.PROJECT_VERSION,
        description = BeeNameGenerator.Constants.PROJECT_DESCRIPTION)
public class Sponge7Plugin implements BeeNameGeneratorPlugin {
    @Inject
    public Sponge7Plugin(PluginContainer container, Logger logger) {
        pluginStart(
                container,
                null,
                logger,
                new LoggerAdapter(BeeNameGenerator.Constants.PROJECT_NAME, logger));
    }
}
