package tv.vanhal.jacb.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import tv.vanhal.jacb.TileBench;

public class BenchContainer extends Container {
	protected TileBench bench;
	
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();
	protected boolean init = false;

	public BenchContainer(InventoryPlayer inv, TileBench tile) {
		bench = tile;
		
		for (int i = 0; i<9; i++) {
			craftMatrix.setInventorySlotContents(i, bench.getStackInSlot(i));
		}
		init = true;
		this.addSlotToContainer(new SlotCrafting(inv.player, craftMatrix, craftResult, 9, 124, 35)); //outputslot
		addCraftingGrid(craftMatrix, 0, 30, 17, 3, 3); //crafting grid
		
		addPlayerInventory(inv);
		
		onCraftMatrixChanged(craftMatrix);
	}
	
	
	@Override
	public void onCraftMatrixChanged(IInventory inv) {
		craftResult.setInventorySlotContents(0, 
    			CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, bench.getWorld()));
    	if (init) updateTile();
    }
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		updateTile();
	}
	
	protected void updateTile() {
		for (int i = 0; i<9; i++) {
			bench.setInventorySlotContents(i, craftMatrix.getStackInSlot(i));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(slotNum);
        
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotNum == 9)  {
                if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (slotNum >= 10 && slotNum < 37) {
                if (!this.mergeItemStack(itemstack1, 1, 9, false)) {
                    return null;
                }
            } else if (slotNum >= 37 && slotNum < 46) {
                if (!this.mergeItemStack(itemstack1, 1, 9, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack)null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
            updateTile();
        }

        return itemstack;
	}
	
	
	public void addPlayerInventory(InventoryPlayer inv) {
		addPlayerInventory(inv, 8, 84);
	}
	
	public void addPlayerInventory(InventoryPlayer inv, int x, int y) {
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
					this.addSlotToContainer(new Slot(inv, j + (i+1)*9, x + j*18, y + i*18));
				}
		}
		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(inv, i, 8 + i*18, y+58));
		}
	}
	
	public void addCraftingGrid(IInventory inventory, int startSlot, int x, int y, int width, int height) {
		int i = 0;
		for(int h = 0; h < height; h++) {
			for(int w = 0; w < width; w++) {
				this.addSlotToContainer(new Slot(inventory, startSlot + i++, x + (w*18), y + (h*18)));
			}
		}
	}
	
	@Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.getSlotIndex() != 9 && super.canMergeSlot(stack, slotIn);
    }
}
