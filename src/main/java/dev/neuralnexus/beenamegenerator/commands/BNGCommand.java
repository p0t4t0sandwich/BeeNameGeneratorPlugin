package dev.neuralnexus.beenamegenerator.commands;

import dev.neuralnexus.beenamegenerator.BeeNameGenerator;
import dev.neuralnexus.beenamegenerator.api.BNGAPIProvider;
import dev.neuralnexus.beenamegenerator.config.BNGConfigLoader;
import dev.neuralnexus.beenamegenerator.config.sections.NamingConfig;
import dev.neuralnexus.taterlib.command.Command;
import dev.neuralnexus.taterlib.command.CommandSender;
import dev.neuralnexus.taterlib.entity.Entity;
import dev.neuralnexus.taterlib.entity.Nameable;
import dev.neuralnexus.taterlib.inventory.ItemStack;
import dev.neuralnexus.taterlib.placeholder.PlaceholderParser;
import dev.neuralnexus.taterlib.player.Player;
import dev.neuralnexus.taterlib.world.Location;

import java.util.*;
import java.util.stream.Collectors;

/** Root command for Bee Name Generator. */
public class BNGCommand implements Command {
    private String name = "bng";

    @Override
    public String name() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String description() {
        return "A plugin that implements the bee-name-generator API to generate names for bees.";
    }

    @Override
    public String usage() {
        return "/bng <command>";
    }

