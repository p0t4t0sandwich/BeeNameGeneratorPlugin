package dev.neuralnexus.beenamegenerator.sponge.commands;

import dev.neuralnexus.beenamegenerator.common.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.common.commands.BNGCommand;
import dev.neuralnexus.taterlib.common.Utils;
import dev.neuralnexus.taterlib.sponge.abstractions.entity.SpongeEntity;
import dev.neuralnexus.taterlib.sponge.abstractions.player.SpongePlayer;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.plugin.PluginContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpongeBNGCommand implements CommandExecutor {
    Parameter.Value<String> commandArgs = Parameter.remainingJoinedStrings().key("command").build();

    /**
     * Register the command
     * @param container The plugin container
     * @param event The event
     */
    public void onRegisterCommands(PluginContainer container, final RegisterCommandEvent<Command.Parameterized> event) {
        // Register commands
        event.register(container, buildCommand(), BNGCommand.getCommandName());
    }

    /**
     * Build the command
     * @return The command
     */
    public Command.Parameterized buildCommand(){
        return Command
                .builder()
                .executor(new SpongeBNGCommand())
                .permission("bng.command")
                .shortDescription(Component.text("A command that generates a name for a bee."))
                .addParameter(commandArgs)
                .build();
    }

    /**
     * @inheritDoc
     */
    @Override
    public CommandResult execute(CommandContext context) throws CommandException {
        try {
            String[] args = context.requireOne(commandArgs).split(" ");

            // Check if sender is a player
            if (!(context.cause().root() instanceof Player)) {
                return CommandResult.builder()
                        .result(0).error(Component.text("You must be a player to run this command.")).build();
            }
            Player serverPlayer = (Player) context.cause().root();
            SpongePlayer player = new SpongePlayer(serverPlayer);

            // Get the first bee Entity in the given radius
            int radius = BeeNameGenerator.getRadius();
            SpongeEntity bee = null;

            // Get all bees in the world
            List<Entity> bees = new ArrayList<>(serverPlayer.world().entities());//.stream()
//                    .filter(entity -> entity.type().toString().equals("BEE") && !entity.customName().isPresent())
//                    .collect(Collectors.toList());

            // Get the closest bee
            double px = serverPlayer.location().x();
            double py = serverPlayer.location().y();
            double pz = serverPlayer.location().z();

            double closestDistance = 0;
            for (Entity b : bees) {
                double bx = b.location().x();
                double by = b.location().y();
                double bz = b.location().z();

                double distance = Math.sqrt(Math.pow(px - bx, 2) + Math.pow(py - by, 2) + Math.pow(pz - bz, 2));
                if (bee == null || distance < closestDistance) {
                    bee = new SpongeEntity(b);
                    closestDistance = distance;
                }
            }

            SpongeEntity finalBee = bee;
            Utils.runTaskAsync(() -> {
                try {
                    // Execute command
                    BNGCommand.executeCommand(player, args, finalBee);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return CommandResult.builder()
                    .result(0).error(Component.text(e.getMessage())).build();
        }
        return CommandResult.builder()
                .result(1).build();
    }
}
