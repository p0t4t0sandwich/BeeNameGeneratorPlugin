package dev.neuralnexus.taterlib.sponge;

import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.TaterLibPlugin;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import dev.neuralnexus.taterlib.sponge.abstractions.logger.SpongeLogger;
import dev.neuralnexus.taterlib.sponge.commands.SpongeTaterLibCommand;
import dev.neuralnexus.taterlib.sponge.listeners.player.SpongePlayerListener;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;

@Plugin("taterlib")
public class SpongeTaterLibPlugin extends TemplateSpongePlugin implements TaterLibPlugin {
    @Inject
    private Logger logger;

    @Inject
    private PluginContainer container;

    /**
     * @inheritDoc
     */
    @Override
    public AbstractLogger pluginLogger() {
        return new SpongeLogger(logger);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerHooks() {
        // Register LuckPerms hook
        if (Sponge.pluginManager().plugin("luckperms").isPresent()) {
            useLogger("LuckPerms detected, enabling LuckPerms hook.");
            TaterLib.addHook(new LuckPermsHook());
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerEventListeners() {
        // Register player event listeners
        Sponge.eventManager().registerListeners(this.container, new SpongePlayerListener());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerCommands() {}

    /**
     * Register commands.
     * @param event The event
     */
    @Listener
    public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {
        new SpongeTaterLibCommand().onRegisterCommands(container, event);
    }

    /**
     * Fired when the server starts.
     * @param event The event
     */
    @Listener
    public void onServerStarting(StartingEngineEvent<Server> event) {
        pluginStart();
    }

    /**
     * Fired when the server stops.
     * @param event The event
     */
    @Listener
    public void onServerStop(StoppingEngineEvent<Server> event) {
        pluginStop();
    }
}