    @Override
    public String permission() {
        return "bng.command";
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission(permission(args))) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }
        if (!sender.isPlayer()) {
            sender.sendMessage("§cYou must be a player to use this command.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("&cUnknown command. Type \"/bng help\" for help.");
            return true;
        }
        String text = "&cUnknown command. Type \"/bng help\" for help.";

        switch (args[0].toLowerCase()) {
            case "help": // Shows all available commands.
                text =
                        "\n&6Available commands:"
                                + "\n&6/bng help &a- Shows all available commands."
                                + "\n&6/bng reload &a- Reloads the plugin."
                                + "\n&6/bng name &a- Name a bee."
                                + "\n&6/bng get &a- Gets a random bee name."
                                + "\n&6/bng add &a- Adds a bee name to the database."
                                + "\n&6/bng suggest &a- Suggests a bee name."
                                + "\n&6/bng suggest submit &a- Submits a suggestion for a bee name."
                                + "\n&6/bng suggest list &a- Gets a list of suggestions."
                                + "\n&6/bng suggest accept &a- Accepts a suggestion."
                                + "\n&6/bng suggest reject &a- Rejects a suggestion.";
                break;
            case "name": // Name a bee.
                if (args.length == 1) {
                    text = "&cUsage: /bng name <auto|tag>";
                } else {
                    Player player = sender.asPlayer();
                    String beeName;
                    switch (args[1].toLowerCase()) {
                        case "auto": // Automatically names a bee.
                            NamingConfig naming = BNGConfigLoader.config().naming();
                            List<Entity> entities =
                                    player.nearbyEntities(
                                            naming.duplicateRadius(),
                                            entity -> entity.type().equals("minecraft:bee"));

                            if (entities.isEmpty()) {
                                text =
                                        "&6Error: &cThere must be an unnamed bee within &6"
                                                + naming.radius()
                                                + " &cblocks of you.";
                                break;
                            }

                            beeName = BNGAPIProvider.get().getBeeName();
                            if (!naming.allowDuplicates()) {
                                List<String> nearbyBeeNames =
                                        entities.stream()
                                                .map(Nameable::customName)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.toList());
                                int tries = 0;
                                while (nearbyBeeNames.contains(beeName)) {
                                    beeName = BNGAPIProvider.get().getBeeName();
                                    if (tries++ > 20) {
                                        break;
                                    }
                                }
                            }

                            Location playerLocation = player.location();
                            Optional<Entity> closestNameableBee =
                                    entities.stream()
                                            .filter(
                                                    entity ->
                                                            entity.location()
                                                                            .distance(
                                                                                    playerLocation)
                                                                    <= naming.radius())
                                            .filter(entity -> entity.customName() == null)
                                            .min(
                                                    Comparator.comparingDouble(
                                                            entity ->
                                                                    entity.location()
                                                                            .distance(
                                                                                    playerLocation)));

                            if (!closestNameableBee.isPresent()) {
                                text =
                                        "&6Error: &cThere must be an unnamed bee within &6"
                                                + naming.radius()
                                                + " &cblocks of you.";
                                break;
                            }

                            // Check if the player has the payment item.
                            boolean hasPaymentItem;
                            String paymentItem = BNGConfigLoader.config().naming().paymentItem();
                            if (BNGConfigLoader.config().naming().requirePayment()) {
                                hasPaymentItem = player.inventory().contains(paymentItem);
                                if (!hasPaymentItem) {
                                    text =
                                            "&6Error: &cYou do not have a &6"
                                                    + paymentItem
                                                    + " &cin your inventory.";
                                    break;
                                }

                                // Check if the player has a payment item with a stack size of 1.
                                for (int i = 0; i < player.inventory().size(); i++) {
                                    ItemStack item = player.inventory().get(i);
                                    if (item != null && item.type().equals(paymentItem)) {
                                        item.setCount(item.count() - 1);
                                        player.inventory().set(i, item);
                                        break;
                                    }
                                }
                            }
                            closestNameableBee.get().setCustomName(beeName);
                            text = "&aSuccessfully named a bee &6\"" + beeName + "\"&a!";
                            break;
                        case "tag": // Gives the player a name tag with a random bee name.
                            // Get a name tag from the player's inventory.
                            Optional<ItemStack> nameTag =
                                    player.inventory().contents().stream()
                                            .filter(
                                                    itemStack ->
                                                            itemStack
                                                                    .type()
                                                                    .equals("minecraft:name_tag"))
                                            .findFirst();
                            if (!nameTag.isPresent()) {
                                text =
                                        "&6Error: &cYou do not have a &6Name Tag &cin your inventory.";
                                break;
                            }

                            beeName = BNGAPIProvider.get().getBeeName();
                            text =
                                    "&aSuccessfully gave you a name tag with the name &6\""
                                            + beeName
                                            + "\"&a!";

                            // Give the player a name tag with the random bee name.
                            if (nameTag.get().count() == 1) {
                                nameTag.get().setDisplayName(beeName);

                                break;
                            }

                            int firstEmptySlot = player.inventory().firstEmpty();
                            if (firstEmptySlot == -1) {
                                text =
                                        "&6Error: &cYou do not have any empty slots in your inventory.";
                                break;
                            }
                            nameTag.get().setCount(nameTag.get().count() - 1);
                            ItemStack newItem = nameTag.get().clone();
                            newItem.setDisplayName(beeName);
                            newItem.setCount(1);
                            player.inventory().set(firstEmptySlot, newItem);
                            break;
                    }
                }
                break;
            case "reload": // Reloads the plugin.
                try {
                    BeeNameGenerator.reload();
                    text = "&aSuccessfully reloaded config.";
                } catch (Exception e) {
                    text = "&cFailed to reload config.";
                }
                break;
            case "get": // Gets a random bee name.
                String name = BNGAPIProvider.get().getBeeName();
                text = "&6Bee Name: &a" + name;
                break;
            case "add": // Adds a bee name to the database.
                if (args.length == 1) {
                    text = "&cUsage: /bng add <name>";
                } else {
                    BNGAPIProvider.get().uploadBeeName(args[1]);
                    text = "&aSuccessfully added " + args[1] + " to the database.";
                }
                break;
            case "suggest": // Suggests a bee name.
                if (args.length == 1) {
                    text = "&cUsage: /bng suggest <command>";
                } else {
                    switch (args[1].toLowerCase()) {
                        case "list": // Gets a list of suggestions.
                            int amount = args.length == 2 ? 10 : Integer.parseInt(args[2]);
                            String[] suggestions = BNGAPIProvider.get().getSuggestions(amount);
                            text = "&a" + Arrays.toString(suggestions);
                            break;
                        case "accept": // Accepts a suggestion.
                            if (args.length == 2) {
                                text = "&cUsage: /bng suggest accept <name>";
                            } else {
                                BNGAPIProvider.get().acceptSuggestion(args[2]);
                                text = "&aSuccessfully accepted suggestion for " + args[2] + ".";
                            }
                            break;
                        case "reject": // Rejects a suggestion.
                            if (args.length == 2) {
                                text = "&cUsage: /bng suggest reject <name>";
                            } else {
                                BNGAPIProvider.get().rejectSuggestion(args[2]);
                                text = "&aSuccessfully rejected suggestion for " + args[2] + ".";
                            }
                            break;
                        default: // Submits a suggestion for a bee name.
                            if (args.length == 2) {
                                text = "&cUsage: /bng suggest <name|command>";
                            } else {
                                BNGAPIProvider.get().submitBeeName(args[2]);
                                text = "&aSuccessfully submitted suggestion for " + args[2] + ".";
                            }
                            break;
                    }
                }
                break;
        }
        sender.sendMessage(PlaceholderParser.substituteSectionSign(text));
        return true;
    }
}
