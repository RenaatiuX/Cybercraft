package com.rena.cybercraft.api;

import com.rena.cybercraft.api.hud.UpdateHudColorPacket;
import com.rena.cybercraft.api.item.ICybercraft;

import java.util.HashMap;
import java.util.Map;
import com.rena.cybercraft.api.item.ICybercraft.Quality;
import com.rena.cybercraft.api.item.IDeconstructable;
import com.rena.cybercraft.api.item.IMenuItem;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.item.CybercraftBaseItem;
import com.rena.cybercraft.common.item.CybercraftItem;
import com.rena.cybercraft.common.util.NNLUtil;
import com.rena.cybercraft.core.network.CCNetwork;
import com.rena.cybercraft.core.network.CybercraftSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class CybercraftAPI {

    /**
     * Store any functional data of your Cybercraft in NBT under this tag, which will be cleared when new items are added or removed
     * to ensure stacking and such works
     */
    public static final String DATA_TAG = "cybercraftFunctionData";

    public static final String QUALITY_TAG = "cybercraftQuality";

    /**
     * Quality for Cybercraft scavenged from mobs
     */
    public static final Quality QUALITY_SCAVENGED = new ICybercraft.Quality("cybercraft.quality.scavenged", "cybercraft.quality.scavenged.name_modifier", "scavenged");

    /**
     * Quality for Cybercraft built at the Engineering Table
     */
    public static final Quality QUALITY_MANUFACTURED = new Quality("cybercraft.quality.manufactured");

    @CapabilityInject(ICybercraftUserData.class)
    public static final Capability<ICybercraftUserData> CYBERCRAFT_CAPABILITY = null;

    /**
     * Maximum Tolerance, per-player
     */
    public static final Attribute TOLERANCE_ATTR = new RangedAttribute( "cybercraft.tolerance", CybercraftConfig.C_ESSENCE.essence.get(), 0.0F, Double.MAX_VALUE).setRegistryName("tolerance").setSyncable(true);

    public static Map<ItemStack, ICybercraft> linkedWare = new HashMap<>();

    public final static SimpleChannel PACKET_HANDLER = CCNetwork.PACKET_HANDLER;

    /**
     * Sets the HUD color for the Hudjack, radial menu, and other AR HUD elements
     *
     * @param color	A float representation of the desired color
     */
    @OnlyIn(Dist.CLIENT)
    public static void setHUDColor(float[] color)
    {
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(playerEntity);
        if (cyberwareUserData != null)
        {
            cyberwareUserData.setHudColor(color);
        }
    }

    public static void syncHUDColor()
    {
        PACKET_HANDLER.sendToServer(new UpdateHudColorPacket(getHUDColorHex()));
    }

    /**
     * Sets the HUD color for the Hudjack, radial menu, and other AR HUD elements
     *
     * @param hexVal A hexadecimal representation of the desired color
     */
    @OnlyIn(Dist.CLIENT)
    public static void setHUDColor(int hexVal)
    {
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(playerEntity);
        if (cyberwareUserData != null)
        {
            cyberwareUserData.setHudColor(hexVal);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void setHUDColor(float r, float g, float b)
    {
        setHUDColor(new float[] { r, g, b });
    }

    @OnlyIn(Dist.CLIENT)
    public static int getHUDColorHex()
    {
        PlayerEntity entityPlayer = Minecraft.getInstance().player;
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
        if (cyberwareUserData != null)
        {
            return cyberwareUserData.getHudColorHex();
        }
        return 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static float[] getHUDColor()
    {
        PlayerEntity entityPlayer = Minecraft.getInstance().player;
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
        if (cyberwareUserData != null)
        {
            return cyberwareUserData.getHudColor();
        }
        return new float[] { 0F, 0F, 0F };
    }

    /**
     * Can be used by your ICybercraft implementation's setQuality function. Helper method that
     * writes a quality to an easily accessible NBT tag. See the partner function, readQualityTag
     *
     * @param stack	The stack to write to
     * @return		The modified stack
     */
    public static ItemStack writeQualityTag(@Nonnull ItemStack stack, @Nonnull Quality quality)
    {
        if (stack.isEmpty()) return stack;
        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound == null)
        {
            tagCompound = new CompoundNBT();
            stack.setTag(tagCompound);
        }
        tagCompound.putString(QUALITY_TAG, quality.getUnlocalizedName());
        return stack;
    }

    @Nullable
    public static Quality getQualityTag(@Nonnull ItemStack stack)
    {
        if (stack.isEmpty()) return null;
        CompoundNBT tagCompound = stack.getTag();
        if ( tagCompound == null
                || !tagCompound.contains(QUALITY_TAG, Constants.NBT.TAG_STRING) )
        {
            return null;
        }
        return Quality.getQualityFromString(stack.getTag().getString(QUALITY_TAG));
    }

    /**
     * Clears all NBT data from Cybercraft related to its function, things like power storage or oxygen storage
     * This ensures that removed Cybercraft will stack. This should only be called on Cybercraft that is being removed
     * from the body or otherwise reset - otherwise it may interrupt functionality.
     *
     * @param stack	The ItemStack to sanitize
     * @return		A sanitized version of the stack
     */
    public static ItemStack sanitize(@Nonnull ItemStack stack)
    {
        if (!stack.isEmpty())
        {
            CompoundNBT tagCompound = stack.getTag();
            if (tagCompound != null && tagCompound.contains(DATA_TAG))
            {
                tagCompound.remove(DATA_TAG);
            }
            if (tagCompound != null && tagCompound.isEmpty())
            {
                stack.setTag(null);
            }
        }

        return stack;
    }

    /**
     * Gets the NBT data for Cybercraft related to its function. This data is removed when a piece of Cybercraft
     * is removed, and is not counted when determining whether Cybercraft stacks are the same for purposes of merging
     * and such. This function will create a data tag if one does not exist.
     *
     * @param stack	The ItemStack for which you want the data
     * @return		The data, in the form of an NBTTagCompound
     */
    @Nonnull
    public static CompoundNBT getCybercraftNBT(@Nonnull ItemStack stack)
    {
        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound == null)
        {
            tagCompound = new CompoundNBT();
            stack.setTag(tagCompound);
        }
        if (!tagCompound.contains(DATA_TAG))
        {
            tagCompound.put(DATA_TAG, new CompoundNBT());
        }

        return tagCompound.getCompound(DATA_TAG);
    }

    public static boolean areCybercraftStacksEqual(@Nonnull ItemStack stack1,@Nonnull  ItemStack stack2)
    {
        if (stack1.isEmpty() || stack2.isEmpty()) return false;

        ItemStack sanitized1 = sanitize(stack1.copy());
        ItemStack sanitized2 = sanitize(stack2.copy());
        return sanitized1.getItem() == sanitized2.getItem()
                && sanitized1.getDamageValue() == sanitized2.getDamageValue()
                && ItemStack.tagMatches(stack1, stack2);
    }

    /**
     * Links an ItemStack to an instance of ICybercraft. This option is generally worse than
     * implementing ICybercraft in your Item, but if you don't have access to the Item it's the
     * best option. This version of the method links a specific meta value.
     *
     * @param stack	The ItemStack to link
     * @param link	An instance of ICybercraft to link it to
     */
    public static void linkCybercraft(@Nonnull ItemStack stack, ICybercraft link)
    {
        if (stack.isEmpty()) return;

        ItemStack key = new ItemStack(stack.getItem(), 1);
        linkedWare.put(withMetaData(key, getMetaData(stack)), link);
    }

    /**
     * Links an Item to an instance of ICybercraft. This option is generally worse than
     * implementing ICybercraft in your Item, but if you don't have access to the Item it's the
     * best option. This version of the method links all meta values.
     *
     * @param item	The Item to link
     * @param link	An instance of ICybercraft to link it to
     */
    public static void linkCybercraft(Item item, ICybercraft link)
    {
        if (item == null) return;

        ItemStack key = new ItemStack(item, 1);
        linkedWare.put(key, link);
    }

    /**
     * Determines if the inputted item stack is Cybercraft. This means its item either
     * implements ICybercraft or is linked to one (in the case of vanilla items)
     *
     * @param stack	The ItemStack to test
     * @return		If the stack is valid Cybercraft
     */
    public static boolean isCybercraft(@Nullable ItemStack stack)
    {
        if (stack != null)
        {
            return !stack.isEmpty()
                    && ( stack.getItem() instanceof ICybercraft
                    || getLinkedWare(stack) != null );
        }
        return false;
    }

    /**
     * Returns an instance of ICybercraft linked with an itemstack, usually
     * the item which extends ICybercraft, though it may be a standalone
     * ICybercraft-implementing object
     *
     * @param stack	The ItemStack, from which the linked ICybercraft is found
     * @return		The linked instance of ICybercraft
     */
    public static ICybercraft getCybercraft(@Nonnull ItemStack stack)
    {
        if (!stack.isEmpty())
        {
            if (stack.getItem() instanceof ICybercraft)
            {
                return (ICybercraft) stack.getItem();
            }
            else if (getLinkedWare(stack) != null)
            {
                return getLinkedWare(stack);
            }
        }

        throw new RuntimeException("Cannot call getCybercraft on a non-cybercraft item!");
    }

    /**
     * Determines if the inputted item stack can be destroyed in the Engineering Table,
     * meaning it implements IDeconstructable.
     *
     * @param stack	The ItemStack to test
     * @return		If the stack can be deconstructed.
     */
    public static boolean canDeconstruct(@Nonnull ItemStack stack)
    {
        return !stack.isEmpty()
                && (stack.getItem() instanceof IDeconstructable)
                && ((IDeconstructable) stack.getItem()).canDestroy(stack);
    }

    @Nullable
    private static ICybercraft getLinkedWare(@Nonnull ItemStack stack)
    {
        if (stack.isEmpty()) return null;

        return getWareFromKey(stack);
    }

    @Nullable
    private static ICybercraft getWareFromKey(@Nonnull ItemStack key)
    {
        for (Map.Entry<ItemStack, ICybercraft> entry : linkedWare.entrySet())
        {
            ItemStack entryKey = entry.getKey();
            if ( key.getItem() == entryKey.getItem() && entryKey.getDamageValue() == key.getDamageValue()) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * A shortcut method to get you the ICybercraftUserData of a specific entity.
     * This will return null if the entity is null or has no capability.
     *
     * @param targetEntity	The entity whose ICybercraftUserData you want
     * @return				The ICybercraftUserData associated with the entity
     */
    @Nullable
    public static ICybercraftUserData getCapabilityOrNull(@Nullable Entity targetEntity)
    {
        if (targetEntity == null) return null;
        return targetEntity.getCapability(CYBERCRAFT_CAPABILITY, Direction.EAST).orElse(null);
    }

    /**
     * A shortcut method to determine if the entity that is inputted
     * has ICybercraftUserData. Works with null entites.
     * This is very CPU intensive, consider using getCapabilityOrNull() instead.
     *
     * @param targetEntity	The entity to test
     * @return				If the entity has ICybercraftUserData
     */
    @Deprecated
    public static boolean hasCapability(@Nullable Entity targetEntity)
    {
        return getCapabilityOrNull(targetEntity) != null;
    }

    /**
     * Assistant method to hasCapability. A shortcut to get you the ICybercraftUserData
     * of a specific entity. Note that you must verify if it has the capability first.
     * This is very CPU intensive, consider using getCapabilityOrNull() instead.
     *
     * @param targetEntity	The entity whose ICybercraftUserData you want
     * @return				The ICybercraftUserData associated with the entity
     */
    @Deprecated
    public static ICybercraftUserData getCapability(@Nonnull Entity targetEntity)
    {
        return getCapabilityOrNull(targetEntity);
    }

    /**
     * A shortcut method for event handlers and the like to quickly tell if an entity
     * has a piece of Cybercraft installed. Can handle null entites and entities without
     * ICybercraftUserData.
     * This is very CPU intensive, consider using getCapabilityOrNull() instead.
     *
     * @param targetEntity	The entity you want to check
     * @param stack			The Cybercraft you want to check for
     * @return				If the entity has the Cybercraft
     */
    @Deprecated
    public static boolean isCybercraftInstalled(@Nullable Entity targetEntity, ItemStack stack)
    {
        ICybercraftUserData cyberwareUserData = getCapabilityOrNull(targetEntity);
        return cyberwareUserData != null && cyberwareUserData.isCybercraftInstalled(stack.getItem());
    }

    /**
     * A shortcut method for event handlers and the like to quickly determine what level of
     * Cybercraft is installed. Returns 0 if none. Can handle null entites and entities without
     * ICybercraftUserData.
     * This is very CPU intensive, consider using getCapabilityOrNull() instead.
     *
     * @param targetEntity	The entity you want to check
     * @param stack			The Cyberware you want to check for
     * @return				If the entity has the Cybercraft, the level, or 0 if not
     */
    @Deprecated
    public static int getCybercraftRank(@Nullable Entity targetEntity, ItemStack stack)
    {
        ICybercraftUserData cyberwareUserData = getCapabilityOrNull(targetEntity);
        return cyberwareUserData == null ? 0 : cyberwareUserData.getCybercraftRank(stack);
    }

    /**
     * A shortcut method for event handlers and the like to get the itemstack for a piece
     * of Cybercraft. Useful for NBT data. Can handle null entites and entities without
     * ICybercraftUserData.
     * This is very CPU intensive, consider using getCapabilityOrNull() instead.
     *
     * @param targetEntity	The entity you want to check
     * @param stack			The Cybercraft you want to check for
     * @return				The ItemStack found, or null if none
     */
    @Deprecated
    public static ItemStack getCybercraft(@Nullable Entity targetEntity, ItemStack stack)
    {
        ICybercraftUserData cyberwareUserData = getCapabilityOrNull(targetEntity);
        return cyberwareUserData == null ? ItemStack.EMPTY : cyberwareUserData.getCybercraft(stack.getItem());
    }

    public static void updateData(Entity targetEntity)
    {
        if (!targetEntity.level.isClientSide)
        {
            ServerWorld world = (ServerWorld) targetEntity.level;

            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(targetEntity);
            if (cyberwareUserData == null) return;
            CompoundNBT tagCompound = cyberwareUserData.serializeNBT();

            if (targetEntity instanceof PlayerEntity)
            {
                CCNetwork.sendTo(new CybercraftSyncPacket(tagCompound, targetEntity.getId()), (ServerPlayerEntity) targetEntity);

            }

            for (PlayerEntity trackingPlayer : world.getPlayers(serverPlayerEntity -> true))
            {
                CCNetwork.sendTo(new CybercraftSyncPacket(tagCompound, targetEntity.getId()), (ServerPlayerEntity) trackingPlayer);
            }
        }
    }

    public static ItemStack withMetaData(ItemStack stack, int metadata){
        stack.getOrCreateTag().putInt("metadata", metadata);
        return stack;
    }

    public static int getMetaData(ItemStack stack){
        return stack.getOrCreateTag().contains("metadata") ? stack.getTag().getInt("metadata") : 0;
    }

    public static void useActiveItem(Entity entity, ItemStack stack)
    {
        ((IMenuItem) stack.getItem()).use(entity, stack);
    }

    /**
     * returns the quality of a stack
     * @param stack
     * @return
     */
    public static Quality getQuality(ItemStack stack)
    {
        return getCybercraft(stack).getQuality();
    }

    /**
     * won´t change the stack, returns a new stack with the quality requested
     */
    public static ItemStack setQuality(ItemStack stack, Quality quality) {
        if (getCybercraft(stack) == null || !getCybercraft(stack).canHoldQuality(quality) || getQuality(stack) == quality){
            return stack;
        }
        ICybercraft cybercraft = getCybercraft(stack);
        Item otherQuality = cybercraft.withQuality(quality);
        return new ItemStack(otherQuality, stack.getCount(), stack.getTag());
    }

    public static NonNullList<ItemStack> getComponents(ItemStack itemStack) {
        return NonNullList.create();
    }
}
