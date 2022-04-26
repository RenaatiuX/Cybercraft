package com.rena.cybercraft.client.screens.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.hud.HudElementBase;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MissingPowerDisplay extends HudElementBase {

    private static final List<ItemStack> exampleStacks = new ArrayList<>();
    static
    {
        exampleStacks.add(new ItemStack(ItemInit.CYBER_EYES.get()));
        exampleStacks.add(new ItemStack(ItemInit.CYBER_EYES.get()));
        exampleStacks.add(new ItemStack(ItemInit.CYBER_EYES.get()));
        exampleStacks.add(new ItemStack(ItemInit.CYBER_EYES.get()));
        exampleStacks.add(new ItemStack(ItemInit.CYBER_EYES.get()));
        exampleStacks.add(new ItemStack(ItemInit.CYBER_EYES.get()));
        exampleStacks.add(new ItemStack(ItemInit.CYBER_EYES.get()));
        exampleStacks.add(new ItemStack(ItemInit.CYBER_EYES.get()));
    }

    public MissingPowerDisplay()
    {
        super("cybercraft:missing_power");
        setDefaultX(-15);
        setDefaultY(35);
        setWidth(16 + 20);
        setHeight(18 * 8);
    }

    @Override
    public void renderElement(int x, int y, PlayerEntity entityPlayer, MatrixStack mstack, boolean isHUDjackAvailable, boolean isConfigOpen, float partialTicks)
    {
        if ( isHidden()
                || !isHUDjackAvailable ) {
            return;
        }

        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
        if (cyberwareUserData == null) return;

        boolean isRightAnchored = getHorizontalAnchor() == EnumAnchorHorizontal.RIGHT;
        float currTime = entityPlayer.tickCount + partialTicks;

        mstack.pushPose();

        Minecraft.getInstance().getTextureManager().bind(HudHandler.HUD_TEXTURE);

        FontRenderer fontRenderer = Minecraft.getInstance().font;

        ItemRenderer renderItem = Minecraft.getInstance().getItemRenderer();
        List<ItemStack> stacksPowerOutage = isConfigOpen ? exampleStacks : cyberwareUserData.getPowerOutages();
        List<Integer> timesPowerOutage = cyberwareUserData.getPowerOutageTimes();
        List<Integer> indexesElapsed = new ArrayList<>();
        float zLevelSaved = renderItem.blitOffset;
        renderItem.blitOffset = -300;
        int xPosition = x - 1 + (isRightAnchored ? 0 : 20);
        int yPosition = y;
        for (int index = stacksPowerOutage.size() - 1; index >= 0; index--)
        {
            ItemStack stack = stacksPowerOutage.get(index);
            if (!stack.isEmpty())
            {
                int time = (int) currTime;
                if (isConfigOpen)
                {
                    if (index == 0)
                    {
                        time = (int) (currTime - 20 - (entityPlayer.tickCount % 40));
                    }
                }
                else
                {
                    time = timesPowerOutage.get(index);
                }

                if (entityPlayer.tickCount - time < 50)
                {
                    double percentVisible = Math.max(0F, (currTime - time - 20) / 30F);
                    float xOffset = (float) (20F * Math.sin(percentVisible * Math.PI / 2F));

                    mstack.pushPose();
                    mstack.translate(isRightAnchored ? xOffset : -xOffset, 0.0F, 0.0F);

                    //fontRenderer.draw("!", xPosition + 14, yPosition + 8, 0xFF0000);

                    RenderHelper.enableStandardItemLighting();
                    renderItem.renderItemAndEffectIntoGUI(stack, xPosition, yPosition);
                    RenderHelper.disableStandardItemLighting();

                    GlStateManager.popMatrix();
                    yPosition += 18;
                }
                else if (!isConfigOpen)
                {
                    indexesElapsed.add(index);
                }
            }
        }
        renderItem.zLevel = zLevelSaved;

        for (int indexElapsed : indexesElapsed)
        {
            stacksPowerOutage.remove(indexElapsed);
            timesPowerOutage.remove(indexElapsed);
        }

        GlStateManager.popMatrix();
    }

}
