package dev.neuralnexus.beenamegenerator.config.sections;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

/** The Naming config section. */
@ConfigSerializable
public class NamingConfig {
    @Setting private int radius;

    @Setting("allowDuplicates")
    private boolean allowDuplicates;

    @Setting("duplicateRadius")
    private int duplicateRadius;

    @Setting("requirePayment")
    private boolean requirePayment;

    @Setting("paymentItem")
    private String paymentItem;

    /**
     * Get the radius.
     *
     * @return The radius
     */
    public int radius() {
        return radius;
    }

    /**
     * Get whether duplicates are allowed.
     *
     * @return Whether duplicates are allowed
     */
    public boolean allowDuplicates() {
        return allowDuplicates;
    }

    /**
     * Get the duplicate radius.
     *
     * @return The duplicate radius
     */
    public int duplicateRadius() {
        return duplicateRadius;
    }

    /**
     * Get whether payment is required.
     *
     * @return Whether payment is required
     */
    public boolean requirePayment() {
        return requirePayment;
    }

    /**
     * Get the payment item.
     *
     * @return The payment item
     */
    public String paymentItem() {
        return paymentItem;
    }
}
