package com.autowalk.mixin;

import com.autowalk.AutoWalkClient;
import com.autowalk.KeyGetter;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyMapping.class)
public class KeyMappingMixin implements KeyGetter
{
    @Shadow private InputConstants.Key key;

    @Inject(method = "click", at = @At("RETURN"))
    private static void onKeyClick(final InputConstants.Key key, final CallbackInfo ci)
    {
        if (key == ((KeyGetter) AutoWalkClient.AUTORUN).getActualKey())
        {
            AutoWalkClient.AUTO_RUN_ENABLED = !AutoWalkClient.AUTO_RUN_ENABLED;
        }
    }

    @Override
    public InputConstants.Key getActualKey()
    {
        return key;
    }
}
