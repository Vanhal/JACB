package tv.vanhal.jacb.core;

import tv.vanhal.jacb.JACB;
import tv.vanhal.jacb.ref.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;


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
	
	public void registerRenderers() {
		RenderItem render = Minecraft.getMinecraft().getRenderItem();
		
		render.getItemModelMesher().register(Item.getItemFromBlock(JACB.bench), 0, new ModelResourceLocation(Ref.MODID + ":betterBench", "inventory"));
	}
}
