package dev.neuralnexus.beenamegenerator.api;

import dev.neuralnexus.taterlib.inventory.ItemStack;
import dev.neuralnexus.taterlib.player.Player;

/** Utility class for BeeNameGenerator. */
public final class BNGUtils {
    /**
     * Gives the player a name tag with a random bee name.
     *
     * @param player The player who is receiving the name tag.
     * @return A string containing the result of the operation.
     */
    public static String giveBeeNameTag(Player player) {
        // Check if the player has a name tag.
        boolean hasNameTag = player.inventory().contains("minecraft:name_tag");
        if (!hasNameTag) {
            return "&6Error: &cYou do not have a &6Name Tag &cin your inventory.";
        }

        // Get a random bee name.
        String name = BNGAPIProvider.get().getBeeName();

        // Give the player a name tag with the random bee name.
        for (int i = 0; i < player.inventory().size(); i++) {
            ItemStack item = player.inventory().get(i);

            if (item != null
                    && !item.hasDisplayName()
                    && item.type().equals("minecraft:name_tag")) {
                // Check if the name tag has a stack size of 1, and if so, rename it.
                if (item.count() == 1) {
                    item.setDisplayName(name);
                    player.inventory().set(i, item);

                    // Otherwise, give the player a new name tag with the random bee name, and lower
                    // the name tag's stack size by 1.
                } else {
                    int firstEmptySlot = player.inventory().firstEmpty();
                    if (firstEmptySlot == -1) {
                        return "&6Error: &cYou do not have any empty slots in your inventory.";
                    }

                    // Lower the name tag's stack size by 1.
                    item.setCount(item.count() - 1);
                    player.inventory().set(i, item);

                    // Give the player a new name tag with the random bee name.
                    ItemStack newItem = item.clone();
                    newItem.setDisplayName(name);
                    newItem.setCount(1);
                    player.inventory().set(firstEmptySlot, newItem);
                }
                return "&aSuccessfully gave you a name tag with the name &6\"" + name + "\"&a!";
            }
        }
        return "&6Error: &cYou do not have any blank &6Name Tag&cs in your inventory.";
    }
}
