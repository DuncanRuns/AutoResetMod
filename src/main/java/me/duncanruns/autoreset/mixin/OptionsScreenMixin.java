package me.duncanruns.autoreset.mixin;

import me.duncanruns.autoreset.AutoReset;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addStopRunButtonMixin(CallbackInfo info) {
        if (AutoReset.isPlaying) {

            // Get menu.stop_resets text or set to default
            Text text;
            if (Language.getInstance().get("menu.stop_resets").equals("menu.stop_resets")) {
                text = new LiteralText("Stop Resets & Quit");
            } else {
                text = new TranslatableText("menu.stop_resets");
            }

            //Add button to disable the auto reset and quit
            this.addButton(new ButtonWidget(0, this.height - 20, 100, 20, text, (buttonWidget) -> {
                AutoReset.isPlaying = false;
                buttonWidget.active = false;
                this.client.world.disconnect();
                this.client.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
                this.client.openScreen(new TitleScreen());
            }));
        }
    }
}
