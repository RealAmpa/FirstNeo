package com.ampa.firstneo.content.item.fn_items;

import com.ampa.firstneo.content.block.ModBlocks;
import com.ampa.firstneo.content.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class UraniumReplacerItem extends Item {
    public UraniumReplacerItem(Properties pProperties) {
        super(pProperties);
    }

    private static final Map<Block, Block> URANIUM_MAP =
            Map.of(
                    ModBlocks.URANIUM_ORE.get(), Blocks.STONE,
                    ModBlocks.DEEPSLATE_URANIUM_ORE.get(), Blocks.DEEPSLATE,
                    ModBlocks.URANIUM_BLOCK.get(), ModBlocks.URANIUM_ORE.get()
            );

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        Block clickedBlock = clickedState.getBlock();

        if (URANIUM_MAP.containsKey(clickedBlock)){
            if(!pContext.getLevel().isClientSide) {
                if (player.getMainHandItem().is(ModItems.URANIUM_REPLACER.get())) {
                    level.setBlockAndUpdate(clickedPos, URANIUM_MAP.get(clickedBlock).defaultBlockState());
                    level.playSound(null, pContext.getClickedPos(), SoundEvents.VAULT_OPEN_SHUTTER, SoundSource.BLOCKS, 1.0f, 1.0f);
                    player.getCooldowns().addCooldown(this, 20);

                    player.getItemInHand(InteractionHand.MAIN_HAND).hurtAndBreak(1, ((ServerLevel) level), player,
                            item -> player.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.FAIL;
    }
}
