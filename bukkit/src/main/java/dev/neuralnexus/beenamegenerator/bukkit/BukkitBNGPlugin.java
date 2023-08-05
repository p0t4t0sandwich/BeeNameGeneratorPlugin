package dev.neuralnexus.beenamegenerator.bukkit;

import dev.neuralnexus.beenamegenerator.bukkit.commands.BukkitBNGCommand;
import dev.neuralnexus.beenamegenerator.common.BeeNameGeneratorPlugin;
import dev.neuralnexus.beenamegenerator.common.commands.BNGCommand;
import dev.neuralnexus.taterlib.common.Utils;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The BeeNameGenerator Bukkit plugin.
 */
public class BukkitBNGPlugin extends JavaPlugin implements BeeNameGeneratorPlugin {
    /**
     * Use whatever logger is being used.
     * @param message The message to log
     */
    public void useLogger(String message) {
        getLogger().info(message);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String pluginConfigPath() {
        return "plugins";
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getServerType() {
        return Utils.getBukkitServerType();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerHooks() {}

    /**
     * @inheritDoc
     */
    @Override
    public void registerEventListeners() {}

    /**
     * @inheritDoc
     */
    @Override
    public void registerCommands() {
        getCommand(BNGCommand.getCommandName()).setExecutor(new BukkitBNGCommand());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onEnable() {
        pluginStart();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onDisable() {
        pluginStop();
    }
}
