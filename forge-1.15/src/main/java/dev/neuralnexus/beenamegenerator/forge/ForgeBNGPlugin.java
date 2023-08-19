package dev.neuralnexus.beenamegenerator.forge;

import dev.neuralnexus.beenamegenerator.common.BeeNameGeneratorPlugin;
import dev.neuralnexus.beenamegenerator.forge.commands.ForgeBNGCommand;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.forge.TemplateForgePlugin;
import dev.neuralnexus.taterlib.forge.abstrations.logger.ForgeLogger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import org.apache.logging.log4j.LogManager;

/**
 * The BeeNameGenerator Forge plugin.
 */
@Mod(ForgeBNGPlugin.MOD_ID)
public class ForgeBNGPlugin extends TemplateForgePlugin implements BeeNameGeneratorPlugin {
    public static final String MOD_ID = "beenamegenerator";

    /**
     * @inheritDoc
     */
    @Override
    public AbstractLogger pluginLogger() {
        return new ForgeLogger(LogManager.getLogger());
    }

    /**
     * Called when the Forge mod is initializing.
     */
    public ForgeBNGPlugin() {
        // Register server starting/stopping events
        MinecraftForge.EVENT_BUS.register(this);

        // Register commands
        MinecraftForge.EVENT_BUS.register(ForgeBNGCommand.class);
        pluginStart();
    }

    /**
     * Called when the server is stopping.
     * @param event The event.
     */
    @SubscribeEvent
    public void onServerStopped(FMLServerStoppedEvent event) {
        pluginStop();
    }
}
