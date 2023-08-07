package dev.neuralnexus.beenamegenerator.bukkit;

import dev.neuralnexus.beenamegenerator.bukkit.commands.BukkitBNGCommand;
import dev.neuralnexus.beenamegenerator.common.BeeNameGeneratorPlugin;
import dev.neuralnexus.beenamegenerator.common.commands.BNGCommand;
import dev.neuralnexus.taterlib.bukkit.TemplateBukkitPlugin;

/**
 * The BeeNameGenerator Bukkit plugin.
 */
public class BukkitBNGPlugin extends TemplateBukkitPlugin implements BeeNameGeneratorPlugin {
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
