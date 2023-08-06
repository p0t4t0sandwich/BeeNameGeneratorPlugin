package dev.neuralnexus.beenamegenerator.fabric.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.neuralnexus.beenamegenerator.common.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.common.commands.BNGCommand;
import dev.neuralnexus.taterlib.common.Utils;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import dev.neuralnexus.taterlib.fabric.abstractions.entity.FabricEntity;
import dev.neuralnexus.taterlib.fabric.abstractions.player.FabricPlayer;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
                .requires(source -> source.hasPermissionLevel(permissionLevel)
                ).then(argument("command", StringArgumentType.greedyString()
                ).executes(context -> {
                    try {
                        String[] args = context.getArgument("command", String.class).split(" ");

                        // Check if sender is a player
                        if (!(context.getSource().getEntity() instanceof ServerPlayerEntity)) return 1;
                        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) context.getSource().getEntity();
                        FabricPlayer player = new FabricPlayer(serverPlayer);

                        // Get the first bee Entity in the given radius
                        int radius = BeeNameGenerator.getRadius();
                        Entity bee = null;

                        World world = serverPlayer.getWorld();

                        List<BeeEntity> bees = world.getEntitiesByType(
                                EntityType.BEE,
                                serverPlayer.getBoundingBox().expand(radius, radius, radius),
                                entity -> entity.getCustomName() == null
                        );

                        if (bees.size() > 0) {
                            TargetPredicate predicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(radius);
                            bee = world.getClosestEntity(
                                    bees, predicate, serverPlayer,
                                    serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ()
                            );
                        }

                        Entity finalBee = bee;
                        AtomicInteger success = new AtomicInteger(1);
                        Utils.runTaskAsync(() -> {
                            try {
                                // Execute command
                                BNGCommand.executeCommand(player, args, new FabricEntity(finalBee));
                            } catch (Exception e) {
                                System.out.println(e);
                                e.printStackTrace();
                                success.set(0);
                            }
                        });
                        return success.get();
                    } catch (Exception e) {
                        System.err.println(e);
                        e.printStackTrace();
                        return 0;
                    }
                })
            ));
    }
}
