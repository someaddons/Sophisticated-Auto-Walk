package com.autowalk.config;

import com.cupboard.config.ICommonConfig;
import com.google.gson.JsonObject;

public class CommonConfiguration implements ICommonConfig
{
    public boolean stopAutoWalkOnUIOpen = false;
    public boolean stopOnEnteringWater = false;
    public boolean stopOnPickBlock = true;
    public int stopAfterInactiveSeconds = 30;
    public boolean stopOnInteractWithBlock = true;
    public boolean stopOnInteractWithEntity = true;
    public boolean stopOnUsingItem = true;
    public boolean keepSprintActive = true;

    public CommonConfiguration()
    {
    }

    public JsonObject serialize()
    {
        final JsonObject root = new JsonObject();

        final JsonObject entry = new JsonObject();
        entry.addProperty("desc:", "Set whether opening an UI should stop auto walking: default:false");
        entry.addProperty("stopAutoWalkOnUIOpen", stopAutoWalkOnUIOpen);
        root.add("stopAutoWalkOnUIOpen", entry);

        final JsonObject entry3 = new JsonObject();
        entry3.addProperty("desc:", "Set whether entering/leaving water should stop auto walking: default:false");
        entry3.addProperty("stopOnEnteringWater", stopOnEnteringWater);
        root.add("stopOnEnteringWater", entry3);

        final JsonObject entry4 = new JsonObject();
        entry4.addProperty("desc:", "Set whether picking a block(like creative mode middle mouse button) does stop auto walking: default:true");
        entry4.addProperty("stopOnPickBlock", stopOnPickBlock);
        root.add("stopOnPickBlock", entry4);

        final JsonObject entry5 = new JsonObject();
        entry5.addProperty("desc:", "Set whether interacting with a block(like opening a chest) does stop auto walking: default:true");
        entry5.addProperty("stopOnInteractWithBlock", stopOnInteractWithBlock);
        root.add("stopOnInteractWithBlock", entry5);

        final JsonObject entry6 = new JsonObject();
        entry6.addProperty("desc:", "Set whether interacting with an entity(right clicking a villager, attacking a zombie) does stop auto walking: default:true");
        entry6.addProperty("stopOnInteractWithEntity", stopOnInteractWithEntity);
        root.add("stopOnInteractWithEntity", entry6);

        final JsonObject entry7 = new JsonObject();
        entry7.addProperty("desc:", "Set whether using an item(eating food, drawing bow etc) does stop auto walking: default:true");
        entry7.addProperty("stopOnUsingItem", stopOnUsingItem);
        root.add("stopOnUsingItem", entry7);

        final JsonObject entry8 = new JsonObject();
        entry8.addProperty("desc:", "Set whether using an item(eating food, drawing bow etc) does stop auto walking: default:true");
        entry8.addProperty("keepSprintActive", keepSprintActive);
        root.add("keepSprintActive", entry8);

        final JsonObject entry2 = new JsonObject();
        entry2.addProperty("desc:", "Set after how many seconds of inactivity auto walking gets disabled: default:30 seconds");
        entry2.addProperty("stopAfterInactiveSeconds", stopAfterInactiveSeconds);
        root.add("stopAfterInactiveSeconds", entry2);

        return root;
    }

    public void deserialize(JsonObject data)
    {
        stopAutoWalkOnUIOpen = data.get("stopAutoWalkOnUIOpen").getAsJsonObject().get("stopAutoWalkOnUIOpen").getAsBoolean();
        stopOnEnteringWater = data.get("stopOnEnteringWater").getAsJsonObject().get("stopOnEnteringWater").getAsBoolean();
        stopOnPickBlock = data.get("stopOnPickBlock").getAsJsonObject().get("stopOnPickBlock").getAsBoolean();
        stopOnInteractWithBlock = data.get("stopOnInteractWithBlock").getAsJsonObject().get("stopOnInteractWithBlock").getAsBoolean();
        stopOnInteractWithEntity = data.get("stopOnInteractWithEntity").getAsJsonObject().get("stopOnInteractWithEntity").getAsBoolean();
        stopOnUsingItem = data.get("stopOnUsingItem").getAsJsonObject().get("stopOnUsingItem").getAsBoolean();
        stopAfterInactiveSeconds = data.get("stopAfterInactiveSeconds").getAsJsonObject().get("stopAfterInactiveSeconds").getAsInt();
        keepSprintActive = data.get("keepSprintActive").getAsJsonObject().get("keepSprintActive").getAsBoolean();
    }
}
