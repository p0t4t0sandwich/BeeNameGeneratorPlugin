package dev.neuralnexus.beenamegenerator.fabric;

import dev.neuralnexus.taterlib.fabric.TemplateFabricPlugin;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.Optional;

/**
 * The BeeNameGenerator Fabric plugin.
 */
public class FabricBNGPlugin implements DedicatedServerModInitializer {
    TemplateFabricPlugin plugin;

    /**
     * @inheritDoc
     */
    @Override
    public void onInitializeServer() {
        // Get version info from fabric API
        Optional<ModContainer> minecraftModContainer = FabricLoader.getInstance().getModContainer("minecraft");
        if (minecraftModContainer.isPresent()) {
            String version = minecraftModContainer.get().getMetadata().getVersion().getFriendlyString();
            System.out.println("[BeeNameGenerator] Minecraft version: " + version);

            // Super smart version check
            switch (version) {
                case "1.15":
                case "1.15.1":
                case "1.15.2":
                case "1.16.1":
                case "1.16.2":
                case "1.16.3":
                case "1.16.4":
                case "1.16.5":
                    System.out.println("[BeeNameGenerator] Detected Minecraft " + version + ", loading 1.15 implementation");
                    plugin = new Fabric_1_15_BNGPlugin();
                    plugin.onInitializeServer();
                    break;
                case "1.17":
                case "1.17.1":
                case "1.18":
                case "1.18.1":
                case "1.18.2":
                case "1.19":
                case "1.19.1":
                case "1.19.2":
                case "1.19.3":
                case "1.19.4":
                    System.out.println("[BeeNameGenerator] Detected Minecraft " + version + ", loading 1.17 implementation");
                    plugin = new Fabric_1_17_BNGPlugin();
                    plugin.onInitializeServer();
                    break;
                case "1.20":
                case "1.20.1":
                case "1.20.2":
                    System.out.println("[BeeNameGenerator] Detected Minecraft " + version + ", loading 1.20 implementation");
                    plugin = new Fabric_1_20_BNGPlugin();
                    plugin.onInitializeServer();
                    break;
                default:
                    System.out.println("[BeeNameGenerator] Detected unsupported Minecraft version, disabling plugin...");
                    break;
            }
        }
    }
}
