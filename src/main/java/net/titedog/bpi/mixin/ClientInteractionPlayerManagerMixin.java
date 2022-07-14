package net.titedog.bpi.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.titedog.bpi.BlockProgressIndicator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientInteractionPlayerManagerMixin {
    @Shadow private float currentBreakingProgress;

    @Inject(method = "tick", at = @At("HEAD"))
    private void moose$updateProgress(CallbackInfo ci) {
        BlockProgressIndicator.currentBreakingProgress = currentBreakingProgress;
    }
}