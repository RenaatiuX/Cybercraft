package com.rena.cybercraft.common.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Cybercraft.MOD_ID, value = Dist.CLIENT)
public class CreativeMenuHandler {

    private static final ResourceLocation CEX_GUI_TEXTURES = Cybercraft.modLoc("textures/gui/creative_expansion.png");
    private static CEXButton salvaged, manufactured;
    public static int pageSelected = 0;


    private static class CEXButton extends Button
    {
        public final int offset;
        public final int baseX;
        public final int baseY;
        public CreativeScreen screen;

        public CEXButton(int x, int y, int offset, CreativeScreen screen) {
            super(x, y, 21, 21, StringTextComponent.EMPTY, CreativeMenuHandler::onPress);
            this.offset = offset;
            this.baseX = this.x;
            this.baseY = this.y;
            this.screen = screen;
        }

        @Override
        public void renderButton(MatrixStack stack, int mouseX, int mouseY, float partialTicks)
        {
            if (this.visible)
            {

                Minecraft.getInstance().textureManager.bind(CEX_GUI_TEXTURES);

                int i = 4;
                int j = 8;
                if (isHovered) {
                    i = 29;
                    j = 0;
                }
                j += offset * (isHovered ? 18 : 23);
                this.blit(stack, this.x, this.y, i, j, 18, 18);
            }
        }
    }

    private static void onPress(Button button){
        if (button == salvaged)
        {
            pageSelected = salvaged.offset;
        }
        else if (button == manufactured)
        {
            pageSelected = manufactured.offset;
        }
        if (button instanceof CEXButton){
            Method setItemGroup = ObfuscationReflectionHelper.findMethod(CreativeScreen.class, "func_147050_b", ItemGroup.class);
            try {
                setItemGroup.invoke(((CEXButton)button).screen, Cybercraft.CYBERCRAFTAB);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }


    }

    @SubscribeEvent
    public static final void initCreativeGui(GuiScreenEvent.InitGuiEvent event){
        if (event.getGui() instanceof CreativeScreen){
            CreativeScreen gui = (CreativeScreen) event.getGui();
            int i = (gui.width - 136) / 2;
            int j = (gui.height - 195) / 2;
            salvaged = new CEXButton(i + 166 + 4, j + 30 + 8, 0, gui);
            manufactured = new CEXButton(i + 166 + 4, j + 30 + 31, 1, gui);
            event.addWidget(salvaged);
            event.addWidget(manufactured);
            if (isCorrectTab(event.getGui())){
                salvaged.visible = false;
                manufactured.visible = false;
            }
        }
    }
    @SubscribeEvent
    public static void drawGui(GuiScreenEvent.DrawScreenEvent.Post event){
        if (isCorrectTab(event.getGui())){
            if (salvaged != null && manufactured != null) {
                salvaged.visible = true;
                manufactured.visible = true;
            }
            CreativeScreen gui = (CreativeScreen) event.getGui();
            int mouseX = event.getMouseX();
            int mouseY = event.getMouseY();
            if (salvaged.isMouseOver(mouseX, mouseY)){
                gui.renderTooltip(event.getMatrixStack(), new TranslationTextComponent(CybercraftAPI.QUALITY_SCAVENGED.getUnlocalizedName()), mouseX, mouseY);
            }else if(manufactured.isMouseOver(mouseX, mouseY)){
                gui.renderTooltip(event.getMatrixStack(), new TranslationTextComponent(CybercraftAPI.QUALITY_MANUFACTURED.getUnlocalizedName()), mouseX, mouseY);
            }
        }else if(salvaged != null && manufactured != null){
            salvaged.visible = false;
            manufactured.visible = false;
        }
    }

    @SubscribeEvent
    public static final void drawBackground(GuiScreenEvent.BackgroundDrawnEvent event){
        if (isCorrectTab(event.getGui())){
            CreativeScreen gui = (CreativeScreen) event.getGui();
            int i = (gui.width - 136) / 2;
            int j = (gui.height - 195) / 2;

            int xSize = 29;
            int ySize = 129;

            int xOffset = 0;
            boolean hasVisibleEffect = false;

            for(EffectInstance potioneffect : Minecraft.getInstance().player.getActiveEffects()) {
                Effect potion = potioneffect.getEffect();
                if(potion.shouldRender(potioneffect)) {
                    hasVisibleEffect = true;
                    break;
                }
            }
            if (!Minecraft.getInstance().player.getActiveEffects().isEmpty() && hasVisibleEffect) {
                xOffset = 59;
            }
            salvaged.x = salvaged.baseX + xOffset;
            manufactured.x = manufactured.baseX + xOffset;
            Minecraft.getInstance().textureManager.bind(CEX_GUI_TEXTURES);
            gui.blit(event.getMatrixStack(), i + 166 + xOffset, j + 30, 0, 0, xSize, ySize);
        }
    }

    private static boolean isCorrectTab(AbstractGui screen){
        if (screen instanceof CreativeScreen){
            return ((CreativeScreen)screen).getSelectedTab() == Cybercraft.CYBERCRAFTAB.getId();
        }
        return false;
    }
}
