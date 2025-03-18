package com.autowalk.mixin;

import com.autowalk.AutoWalk;
import com.autowalk.AutoWalkClient;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;

@Mixin(Minecraft.class)
public class MinecraftMixin
{
    @Shadow
    @Final
    public Options options;

    @Shadow @Nullable public LocalPlayer player;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void afterInit(final GameConfig p_91084_, final CallbackInfo ci)
    {
        ArrayList<KeyMapping> list = new ArrayList<>(Arrays.asList(options.keyMappings));
        list.add(AutoWalkClient.AUTORUN);
        options.keyMappings = list.toArray(new KeyMapping[0]);
    }

    @Inject(method = "pickBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;findSlotMatchingItem(Lnet/minecraft/world/item/ItemStack;)I"))
    private void onPickBlock(
        final CallbackInfo ci)
    {
        if (player != null && AutoWalk.config.getCommonConfig().stopOnPickBlock)
        {
            if (this.player.getAbilities().instabuild)
            {
                AutoWalkClient.AUTO_RUN_ENABLED = false;
            }
        }
    }
}
