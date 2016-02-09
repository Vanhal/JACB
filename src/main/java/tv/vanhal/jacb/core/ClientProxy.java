package tv.vanhal.jacb.core;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import tv.vanhal.jacb.compat.JeiHandler;

public class ClientProxy extends Proxy {
	
	@Override
	public void registerEntities() {
		super.registerEntities();
	}
	
	@Override
	public void registerJeiHandler() {
	}

	@Override
	public boolean isClient() {
		return true;
	}
	
	@Override
	public boolean isServer() {
		return false;
	}
}
