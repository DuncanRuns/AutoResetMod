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

import java.util.Random;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    private static final Identifier GOLD_BOOTS = new Identifier("textures/item/golden_boots.png");
    private ButtonWidget resetsButton;
    private Text difficultyString;
    private Random random;

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void initMixin(CallbackInfo info) {
        // If auto reset mode is on, instantly switch to create world menu.
        if (AutoReset.isPlaying) {
            client.openScreen(new CreateWorldScreen(this));
        } else if (!this.client.isDemo()) {
            // Add new button for starting auto resets.
            int y = this.height / 4 + 48;
            resetsButton = this.addButton(new ButtonWidget(this.width / 2 - 124, y, 20, 20, new LiteralText(""), (buttonWidget) -> {
                if (!hasShiftDown()) {
                    AutoReset.isPlaying = true;
                    AutoReset.saveDifficulty();
                    client.openScreen(new CreateWorldScreen(this));
                } else {
                    AutoReset.difficulty++;
                    if (AutoReset.difficulty > 4) {
                        AutoReset.difficulty = 0;
                    }
                    refreshDifficultyString();
                }
            }));
        }

        refreshDifficultyString();
        random = new Random();
    }

    private void refreshDifficultyString() {
        switch (AutoReset.difficulty) {
            case 0:
                difficultyString = new LiteralText("Peaceful");
                break;
            case 1:
                difficultyString = new LiteralText("Easy");
                break;
            case 2:
                difficultyString = new LiteralText("Normal");
                break;
            case 3:
                difficultyString = new LiteralText("Hard");
                break;
            case 4:
                difficultyString = new LiteralText("Hardcore");
                break;
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void goldBootsOverlayMixin(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int y = this.height / 4 + 48;
        this.client.getTextureManager().bindTexture(GOLD_BOOTS);
        drawTexture(matrices, (width / 2) - 122, y + 2, 0.0F, 0.0F, 16, 16, 16, 16);
        if (resetsButton.isHovered() && hasShiftDown()) {
            drawCenteredText(matrices, textRenderer, difficultyString, this.width / 2 - 114, this.height / 4 + 35, 16777215);
        }

    }
}
