package com.ampa.firstneo.content.block.fn_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Map;

public class HotBlock extends Block {
    public HotBlock(Properties properties) {
        super(properties);
    }

    private static final Map<Item, Item> BURN_MAP =
            Map.of(
                    Items.BEEF, Items.COOKED_BEEF,
                    Items.CHICKEN, Items.COOKED_CHICKEN,
                    Items.COD, Items.COOKED_COD,
                    Items.MUTTON, Items.COOKED_MUTTON,
                    Items.PORKCHOP, Items.COOKED_PORKCHOP,
                    Items.RABBIT, Items.COOKED_RABBIT,
                    Items.SALMON, Items.COOKED_SALMON

            );

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        Item itemInHand = stack.getItem();
        boolean canUse = BURN_MAP.containsKey(itemInHand);
        if(!level.isClientSide && canUse) {
            stack.consume(1, player);
            player.addItem(new ItemStack(BURN_MAP.get(itemInHand), 1));
            level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0f, 1.0f);
        }

        // ---------------------------------------------------------------------------------------------------------------------
        // !(!level.isClientSide && itemInHand == Items.AIR)
        if(!level.isClientSide && canUse) {
            System.out.println("ItemInteractionResult.SUCCESS");
            return ItemInteractionResult.SUCCESS;
        }
        else if(!(!level.isClientSide && itemInHand == Items.AIR)) {
            System.out.println("ItemInteractionResult.FAIL");
            return ItemInteractionResult.FAIL;
        }
        System.out.println("super.useItemOn");
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide) {
            player.hurt(level.damageSources().onFire(), 3.0f);
            level.playSound(player, pos, SoundEvents.PLAYER_HURT_ON_FIRE, SoundSource.PLAYERS, 1.0f, 1.0f);
        }
        System.out.println("InteractionResult.SUCCESS");
        return InteractionResult.SUCCESS;
    }


    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if(entity instanceof ItemEntity itemEntity){
            Item item = itemEntity.getItem().getItem();
            if(BURN_MAP.containsKey(item)) {
                itemEntity.setItem(new ItemStack(Items.DRIED_KELP, itemEntity.getItem().getCount()));
                level.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        } else if(entity instanceof Player player) {
            Block aboveBlock = level.getBlockState(pos.above()).getBlock();
            if(!level.isClientSide && aboveBlock != Blocks.FIRE){
                level.setBlock(pos.above(), Blocks.FIRE.defaultBlockState(), 3);
                level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }


    }
}
