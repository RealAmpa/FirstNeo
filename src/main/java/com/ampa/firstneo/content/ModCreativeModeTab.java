package com.ampa.firstneo.content;

import com.ampa.firstneo.FirstNeo;
import com.ampa.firstneo.content.block.ModBlocks;
import com.ampa.firstneo.content.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FirstNeo.MOD_ID);

    public static final Supplier<CreativeModeTab> FN_ITEMS_TAB = CREATIVE_MODE_TAB.register("fn_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.URANIUM_ORE.get()))
                    .title(Component.translatable("creativetab.firstneo.fn_items_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                       output.accept(ModItems.RAW_URANIUM);
                       output.accept(ModItems.URANIUM_INGOT);
                       output.accept(ModItems.URANIUM_REPLACER);

                    })
                    .build());
    public static final Supplier<CreativeModeTab> FN_BLOCKS_TAB = CREATIVE_MODE_TAB.register("fn_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.URANIUM_ORE))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(FirstNeo.MOD_ID, "fn_items_tab"))
                    .title(Component.translatable("creativetab.firstneo.fn_blocks_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                       output.accept(ModBlocks.URANIUM_ORE);
                       output.accept(ModBlocks.DEEPSLATE_URANIUM_ORE);
                       output.accept(ModBlocks.BLOCK_OF_URANIUM);

                    })
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
