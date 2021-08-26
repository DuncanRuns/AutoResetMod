package me.duncanruns.autoreset.mixin;

import me.duncanruns.autoreset.AutoReset;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {

    @Shadow
    private TextFieldWidget levelNameField;
    @Shadow
    private boolean hardcore;

    @Shadow
    protected abstract void createLevel();

    @Inject(method = "init", at = @At("TAIL"))
    private void autoStartMixin(CallbackInfo info) {
        // If auto reset mode is on, set difficulty to easy and instantly create world.
        if (AutoReset.isPlaying) {
            if (AutoReset.isHardcore) {
                hardcore = true;
            }
            AutoReset.loopPrevent = true;
            levelNameField.setText("Speedrun #" + AutoReset.getNextAttempt());
            createLevel();
        }
    }
}
