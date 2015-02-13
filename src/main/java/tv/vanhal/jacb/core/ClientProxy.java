package tv.vanhal.jacb.core;

import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends Proxy {
	
	@Override
	public void registerEntities() {
		super.registerEntities();
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
