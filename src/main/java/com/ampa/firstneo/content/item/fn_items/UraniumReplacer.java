package com.ampa.firstneo.content.item.fn_items;

import com.ampa.firstneo.content.block.ModBlocks;
import com.ampa.firstneo.content.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class UraniumReplacer extends Item {
    public UraniumReplacer(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState state = level.getBlockState(blockpos);
        Boolean b = false;

        if(!pContext.getLevel().isClientSide) {
            if (isBlockUraniumOre(state))
                b = true;

            if (b && player.getMainHandItem().is(ModItems.URANIUM_REPLACER.get())) {
//              level.playSound(null, blockpos, SoundEvents.NETHERITE_BLOCK_PLACE, SoundSource.BLOCKS, 1f, 1f);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.VAULT_OPEN_SHUTTER, SoundSource.PLAYERS, 1F, 1F);
                level.setBlockAndUpdate(blockpos, Blocks.STONE.defaultBlockState());
                player.getCooldowns().addCooldown(this, 20);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }

    private boolean isBlockUraniumOre(BlockState state) {
        return state.is(ModBlocks.URANIUM_ORE.get());
    }
}
