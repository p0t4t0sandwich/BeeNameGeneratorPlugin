package dev.neuralnexus.beenamegenerator.bukkit.commands;

import dev.neuralnexus.beenamegenerator.bukkit.abstractions.entity.BukkitEntity;
import dev.neuralnexus.beenamegenerator.common.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.common.commands.BNGCommand;
import dev.neuralnexus.taterlib.bukkit.player.BukkitPlayer;
import dev.neuralnexus.taterlib.common.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Bukkit implementation of the BNG command.
 */
public class BukkitBNGCommand implements CommandExecutor {
    /**
     * @inheritDoc
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            // Check if sender is a player
            if (!(sender instanceof Player)) return true;
            Player serverPlayer = (Player) sender;
            BukkitPlayer player = new BukkitPlayer(serverPlayer);

            // Get the first bee Entity in the given radius
            int radius = BeeNameGenerator.getRadius();
            Entity bee = null;

            Collection<Entity> entities = serverPlayer.getWorld().getNearbyEntities(serverPlayer.getLocation(), radius, radius, radius);
            for (Entity entity : entities) {
                if (entity.getType().toString().equals("BEE") && entity.getCustomName() == null) {
                    bee = entity;
                    break;
                }
            }

            Entity finalBee = bee;
            AtomicBoolean success = new AtomicBoolean(true);
            Utils.runTaskAsync(() -> {
                try {
                    // Execute command
                    BNGCommand.executeCommand(player, args, new BukkitEntity(finalBee));
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                    success.set(false);
                }
            });
            return success.get();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
