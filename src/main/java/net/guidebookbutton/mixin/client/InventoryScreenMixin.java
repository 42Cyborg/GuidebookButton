package net.guidebookbutton.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;

import net.guidebookbutton.GuidebookButtonMain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.guidebookbutton.network.GuidebookButtonClientPacket;
import vazkii.patchouli.api.PatchouliAPI;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> {

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    private void mouseClickedMixin(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> info) {
        if (this.client != null && this.focusedSlot == null && this.isPointWithinBounds(GuidebookButtonMain.CONFIG.posX(), GuidebookButtonMain.CONFIG.posY(), 20, 18, (double) mouseX, (double) mouseY))
            if (GuidebookButtonMain.CONFIG.openAllBooksScreen())
                GuidebookButtonClientPacket.writeC2SModpackScreenPacket(client);
            else
                PatchouliAPI.get().openBookGUI(new Identifier(GuidebookButtonMain.CONFIG.bookIdentifier()));

    }

    @Inject(method = "drawBackground", at = @At("TAIL"))
    protected void drawBackgroundMixin(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo info) {
        RenderSystem.setShaderTexture(0, GuidebookButtonMain.PATCHOULI_BUTTON);
        if (this.isPointWithinBounds(GuidebookButtonMain.CONFIG.posX(), GuidebookButtonMain.CONFIG.posY(), 20, 18, (double) mouseX, (double) mouseY)) {
            this.drawTexture(matrices, this.x + GuidebookButtonMain.CONFIG.posX(), this.y + GuidebookButtonMain.CONFIG.posY(), 166, 0, 20, 18);
            this.renderTooltip(matrices, Text.translatable("screen.patchoulibutton"), mouseX, mouseY);
        } else
            this.drawTexture(matrices, this.x + GuidebookButtonMain.CONFIG.posX(), this.y + GuidebookButtonMain.CONFIG.posY(), 146, 0, 20, 18);

    }
}
