package me.duncanruns.autoreset.mixin;

import me.duncanruns.autoreset.AutoReset;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.gui.DrawableHelper.drawCenteredText;

@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin extends Screen {

    protected LevelLoadingScreenMixin(Text title) {
        super(title);
    }

//    @ModifyArg(method="render", at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/screen/LevelLoadingScreen;drawCenteredText(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"), index=2)
//    private String mixin(String in) {
//        return "Loading seed " + AutoReset.seed + ": " + in;
//    }

    @Inject(method="render", at=@At("TAIL"))
    private void thing(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (AutoReset.isPlaying) {
            drawCenteredText(matrices, this.textRenderer, "Loading seed " + AutoReset.seed, this.width / 2, (this.height / 2) - 60, 16777215);
        }
    }
}
