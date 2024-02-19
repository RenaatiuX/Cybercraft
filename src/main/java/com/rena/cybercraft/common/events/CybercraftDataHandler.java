package com.rena.cybercraft.common.events;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUserDataImpl;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.core.network.CCNetwork;
import com.rena.cybercraft.core.network.CybercraftSyncPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CybercraftDataHandler {

    public static final CybercraftDataHandler INSTANCE = new CybercraftDataHandler();
    public static final String KEEP_WARE_GAMERULE = "cybercraft_keepCyberware";
    public static final String DROP_WARE_GAMERULE = "cybercraft_dropCyberware";

    @SubscribeEvent
    public void onEntityConstructed(EntityEvent.EntityConstructing event)
    {
        if (event.getEntity() instanceof LivingEntity)
        {
            LivingEntity entityLivingBase = (LivingEntity) event.getEntity();
            entityLivingBase.getAttributes().getInstance(CybercraftAPI.TOLERANCE_ATTR);
        }
    }

    /*@SubscribeEvent
    public void worldLoad(WorldEvent.Load event)
    {
        GameRules rules = event.getWorld().getLevelData().getGameRules();
        if(!rules.hasRule(KEEP_WARE_GAMERULE))
        {
            rules.addGameRule(KEEP_WARE_GAMERULE, Boolean.toString(CybercraftConfig.C_GAMERULES.defaultKeep.get()), ValueType.BOOLEAN_VALUE);
        }
        if(!rules.hasRule(DROP_WARE_GAMERULE))
        {
            rules.addGameRule(DROP_WARE_GAMERULE, Boolean.toString(CybercraftConfig.C_GAMERULES.defaultDrop.get()), ValueType.BOOLEAN_VALUE);
        }
    }*/

    @SubscribeEvent
    public void attachCybercraftData(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof PlayerEntity)
        {
            event.addCapability(CybercraftUserDataImpl.Provider.NAME, new CybercraftUserDataImpl.Provider());
        }
    }

    /*@SubscribeEvent
    public void playerDeathEvent(PlayerEvent.Clone event)
    {
        PlayerEntity entityPlayerLiving = event.getPlayer();
        PlayerEntity entityPlayerDead = event.getOriginal();
        if (event.isWasDeath())
        {
            if (entityPlayerLiving.level.getWorldInfo().getGameRulesInstance().getBoolean(KEEP_WARE_GAMERULE))
            {
                ICybercraftUserData cyberwareUserDataDead = CybercraftAPI.getCapabilityOrNull(entityPlayerDead);
                ICybercraftUserData cyberwareUserDataLiving = CybercraftAPI.getCapabilityOrNull(entityPlayerLiving);
                if (cyberwareUserDataDead != null && cyberwareUserDataLiving != null)
                {
                    cyberwareUserDataLiving.deserializeNBT(cyberwareUserDataDead.serializeNBT());
                }
            }
        }
        else
        {
            ICybercraftUserData cyberwareUserDataDead = CybercraftAPI.getCapabilityOrNull(entityPlayerDead);
            ICybercraftUserData cyberwareUserDataLiving = CybercraftAPI.getCapabilityOrNull(entityPlayerLiving);
            if (cyberwareUserDataDead != null && cyberwareUserDataLiving != null)
            {
                cyberwareUserDataLiving.deserializeNBT(cyberwareUserDataDead.serializeNBT());
            }
        }
    }*/

    /*@SubscribeEvent
    public void handleCyberzombieDrops(LivingDropsEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase instanceof PlayerEntity && !entityLivingBase.level.isClientSide)
        {
            PlayerEntity entityPlayer = (PlayerEntity) entityLivingBase;
            if ( ( entityPlayer.level.getWorldInfo().getGameRulesInstance().getBoolean(DROP_WARE_GAMERULE)
                    && !entityPlayer.level.getWorldInfo().getGameRulesInstance().getBoolean(KEEP_WARE_GAMERULE) )
                    || ( entityPlayer.level.getWorldInfo().getGameRulesInstance().getBoolean(KEEP_WARE_GAMERULE)
                    && shouldDropWare(event.getSource()) ))
            {
                ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
                if (cyberwareUserData != null) {
                    for (ICybercraft.EnumSlot slot : ICybercraft.EnumSlot.values())
                    {
                        NonNullList<ItemStack> nnlInstalled = cyberwareUserData.getInstalledCybercraft(slot);
                        NonNullList<ItemStack> nnlDefaults = NonNullList.create();
                        for (ItemStack itemStackDefault : CybercraftConfig.getStartingItems(ICybercraft.EnumSlot.values()[slot.ordinal()]))
                        {
                            nnlDefaults.add(itemStackDefault.copy());
                        }
                        for (ItemStack itemStackInstalled : nnlInstalled)
                        {
                            if (!itemStackInstalled.isEmpty())
                            {
                                ItemStack itemStackToDrop = itemStackInstalled.copy();
                                boolean found = false;
                                for (ItemStack itemStackDefault : nnlDefaults)
                                {
                                    if (CybercraftAPI.areCybercraftStacksEqual(itemStackDefault, itemStackToDrop))
                                    {
                                        if (itemStackToDrop.getCount() > itemStackDefault.getCount())
                                        {
                                            itemStackToDrop.shrink(itemStackDefault.getCount());
                                        }
                                        else
                                        {
                                            found = true;
                                        }
                                    }
                                }

                                if ( !found
                                        && entityPlayer.level.random.nextFloat() < CybercraftConfig.C_GAMERULES.dropChance.get() / 100F )
                                {
                                    ItemEntity entityItem = new ItemEntity(entityPlayer.level, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, itemStackToDrop);
                                    event.getDrops().add(entityItem);
                                }
                            }
                        }
                    }
                    cyberwareUserData.resetWare(entityPlayer);
                }
            }
        }
    }*/

    private boolean shouldDropWare(DamageSource source)
    {
        if (source == EssentialsMissingHandler.noessence) return true;
        if (source == EssentialsMissingHandler.heartless) return true;
        if (source == EssentialsMissingHandler.brainless) return true;
        if (source == EssentialsMissingHandler.nomuscles) return true;
        if (source == EssentialsMissingHandler.spineless) return true;

        return false;
    }

   /*@SubscribeEvent(priority = EventPriority.LOWEST)
    public void handleCZSpawn(LivingSpawnEvent.SpecialSpawn event)
    {
        if (!(event.getEntityLiving() instanceof LivingEntity)) {
            return;
        }

        LivingEntity entityLiving = (LivingEntity) event.getEntityLiving();

        if ( entityLiving instanceof AbstractPiglinEntity
                || !(entityLiving instanceof ZombieEntity) )
        {
            final ResourceLocation resourceLocation = EntityList.getKey(entityLiving);
            if ( resourceLocation == null
                    || !resourceLocation.getPath().contains("zombie") )
            {
                return;
            }
        }

        if ( CybercraftConfig.C_MOBS.enableCyberZombies.get()
                && !(entityLiving instanceof CyberZombieEntity)
                && ( !CybercraftConfig.C_MOBS.applyDimensionToBeacon.get()
                || isValidDimension(event.getWorld()) ) )
        {
            int tier = TileEntityBeacon.isInRange(entityLiving.level, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ());
            if (tier > 0)
            {
                float chance = tier == 2 ? LibConstants.BEACON_CHANCE
                        : tier == 1 ? LibConstants.BEACON_CHANCE_INTERNAL
                        : LibConstants.LARGE_BEACON_CHANCE;
                if ((event.getWorld().getRandom().nextFloat() < (chance / 100F))) {
                    CyberZombieEntity entityCyberZombie = new CyberZombieEntity(event.getWorld());
                    if (event.getWorld().getRandom.nextFloat() < (LibConstants.BEACON_BRUTE_CHANCE / 100F)) {
                        boolean works = entityCyberZombie.setBrute();
                    }
                    entityCyberZombie.setLocationAndAngles(entityLiving.posX, entityLiving.posY, entityLiving.posZ, entityLiving.rotationYaw, entityLiving.rotationPitch);
                    entityCyberZombie.onInitialSpawn(event.getWorld().getDifficultyForLocation(entityCyberZombie.getPosition()), null);

                    for (EquipmentSlotType slot : EquipmentSlotType.values()) {
                        if (entityCyberZombie.getItemBySlot(slot).isEmpty())
                        {
                            entityCyberZombie.setItemSlot(slot, entityLiving.getItemBySlot(slot));
                            // @TODO: transfer drop chance, see Halloween in Vanilla
                        }
                    }
                    event.getWorld().spawnEntity(entityCyberZombie);
                    entityLiving.deathTime = 19;
                    entityLiving.setHealth(0F);

                    // continue processing to get a chance for clothing
                    entityLiving = entityCyberZombie;
                }
            }
        }

        if ( CybercraftConfig.C_OTHER.enableClothes.get()
                && CybercraftConfig.C_MOBS.addClothes.get())
        {
            if ( entityLiving.getItemBySlot(EquipmentSlotType.HEAD).isEmpty()
                    && entityLiving.level.random.nextFloat() < LibConstants.ZOMBIE_SHADES_CHANCE / 100F )
            {
                if (entityLiving.level.random.nextBoolean())
                {
                    entityLiving.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ItemInit.SHADES.get()));
                }
                else
                {
                    entityLiving.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ItemInit.SHADES2.get()));
                }

                entityLiving.setDropChance(EquipmentSlotType.HEAD, CybercraftConfig.C_MOBS.clothDropRarity.get() / 100F);
            }

            float chestRand = entityLiving.level.random.nextFloat();

            if ( entityLiving.getItemBySlot(EquipmentSlotType.CHEST).isEmpty()
                    && chestRand < LibConstants.ZOMBIE_TRENCH_CHANCE / 100F )
            {
                ItemStack stack = new ItemStack(ItemInit.TRENCHCOAT.get());
                int rand = entityLiving.level.random.nextInt(3);
                if (rand == 0)
                {
                    ItemInit.TRENCHCOAT.get().setColor(stack, 0x664028);
                }
                else if (rand == 1)
                {
                    ItemInit.TRENCHCOAT.get().setColor(stack, 0xEAEAEA);
                }

                entityLiving.setItemSlot(EquipmentSlotType.CHEST, stack);

                entityLiving.setDropChance(EquipmentSlotType.CHEST, CybercraftConfig.C_MOBS.clothDropRarity.get() / 100F);
            }
            else if ( entityLiving.getItemBySlot(EquipmentSlotType.CHEST).isEmpty()
                    && chestRand - (LibConstants.ZOMBIE_TRENCH_CHANCE / 100F) < LibConstants.ZOMBIE_BIKER_CHANCE / 100F )
            {
                ItemStack stack = new ItemStack(ItemInit.JACKET.get());

                entityLiving.setItemSlot(EquipmentSlotType.CHEST, stack);

                entityLiving.setDropChance(EquipmentSlotType.CHEST, CybercraftConfig.C_MOBS.clothDropRarity.get() / 100F);
            }
        }
    }*/

    /*public static void addRandomCybercraft(CyberZombieEntity cyberZombie, boolean brute) {
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(cyberZombie);
        if (cyberwareUserData == null) return;

        NonNullList<NonNullList<ItemStack>> wares = NonNullList.create();

        for (ICybercraft.EnumSlot slot : ICybercraft.EnumSlot.values()) {
            NonNullList<ItemStack> toAdd = cyberwareUserData.getInstalledCybercraft(slot);
            toAdd.removeAll(Collections.singleton(ItemStack.EMPTY));
            wares.add(toAdd);
        }

        // Cyberzombies get all the power
        ItemStack battery = new ItemStack(ItemInit.CREATIVE_BATTERY.get());
        wares.get(ItemInit.CREATIVE_BATTERY.get().getSlot(battery).ordinal()).add(battery);

        int numberOfItemsToInstall = WeightedRandom.getRandomItem(cyberZombie.level.random, CyberwareContent.numItems).num;
        if (brute) {
            numberOfItemsToInstall += LibConstants.MORE_ITEMS_BRUTE;
        }

        List<ItemStack> installed = new ArrayList<>();

        List<ZombieItem> items = new ArrayList<>(CyberwareContent.zombieItems);
        for (int indexItem = 0; indexItem < numberOfItemsToInstall; indexItem++) {
            int tries = 0;
            ItemStack randomItem;
            ICybercraft randomWare;

            // Ensure we get a unique item
            do {
                randomItem = WeightedRandom.getRandomItem(cyberZombie.level.random, items).stack.copy();
                randomWare = CybercraftAPI.getCybercraft(randomItem);
                randomItem.setCount(randomWare.installedStackSize(randomItem));
                tries++;
            }
            while (contains(wares.get(randomWare.getSlot(randomItem).ordinal()), randomItem) && tries < 10);

            if (tries < 10) {
                // Fulfill requirements
                NonNullList<NonNullList<ItemStack>> required = randomWare.required(randomItem);
                for (NonNullList<ItemStack> requiredCategory : required) {
                    boolean found = false;
                    for (ItemStack option : requiredCategory) {
                        ICybercraft optionWare = CybercraftAPI.getCybercraft(option);
                        option.setCount(optionWare.installedStackSize(option));
                        if (contains(wares.get(optionWare.getSlot(option).ordinal()), option)) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        ItemStack req = requiredCategory.get(cyberZombie.level.random.nextInt(requiredCategory.size())).copy();
                        ICybercraft reqWare = CybercraftAPI.getCybercraft(req);
                        req.setCount(reqWare.installedStackSize(req));
                        wares.get(reqWare.getSlot(req).ordinal()).add(req);
                        installed.add(req);
                        indexItem++;
                    }
                }
                wares.get(randomWare.getSlot(randomItem).ordinal()).add(randomItem);
                installed.add(randomItem);
            }
        }

        for (ICybercraft.EnumSlot slot : ICybercraft.EnumSlot.values())
        {
            cyberwareUserData.setInstalledCybercraft(cyberZombie, slot, wares.get(slot.ordinal()));
        }
        cyberwareUserData.updateCapacity();

        cyberZombie.setHealth(cyberZombie.getMaxHealth());
        cyberZombie.hasRandomWare = true;

        CybercraftAPI.updateData(cyberZombie);
    }*/

    private static boolean contains(NonNullList<ItemStack> nnlHaystack, ItemStack needle)
    {
        for (ItemStack check : nnlHaystack)
        {
            if ( !check.isEmpty()
                    && !needle.isEmpty()
                    && check.getItem() == needle.getItem()
                    && check.getDamageValue() == needle.getDamageValue() )
            {
                return true;
            }
        }
        return false;
    }

    /*@SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPotentialSpawns(@Nonnull WorldEvent.PotentialSpawns event)
    {
        if (event.getType() != EntityClassification.MONSTER) return;

        if (!CybercraftConfig.C_MOBS.applyDimensionToSpawning.get()) return;

        if (isValidDimension(event.getWorld())) return;

        List<SpawnListEntry> spawnListEntriesToRemove = new ArrayList<>(4);
        for (SpawnListEntry spawnListEntry : event.getList())
        {
            if (spawnListEntry.entityClass.equals(CyberZombieEntity.class))
            {
                spawnListEntriesToRemove.add(spawnListEntry);
            }
        }
        event.getList().removeAll(spawnListEntriesToRemove);
    }*/

    /*public boolean isValidDimension(@Nonnull World world)
    {
        boolean isListed = CybercraftConfig.MOBS_DIMENSION_IDS.contains(world.provider.getDimension());
        return (CybercraftConfig.C_MOBS.isDimensionBlacklist.get() && !isListed)
                || (!CybercraftConfig.C_MOBS.isDimensionBlacklist.get() && isListed);
    }*/

    @SubscribeEvent
    public void syncCyberwareData(EntityJoinWorldEvent event)
    {
        if (!event.getWorld().isClientSide)
        {
            Entity entity = event.getEntity();
            if (entity instanceof PlayerEntity)
            {
                ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entity);
                if (cyberwareUserData != null)
                {
                    CompoundNBT tagCompound = cyberwareUserData.serializeNBT();
                    CCNetwork.sendTo(new CybercraftSyncPacket(tagCompound, entity.getId()), (ServerPlayerEntity) entity);
                }
            }
        }
    }

    @SubscribeEvent
    public void startTrackingEvent(PlayerEvent.StartTracking event)
    {
        PlayerEntity entityPlayer = event.getPlayer();
        Entity entityTarget = event.getTarget();

        if (!entityTarget.level.isClientSide)
        {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityTarget);
            if (cyberwareUserData != null)
            {
                CompoundNBT tagCompound = cyberwareUserData.serializeNBT();
                CCNetwork.sendTo(new CybercraftSyncPacket(tagCompound, entityTarget.getId()), (ServerPlayerEntity) entityPlayer);
            }
        }
    }
}
