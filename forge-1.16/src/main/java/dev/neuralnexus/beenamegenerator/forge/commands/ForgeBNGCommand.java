package dev.neuralnexus.beenamegenerator.forge.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import dev.neuralnexus.beenamegenerator.common.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.common.commands.BNGCommand;
import dev.neuralnexus.beenamegenerator.forge.ForgeBNGPlugin;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import dev.neuralnexus.taterlib.forge.abstrations.entity.ForgeEntity;
import dev.neuralnexus.taterlib.forge.abstrations.player.ForgePlayer;
import net.minecraft.command.Commands;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraft.command.Commands.*;

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
        if (event.getEnvironment() == Commands.EnvironmentType.DEDICATED) {
            // Check if LuckPerms is hooked
            permissionLevel = LuckPermsHook.isHooked() ? 0 : 4;
        } else {
            permissionLevel = 0;
        }

        event.getDispatcher().register(literal(BNGCommand.getCommandName())
            .requires(source -> source.hasPermissionLevel(permissionLevel))
            .then(argument("command", StringArgumentType.greedyString())
            .executes(context -> {
                try {
                    String[] args = context.getArgument("command", String.class).split(" ");

                    // Check if sender is a player
                    if (!(context.getSource().getEntity() instanceof PlayerEntity)) return 1;
                    PlayerEntity serverPlayer = (PlayerEntity) context.getSource().getEntity();
                    ForgePlayer player = new ForgePlayer(serverPlayer);

                    // Get the first bee Entity in the given radius
                    int radius = BeeNameGenerator.getRadius();
                    ForgeEntity bee = null;

                    World world = context.getSource().getWorld();

                    List<BeeEntity> bees = world.getEntitiesWithinAABB(
                            EntityType.BEE,
                            serverPlayer.getBoundingBox().expand(radius, radius, radius),
                            beeEntity -> {
                                beeEntity.getDisplayName();
                                return false;
                            }
                    );

                    if (bees.size() > 0) {
                        EntityPredicate predicate = EntityPredicate.DEFAULT.setDistance(radius);
                        bee = new ForgeEntity(
                                world.getClosestEntityWithinAABB(
                                        BeeEntity.class, predicate, serverPlayer,
                                        serverPlayer.getPosX(), serverPlayer.getPosY(), serverPlayer.getPosZ(),
                                        serverPlayer.getBoundingBox().expand(radius, radius, radius)
                        ));
                    }

                    ForgeEntity finalBee = bee;
                    AtomicInteger success = new AtomicInteger(1);
                    Util.getServerExecutor().execute(() -> {
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