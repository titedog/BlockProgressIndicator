package net.titedog.bpi.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameMode;
import net.titedog.bpi.BlockProgressIndicator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract boolean shouldRenderSpectatorCrosshair(HitResult hitResult);

    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    @Inject(method = "renderCrosshair", at = @At("HEAD"))
    private void moose$drawBlockBreakingProgressIndicator(MatrixStack matrices, CallbackInfo ci) {
        GameOptions options = client.options;
        if(options.getPerspective().isFirstPerson() && this.client.interactionManager != null) {
            if(this.client.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR || shouldRenderSpectatorCrosshair(this.client.crosshairTarget)) {
                if(client.crosshairTarget != null && client.world != null) {
                    HitResult hit = client.crosshairTarget;
                    if(hit.getType() == HitResult.Type.BLOCK && BlockProgressIndicator.getCurrentBreakingProgress() > 0) {
                        RenderSystem.setShader(GameRenderer::getPositionTexShader);
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        RenderSystem.setShaderTexture(0, new Identifier("bpi:textures/gui/general_icons.png"));
                        RenderSystem.enableBlend();
                        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
                        drawTexture(matrices, (this.scaledWidth - 15) / 2, (this.scaledHeight + 14) / 2, 0, 0, BlockProgressIndicator.getCurrentBreakingProgress(), 1);
                        RenderSystem.setShaderTexture(0, InGameHud.GUI_ICONS_TEXTURE);
                    }
                }
            }
        }
    }
}