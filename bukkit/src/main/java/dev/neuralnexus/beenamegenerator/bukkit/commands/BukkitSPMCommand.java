package dev.neuralnexus.beenamegenerator.bukkit.commands;

import dev.neuralnexus.beenamegenerator.bukkit.player.BukkitPlayer;
import dev.neuralnexus.serverpanelmanager.common.commands.SPMCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicBoolean;

import static dev.neuralnexus.serverpanelmanager.common.Utils.runTaskAsync;

/**
 * Bukkit implementation of the SPM command.
 */
public class BukkitSPMCommand implements CommandExecutor {
    /**
     * @inheritDoc
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        AtomicBoolean success = new AtomicBoolean(true);
        runTaskAsync(() -> {
            try {
                // Check if sender is a player
                boolean isPlayer = sender instanceof Player;
                BukkitPlayer player = isPlayer ? new BukkitPlayer((Player) sender) : null;

                // Execute command
                SPMCommand.executeCommand(player, isPlayer, args);
            } catch (Exception e) {
                success.set(false);
                System.err.println(e);
                e.printStackTrace();
            }
        });
        return success.get();
    }
}
