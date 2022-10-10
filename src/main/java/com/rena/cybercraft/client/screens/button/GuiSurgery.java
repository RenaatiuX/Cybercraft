package com.rena.cybercraft.client.screens.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft.EnumSlot;
import com.rena.cybercraft.client.model.block.ModelBox;
import com.rena.cybercraft.common.container.SurgeryContainer;
import com.rena.cybercraft.common.container.slot.SurgerySlot;
import com.rena.cybercraft.common.tileentities.TileEntitySurgery;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.common.util.NNLUtil;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class GuiSurgery extends ContainerScreen<SurgeryContainer> {

    private static final ResourceLocation SURGERY_GUI_TEXTURES = new ResourceLocation(Cybercraft.MOD_ID + ":textures/gui/surgery.png");
    private static final ResourceLocation GREY_TEXTURE = new ResourceLocation(Cybercraft.MOD_ID + ":textures/gui/greypx.png");
    private static final ResourceLocation BLUE_TEXTURE = new ResourceLocation(Cybercraft.MOD_ID + ":textures/gui/bluepx.png");

    private Entity skeleton;
    private ModelBox box;

    private float partialTicks;

    private GuiButtonSurgery[] bodyIcons = new GuiButtonSurgery[7];
    private InterfaceButton back;
    private InterfaceButton index;

    private GuiButtonSurgeryLocation[] headIcons = new GuiButtonSurgeryLocation[3];
    private GuiButtonSurgeryLocation[] torsoIcons = new GuiButtonSurgeryLocation[4];
    private GuiButtonSurgeryLocation[] crossSectionIcons = new GuiButtonSurgeryLocation[3];
    private GuiButtonSurgeryLocation[] armIcons = new GuiButtonSurgeryLocation[2];
    private GuiButtonSurgeryLocation[] legIcons = new GuiButtonSurgeryLocation[2];

    private PageConfiguration current;
    private PageConfiguration target;
    private PageConfiguration ease;

    private NonNullList<ItemStack> indexStacks;
    private int[] indexPages;
    private int[] indexNews;

    private int indexCount;

    private float lastTicks;
    private float addedRotate;
    private float oldRotate;

    private float transitionStart = 0;
    private float operationTime = 0;
    private float amountDone = 1;

    private float openTime = 0;

    private int page = 0;
    private boolean mouseDown;
    private int mouseDownX;
    private float[] lastDownX = new float[5];
    private float rotateVelocity = 0;

    private PageConfiguration[] configs = new PageConfiguration[25];
    List<SurgerySlot> visibleSlots = new ArrayList<>();
    private int parent;

    public GuiSurgery(SurgeryContainer surgeryContainer, PlayerInventory playerInventory, ITextComponent textComponent) {
        super(surgeryContainer, playerInventory, textComponent);

        //this.surgery = surgery;
        //this.ySize = 222;

        configs[0] = new PageConfiguration(0, 0, 0, 50, 35, 35, -50, 10);
        configs[1] = new PageConfiguration(50, 0, 210, 150, 0, 0, -150, 0);
        configs[2] = new PageConfiguration(15, 0, 100, 130, 0, 0, -150, 0);
        configs[3] = new PageConfiguration(-50, 0, 100, 130, 0, 0, -150, 0);
        configs[4] = new PageConfiguration(50, 0, 100, 130, 0, 0, -150, 0);
        configs[5] = new PageConfiguration(-70, 0, 10, 130, 0, 0, -150, 0);
        configs[6] = new PageConfiguration(70, 0, 10, 130, 0, 0, -150, 0);
        configs[7] = new PageConfiguration(0, 0, 0, 50, 170, 125, 0, 0);

        configs[11] = new PageConfiguration(160, 0, 300, 200);
        configs[12] = new PageConfiguration(5, 0, 330, 220);
        configs[13] = new PageConfiguration(5, 0, 330, 220);
        configs[14] = new PageConfiguration(-20, 0, 220, 210);
        configs[15] = new PageConfiguration(0, 0, 180, 180);
        configs[16] = new PageConfiguration(0, 0, 180, 180);
        configs[17] = new PageConfiguration(0, 0, 125, 180);
        configs[18] = new PageConfiguration(0, 0, 0, 50, 190, 180, 0, 0);
        configs[19] = new PageConfiguration(0, 0, 0, 50, 170, 180, 0, 0);
        configs[20] = new PageConfiguration(0, 0, 0, 50, 170, 180, 0, 0);

        configs[21] = new PageConfiguration(-70, 0, 180, 200);
        configs[22] = new PageConfiguration(-70, 0, 120, 220);

        configs[23] = new PageConfiguration(10, 0, 20, 200);
        configs[24] = new PageConfiguration(10, 0, -30, 220);

        current = ease = target = configs[0].copy();
    }


    @Override
    protected void renderBg(MatrixStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {

    }
}
