package com.rena.cybercraft.client.screens;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.container.ScannerContainer;
import com.rena.cybercraft.common.tileentities.TileEntityScanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collections;
import java.util.Random;

public class ScannerScreen extends ContainerScreen<ScannerContainer> {

    public static final ResourceLocation TEXTURE = Cybercraft.modLoc("textures/gui/scanner.png");
    private static final int TICKS_PER_DOT = 20;
    private static final ImmutableList<ITextComponent> RANDOM_MESSAGES = ImmutableList.<ITextComponent>builder().add(
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message1"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message2"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message3"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message4"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message5"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message6"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message7"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message8"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message9"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message10"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message11"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message12"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message13"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message14"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message15"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message16"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message17"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message18"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message19"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message20"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message21"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message22"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message23"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message24"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message25"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message26"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message27"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message28"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message29"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message30"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message31"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message32"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message33"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message34"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message35"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message36"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message37"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message38"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message39"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message40"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message41"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message42"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message43"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message44"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message45"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message46"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message47"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message48"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message49"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message50"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message51"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message52"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message53"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message54"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message55"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message56"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message57"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message58"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message59"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message60"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message61"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message62"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message63"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message64"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message65"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message66"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message67"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message68"),
            new TranslationTextComponent(Cybercraft.MOD_ID + ".scanner.random_message69")
    ).build();

    private int tickCounter = 0, dotCounter = 0, chosenIndex = 0;
    private ITextComponent chosenText;

    public ScannerScreen(ScannerContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        chooseRandomText();
    }

    @Override
    protected void renderBg(MatrixStack stack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        Minecraft.getInstance().textureManager.bind(TEXTURE);
        int middleX = (this.width - this.imageWidth) / 2;
        int middleY = (this.height - this.imageHeight) / 2;
        this.blit(stack, middleX, middleY, 0, 0, 176, 166);
        this.blit(stack, middleX + 7, middleY + 30, 0, 166, 162, 9);
        if (menu.getCounterPercentage() != 0){
            float percentage = ((float) menu.getCounterPercentage()) / 100f;
            this.blit(stack, middleX + 7, middleY + 30, 0, 175, (int) (162f * percentage / 100), 9);
            ITextComponent chance = new StringTextComponent(TileEntityScanner.getBlueprintChance(this.menu.getTileEntity())*100 + "% ").append(new TranslationTextComponent(Cybercraft.MOD_ID +".scanner.chance")).withStyle(TextFormatting.AQUA);
            this.font.draw(stack, chance,
                     middleX + 176 - this.font.width(chance) - 5, middleY + 6, 4210752);
            this.font.draw(stack, chosenText.copy().withStyle(TextFormatting.DARK_AQUA), middleX + 8, middleY + 20, 4210752);
            if (tickCounter <= TICKS_PER_DOT){
                tickCounter ++;
            }else{
                if (dotCounter >= 3){
                    if (new Random().nextDouble() <= 0.02){
                        chooseRandomText();
                    }else
                        chosenText = RANDOM_MESSAGES.get(chosenIndex);
                    dotCounter = 0;
                }else {
                    chosenText = chosenText.copy().append(".");
                    dotCounter++;
                }
                tickCounter = 0;
            }
        }

    }

    @Override
    protected void renderTooltip(MatrixStack stack, int mouseX, int mouseY) {
        super.renderTooltip(stack, mouseX, mouseY);
        int middleX = (this.width - this.imageWidth) / 2;
        int middleY = (this.height - this.imageHeight) / 2;
        if (mouseX >= middleX + 7 && mouseY >= middleY + 30){
            if (mouseX <= middleX + 7 + 162 && mouseY <= middleY + 30 + 9){
                renderTooltip(stack, new StringTextComponent(menu.getCounterPercentage()/100 + "%").withStyle(TextFormatting.AQUA), mouseX, mouseY);
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    private void chooseRandomText(){
        int rand = new Random().nextInt(RANDOM_MESSAGES.size());
        chosenText = RANDOM_MESSAGES.get(rand);
        chosenIndex = rand;
    }
}
