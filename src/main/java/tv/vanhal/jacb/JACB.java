package tv.vanhal.jacb;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tv.vanhal.jacb.core.Proxy;
import tv.vanhal.jacb.gui.SimpleGuiHandler;
import tv.vanhal.jacb.ref.Ref;
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
		public ItemStack getTabIconItem() {
			return new ItemStack(Blocks.CRAFTING_TABLE);
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
		GameRegistry.findRegistry(Block.class).register(bench);
		GameRegistry.findRegistry(Item.class).register(new ItemBlock(bench).setRegistryName(bench.getRegistryName()));
		
		//set recipes
		ShapelessOreRecipe recipe;
		if (config.getBoolean("straightSwap", "General", true, "JACB Crafting tables can be crafted by putting a vanilla crafting bench in a crafting grid, other wise it requires a chest as well")) {
			recipe = new ShapelessOreRecipe(null, new ItemStack(bench), Blocks.CRAFTING_TABLE);
		} else {
			recipe = new ShapelessOreRecipe(null, new ItemStack(bench), Blocks.CRAFTING_TABLE, Blocks.CHEST);
		}
		GameRegistry.findRegistry(IRecipe.class).register(recipe.setRegistryName(bench.getRegistryName()));
		
		config.save();
	}

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
