package dev.neuralnexus.beenamegenerator.common.commands;

import dev.neuralnexus.beenamegenerator.common.BNGUtils;
import dev.neuralnexus.beenamegenerator.common.BeeNameGenerator;
import dev.neuralnexus.taterlib.common.abstractions.entity.AbstractEntity;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.placeholder.PlaceholderParser;

import java.util.Arrays;

/**
 * Root command for Bee Name Generator.
 */
public interface BNGCommand {
    static String getCommandName() {
        return "bng";
    }

    static String getCommandDescription() {
        return "A plugin that implements the bee-name-generator API to generate names for bees.";
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

    static void executeCommand(AbstractPlayer player, String[] args, AbstractEntity entity) {
        if (!player.hasPermission(permissionBuilder(args))) {
            player.sendMessage("§cYou do not have permission to use this command.");
            return;
        }
        if (args.length == 0) {
            player.sendMessage("&cUsage: /bng <command>");
            return;
        }
        String text = "&cUnknown command. Type \"/bng help\" for help.";

        switch (args[0].toLowerCase()) {
            // Shows all available commands.
            case "help":
                text = "\n&6Available commands:" +
                    "\n&6/bng help &a- Shows all available commands." +
                    "\n&6/bng reload &a- Reloads the plugin." +
                    "\n&6/bng name &a- Name a bee." +
                    "\n&6/bng get &a- Gets a random bee name." +
                    "\n&6/bng add &a- Adds a bee name to the database." +
                    "\n&6/bng suggest &a- Suggests a bee name." +
                    "\n&6/bng suggest submit &a- Submits a suggestion for a bee name." +
                    "\n&6/bng suggest list &a- Gets a list of suggestions." +
                    "\n&6/bng suggest accept &a- Accepts a suggestion." +
                    "\n&6/bng suggest reject &a- Rejects a suggestion.";
                break;

            // Reloads the plugin.
            case "reload":
                try {
                    BeeNameGenerator.reload();
                    text = "&aSuccessfully reloaded config.";
                } catch (Exception e) {
                    text = "&cFailed to reload config.";
                }
                break;

            // Gets a random bee name.
            case "get":
                String name = BeeNameGenerator.getBNGAPIHandler().getBeeName();
                text = "&6Bee Name: &a" + name;
                break;

            // Adds a bee name to the database.
            case "add":
                if (args.length == 1) {
                    text = "&cUsage: /bng add <name>";
                } else {
                    BeeNameGenerator.getBNGAPIHandler().uploadBeeName(args[1]);
                    text = "&aSuccessfully added " + args[1] + " to the database.";
                }
                break;

            // Suggests a bee name.
            case "suggest":
                if (args.length == 1) {
                    text = "&cUsage: /bng suggest <command>";
                } else {
                    switch (args[1].toLowerCase()) {
                        // Gets a list of suggestions.
                        case "list":
                            int amount = args.length == 2 ? 10 : Integer.parseInt(args[2]);
                            String[] suggestions = BeeNameGenerator.getBNGAPIHandler().getSuggestions(amount);
                            text = "&a" + Arrays.toString(suggestions);
                            break;

                        // Accepts a suggestion.
                        case "accept":
                            if (args.length == 2) {
                                text = "&cUsage: /bng suggest accept <name>";
                            } else {
                                BeeNameGenerator.getBNGAPIHandler().acceptSuggestion(args[2]);
                                text = "&aSuccessfully accepted suggestion for " + args[2] + ".";
                            }
                            break;

                        // Rejects a suggestion.
                        case "reject":
                            if (args.length == 2) {
                                text = "&cUsage: /bng suggest reject <name>";
                            } else {
                                BeeNameGenerator.getBNGAPIHandler().rejectSuggestion(args[2]);
                                text = "&aSuccessfully rejected suggestion for " + args[2] + ".";
                            }
                            break;

                        // Submits a suggestion for a bee name.
                        default:
                            if (args.length == 2) {
                                text = "&cUsage: /bng suggest <name|command>";
                            } else {
                                BeeNameGenerator.getBNGAPIHandler().submitBeeName(args[2]);
                                text = "&aSuccessfully submitted suggestion for " + args[2] + ".";
                            }
                            break;
                    }
                }
                break;

            // Name a bee.
            case "name":
                if (args.length == 1) {
                    text = "&cUsage: /bng name <auto|tag>";
                } else {
                    switch (args[1].toLowerCase()) {
                        // Automatically names a bee.
                        case "auto":
                            text = BNGUtils.autoNameBee(player, entity);
                            break;

                        // Gives the player a name tag with a random bee name.
                        case "tag":
                            text = BNGUtils.giveBeeNameTag(player);
                            break;
                    }
                }
                break;
        }
        player.sendMessage(PlaceholderParser.substituteSectionSign(text));
    }
}
