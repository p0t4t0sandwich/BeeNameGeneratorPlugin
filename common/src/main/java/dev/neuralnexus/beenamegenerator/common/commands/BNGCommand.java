package dev.neuralnexus.beenamegenerator.common.commands;

import dev.neuralnexus.beenamegenerator.common.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.common.BeeNameGeneratorPlugin;
import dev.neuralnexus.taterlib.common.Utils;
import dev.neuralnexus.taterlib.common.player.AbstractPlayer;

import java.util.Map;

/**
 * Root command for Server Panel Manager.
 */
public interface BNGCommand {
    static String getCommandName() {
        return "spm";
    }

    static String getCommandDescription() {
        return "Root command for Server Panel Manager.";
    }

    static String permissionBuilder(String[] args) {
        if (args.length == 0) {
            return "bng.command";
        } else if (args.length == 1) {
            return "bng.command." + args[0].toLowerCase();
        } else if (args.length == 2) {
            return "bng.command." + args[0].toLowerCase() + "." + args[1].toLowerCase();
        } else {
            return "bng.command." + args[0].toLowerCase() + "." + args[1].toLowerCase() + "." + args[2].toLowerCase();
        }
    }

    static String executeCommand(String[] args) {
        if (args.length == 0) return "&cUsage: /bng <command>";
        String text = "&cUnknown command. Type \"/bng help\" for help.";

        switch (args[0].toLowerCase()) {
            case "help":
                text = "&cUsage: /bng <command>";
                break;
            case "reload":
                try {
                    BeeNameGenerator.reload();
                    text = "&aSuccessfully reloaded config.";
                } catch (Exception e) {
                    text = "&cFailed to reload config.";
                }
                break;
            case "get":
                Map<?, ?> res = BeeNameGenerator.getBNGAPIHandler().getBeeName();
                text = "&a" + res.get("name");
                break;
        }

        return text;
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
