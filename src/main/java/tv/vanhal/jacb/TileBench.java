package tv.vanhal.jacb;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class TileBench extends TileEntity implements IInventory {
	protected NonNullList<ItemStack> slots;
	
	public TileBench() {
		slots = NonNullList.<ItemStack>withSize(10, ItemStack.EMPTY);
	}
	
	@Override
	public final NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt = super.writeToNBT(nbt);
		ItemStackHelper.saveAllItems(nbt, this.slots, false);
		return nbt;
	}
	
	@Override
	public final void readFromNBT(NBTTagCompound nbt) {
		slots = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		super.readFromNBT(nbt);
		if (nbt.hasKey("Items")) {
			ItemStackHelper.loadAllItems(nbt, this.slots);
		}
	}

	@Override
	public int getSizeInventory() {
		return slots.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return (ItemStack)slots.get(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		return ItemStackHelper.getAndSplit(this.slots, slot, amt);
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		return ItemStackHelper.getAndRemove(this.slots, slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.slots.set(slot, stack);
		if (stack != ItemStack.EMPTY && stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
		this.markDirty();
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
	public ITextComponent getDisplayName() {
		return null;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(pos) == this &&
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
		this.slots.clear();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.slots) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
	}

}
