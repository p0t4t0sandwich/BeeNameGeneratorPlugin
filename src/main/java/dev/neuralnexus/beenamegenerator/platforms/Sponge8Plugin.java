package dev.neuralnexus.beenamegenerator.platforms;

import com.google.inject.Inject;

import dev.neuralnexus.beenamegenerator.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.BeeNameGeneratorPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import org.apache.logging.log4j.Logger;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

/** Sponge entry point. */
@Plugin(BeeNameGenerator.Constants.PROJECT_ID)
public class Sponge8Plugin implements BeeNameGeneratorPlugin {
    @Inject
    public Sponge8Plugin(PluginContainer container, Logger logger) {
        pluginStart(
                container,
                null,
                logger,
                new LoggerAdapter(BeeNameGenerator.Constants.PROJECT_NAME, logger));
    }
}
