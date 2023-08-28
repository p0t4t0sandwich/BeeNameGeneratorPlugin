package dev.neuralnexus.beenamegenerator.forge;

import com.mojang.logging.LogUtils;
import dev.neuralnexus.beenamegenerator.common.BeeNameGeneratorPlugin;
import dev.neuralnexus.beenamegenerator.forge.commands.Forge_1_19_BNGCommand;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.forge.TemplateForgePlugin;
import dev.neuralnexus.taterlib.forge.abstrations.logger.ForgeLogger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * The BeeNameGenerator Forge plugin.
 */
//@Mod(Forge_1_19_BNGPlugin.MOD_ID)
public class Forge_1_19_BNGPlugin extends TemplateForgePlugin implements BeeNameGeneratorPlugin {
    public static final String MOD_ID = "beenamegenerator";

    /**
     * @inheritDoc
     */
    @Override
    public AbstractLogger pluginLogger() {
        return new ForgeLogger(LogUtils.getLogger());
    }

    /**
     * Called when the Forge mod is initializing.
     */
    public Forge_1_19_BNGPlugin() {
        // Register server starting/stopping events
        MinecraftForge.EVENT_BUS.register(this);

        // Register commands
        MinecraftForge.EVENT_BUS.register(Forge_1_19_BNGCommand.class);
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
