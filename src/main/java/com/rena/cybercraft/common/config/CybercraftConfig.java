package com.rena.cybercraft.common.config;

import com.google.common.collect.Lists;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CybercraftConfig {

    public static C_Essence C_ESSENCE;
    public static C_Mobs C_MOBS;
    public static C_Other C_OTHER;
    public static C_Hud C_HUD;
    public static C_Machines C_MACHINES;
    public static C_Gamerules C_GAMERULES;


    public static ForgeConfigSpec init(ForgeConfigSpec.Builder builder){

        C_ESSENCE = new C_Essence(builder);
        C_MOBS = new C_Mobs(builder);
        C_OTHER = new C_Other(builder);
        C_HUD = new C_Hud(builder);
        C_MACHINES = new C_Machines(builder);
        C_GAMERULES = new C_Gamerules(builder);
        return builder.build();

    }

    public static final class C_Mobs{
        public final ForgeConfigSpec.BooleanValue enableCyberZombies, isDimensionBlacklist, applyDimensionToSpawning, applyDimensionToBeacon, addClothes;
        public final ForgeConfigSpec.IntValue cyberZombieWeight, cyberZombieMinPack, cyberZombieMaxPack;
        public final ForgeConfigSpec.DoubleValue cyberZombieDropRarity, clothDropRarity;
        public final Map<ICybercraft.EnumSlot, ForgeConfigSpec.ConfigValue<List<? extends ItemStack>>> startItems = new HashMap<>();

        C_Mobs(ForgeConfigSpec.Builder builder){
            builder.push("Mobs").comment("Config for the Cyberzombies");
            enableCyberZombies = builder.define("CyberZombies are enabled", true);
            builder.comment("Vanilla Zombie = 100, Enderman = 10, Witch = 5");
            cyberZombieWeight = builder.defineInRange("CyberZombies spawning weight", 15, 0, Integer.MAX_VALUE);
            builder.comment("Vanilla Zombie = 4, Enderman = 1, Witch = 1");
            cyberZombieMinPack = builder.defineInRange("CyberZombies minimun pack size", 1, 0, Integer.MAX_VALUE);
            builder.comment("Vanilla Zombie = 4, Enderman = 1, Witch = 1");
            cyberZombieMaxPack = builder.defineInRange("CyberZombies maximum pack size", 1, 0, Integer.MAX_VALUE);
            isDimensionBlacklist = builder.define("Dimension ids is a blacklist?", true);
            applyDimensionToSpawning = builder.define("Dimension ids applies to natural spawning?", true);
            applyDimensionToBeacon = builder.define("Dimension ids applies to beacon, radio & cranial broadcaster?", true);
            addClothes = builder.define("Add Cybercraft clothing to mobs", true);
            cyberZombieDropRarity = builder.defineInRange("Percent chance a CyberZombie drops a Cybercraft", 50F, 0F, 100F);
            clothDropRarity = builder.defineInRange("Percent chance a Cybercrafr clothing is dropped", 50F, 0F, 100F);
            builder.push("Starting Items  per slot");
            builder.comment("Eyes");
            startItems.put(ICybercraft.EnumSlot.EYES, builder.defineList("items", NonNullList.of(new ItemStack(ItemInit.EYES.get())), item -> item instanceof ItemStack && CybercraftAPI.isCybercraft((ItemStack) item)));
            builder.pop();
        }
    }

    public static final class C_Other{
        public final ForgeConfigSpec.BooleanValue surgeryCrafting, enableKatana, enableClothes, render;
        public final ForgeConfigSpec.IntValue testlaPerPower, fistMiningLevel;

        C_Other(ForgeConfigSpec.Builder builder){
            builder.push("Other").comment("Config for Other things");
            builder.comment("Normally only found in Nether fortresses");
            surgeryCrafting = builder.define("Enable crafting recipe for Robosurgeon", false);
            testlaPerPower = builder.defineInRange("RF/Tesla per internal power unit", 1, 0, Integer.MAX_VALUE);
            enableKatana = builder.define("Enable Katana", true);
            enableClothes = builder.define("Enable Trench Coat, Mirror Shades, and Biker Jacket", true);
            render = builder.define("Enable changes to player model (missing skin, missing limbs, Cybernetic limbs)", true);
            fistMiningLevel = builder.defineInRange("Configure the mining level for the reinforced fist", 2, 1, 3);
            builder.pop();
        }
    }

    public static final class C_Essence{
        public final ForgeConfigSpec.IntValue essence, criticalEssence;

        C_Essence(ForgeConfigSpec.Builder builder){
            builder.push("Essence").comment("Config for the Essence");
            essence = builder.defineInRange("Maximum Essence", 100, 0, Integer.MAX_VALUE);
            criticalEssence = builder.defineInRange("Critical Essence value, where rejection begins", 25, 0, Integer.MAX_VALUE );
            builder.pop();
        }

    }

    public static final class C_Gamerules{
        public final ForgeConfigSpec.BooleanValue defaultDrop, defaultKeep;
        public final ForgeConfigSpec.DoubleValue dropChance;

        C_Gamerules(ForgeConfigSpec.Builder builder){
            builder.push("Gamerules").comment("Config for the Gamerules");
            builder.comment("Determines if players drop their Cybercraft on death. Does not change settings on existing worlds, use /gamerule for that. Overridden if cybercraft_keepCybercraft is true");
            defaultDrop = builder.define("Default for gamerule cybercraft_dropCybercraft", false);
            builder.comment("Determines if players keep their Cybercraft between lives. Does not change settings on existing worlds, use /gamerule for that");
            defaultKeep = builder.define("Default for gamerule cyberware_keepCybercraft", false);
            builder.comment("If dropCybecraft enabled, chance for a piece of Cybercraft to successfuly drop instead of being destroyed");
            dropChance = builder.defineInRange("Chance of successful drop", 100F, 0F, 100F);
            builder.pop();
        }
    }

    public static final class C_Machines{
        public final ForgeConfigSpec.DoubleValue engineeringChance, scannerChance, scannerChanceAddl;
        public final ForgeConfigSpec.IntValue scannerTime;

        C_Machines(ForgeConfigSpec.Builder builder){
            builder.push("Machines").comment("Config for the Machines");
            engineeringChance = builder.defineInRange("Chance of blueprint from Engineering Table", 15F, 0, 100F);
            scannerChance = builder.defineInRange("Chance of blueprint from Scanner", 10F, 0, 100F);
            scannerChanceAddl = builder.defineInRange("Additive chance for Scanner per extra item", 10F, 0, 100F);
            builder.comment("24000 is one Minecraft day, 1200 is one real-life minute");
            scannerTime = builder.defineInRange("Ticks taken per Scanner operation", 24000, 0, Integer.MAX_VALUE);
            builder.pop();

        }
    }

    public static final class C_Hud{
        public final ForgeConfigSpec.BooleanValue enableFloat;
        public final ForgeConfigSpec.DoubleValue hudjackFloat, hudlensFloat;

        C_Hud(ForgeConfigSpec.Builder builder){
            builder.push("Hud").comment("Config for the Hud");
            builder.comment("Experimental, defaults to false");
            enableFloat = builder.define("Enable hudlens and hudjack float", false);
            hudjackFloat = builder.defineInRange("Amount hudjack HUD will 'float' with movement. Set to 0 for no float", 0.05F, 0F, 100F);
            hudlensFloat = builder.defineInRange("Amount hudlens HUD will 'float' with movement. Set to 0 for no float", 0.1F, 0F, 100F);
            builder.pop();
        }
    }

}
