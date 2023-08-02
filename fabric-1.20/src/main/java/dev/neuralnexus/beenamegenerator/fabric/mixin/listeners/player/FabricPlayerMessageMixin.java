package dev.neuralnexus.beenamegenerator.fabric.mixin.listeners.player;

import dev.neuralnexus.beenamegenerator.fabric.events.player.FabricPlayerEvents;
import dev.neuralnexus.serverpanelmanager.common.listneners.player.SPMPlayerMessageListener;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Listens for player messages and emits an event.
 */
@Mixin(ServerPlayNetworkHandler.class)
public abstract class FabricPlayerMessageMixin implements SPMPlayerMessageListener {
    @Shadow public abstract ServerPlayerEntity getPlayer();

    /**
     * Called when a player sends a message.
     * @param packet The packet.
     * @param ci The callback info.
     */
    @Inject(method = "onChatMessage", at = @At("HEAD"))
    public void onPlayerMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {
        if (packet.chatMessage().startsWith("/")) return;

        // Fire the message event
        FabricPlayerEvents.MESSAGE.invoker().onPlayerMessage(getPlayer(), packet.chatMessage(), ci.isCancelled());
    }
}
