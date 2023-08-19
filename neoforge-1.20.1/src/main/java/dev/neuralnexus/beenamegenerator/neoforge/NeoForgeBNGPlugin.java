package dev.neuralnexus.beenamegenerator.neoforge;

import com.mojang.logging.LogUtils;
import dev.neuralnexus.beenamegenerator.common.BeeNameGeneratorPlugin;
import dev.neuralnexus.beenamegenerator.neoforge.commands.NeoForgeBNGCommand;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.forge.TemplateForgePlugin;
import dev.neuralnexus.taterlib.neoforge.abstractions.logger.NeoForgeLogger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * The BeeNameGenerator Forge plugin.
 */
@Mod(NeoForgeBNGPlugin.MOD_ID)
public class NeoForgeBNGPlugin extends TemplateForgePlugin implements BeeNameGeneratorPlugin {
    public static final String MOD_ID = "beenamegenerator";

    /**
     * @inheritDoc
     */
    @Override
    public AbstractLogger pluginLogger() {
        return new NeoForgeLogger(LogUtils.getLogger());
    }

    /**
     * Called when the Forge mod is initializing.
     */
    public NeoForgeBNGPlugin() {
        // Register server starting/stopping events
        MinecraftForge.EVENT_BUS.register(this);

        // Register commands
        MinecraftForge.EVENT_BUS.register(NeoForgeBNGCommand.class);
        pluginStart();
    }

    /**
     * Called when the server is stopping.
     * @param event The event.
     */
    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        pluginStop();
    }
}
