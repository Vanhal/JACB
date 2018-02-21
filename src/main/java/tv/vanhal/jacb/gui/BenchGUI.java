package tv.vanhal.jacb.gui;

import org.lwjgl.opengl.GL11;

import tv.vanhal.jacb.TileBench;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class BenchGUI extends GuiContainer {
	protected ResourceLocation background = new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png");

	public BenchGUI(InventoryPlayer inv, TileBench tile) {
		super(new BenchContainer(inv, tile));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		this.fontRenderer.drawString(I18n.translateToLocal("container.bettercrafting"), 28, 6, 0x404040);
		this.fontRenderer.drawString(I18n.translateToLocal("container.inventory"), 8, 74, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 176, 166);
	}

}
