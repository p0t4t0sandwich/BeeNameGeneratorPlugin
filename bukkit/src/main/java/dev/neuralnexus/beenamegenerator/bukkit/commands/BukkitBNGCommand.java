package dev.neuralnexus.beenamegenerator.bukkit.commands;

import dev.neuralnexus.beenamegenerator.common.commands.BNGCommand;
import dev.neuralnexus.taterlib.bukkit.player.BukkitPlayer;
import dev.neuralnexus.taterlib.common.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                boolean isPlayer = sender instanceof Player;
                BukkitPlayer player = isPlayer ? new BukkitPlayer((Player) sender) : null;

                // Execute command
                BNGCommand.executeCommand(player, isPlayer, args);
            } catch (Exception e) {
                success.set(false);
                System.err.println(e);
                e.printStackTrace();
            }
        });
        return success.get();
    }
}
