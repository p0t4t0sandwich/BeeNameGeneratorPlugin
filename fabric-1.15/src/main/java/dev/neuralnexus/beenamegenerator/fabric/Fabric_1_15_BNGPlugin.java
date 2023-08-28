package dev.neuralnexus.beenamegenerator.fabric;

import dev.neuralnexus.beenamegenerator.common.BeeNameGeneratorPlugin;
import dev.neuralnexus.beenamegenerator.fabric.commands.Fabric_1_15_BNGCommand;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.fabric.TemplateFabricPlugin;
import dev.neuralnexus.taterlib.fabric.abstractions.logger.FabricLogger;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.apache.logging.log4j.LogManager;

/**
 * The BeeNameGenerator Fabric plugin.
 */
public class Fabric_1_15_BNGPlugin extends TemplateFabricPlugin implements BeeNameGeneratorPlugin {
    private static final String MOD_ID = "beenamegenerator";

    /**
     * @inheritDoc
     */
    @Override
    public AbstractLogger pluginLogger() {
        return new FabricLogger( "[BeeNameGenerator] ", LogManager.getLogger(MOD_ID));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerCommands() {
        CommandRegistrationCallback.EVENT.register(Fabric_1_15_BNGCommand::register);
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
