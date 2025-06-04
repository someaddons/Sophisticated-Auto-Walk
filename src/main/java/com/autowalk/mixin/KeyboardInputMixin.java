package com.autowalk.mixin;

import com.autowalk.AutoWalkClient;
import net.minecraft.client.Options;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.entity.player.Input;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends ClientInput
{
    @Shadow
    @Final
    private Options options;

    @Unique
    private boolean prevUp = false;

    @Unique
    private boolean prevDown = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void checKAutoRunCancel(final CallbackInfo ci)
    {
        if (AutoWalkClient.AUTO_RUN_ENABLED)
        {
            // Disable on not down -> down
            if (this.options.keyUp.isDown() && !prevUp || this.options.keyDown.isDown())
            {
                AutoWalkClient.AUTO_RUN_ENABLED = false;
                return;
            }
        }

        prevUp = this.options.keyUp.isDown();
        prevDown = this.options.keyDown.isDown();
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/KeyboardInput;calculateImpulse(ZZ)F", ordinal = 0))
    private void checKAutoRun(final CallbackInfo ci)
    {
        if (AutoWalkClient.AUTO_RUN_ENABLED)
        {
            this.keyPresses = new Input(
                true,
                this.keyPresses.backward(),
                this.keyPresses.left(),
                this.keyPresses.right(),
                this.keyPresses.jump(),
                this.keyPresses.shift(),
                this.keyPresses.sprint()
            );
        }
    }
}
