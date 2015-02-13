package tv.vanhal.jacb;

import java.util.ArrayList;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBench extends BlockContainer {
	public final String blockName = "betterBench";
	
	@SideOnly(Side.CLIENT)
	protected IIcon top;
	@SideOnly(Side.CLIENT)
	protected IIcon front;

	protected BlockBench() {
		super(Material.wood);
		setHardness(1.0f);
		setBlockName(blockName);
		setCreativeTab(JACB.JACBTab);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new TileBench();
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, JACB.instance, 1, world, x, y, z);
		}
		return true;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
		TileBench tileEntity = (TileBench)world.getTileEntity(x, y, z);

        if (tileEntity != null) {
            ArrayList<ItemStack> items = getInsides(world, x, y, z);
            
            for (ItemStack item: items) {
            	dumpItems(world, x, y, z, item);
            }
            

            world.func_147453_f(x, y, z, block);
        }
        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }
	
	public void dumpItems(World world, int x, int y, int z, ItemStack items) {
		EntityItem entItem = new EntityItem(world, (float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f, items);
		float f3 = 0.05F;
		entItem.motionX = (double)((float)world.rand.nextGaussian() * f3);
		entItem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
		entItem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
		
		if (items.hasTagCompound()) {
			entItem.getEntityItem().setTagCompound((NBTTagCompound)items.getTagCompound().copy());
        }
		
		world.spawnEntityInWorld(entItem);
	}
	
	protected ArrayList<ItemStack> getInsides(World world, int x, int y, int z) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		
		TileBench tileEntity = (TileBench)world.getTileEntity(x, y, z);
		if (tileEntity != null) {
        	//get the inventory
            for (int i = 0; i < tileEntity.getSizeInventory(); ++i) {
                ItemStack itemstack = tileEntity.getStackInSlot(i);
                if (itemstack != null) {
                	items.add(itemstack);
                }
            }
		}
		
		return items;
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.top : Blocks.planks.getBlockTextureFromSide(side);//(side == 0 ? Blocks.planks.getBlockTextureFromSide(side) : (side != 2 && side != 4 ? this.blockIcon : this.front));
    }
	
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
		String iconPrefix = "minecraft:crafting_table";
		blockIcon = register.registerIcon(iconPrefix + "_side");
		top = register.registerIcon(iconPrefix + "_top");
		front = register.registerIcon(iconPrefix + "_front");
    }

}
