package dev.neuralnexus.beenamegenerator.platforms;

import dev.neuralnexus.beenamegenerator.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.BeeNameGeneratorPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/** Bukkit entry point. */
public class BukkitPlugin extends JavaPlugin implements BeeNameGeneratorPlugin {
    public BukkitPlugin() {
        pluginStart(
                this,
                Bukkit.getServer(),
                Bukkit.getLogger(),
                new LoggerAdapter(BeeNameGenerator.Constants.PROJECT_NAME, Bukkit.getLogger()));
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}
}
