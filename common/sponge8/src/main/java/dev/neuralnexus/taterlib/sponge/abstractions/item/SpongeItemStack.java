package dev.neuralnexus.taterlib.sponge.abstractions.item;

import dev.neuralnexus.taterlib.common.abstractions.item.AbstractItemMeta;
import dev.neuralnexus.taterlib.common.abstractions.item.AbstractItemStack;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.inventory.ItemStack;

/**
 * Abstracts a Sponge item stack to an AbstractItemStack.
 */
public class SpongeItemStack implements AbstractItemStack {
    private final ItemStack itemStack;

    /**
     * Constructor.
     * @param itemStack The Sponge item stack.
     */
    public SpongeItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Getter for the Sponge item stack.
     * @return The Sponge item stack.
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * @inheritDoc
     */
    @Override
    public AbstractItemMeta getMeta() {
        return new SpongeItemMeta(itemStack);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setMeta(AbstractItemMeta itemMeta) {
        // TODO: Implement
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getType() {
        return itemStack.get(Keys.DISPLAY_NAME).get().toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getCount() {
        return itemStack.quantity();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setCount(int count) {
        itemStack.setQuantity(count);
    }

    /**
     * @inheritDoc
     */
    @Override
    public AbstractItemStack clone() {
        return new SpongeItemStack(itemStack.copy());
    }
}
