package tv.vanhal.jacb.compat;

import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import tv.vanhal.jacb.gui.BenchContainer;
import tv.vanhal.jacb.gui.BenchGUI;

@JEIPlugin
public class JeiHandler implements IModPlugin {
	
	public static void init(){
	}
	
	@Override
	public void register(IModRegistry registry) {
		registry.addRecipeClickArea(BenchGUI.class, 88, 32, 28, 23, VanillaRecipeCategoryUid.CRAFTING);
		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
		recipeTransferRegistry.addRecipeTransferHandler(BenchContainer.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
	}

	@Override
	public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {
		
	}

	@Override
	public void onItemRegistryAvailable(IItemRegistry itemRegistry) {
		
	}

	@Override
	public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {
		
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		
	}
}
