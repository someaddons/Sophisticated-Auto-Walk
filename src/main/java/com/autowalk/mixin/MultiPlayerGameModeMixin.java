package com.autowalk.mixin;

import com.autowalk.AutoWalk;
import com.autowalk.AutoWalkClient;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin
{
    @Shadow private float destroyTicks;

    @Inject(method = "performUseItemOn", at = @At("RETURN"))
    private void checkResult(final LocalPlayer player, final InteractionHand p_233748_, final BlockHitResult blockHitResult, final CallbackInfoReturnable<InteractionResult> cir)
    {
        if (AutoWalk.config.getCommonConfig().stopOnInteractWithBlock)
        {
            if (cir.getReturnValue().consumesAction())
            {
                if (blockHitResult.getType() == HitResult.Type.BLOCK && (player.level().getBlockState(blockHitResult.getBlockPos()).is(BlockTags.DOORS) || player.level().getBlockState(blockHitResult.getBlockPos()).is(BlockTags.FENCE_GATES)))
                {
                    return;
                }
                AutoWalkClient.AUTO_RUN_ENABLED = false;
            }
        }
    }

    @Inject(method = "attack", at = @At("RETURN"))
    private void onAttack(final Player p_105224_, final Entity p_105225_, final CallbackInfo ci)
    {
        if (AutoWalk.config.getCommonConfig().stopOnInteractWithEntity)
        {
            AutoWalkClient.AUTO_RUN_ENABLED = false;
        }
    }

    @Inject(method = "interactAt", at = @At("RETURN"))
    private void onInteractAt(
        final Player p_105231_,
        final Entity entity,
        final EntityHitResult p_105233_,
        final InteractionHand p_105234_,
        final CallbackInfoReturnable<InteractionResult> cir)
    {
        if (AutoWalk.config.getCommonConfig().stopOnInteractWithEntity)
        {
            AutoWalkClient.AUTO_RUN_ENABLED = false;
        }
    }

    @Inject(method = "interact", at = @At("RETURN"))
    private void onInteract(
        final Player p_105227_, final Entity entity, final InteractionHand p_105229_, final CallbackInfoReturnable<InteractionResult> cir)
    {
        if (AutoWalk.config.getCommonConfig().stopOnInteractWithEntity)
        {
            AutoWalkClient.AUTO_RUN_ENABLED = false;
        }
    }

    @Inject(method = "continueDestroyBlock", at = @At("RETURN"))
    private void onMineBlock(final BlockPos p_105284_, final Direction p_105285_, final CallbackInfoReturnable<Boolean> cir)
    {
        if (AutoWalk.config.getCommonConfig().stopOnInteractWithBlock && destroyTicks > 6)
        {
            AutoWalkClient.AUTO_RUN_ENABLED = false;
        }
    }

    @Inject(method = "destroyBlock", at = @At("RETURN"))
    private void onDestroyBlock(final BlockPos p_105268_, final CallbackInfoReturnable<Boolean> cir)
    {
        if (AutoWalk.config.getCommonConfig().stopOnInteractWithBlock && destroyTicks > 3 && cir.getReturnValue())
        {
            AutoWalkClient.AUTO_RUN_ENABLED = false;
        }
    }
}
