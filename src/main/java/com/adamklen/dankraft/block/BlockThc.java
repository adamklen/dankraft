package com.adamklen.dankraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockThc extends Block {
    private static final String NAME = "thcblock";
    private static final Material MATERIAL = Material.ROCK;

    Item toDrop;
    int minDropAmount = 3;
    int maxDropAmount = 10;

    public BlockThc(Item toDrop) {
        super(MATERIAL);
        setUnlocalizedName(NAME);
        setRegistryName(NAME);

        this.toDrop = toDrop;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.toDrop;
    }

    @Override
    public int quantityDropped(Random random) {
        return this.minDropAmount + random.nextInt(this.maxDropAmount - this.minDropAmount + 1);
    }
}
