package com.rena.cybercraft.events.loot;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class SurgeryApparatusAdditionModifier extends LootModifier {

    private final Block addition;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     * @param addition
     */
    protected SurgeryApparatusAdditionModifier(ILootCondition[] conditionsIn, Block addition) {
        super(conditionsIn);
        this.addition = addition;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if(context.getRandom().nextFloat() > 0.15) {
            generatedLoot.add(new ItemStack(addition, 1));
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<SurgeryApparatusAdditionModifier> {

        @Override
        public SurgeryApparatusAdditionModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
            Block addition = ForgeRegistries.BLOCKS.getValue(
                    new ResourceLocation(JSONUtils.getAsString(object, "addition")));
            return new SurgeryApparatusAdditionModifier(conditionsIn, addition);
        }

        @Override
        public JsonObject write(SurgeryApparatusAdditionModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("addition", ForgeRegistries.BLOCKS.getKey(instance.addition).toString());
            return json;
        }
    }
}
