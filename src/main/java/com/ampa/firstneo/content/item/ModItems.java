package com.ampa.firstneo.content.item;

import com.ampa.firstneo.FirstNeo;
import com.ampa.firstneo.content.item.fn_items.UraniumReplacerItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FirstNeo.MOD_ID);

    public static final DeferredItem<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_URANIUM = ITEMS.register("raw_uranium",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> URANIUM_REPLACER = ITEMS.register("uranium_replacer",
            () -> new UraniumReplacerItem(new Item.Properties().durability(64)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
