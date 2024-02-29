package com.rena.cybercraft.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.client.model.block.ModelBox;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.container.SurgeryContainer;
import com.rena.cybercraft.common.container.slot.SurgerySlot;
import com.rena.cybercraft.common.tileentities.TileEntitySurgery;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.common.util.NNLUtil;
import com.rena.cybercraft.core.init.AttributeInit;
import com.rena.cybercraft.core.network.CCNetwork;
import com.rena.cybercraft.core.network.SurgeryRemovePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SurgeryScreen extends ContainerScreen<SurgeryContainer> {


    public static final ResourceLocation SURGERY_GUI_TEXTURES = Cybercraft.modLoc("textures/gui/surgery.png");
    public static final ResourceLocation GREY_TEXTURE = Cybercraft.modLoc("textures/gui/greypx.png");
    public static final ResourceLocation BLUE_TEXTURE = Cybercraft.modLoc("textures/gui/bluepx.png");

    private TileEntitySurgery surgery;
    private LivingEntity skeleton;
    private ModelBox box;

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
    private float partialTicks = 0;

    private float openTime = 0;

    private int page = 0;
    private boolean mouseDown;
    private double mouseDownX;
    private double[] lastDownX = new double[5];
    private double rotateVelocity = 0;

    private PageConfiguration[] configs = new PageConfiguration[25];
    private int parent;
    List<SurgeryContainer.SlotSurgery> visibleSlots = new ArrayList<>();

    public SurgeryScreen(SurgeryContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        this.surgery = getMenu().getSurgery();
    }

    @Override
    protected void init() {
        this.imageWidth = 176;
        this.imageHeight = 222;
        super.init();
        this.skeleton = new SkeletonEntity(EntityType.SKELETON, this.menu.getSurgery().getLevel());
        skeleton.xRot = 0;
        skeleton.xRotO = 0;
        skeleton.yHeadRot = 0;
        skeleton.yHeadRotO = 0;
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


        bodyIcons[0] = addButton(new GuiButtonSurgery(1, leftPos + (imageWidth / 2) - 18, topPos + 8, 36, 27));
        bodyIcons[1] = addButton(new GuiButtonSurgery(2, leftPos + (imageWidth / 2) - 13, topPos + 35, 26, 38));
        bodyIcons[2] = addButton(new GuiButtonSurgery(3, leftPos + (imageWidth / 2) - 8 + 21, topPos + 35, 16, 38));
        bodyIcons[3] = addButton(new GuiButtonSurgery(4, leftPos + (imageWidth / 2) - 8 - 21, topPos + 35, 16, 38));
        bodyIcons[4] = addButton(new GuiButtonSurgery(5, leftPos + (imageWidth / 2) - 6 + 7, topPos + 73, 12, 39));
        bodyIcons[5] = addButton(new GuiButtonSurgery(6, leftPos + (imageWidth / 2) - 6 - 7, topPos + 73, 12, 39));
        back = addButton(new InterfaceButton(8, leftPos + imageWidth - 25, topPos + 5, Type.BACK));
        index = addButton(new InterfaceButton(9, leftPos + imageWidth - 22, topPos + 5, Type.INDEX));
        back.visible = false;

        bodyIcons[6] = addButton(new GuiButtonSurgery(7,
                leftPos + (int) (imageWidth / 2 + configs[0].boxX - (configs[0].boxWidth / 2)),
                topPos + (int) ((125F / 2F) + 3F + configs[0].boxY - (configs[0].boxHeight / 2)),
                (int) configs[0].boxWidth, (int) configs[0].boxHeight)); // CAW

        headIcons[0] = addButton(new GuiButtonSurgeryLocation(11, -2F, 19, 0));
        headIcons[1] = addButton(new GuiButtonSurgeryLocation(12, 4F, 21, 2.F));
        headIcons[2] = addButton(new GuiButtonSurgeryLocation(13, 4F, 21, -2F));
        torsoIcons[0] = addButton(new GuiButtonSurgeryLocation(14, 1F, 8, -1F));
        torsoIcons[1] = addButton(new GuiButtonSurgeryLocation(15, 0F, 9, -2F));
        torsoIcons[2] = addButton(new GuiButtonSurgeryLocation(16, 0F, 9, 2F));
        torsoIcons[3] = addButton(new GuiButtonSurgeryLocation(17, 0F, 13, 0F));
        crossSectionIcons[0] = addButton(new GuiButtonSurgeryLocation(18, -12F, -8, -1F));
        crossSectionIcons[1] = addButton(new GuiButtonSurgeryLocation(19, 12F, -1, 2F));
        crossSectionIcons[2] = addButton(new GuiButtonSurgeryLocation(20, 3F, 5, 12F));
        armIcons[0] = addButton(new GuiButtonSurgeryLocation(21, 0F, 10, -5.3F));
        armIcons[1] = addButton(new GuiButtonSurgeryLocation(22, 0F, 16, -6.0F));
        legIcons[0] = addButton(new GuiButtonSurgeryLocation(23, 0F, 1, -2.2F));
        legIcons[1] = addButton(new GuiButtonSurgeryLocation(24, 0F, 6.4F, -2.2F));

        updateSurgerySlotsVisibility(true);
    }

    @Override
    protected void renderBg(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
        stack.pushPose();
        stack.translate(leftPos, topPos, 0);
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        Minecraft.getInstance().getTextureManager().bind(SURGERY_GUI_TEXTURES);
        this.blit(stack, 0, 0, 0, 0, 176, 222);
        renderSlotItems(stack);
        renderTolerance(stack);
        renderPlayerName(stack);
        SkeletonEntity skeleton = new SkeletonEntity(EntityType.SKELETON, this.menu.getSurgery().getLevel());
        ScreenUtils.renderEntityInScreen(stack, imageWidth / 2, imageHeight / 2, 50, skeleton);
        stack.popPose();

    }

    protected void renderTolerance(MatrixStack stack) {
        int maxTolerance = MathHelper.floor(Minecraft.getInstance().player.getAttributeValue(AttributeInit.TOLERANCE_ATTRIBUTE.get()));
        int tolerance = this.menu.getSurgery().essence;
        StringTextComponent essence = new StringTextComponent(tolerance + "/" + maxTolerance);
        font.draw(stack, essence, 18, 6, 0x1DA9C1);
        int criticalEssence = CybercraftConfig.C_ESSENCE.criticalEssence.get();
        int height = 49;
        int criticalToleranceHeight = MathHelper.floor((float) height * (float) criticalEssence / (float) maxTolerance);
        int toleranceHeight = MathHelper.floor((float) height * (float) tolerance / (float) maxTolerance) - criticalToleranceHeight;
        int greyHeight = height - toleranceHeight - criticalToleranceHeight;
        int x = 3;
        int y = 5;
        Minecraft.getInstance().getTextureManager().bind(SURGERY_GUI_TEXTURES);
        RenderSystem.enableBlend();
        if (greyHeight > 0)
            blit(stack, x, y, 211, 61, 9, greyHeight);
        if (toleranceHeight > 0)
            blit(stack, x, y + greyHeight, 176, 61, 9, toleranceHeight);
        if (criticalToleranceHeight > 0)
            blit(stack, x, y + greyHeight + toleranceHeight, 220, 61, 9, criticalToleranceHeight);
        RenderSystem.disableBlend();
    }

    protected void renderPlayerName(MatrixStack stack) {
        String name = "_" + Minecraft.getInstance().player.getName().getString().toUpperCase();
        font.draw(stack, name, (float) imageWidth / 2 - font.width(name), 115, 0x1DA9C1);
    }

    protected void renderSlotItems(MatrixStack stack) {
        int xLeft = (this.width - this.imageWidth) / 2;
        int yTop = (this.height - this.imageHeight) / 2;
        Minecraft.getInstance().getTextureManager().bind(SURGERY_GUI_TEXTURES);
        for (SurgeryContainer.SlotSurgery pos : visibleSlots) {
            this.blit(stack, xLeft + pos.x - 1, yTop + pos.y - 1, 176, 43, 18, 18);        // Blue slot
            this.blit(stack, xLeft + pos.x - 1, yTop + pos.y - 1 - 26, 176, 18, 18, 25);    // Red 'slot'
        }
    }

    @Override
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
    }

    protected void renderHoverTooltip(MatrixStack matrixStack, ItemStack stack, double mouseX, double mouseY, int extras) {
        List<ITextComponent> listTooltips = stack.getTooltipLines(Minecraft.getInstance().player, Minecraft.getInstance().options.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
        for (int indexTooltip = 0; indexTooltip < listTooltips.size(); indexTooltip++) {
            if (indexTooltip == 0) {
                listTooltips.set(indexTooltip, listTooltips.get(indexTooltip).copy().withStyle(stack.getItem().getRarity(stack).color));
            } else {
                listTooltips.set(indexTooltip, listTooltips.get(indexTooltip).copy().withStyle(TextFormatting.GRAY));
            }
        }

        if (extras == 1) {
            listTooltips.add(1, new TranslationTextComponent("cyberware.gui.remove"));
        } else if (extras >= 2) {
            listTooltips.add(1, new TranslationTextComponent("cyberware.gui.click"));

            if (extras == 3) {
                listTooltips.set(0, listTooltips.get(0).copy().append(new StringTextComponent(" ")).append(new TranslationTextComponent("cyberware.gui.added")));
            } else if (extras == 4) {
                listTooltips.set(0, listTooltips.get(0).copy().append(new StringTextComponent(" ")).append(new TranslationTextComponent("cyberware.gui.removed")));
            }
        }
        renderComponentTooltip(matrixStack, listTooltips, (int) mouseX, (int) mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseKey) {
        if (mouseKey == 0 && mouseX >= leftPos && mouseX < leftPos + imageWidth && mouseY >= topPos && mouseY < topPos + imageHeight) {
            oldRotate = ease.rotation;
            mouseDown = true;
            mouseDownX = mouseX;
            for (int n = 0; n < 5; n++) {
                lastDownX[n] = mouseDownX;
            }
        }

        // Right click to go back
        if (mouseKey == 1 && (page != 0 || ease.rotation != 0) && findSlot(mouseX, mouseY) == null && mouseY < topPos + imageHeight) {
            int pageToGoTo = page <= 10 ? 0 : parent;
            prepTransition(20, pageToGoTo);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseKey);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseKey) {
        // Make it spin! :D
        if (mouseKey == 0) {
            if (mouseDown) {
                mouseDown = false;
                rotateVelocity = (mouseX - lastDownX[4]);
                if (Math.abs(rotateVelocity) < 5) {
                    rotateVelocity = 0;
                }
            }
        }
        return super.mouseReleased(mouseX, mouseY, mouseKey);
    }

    @Override
    protected void slotClicked(Slot slot, int id, int mouseButton, ClickType type) {
        if (slot instanceof SurgeryContainer.SlotSurgery && !isSlotAccessible((SurgeryContainer.SlotSurgery) slot)) {
            return;
        }

        if (slot instanceof SurgeryContainer.SlotSurgery) {
            SurgeryContainer.SlotSurgery surgerySlot = (SurgeryContainer.SlotSurgery) slot;
            if (surgerySlot.getItem().isEmpty() && !surgerySlot.getPlayerStack().isEmpty()) {
                int number = surgerySlot.getSlotIndex();

                ItemStack playerSlotItem = surgerySlot.getPlayerStack();
                if (surgerySlot.slotDiscarded()) {
                    if (!surgery.doesItemConflict(playerSlotItem, ((SurgeryContainer.SlotSurgery) slot).slot, number % LibConstants.WARE_PER_SLOT)) {
                        surgerySlot.setDiscarded(false);
                        surgery.enableDependsOn(playerSlotItem, ((SurgeryContainer.SlotSurgery) slot).slot, number % LibConstants.WARE_PER_SLOT);
                        CCNetwork.PACKET_HANDLER.sendToServer(new SurgeryRemovePacket(surgery.getBlockPos(), number, false));
                    }
                } else {
                    if (surgery.canDisableItem(playerSlotItem, ((SurgeryContainer.SlotSurgery) slot).slot, number % LibConstants.WARE_PER_SLOT)) {
                        surgerySlot.setDiscarded(true);
                        surgery.disableDependants(playerSlotItem, ((SurgeryContainer.SlotSurgery) slot).slot, number % LibConstants.WARE_PER_SLOT);
                        CCNetwork.PACKET_HANDLER.sendToServer(new SurgeryRemovePacket(surgery.getBlockPos(), number, true));
                    }
                }

            }
        }
        super.slotClicked(slot, id, mouseButton, type);
    }

    @Nullable
    private Slot findSlot(double p_195360_1_, double p_195360_3_) {
        for (int i = 0; i < this.menu.slots.size(); ++i) {
            Slot slot = this.menu.slots.get(i);
            if (this.isHovering(slot, p_195360_1_, p_195360_3_) && slot.isActive()) {
                return slot;
            }
        }

        return null;
    }

    private boolean isHovering(Slot p_195362_1_, double p_195362_2_, double p_195362_4_) {
        return this.isHovering(p_195362_1_.x, p_195362_1_.y, 16, 16, p_195362_2_, p_195362_4_);
    }

    private void prepTransition(int time, int targetPage) {
        if (page == index.buttonId) {
            if (targetPage == 0) {
                back.visible = false;

                page = 0;
                showHideRelevantButtons(true);
                ease = current = configs[0].copy();

                return;
            } else {
                if (targetPage >= 18 && targetPage <= 20) {
                    ease = current = configs[targetPage].copy();
                    page = targetPage;
                    showHideRelevantButtons(true);
                    return;
                } else {
                    if (time == 0) {
                        ease = current = configs[targetPage].copy();
                        page = targetPage;
                        showHideRelevantButtons(true);
                        return;
                    }
                    ease = current = configs[0].copy();
                }
            }


        }

        // INDEX
        if (targetPage == index.buttonId) {
            showHideRelevantButtons(false);

            page = 9;
            parent = 0;

            back.visible = true;
            index.visible = false;

            indexStacks = NNLUtil.initListOfSize(40);
            indexPages = new int[5 * 8];
            indexNews = new int[5 * 8];

            indexCount = 0;
            for (int indexSurgeySlot = 0; indexSurgeySlot < surgery.slots.getSlots() && indexCount < indexStacks.size(); indexSurgeySlot++) {
                ItemStack playerStack = surgery.slotsPlayer.getStackInSlot(indexSurgeySlot);
                ItemStack surgeryStack = surgery.slots.getStackInSlot(indexSurgeySlot);

                int nu = 0;
                ItemStack draw = ItemStack.EMPTY;
                if (!surgeryStack.isEmpty()) {
                    draw = surgeryStack.copy();

                    if (!playerStack.isEmpty()) {
                        if (CybercraftAPI.areCybercraftStacksEqual(playerStack, surgeryStack)) {
                            draw.grow(playerStack.getCount());
                        } else {
                            indexStacks.set(indexCount, playerStack.copy());
                            ICybercraft.EnumSlot slot = ICybercraft.EnumSlot.values()[indexSurgeySlot / LibConstants.WARE_PER_SLOT];
                            indexPages[indexCount] = slot.getSlotNumber();
                            indexNews[indexCount] = 2;
                            indexCount++;

                            if (indexCount >= indexStacks.size()) {
                                break;
                            }
                        }
                    }
                    nu = 1;
                } else if (!playerStack.isEmpty() && !surgery.discardSlots[indexSurgeySlot]) {
                    draw = playerStack.copy();
                } else if (!playerStack.isEmpty() && surgery.discardSlots[indexSurgeySlot]) {
                    draw = playerStack.copy();
                    nu = 2;
                }

                if (!draw.isEmpty()) {
                    indexStacks.set(indexCount, draw);
                    ICybercraft.EnumSlot slot = ICybercraft.EnumSlot.values()[indexSurgeySlot / LibConstants.WARE_PER_SLOT];
                    indexPages[indexCount] = slot.getSlotNumber();
                    indexNews[indexCount] = nu;
                    indexCount++;
                }
            }

            return;
        }

        transitionStart = ticksExisted() + this.partialTicks;

        current = ease;
        operationTime = amountDone * time;

        showHideRelevantButtons(false);
        page = targetPage;
        target = configs[page].copy();
        if (page == 0) {
            back.visible = false;
            //index.visible = true;
        } else {
            back.visible = true;
            index.visible = false;
        }
    }

    protected void actionPerformed(Button button) {
        if (button.active) {
            int id = -1;
            if (button instanceof IIdButton)
                id = ((IIdButton) button).getId();

            // BACK
            if (id == back.buttonId) {
                if (page != 0 || ease.rotation != 0) {
                    int pageToGoTo = page <= 10 ? 0 : parent;
                    prepTransition(20, pageToGoTo);
                }
                return;
            }

            openTime = 1;
            if (id > 10) {
                parent = page;
            }

            if (id == 4) {
                prepTransition(20, 3);
            } else if (id == 6) {
                prepTransition(20, 5);
            } else if (id == 13) {
                prepTransition(20, 12);
            } else if (id == 16) {
                prepTransition(20, 15);
            } else {
                prepTransition(20, id);
            }
        }
    }

    private void showHideRelevantButtons(boolean show) {
        Button[] list = new Button[0];

        switch (page) {
            case 0:
                list = bodyIcons;
                break;
            case 1:
                list = headIcons;
                break;
            case 2:
                list = torsoIcons;
                break;
            case 7:
                list = crossSectionIcons;
                break;
            case 5:
                list = legIcons;
                break;
            case 3:
                list = armIcons;
                break;
        }

        for (Button guiButton : list) {
            guiButton.visible = show;
        }

        updateSurgerySlotsVisibility(show);
    }

    private void updateLocationButtons(float rot, float scale, float yOffset) {
        //SPECIAL CASE FOR GOING BACK TO MENU
        if (page == 0) {
            index.visible = true;
        }
        int xLeft = (width - imageWidth) / 2;
        int yTop = (height - imageHeight) / 2;

        GuiButtonSurgeryLocation[] list = new GuiButtonSurgeryLocation[0];

        switch (page) {
            case 1:
                list = headIcons;
                break;
            case 2:
                list = torsoIcons;
                break;
            case 7:
                list = crossSectionIcons;
                break;
            case 5:
                list = legIcons;
                break;
            case 3:
                list = armIcons;
                break;
        }

        if (page == 7) {
            rot += addedRotate;
        }

        float radRot = (float) Math.toRadians(rot);
        float sin = (float) Math.sin(radRot);
        float cos = -(float) Math.cos(radRot);
        float upDown = page == 7 ? (float) Math.sin(Math.toRadians(10)) : 0;

        for (GuiButtonSurgeryLocation guiButtonSurgeryLocation : list) {
            guiButtonSurgeryLocation.xPos = xLeft
                    + sin * scale * guiButtonSurgeryLocation.x3 * 0.065F
                    + cos * scale * guiButtonSurgeryLocation.z3 * 0.065F
                    + this.imageWidth / 2F
                    - 2.0F
                    - guiButtonSurgeryLocation.getWidth() / 2F;
            guiButtonSurgeryLocation.yPos = -upDown * cos * scale * guiButtonSurgeryLocation.x3 * 0.065F
                    + upDown * sin * scale * guiButtonSurgeryLocation.z3 * 0.065F
                    + yTop + 2 - yOffset
                    + scale * guiButtonSurgeryLocation.y3 * 0.065F
                    + 130 / 2F
                    - guiButtonSurgeryLocation.getHeight() / 2F;
            guiButtonSurgeryLocation.x = Math.round(guiButtonSurgeryLocation.xPos);
            guiButtonSurgeryLocation.y = Math.round(guiButtonSurgeryLocation.yPos);
        }
    }


    public boolean isSlotAccessible(SurgeryContainer.SlotSurgery slot) {
        return page == slot.slot.getSlotNumber();
    }

    protected void updateSurgerySlotsVisibility(boolean show) {
        visibleSlots.clear();
        Iterator<Slot> iteratorSlots = this.menu.slots.iterator();

        Slot slot = iteratorSlots.next();
        while (slot instanceof SurgeryContainer.SlotSurgery) {
            SurgeryContainer.SlotSurgery slotSurgery = (SurgeryContainer.SlotSurgery) slot;

            if (show && isSlotAccessible(slotSurgery)) {
                slotSurgery.setVisible(true);
                visibleSlots.add(slotSurgery);
            } else {
                slotSurgery.setVisible(false);
            }

            slot = iteratorSlots.next();
        }
    }

    private static PageConfiguration interpolate(float amountDone, PageConfiguration start, PageConfiguration end) {
        return new PageConfiguration(
                ease(Math.min(1.0F, amountDone), start.rotation, end.rotation),
                ease(Math.min(1.0F, amountDone), start.x, end.x),
                ease(Math.min(1.0F, amountDone), start.y, end.y),
                ease(Math.min(1.0F, amountDone), start.scale, end.scale),
                ease(Math.min(1.0F, amountDone), start.boxWidth, end.boxWidth),
                ease(Math.min(1.0F, amountDone), start.boxHeight, end.boxHeight),
                ease(Math.min(1.0F, amountDone), start.boxX, end.boxX),
                ease(Math.min(1.0F, amountDone), start.boxY, end.boxY)
        );
    }

    // http://stackoverflow.com/a/8317722/1754640
    private static float ease(float percent, float startValue, float endValue) {
        endValue -= startValue;
        float total = 100;
        float elapsed = percent * total;

        if ((elapsed /= total / 2) < 1)
            return endValue / 2 * elapsed * elapsed + startValue;
        return -endValue / 2 * ((--elapsed) * (elapsed - 2) - 1) + startValue;
    }

    private float ticksExisted() {
        return Minecraft.getInstance().player != null ? Minecraft.getInstance().player.tickCount : 0;
    }


    private class GuiButtonSurgeryLocation extends Button implements IIdButton {
        private static final int buttonSize = 16;
        private float x3;
        private float y3;
        private float z3;
        private float xPos;
        private float yPos;
        private final int buttonId;

        public GuiButtonSurgeryLocation(int buttonId, float x3, float y3, float z3) {
            super(0, 0, buttonSize, buttonSize, StringTextComponent.EMPTY, SurgeryScreen.this::actionPerformed);
            this.x3 = x3;
            this.y3 = y3;
            this.z3 = z3;
            this.visible = false;
            this.buttonId = buttonId;
        }

        @Override
        public void renderButton(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
            if (this.isHovered()) {
                this.renderToolTip(stack, mouseX, mouseY);
            }
            if (visible) {
                stack.pushPose();
                GlStateManager._enableBlend();

                float trans = 0.4F;
                if (this.isHovered()) {
                    trans = 0.6F;
                }
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, trans);


                Minecraft.getInstance().getTextureManager().bind(SURGERY_GUI_TEXTURES);
                this.blit(stack, 0, 0, 194, 0, width, height);

                stack.popPose();
            }
        }

        @Override
        public int getId() {
            return this.buttonId;
        }
    }

    private class GuiButtonSurgery extends Button implements IIdButton {
        private final int buttonId;

        public GuiButtonSurgery(int buttonId, int x, int y, int imageWidth, int ySize) {
            super(x, y, imageWidth, ySize, StringTextComponent.EMPTY, SurgeryScreen.this::actionPerformed);
            this.buttonId = buttonId;
        }

        @Override
        public void renderButton(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        }

        @Override
        public int getId() {
            return this.buttonId;
        }
    }

    private enum Type {
        BACK(176, 111, 18, 10),
        INDEX(176, 122, 12, 11);

        private int left;
        private int top;
        private int width;
        private int height;

        Type(int left, int top, int width, int height) {
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
        }
    }

    private class InterfaceButton extends Button implements IIdButton {
        private final Type type;
        private final int buttonId;

        public InterfaceButton(int buttonId, int x, int y, Type type) {
            super(x, y, type.width, type.height, StringTextComponent.EMPTY, SurgeryScreen.this::actionPerformed);
            this.type = type;
            this.buttonId = buttonId;
        }

        @Override
        public void renderButton(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
            if (this.isHovered()) {
                this.renderToolTip(stack, mouseX, mouseY);
            }
            if (visible) {
                stack.pushPose();
                RenderSystem.enableBlend();
                Minecraft mc = Minecraft.getInstance();
                float trans = 0.4F;
                if (this.isHovered()) trans = 0.6F;

                RenderSystem.color4f(1.0F, 1.0F, 1.0F, trans);
                mc.getTextureManager().bind(SURGERY_GUI_TEXTURES);

                this.blit(stack, x, y, type.left + type.width, type.top, type.width, type.height);

                RenderSystem.color4f(1.0F, 1.0F, 1.0F, trans / 2F);
                this.blit(stack, x, y, type.left, type.top, type.width, type.height);

                stack.popPose();
                RenderSystem.disableBlend();
            }

        }

        @Override
        public int getId() {
            return this.buttonId;
        }
    }

    private static class PageConfiguration {
        private float rotation;
        private float x;
        private float y;
        private float scale;
        private float boxWidth;
        private float boxHeight;
        private float boxX;
        private float boxY;

        private PageConfiguration(float rotation, float x, float y, float scale) {
            this(rotation, x, y, scale, 0, 0, 0, 0);
        }

        private PageConfiguration(float rotation, float x, float y, float scale, float boxWidth, float boxHeight, float boxX, float boxY) {
            this.rotation = rotation;
            this.x = x;
            this.y = y;
            this.scale = scale;
            this.boxHeight = boxHeight;
            this.boxWidth = boxWidth;
            this.boxX = boxX;
            this.boxY = boxY;
        }

        public PageConfiguration copy() {
            return new PageConfiguration(rotation, x, y, scale, boxWidth, boxHeight, boxX, boxY);
        }

    }

    private interface IIdButton {
        public int getId();
    }

    @Override
    protected void renderTooltip(MatrixStack p_230459_1_, int p_230459_2_, int p_230459_3_) {
        super.renderTooltip(p_230459_1_, p_230459_2_, p_230459_3_);
    }
}
