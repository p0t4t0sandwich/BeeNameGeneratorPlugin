package dev.neuralnexus.beenamegenerator.neoforge.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import dev.neuralnexus.beenamegenerator.common.commands.BNGCommand;
import dev.neuralnexus.taterlib.common.Utils;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import dev.neuralnexus.taterlib.forge.player.ForgePlayer;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

/**
 * NeoForge implementation of the BNG command.
 */
@Mod.EventBusSubscriber(modid = "beenamegenerator")
public class NeoForgeBNGCommand {
    /**
     * Registers the command.
     * @param event The command registration event.
     */
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        int permissionLevel;
        if (event.getCommandSelection() == Commands.CommandSelection.DEDICATED) {
            // Check if LuckPerms is hooked
            permissionLevel = LuckPermsHook.isHooked() ? 0 : 4;
        } else {
            permissionLevel = 0;
        }

        event.getDispatcher().register(literal(BNGCommand.getCommandName())
                .requires(source -> source.hasPermission(permissionLevel))
                .then(argument("command", StringArgumentType.greedyString())
                        .executes(context -> {
                            Utils.runTaskAsync(() -> {
                                try {
                                    String[] args = context.getArgument("command", String.class).split(" ");

                                    // Check if sender is a player
                                    boolean isPlayer = context.getSource().getEntity() instanceof ServerPlayer;
                                    ForgePlayer player = isPlayer ? new ForgePlayer((ServerPlayer) context.getSource().getEntity()) : null;

                                    // Execute command
                                    BNGCommand.executeCommand(player, isPlayer, args);
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
