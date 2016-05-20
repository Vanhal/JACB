package tv.vanhal.jacb;

import java.util.ArrayList;

import tv.vanhal.jacb.ref.Ref;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBench extends BlockContainer {
	public final String blockName = "betterBench";

	protected BlockBench() {
		super(Material.WOOD);
		setHardness(1.0f);
		this.setUnlocalizedName(blockName);
		setCreativeTab(JACB.JACBTab);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new TileBench();
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, JACB.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileBench tileEntity = (TileBench)world.getTileEntity(pos);

        if (tileEntity != null) {
            ArrayList<ItemStack> items = getInsides(world, pos);
            for (ItemStack item: items) {
            	dumpItems(world, pos, item);
            }


            //world.func_147453_f(x, y, z, block);
        }
        super.breakBlock(world, pos, state);
    }
	
	public void dumpItems(World world, BlockPos pos, ItemStack items) {
		EntityItem entItem = new EntityItem(world, (float)pos.getX() + 0.5f, (float)pos.getY() + 0.5f, (float)pos.getZ() + 0.5f, items);
		float f3 = 0.05F;
		entItem.motionX = (double)((float)world.rand.nextGaussian() * f3);
		entItem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
		entItem.motionZ = (double)((float)world.rand.nextGaussian() * f3);

		if (items.hasTagCompound()) {
			entItem.getEntityItem().setTagCompound((NBTTagCompound)items.getTagCompound().copy());
        }

		world.spawnEntityInWorld(entItem);
	}
	
	protected ArrayList<ItemStack> getInsides(World world, BlockPos pos) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		
		TileBench tileEntity = (TileBench)world.getTileEntity(pos);
		if (tileEntity != null) {
        	//get the inventory
            for (int i = 0; i < tileEntity.getSizeInventory() - 1; ++i) {
                ItemStack itemstack = tileEntity.getStackInSlot(i);
                if (itemstack != null) {
                	items.add(itemstack);
                }
            }
		}

		return items;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
	
	@SideOnly(Side.CLIENT)
	public void postInit() {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(Item.getItemFromBlock(this), 0, new ModelResourceLocation(Ref.MODID + ":" + blockName, "inventory"));
	}

}
