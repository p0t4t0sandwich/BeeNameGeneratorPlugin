package dev.neuralnexus.beenamegenerator.common.commands;

import dev.neuralnexus.beenamegenerator.common.BeeNameGeneratorPlugin;
import dev.neuralnexus.beenamegenerator.common.Utils;
import dev.neuralnexus.taterapi.common.player.AbstractPlayer;

/**
 * Root command for Server Panel Manager.
 */
public interface SPMCommand {
    static String getCommandName() {
        return "spm";
    }

    static String getCommandDescription() {
        return "Root command for Server Panel Manager.";
    }

    static String permissionBuilder(String[] args) {
        if (args.length == 0) {
            return "spm.command";
        } else if (args.length == 1) {
            return "spm.command." + args[0].toLowerCase();
        } else if (args.length == 2) {
            return "spm.command." + args[0].toLowerCase() + "." + args[1].toLowerCase();
        } else {
            return "spm.command." + args[0].toLowerCase() + "." + args[1].toLowerCase() + "." + args[2].toLowerCase();
        }
    }

    static String executeCommand(String[] args) {
        return args.length == 0 ? "§cUsage: /spm <command>" : ""; // BeeNameGenerator.commandHandler.commandMessenger(args);
    }

    static void executeCommand(AbstractPlayer player, boolean isPlayer, String[] args) {
        if (isPlayer) {
            if (!player.hasPermission(permissionBuilder(args))) {
                player.sendMessage("§cYou do not have permission to use this command.");
            } else {
                player.sendMessage(executeCommand(args));
            }
        } else {
            BeeNameGeneratorPlugin.useLogger(Utils.ansiiParser(executeCommand(args)));
        }
    }
}
