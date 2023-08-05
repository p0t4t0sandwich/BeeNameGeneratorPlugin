package dev.neuralnexus.beenamegenerator.fabric.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.neuralnexus.beenamegenerator.common.commands.BNGCommand;
import dev.neuralnexus.taterlib.fabric.player.FabricPlayer;
import dev.neuralnexus.taterlib.common.commands.TaterLibCommand;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static dev.neuralnexus.taterlib.common.Utils.runTaskAsync;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

/**
 * Fabric implementation of the BNG command.
 */
public class FabricBNGCommand {
    /**
     * Registers the command.
     * @param dispatcher The command dispatcher.
     * @param registryAccess The command registry access.
     * @param environment The command registration environment.
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        int permissionLevel;
        if (environment.name().equals("DEDICATED")) {
            // Check if LuckPerms is hooked
            permissionLevel = LuckPermsHook.isHooked() ? 0 : 4;
        } else {
            permissionLevel = 0;
        }

        // Register command
        dispatcher.register(literal(BNGCommand.getCommandName())
                .requires(source -> source.hasPermissionLevel(permissionLevel))
                .then(argument("command", StringArgumentType.greedyString())
                        .executes(context -> {
                            runTaskAsync(() -> {
                                try {
                                    String[] args = context.getArgument("command", String.class).split(" ");

                                    // Check if sender is a player
                                    boolean isPlayer = context.getSource().getEntity() instanceof ServerPlayerEntity;
                                    FabricPlayer player = isPlayer ? new FabricPlayer((ServerPlayerEntity) context.getSource().getEntity()) : null;

                                    // Execute command
                                    TaterLibCommand.executeCommand(player, isPlayer, args);
                                } catch (Exception e) {
                                    System.err.println(e);
                                    e.printStackTrace();
                                }
                            });
                            return 1;
                        })
                ));
    }
}
