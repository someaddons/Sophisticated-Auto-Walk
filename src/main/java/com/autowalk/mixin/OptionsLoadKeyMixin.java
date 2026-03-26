package com.autowalk.mixin;

import com.autowalk.AutoWalkClient;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public class OptionsLoadKeyMixin
{
    @Shadow
    public KeyMapping[] keyMappings;

    @Inject(method = "load", at = @At("HEAD"))
    private void addKeyMapping(final CallbackInfo ci)
    {
        KeyMapping[] tmp = new KeyMapping[keyMappings.length + 1];
        for (int i = 0; i < keyMappings.length; i++)
        {
            tmp[i] = keyMappings[i];
        }
        tmp[tmp.length - 1] = AutoWalkClient.AUTORUN;
        keyMappings = tmp;
    }
}
