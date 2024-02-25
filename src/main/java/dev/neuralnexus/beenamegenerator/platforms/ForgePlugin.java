package dev.neuralnexus.beenamegenerator.platforms;

import dev.neuralnexus.beenamegenerator.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.BeeNameGeneratorPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.minecraftforge.fml.common.Mod;

/** Forge entry point. */
@Mod(
        value = BeeNameGenerator.Constants.PROJECT_ID,
        modid = BeeNameGenerator.Constants.PROJECT_ID,
        useMetadata = true,
        serverSideOnly = true,
        acceptableRemoteVersions = "*")
public class ForgePlugin implements BeeNameGeneratorPlugin {
    public ForgePlugin() {
        pluginStart(this, null, null, new LoggerAdapter(BeeNameGenerator.Constants.PROJECT_NAME));
    }
}
