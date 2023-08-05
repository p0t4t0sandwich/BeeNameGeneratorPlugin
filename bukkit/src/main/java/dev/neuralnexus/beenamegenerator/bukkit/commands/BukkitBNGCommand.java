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
        AtomicBoolean success = new AtomicBoolean(true);
        Utils.runTaskAsync(() -> {
            try {
                // Check if sender is a player
                if (!(sender instanceof Player)) return;

                BukkitPlayer player = new BukkitPlayer((Player) sender);

                // Get the first bee Entity in the given radius
                int radius = BeeNameGenerator.getRadius();
                Entity bee = null;
                Collection<Entity> entities = ((Player) sender).getWorld().getNearbyEntities(((Player) sender).getLocation(), radius, radius, radius);
                for (Entity entity : entities) {
                    if (entity.getType().toString().equals("BEE") && entity.getCustomName() == null) {
                        bee = entity;
                        break;
                    }
                }

                // Execute command
                BNGCommand.executeCommand(player, args, new BukkitEntity(bee));
            } catch (Exception e) {
                success.set(false);
                System.err.println(e);
                e.printStackTrace();
            }
        });
        return success.get();
    }
}
