package tv.vanhal.jacb.gui;

import tv.vanhal.jacb.TileBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/*
 * Contains some code from cofh Core
 */

public class SimpleGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		if (ID==1) {
			if (world.isAirBlock(pos)) {
				return null;
			}
			if (!(world.getTileEntity(pos) instanceof TileBench)) {
				return null;
			}
			TileBench tile = (TileBench)world.getTileEntity(pos);
			return new BenchContainer(player.inventory, tile);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		if (ID==1) {
			if (world.isAirBlock(pos)) {
				return null;
			}
			if (!(world.getTileEntity(pos) instanceof TileBench)) {
				return null;
			}
			TileBench tile = (TileBench)world.getTileEntity(pos);
			return new BenchGUI(player.inventory, tile);
		}
		return null;
	}


}
