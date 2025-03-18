package com.autowalk.mixin;

import com.autowalk.AutoWalk;
import com.autowalk.AutoWalkClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ScreenMixin
{
    @Inject(method = "setScreen", at = @At("HEAD"))
    private void onSetScreen(final Screen screen, final CallbackInfo ci)
    {
        if (screen != null && AutoWalk.config.getCommonConfig().stopAutoWalkOnUIOpen)
        {
            AutoWalkClient.AUTO_RUN_ENABLED = false;
        }
    }
}
