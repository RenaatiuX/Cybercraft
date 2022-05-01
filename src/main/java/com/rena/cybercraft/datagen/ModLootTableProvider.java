package com.rena.cybercraft.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.rena.cybercraft.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(ModBlockLootTable::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((location, lootTable) -> LootTableManager.validate(validationtracker, location, lootTable));
    }

    public static class ModBlockLootTable extends BlockLootTables {

        private ArrayList<Block> list = new ArrayList<>();

        @Override
        protected void addTables() {
            dropSelf(BlockInit.SCANNER_BLOCK.get());
            dropSelf(BlockInit.CHARGER_BLOCK.get());
            dropSelf(BlockInit.COMPONENT_BOX.getPrimary());
        }

        @Override
        protected void add(Block block, LootTable.Builder builder) {
            list.add(block);
            super.add(block, builder);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {return list; }
    }
}
