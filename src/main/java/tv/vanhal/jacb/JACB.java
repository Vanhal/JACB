package tv.vanhal.jacb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tv.vanhal.jacb.core.Proxy;
import tv.vanhal.jacb.gui.SimpleGuiHandler;
import tv.vanhal.jacb.ref.Ref;


@Mod(modid = Ref.MODID, name = Ref.MODNAME, version = Ref.Version)
public class JACB {
	@Instance(Ref.MODID)
	public static JACB instance;

	@SidedProxy(clientSide = "tv.vanhal."+Ref.MODID+".core.ClientProxy", serverSide = "tv.vanhal."+Ref.MODID+".core.Proxy")
	public static Proxy proxy;

	//logger
	public static final Logger logger = LogManager.getLogger(Ref.MODID);
	
	//gui handler
	public static SimpleGuiHandler guiHandler = new SimpleGuiHandler();

	//Creative Tab
	public static CreativeTabs JACBTab = new CreativeTabs("JACB") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Blocks.CRAFTING_TABLE);
		}
	};
	
	//crafting bench Block
	public static BlockBench bench;


	public JACB() {
		logger.info("Now with extra crafting");
	}


	@SuppressWarnings("deprecation")
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		//Initialise the block
		bench = new BlockBench();
		GameRegistry.registerBlock(bench, bench.blockName);
		
		//set recipes
		int mode = config.getInt("recipeMode", "General", 1, 1, 3, "The recipe-mode for the JACB table."
				+ "1: Straight swap (just place a crafting bench in the grid)"
				+ "2: Crafting bench + Chest"
				+ "3: Crafting bench surrounded by 3 wood");
		
		switch (mode) {
			case 1:
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(bench), Blocks.CRAFTING_TABLE));
				break;
			case 2:
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(bench), Blocks.CRAFTING_TABLE, Blocks.CHEST));
				break;
			case 3:
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bench), "BW", "WW", 'B', Blocks.CRAFTING_TABLE, 'W', Blocks.PLANKS));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bench), "WW", "BW", 'B', Blocks.CRAFTING_TABLE, 'W', Blocks.PLANKS));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bench), "WB", "WW", 'B', Blocks.CRAFTING_TABLE, 'W', Blocks.PLANKS));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bench), "WW", "WB", 'B', Blocks.CRAFTING_TABLE, 'W', Blocks.PLANKS));
				break;
			default:
				break;
		}
		
		config.save();
	}

	@SuppressWarnings("unused")
	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		if(Loader.isModLoaded("JEI"))
			proxy.registerJeiHandler();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.registerEntities();
		if (event.getSide() == Side.CLIENT) {
			bench.postInit();
		}
	}

}
