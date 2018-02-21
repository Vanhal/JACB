package tv.vanhal.jacb.core;

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
