package tv.vanhal.jacb.core;

import tv.vanhal.jacb.TileBench;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Proxy {

	public void registerEntities() {
		GameRegistry.registerTileEntity(TileBench.class, "TileBench");
	}
	
	public void registerNeiHandler(){
		
	}

	public boolean isClient() {
		return false;
	}
	
	public boolean isServer() {
		return true;
	}
}
