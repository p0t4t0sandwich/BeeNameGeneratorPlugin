package dev.neuralnexus.beenamegenerator.forge.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import dev.neuralnexus.beenamegenerator.common.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.common.commands.BNGCommand;
import dev.neuralnexus.beenamegenerator.forge.ForgeBNGPlugin;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import dev.neuralnexus.taterlib.forge.abstrations.entity.ForgeEntity;
import dev.neuralnexus.taterlib.forge.abstrations.player.ForgePlayer;
import net.minecraft.Util;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Bee;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

/**
 * Forge implementation of the BNG command.
 */
@Mod.EventBusSubscriber(modid = ForgeBNGPlugin.MOD_ID)
public class ForgeBNGCommand {
    /**
     * Registers the command.
     * @param event The command registration event.
     */
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        int permissionLevel;
        if (event.getEnvironment() == Commands.CommandSelection.DEDICATED) {
            // Check if LuckPerms is hooked
            permissionLevel = LuckPermsHook.isHooked() ? 0 : 4;
        } else {
            permissionLevel = 0;
        }

        event.getDispatcher().register(literal(BNGCommand.getCommandName())
            .requires(source -> source.hasPermission(permissionLevel))
            .then(argument("command", StringArgumentType.greedyString())
            .executes(context -> {
                try {
                    String[] args = context.getArgument("command", String.class).split(" ");

                    // Check if sender is a player
                    if (!(context.getSource().getEntity() instanceof ServerPlayer)) return 1;
                    ServerPlayer serverPlayer = (ServerPlayer) context.getSource().getEntity();
                    ForgePlayer player = new ForgePlayer(serverPlayer);

                    // Get the first bee Entity in the given radius
                    int radius = BeeNameGenerator.getRadius();
                    ForgeEntity bee = null;

                    ServerLevel world = context.getSource().getLevel();

                    List<Bee> bees = world.getEntities(
                            EntityType.BEE,
                            serverPlayer.getBoundingBox().inflate(radius, radius, radius),
                            entity -> entity.getCustomName() == null
                    );

                    if (bees.size() > 0) {
                        TargetingConditions predicate = TargetingConditions.forNonCombat().range(radius);
                        bee = new ForgeEntity(
                                world.getNearestEntity(
                                    bees, predicate, serverPlayer,
                                    serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ()
                        ));
                    }

                    ForgeEntity finalBee = bee;
                    AtomicInteger success = new AtomicInteger(1);
                    Util.backgroundExecutor().execute(() -> {
                        try {
                            // Execute command
                            BNGCommand.executeCommand(player, args, finalBee);
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
