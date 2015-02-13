package tv.vanhal.jacb;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;

public class TileBench extends TileEntity implements IInventory {
	protected ItemStack[] slots;
	
	public TileBench() {
		slots = new ItemStack[10];
	}
	
	@Override
	public final void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (slots.length>0) {
			NBTTagList contents = new NBTTagList();
			for (int i = 0; i < slots.length; i++) {
				if (slots[i] != null) {
					ItemStack stack = slots[i];
					NBTTagCompound tag = new NBTTagCompound();
					tag.setByte("Slot", (byte)i);
					stack.writeToNBT(tag);
					contents.appendTag(tag);
				}
			}
			nbt.setTag("Contents", contents);
		}
	}
	
	@Override
	public final void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("Contents")) {
			NBTTagList contents = nbt.getTagList("Contents", 10);
			for (int i = 0; i < contents.tagCount(); i++) {
				NBTTagCompound tag = (NBTTagCompound) contents.getCompoundTagAt(i);
				byte slot = tag.getByte("Slot");
				if (slot < slots.length) {
					slots[slot] = ItemStack.loadItemStackFromNBT(tag);
				}
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slots[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		if (slots[slot] != null) {
			ItemStack newStack;
			if (slots[slot].stackSize <= amt) {
				newStack = slots[slot];
				slots[slot] = null;
			} else {
				newStack = slots[slot].splitStack(amt);
				if (slots[slot].stackSize == 0) {
					slots[slot] = null;
				}
			}
			return newStack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (slots[slot]!=null) {
			ItemStack stack = slots[slot];
			slots[slot] = null;
			return stack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		slots[slot] = stack;
		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return null;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(pos) == this &&
				 player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 64;
	}

	@Override
	public void openInventory(EntityPlayer playerIn) { }

	@Override
	public void closeInventory(EntityPlayer playerIn) { }

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		if (slot==9) return false;
		return true;
	}





	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		
	}
}
