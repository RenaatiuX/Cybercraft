package com.rena.cybercraft.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.client.model.armor.TrenchCoatModel;
import com.rena.cybercraft.core.network.CCNetwork;
import com.rena.cybercraft.core.network.TriggerActiveAbilityPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.HashMap;
import java.util.List;

public class ClientUtils {

    @OnlyIn(Dist.CLIENT)
    public static final TrenchCoatModel TRENCH_COAT = new TrenchCoatModel(0.51F);

    private static final float TEXTURE_SCALE = 1.0F / 256;
    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.vertex(x        , y + height, 0.0F).uv((textureX        ) * TEXTURE_SCALE, (textureY + height) * TEXTURE_SCALE).endVertex();
        bufferBuilder.vertex(x + width, y + height, 0.0F).uv((textureX + width) * TEXTURE_SCALE, (textureY + height) * TEXTURE_SCALE).endVertex();
        bufferBuilder.vertex(x + width, y         , 0.0F).uv((textureX + width) * TEXTURE_SCALE, (textureY         ) * TEXTURE_SCALE).endVertex();
        bufferBuilder.vertex(x        , y         , 0.0F).uv((textureX        ) * TEXTURE_SCALE, (textureY         ) * TEXTURE_SCALE).endVertex();
        tessellator.end();
    }

    private static HashMap<String, ResourceLocation> textures = new HashMap<>();

    public static void bindTexture(String string)
    {
        if (!textures.containsKey(string))
        {
            textures.put(string, new ResourceLocation(string));
            Cybercraft.LOGGER.info("Registering new ResourceLocation: " + string);
        }
        Minecraft.getInstance().getTextureManager().bind(textures.get(string));
    }

    public static void drawHoveringText(MatrixStack stack, Screen gui, List<ITextComponent> textLines, int x, int y, FontRenderer font)
    {
        GuiUtils.drawHoveringText(stack, textLines, x, y, gui.width, gui.height, -1, font);
    }


    public static void useActiveItemClient(Entity entity, ItemStack stack)
    {
        CCNetwork.PACKET_HANDLER.sendToServer(new TriggerActiveAbilityPacket(stack));
        CybercraftAPI.useActiveItem(entity, stack);
    }

}
