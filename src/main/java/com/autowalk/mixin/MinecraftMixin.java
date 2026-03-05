package com.autowalk.mixin;

import com.autowalk.AutoWalk;
import com.autowalk.AutoWalkClient;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(Minecraft.class)
public class MinecraftMixin
{
    @Shadow
    @Final
    public Options options;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void afterInit(final GameConfig p_91084_, final CallbackInfo ci)
    {
        ArrayList<KeyMapping> list = new ArrayList<>(Arrays.asList(options.keyMappings));
        list.add(AutoWalkClient.AUTORUN);
        options.keyMappings = list.toArray(new KeyMapping[0]);
    }
}
