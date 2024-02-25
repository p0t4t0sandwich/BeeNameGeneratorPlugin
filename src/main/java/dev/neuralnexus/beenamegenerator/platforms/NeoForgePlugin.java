package dev.neuralnexus.beenamegenerator.platforms;

import com.mojang.logging.LogUtils;

import dev.neuralnexus.beenamegenerator.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.BeeNameGeneratorPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/** NeoForge entry point. */
@Mod(BeeNameGenerator.Constants.PROJECT_ID)
public class NeoForgePlugin implements BeeNameGeneratorPlugin {
    public NeoForgePlugin() {
        pluginStart(
                this,
                ServerLifecycleHooks.getCurrentServer(),
                LogUtils.getLogger(),
                new LoggerAdapter(BeeNameGenerator.Constants.PROJECT_NAME, LogUtils.getLogger()));
    }
}
