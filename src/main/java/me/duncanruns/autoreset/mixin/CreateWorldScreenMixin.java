package me.duncanruns.autoreset.mixin;

import jdk.internal.org.jline.utils.DiffHelper;
import me.duncanruns.autoreset.AutoReset;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
    @Shadow
    protected abstract void createLevel();

    @Shadow private ButtonWidget gameModeSwitchButton;

    @Shadow private Difficulty field_24289;

    @Shadow private Difficulty field_24290;

    @Inject(method = "init", at = @At("TAIL"))
    private void autoStartMixin(CallbackInfo info) {
        if(AutoReset.isPlaying) {
            field_24289 = Difficulty.EASY;
            field_24290 = Difficulty.EASY;
            createLevel();
        }
    }
}
