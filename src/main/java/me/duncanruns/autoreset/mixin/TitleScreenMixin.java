package me.duncanruns.autoreset.mixin;

import me.duncanruns.autoreset.AutoReset;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    private static final Identifier GOLD_BOOTS = new Identifier("textures/item/golden_boots.png");

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void initMixin(CallbackInfo info) {
        if (AutoReset.isPlaying) {
            client.openScreen(new CreateWorldScreen(this));
        } else if (!this.client.isDemo()) {
            int y = this.height / 4 + 48;
            this.addButton(new ButtonWidget(this.width / 2 - 124, y, 20, 20, new LiteralText(""), (buttonWidget) -> {
                AutoReset.isPlaying = true;
                client.openScreen(new CreateWorldScreen(this));
            }));
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void goldBootsOverlayMixin(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int y = this.height / 4 + 48;
        this.client.getTextureManager().bindTexture(GOLD_BOOTS);
        drawTexture(matrices,(width/2)-122,y+2,0.0F,0.0F,16,16,16,16);
    }
}
