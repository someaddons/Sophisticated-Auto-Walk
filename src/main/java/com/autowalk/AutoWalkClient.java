package com.autowalk;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;

@Environment(EnvType.CLIENT)
public class AutoWalkClient implements ClientModInitializer
{
    public static       boolean    AUTO_RUN_ENABLED = false;
    public final static KeyMapping AUTORUN          = new KeyMapping("autowalk.keybind.name", InputConstants.Type.MOUSE, 2, KeyMapping.Category.MOVEMENT);

    @Override
    public void onInitializeClient()
    {

    }
}
