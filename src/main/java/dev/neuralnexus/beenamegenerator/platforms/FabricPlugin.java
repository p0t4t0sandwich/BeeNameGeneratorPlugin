package dev.neuralnexus.beenamegenerator.platforms;

import dev.neuralnexus.beenamegenerator.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.BeeNameGeneratorPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.fabricmc.api.ModInitializer;

/** Fabric entry point. */
public class FabricPlugin implements ModInitializer, BeeNameGeneratorPlugin {
    public FabricPlugin() {
        pluginStart(this, null, null, new LoggerAdapter(BeeNameGenerator.Constants.PROJECT_NAME));
    }

    @Override
    public void onInitialize() {}
}
