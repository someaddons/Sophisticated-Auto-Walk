package com.autowalk;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.IKeyConflictContext;

public class AutoWalkClient
{
    public static       boolean    AUTO_RUN_ENABLED = false;
    public final static KeyMapping AUTORUN          = new KeyMapping("autowalk.keybind.name", InputConstants.Type.MOUSE, 2, KeyMapping.Category.MOVEMENT);
    static
    {
        AUTORUN.setKeyConflictContext(new IKeyConflictContext()
        {
            @Override
            public boolean isActive()
            {
                return false;
            }

            @Override
            public boolean conflicts(final IKeyConflictContext other)
            {
                return false;
            }
        });
    }
}
