package tv.vanhal.jacb;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import tv.vanhal.jacb.compat.NeiHandler;
import tv.vanhal.jacb.core.Proxy;
import tv.vanhal.jacb.gui.BenchGUI;
import tv.vanhal.jacb.gui.SimpleGuiHandler;
import tv.vanhal.jacb.ref.Ref;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;


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
			return Item.getItemFromBlock(Blocks.crafting_table);
		}
	};
	
	//crafting bench Block
	public static BlockBench bench;


	public JACB() {
		logger.info("Now with extra crafting");
	}


	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		//Initialise the block
		bench = new BlockBench();
		GameRegistry.registerBlock(bench, bench.blockName);
		
		//set recipes
		ShapelessOreRecipe recipe;
		if (config.getBoolean("straightSwap", "General", true, "JACB Crafting tables can be crafted by putting a vanilla crafting bench in a crafting grid, other wise it requires a chest as well")) {
			recipe = new ShapelessOreRecipe(new ItemStack(bench), Blocks.crafting_table);
		} else {
			recipe = new ShapelessOreRecipe(new ItemStack(bench), Blocks.crafting_table, Blocks.chest);
		}
		GameRegistry.addRecipe(recipe);
		
		config.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		//if(Loader.isModLoaded("NotEnoughItems"))
		//	proxy.registerNeiHandler();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.registerEntities();
		if (event.getSide() == Side.CLIENT) {
			bench.postInit();
		}
	}

}
