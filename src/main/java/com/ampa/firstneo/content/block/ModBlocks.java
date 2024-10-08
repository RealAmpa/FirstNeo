package com.ampa.firstneo.content.block;

import com.ampa.firstneo.FirstNeo;
import com.ampa.firstneo.content.block.fn_blocks.HotBlock;
import com.ampa.firstneo.content.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FirstNeo.MOD_ID);

    public static final DeferredBlock<Block> URANIUM_ORE = registerBlock("uranium_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.of()
                    .strength(3.0f, 3.0f).sound(SoundType.LODESTONE)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> DEEPSLATE_URANIUM_ORE = registerBlock("deepslate_uranium_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.of()
                    .strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> URANIUM_BLOCK = registerBlock("uranium_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5.0f, 6.0f).sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> HOT_BLOCK = registerBlock("hot_block",
            () -> new HotBlock(BlockBehaviour.Properties.of()
                    .strength(3.0f, 3.0f).sound(SoundType.NETHERRACK)
                    .requiresCorrectToolForDrops()));

    // --- Helper methods ---
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> void registerBlockItem (String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
