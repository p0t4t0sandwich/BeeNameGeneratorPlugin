package dev.neuralnexus.beenamegenerator.fabric;

import dev.neuralnexus.beenamegenerator.common.BeeNameGeneratorPlugin;
import dev.neuralnexus.beenamegenerator.fabric.commands.FabricBNGCommand;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.fabric.TemplateFabricPlugin;
import dev.neuralnexus.taterlib.fabric.abstractions.logger.FabricLogger;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.LoggerFactory;

/**
 * The BeeNameGenerator Fabric plugin.
 */
public class FabricBNGPlugin extends TemplateFabricPlugin implements BeeNameGeneratorPlugin {
    private static final String MOD_ID = "beenamegenerator";

    /**
     * @inheritDoc
     */
    @Override
    public AbstractLogger pluginLogger() {
        return new FabricLogger( "[TaterLib] ", LoggerFactory.getLogger(MOD_ID));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerCommands() {
        CommandRegistrationCallback.EVENT.register(FabricBNGCommand::register);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onInitializeServer() {
        pluginStart();
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> pluginStop());
    }
}
