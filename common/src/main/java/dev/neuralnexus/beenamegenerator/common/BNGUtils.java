package dev.neuralnexus.beenamegenerator.common;

import dev.neuralnexus.taterlib.common.abstractions.entity.AbstractEntity;
import dev.neuralnexus.taterlib.common.abstractions.item.AbstractItemMeta;
import dev.neuralnexus.taterlib.common.abstractions.item.AbstractItemStack;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;

/**
 * Utility class for BeeNameGenerator.
 */
public final class BNGUtils {
    /**
     * Private constructor to prevent instantiation.
     */
    private BNGUtils() {}

    /**
     * Automatically names a bee.
     * @param player The player who is naming the bee.
     * @param entity The bee to be named.
     * @return A string containing the result of the operation.
     */
    public static String autoNameBee(AbstractPlayer player, AbstractEntity entity) {
        // Check to see if bee is null.
        if (entity == null) {
            return "&6Error: &cThere must be an unnamed bee within &6" + BeeNameGenerator.getRadius() + " &cblocks of you.";
        }

        // Check if the player has the payment item.
        String paymentItem = BeeNameGenerator.getPaymentItem();
        boolean hasPaymentItem = player.getInventory().contains(paymentItem);
        if (!hasPaymentItem) {
            return "&6Error: &cYou do not have a &6" + paymentItem + " &cin your inventory.";
        }

        // Check if the player has a payment item with a stack size of 1.
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            AbstractItemStack item = player.getInventory().getItem(i);

            if (item != null && item.getType().equals(paymentItem)) {
                // Lower the payment item's stack size by 1.
                item.setCount(item.getCount() - 1);
                player.getInventory().setItem(i, item);

                // Get a random bee name.
                String name = BeeNameGenerator.getBNGAPIHandler().getBeeName();
                entity.setCustomName(name);
                return "&aSuccessfully named a bee &6\"" + name + "\"&a!";
            }
        }
        return "&6Error: &cYou have a &6" + paymentItem + ", and there's a &6Bee &cnearby, but something still went wrong.";
    }

    /**
     * Gives the player a name tag with a random bee name.
     * @param player The player who is receiving the name tag.
     * @return A string containing the result of the operation.
     */
    public static String giveBeeNameTag(AbstractPlayer player) {
        // Check if the player has a name tag.
        boolean hasNameTag = player.getInventory().contains("minecraft:name_tag");
        if (!hasNameTag) {
            return "&6Error: &cYou do not have a &6Name Tag &cin your inventory.";
        }

        // Get a random bee name.
        String name = BeeNameGenerator.getBNGAPIHandler().getBeeName();

        // Give the player a name tag with the random bee name.
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            AbstractItemStack item = player.getInventory().getItem(i);

            if (item != null && !item.getMeta().hasDisplayName() && item.getType().equals("minecraft:name_tag")) {
                // Check if the name tag has a stack size of 1, and if so, rename it.
                if (item.getCount() == 1) {
                    AbstractItemMeta meta = item.getMeta();
                    meta.setDisplayName(name);
                    item.setMeta(meta);
                    player.getInventory().setItem(i, item);

                // Otherwise, give the player a new name tag with the random bee name, and lower the name tag's stack size by 1.
                } else {
                    int firstEmptySlot = player.getInventory().firstEmpty();
                    if (firstEmptySlot == -1) {
                        return "&6Error: &cYou do not have any empty slots in your inventory.";
                    }

                    // Lower the name tag's stack size by 1.
                    item.setCount(item.getCount() - 1);
                    player.getInventory().setItem(i, item);

                    // Give the player a new name tag with the random bee name.
                    AbstractItemStack newItem = item.clone();
                    AbstractItemMeta newItemMeta = newItem.getMeta();
                    newItemMeta.setDisplayName(name);
                    newItem.setMeta(newItemMeta);
                    newItem.setCount(1);
                    player.getInventory().setItem(firstEmptySlot, newItem);
                }
                return "&aSuccessfully gave you a name tag with the name &6\"" + name + "\"&a!";
            }
        }
        return "&6Error: &cYou do not have any blank &6Name Tag&cs in your inventory.";
    }
}
