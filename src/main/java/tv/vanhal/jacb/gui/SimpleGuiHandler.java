package tv.vanhal.jacb.gui;

import tv.vanhal.jacb.TileBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

/*
 * Contains some code from cofh Core
 */

public class SimpleGuiHandler implements IGuiHandler {

	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID==1) {
			if (!world.blockExists(x, y, z)) {
				return null;
			}
			if (!(world.getTileEntity(x, y, z) instanceof TileBench)) {
				return null;
			}
			TileBench tile = (TileBench)world.getTileEntity(x, y, z);
			return new BenchContainer(player.inventory, tile);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID==1) {
			if (!world.blockExists(x, y, z)) {
				return null;
			}
			if (!(world.getTileEntity(x, y, z) instanceof TileBench)) {
				return null;
			}
			TileBench tile = (TileBench)world.getTileEntity(x, y, z);
			return new BenchGUI(player.inventory, tile);
		}
		return null;
	}


}
