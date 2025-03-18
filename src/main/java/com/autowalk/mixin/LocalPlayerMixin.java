package com.autowalk.mixin;

import com.autowalk.AutoWalk;
import com.autowalk.AutoWalkClient;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer
{
    public LocalPlayerMixin(final ClientLevel p_250460_, final GameProfile p_249912_)
    {
        super(p_250460_, p_249912_);
    }

    @Shadow
    public abstract float getVisualRotationYInDegrees();

    @Shadow public abstract boolean isUnderWater();

    @Shadow public abstract boolean isShiftKeyDown();

    @Shadow public abstract boolean isUsingItem();

    @Unique
    private float prevRotation = 0;

    @Unique
    private int inactiveTicks = 0;

    @Unique
    private boolean prevUnderWater = false;

    @Unique
    private boolean prevIsInPortal = false;

    @Inject(method = "tick", at = @At("RETURN"))
    private void checkRotation(final CallbackInfo ci)
    {
        if (prevRotation == getVisualRotationYInDegrees())
        {
            inactiveTicks++;
        }
        else
        {
            inactiveTicks = 0;
        }
        prevRotation = getVisualRotationYInDegrees();
        if (inactiveTicks > 20 * AutoWalk.config.getCommonConfig().stopAfterInactiveSeconds)
        {
            AutoWalkClient.AUTO_RUN_ENABLED = false;
        }

        if (isUnderWater() != prevUnderWater && AutoWalk.config.getCommonConfig().stopOnEnteringWater)
        {
            AutoWalkClient.AUTO_RUN_ENABLED = false;
        }

        prevUnderWater = this.isUnderWater();

        if (this.onClimbable() && isShiftKeyDown())
        {
            AutoWalkClient.AUTO_RUN_ENABLED = false;
        }

        if (portalProcess != null && prevIsInPortal != this.portalProcess.isInsidePortalThisTick())
        {
            AutoWalkClient.AUTO_RUN_ENABLED = false;
        }

        prevIsInPortal = portalProcess != null && portalProcess.isInsidePortalThisTick();
    }

    @Inject(method = "startRiding", at = @At("HEAD"))
    private void onStartRiding(final Entity p_108667_, final boolean p_108668_, final CallbackInfoReturnable<Boolean> cir)
    {
        AutoWalkClient.AUTO_RUN_ENABLED = false;
    }

    @Inject(method = "removeVehicle", at = @At("HEAD"))
    private void onRemoveVehicle(final CallbackInfo ci)
    {
        if ((this.getVehicle() != null))
        {
            AutoWalkClient.AUTO_RUN_ENABLED = false;
        }
    }

    @Inject(method = "respawn", at = @At("HEAD"))
    private void onRespawn(final CallbackInfo ci)
    {
        AutoWalkClient.AUTO_RUN_ENABLED = false;
    }

    @Inject(method = "isSuppressingSlidingDownLadder", at = @At("RETURN"))
    private void onStopOnLadder(final CallbackInfoReturnable<Boolean> cir)
    {
        if (cir.getReturnValue())
        {
            AutoWalkClient.AUTO_RUN_ENABLED = false;
        }
    }

    @Inject(method = "startUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;startUsingItem(Lnet/minecraft/world/InteractionHand;)V"))
    private void onUsingItem(final InteractionHand p_108718_, final CallbackInfo ci)
    {
        if (AutoWalk.config.getCommonConfig().stopOnUsingItem)
        {
            AutoWalkClient.AUTO_RUN_ENABLED = false;
        }
    }
}
