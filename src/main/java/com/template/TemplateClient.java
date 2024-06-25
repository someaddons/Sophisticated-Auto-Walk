package com.template;

import com.template.event.ClientEventHandler;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

public class TemplateClient
{
    public static void onInitializeClient(final FMLClientSetupEvent event)
    {
        NeoForge.EVENT_BUS.register(ClientEventHandler.class);
    }
}
