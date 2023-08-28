package dev.neuralnexus.beenamegenerator.forge;

import dev.neuralnexus.taterlib.forge.TemplateForgePlugin;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;

import java.lang.reflect.Field;

/**
 * The BeeNameGenerator Forge plugin.
 */
@Mod(ForgeBNGPlugin.MOD_ID)
public class ForgeBNGPlugin {
    public static final String MOD_ID = "beenamegenerator";
    TemplateForgePlugin plugin;

    /**
     * Called when the Forge mod is initializing.
     */
    public ForgeBNGPlugin() {
        // Reflect to get the Minecraft and Forge versions from FMLLoader
        String version = null;

        try {
            Field mcVersionField = FMLLoader.class.getDeclaredField("mcVersion");
            mcVersionField.setAccessible(true);
            version = (String) mcVersionField.get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            System.out.println("[BeeNameGenerator] Failed to get Minecraft version, disabling plugin...");
            // TODO Nest reflection exceptions till one works
        }

        if (version != null) {
            System.out.println("[BeeNameGenerator] Minecraft version: " + version);

            // Super smart version check
            switch (version) {
                case "1.15":
                case "1.15.1":
                case "1.15.2":
                    System.out.println("[BeeNameGenerator] Detected Minecraft " + version + ", loading 1.15 implementation");
                    plugin = new Forge_1_15_BNGPlugin();
                case "1.16.1":
                case "1.16.2":
                case "1.16.3":
                case "1.16.4":
                case "1.16.5":
                    System.out.println("[BeeNameGenerator] Detected Minecraft " + version + ", loading 1.16 implementation");
                    plugin = new Forge_1_16_BNGPlugin();
                    break;
                case "1.17":
                case "1.17.1":
                    System.out.println("[BeeNameGenerator] Detected Minecraft " + version + ", loading 1.17 implementation");
                    plugin = new Forge_1_17_BNGPlugin();
                    break;
                case "1.18":
                case "1.18.1":
                case "1.18.2":
                    System.out.println("[BeeNameGenerator] Detected Minecraft " + version + ", loading 1.18 implementation");
                    plugin = new Forge_1_18_BNGPlugin();
                    break;
                case "1.19":
                case "1.19.1":
                case "1.19.2":
                case "1.19.3":
                case "1.19.4":
                    System.out.println("[BeeNameGenerator] Detected Minecraft " + version + ", loading 1.19 implementation");
                    plugin = new Forge_1_19_BNGPlugin();
                    break;
                case "1.20":
                case "1.20.1":
                case "1.20.2":
                    System.out.println("[BeeNameGenerator] Detected Minecraft " + version + ", loading 1.20 implementation");
                    plugin = new Forge_1_20_BNGPlugin();
                    break;
                default:
                    System.out.println("[BeeNameGenerator] Detected unsupported Minecraft version, disabling plugin...");
                    break;
            }
        }
    }
}
