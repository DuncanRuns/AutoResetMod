package me.duncanruns.autoreset.mixin;

import me.duncanruns.autoreset.AutoReset;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SettingsScreen.class)
public abstract class SettingsScreenMixin extends Screen {
    protected SettingsScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addStopRunButtonMixin(CallbackInfo info) {
        if (AutoReset.isPlaying) {

            // Get menu.stop_resets text or set to default
            String text = Language.getInstance().translate("menu.stop_resets");
            if (text.equals("menu.stop_resets")) {
                text = "Stop Resets & Quit";
            }

            //Add button to disable the auto reset and quit
            this.addButton(new ButtonWidget(0, this.height - 20, 100, 20, text, (buttonWidget) -> {
                AutoReset.isPlaying = false;
                buttonWidget.active = false;
                this.minecraft.world.disconnect();
                this.minecraft.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
                this.minecraft.openScreen(new TitleScreen());
            }));
        }
    }
}
