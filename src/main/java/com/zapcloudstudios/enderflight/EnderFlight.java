package com.zapcloudstudios.enderflight;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import com.zapcloudstudios.enderflight.entity.EntityEnderFlightPearl;
import com.zapcloudstudios.enderflight.events.EventHooks;
import com.zapcloudstudios.enderflight.item.ItemEnderFlightBlock;
import com.zapcloudstudios.enderflight.player.PlayerData;
import com.zapcloudstudios.enderflight.utils.EnderFlightUtils;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = EnderFlight.ID, name = EnderFlight.NAME, version = EnderFlight.MAJOR + "." + EnderFlight.MINOR + "." + EnderFlight.BUILD)
public class EnderFlight
{
	public static final String ID = "enderflight";
	public static final String NAME = "Ender Flight";
	public static final String CHANNEL = ID;

	public static final String CHATNAME = ID;

	public static final String PLAYERDATA = ID;

	public static final int MAJOR = 1;
	public static final int MINOR = 0;
	public static final int BUILD = 0;

	public final static int block3dItemRenderId = RenderingRegistry.getNextAvailableRenderId();
	public final static int block2dItemRenderId = RenderingRegistry.getNextAvailableRenderId();

	public static final Class blockItem = ItemEnderFlightBlock.class;

	@Instance("enderflight")
	public static EnderFlight instance;

	public static final String shiftInfo = "\u00A7" + "o" + "Hold Shift For More...";

	@SidedProxy(modId = EnderFlight.ID, clientSide = "com.zapcloudstudios.enderflight.client.ClientProxy", serverSide = "com.zapcloudstudios.enderflight.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;

		MinecraftForge.EVENT_BUS.register(new EventHooks());
	}

	@EventHandler
	public void onPostInit(FMLPostInitializationEvent event)
	{
		TextureManager manage = Minecraft.getMinecraft().renderEngine;
		try
		{
			ITextureObject blocks = manage.getTexture(TextureMap.locationBlocksTexture);
			ITextureObject items = manage.getTexture(TextureMap.locationItemsTexture);

			ImageIO.write(EnderFlightUtils.getImageFromGLTexture(blocks.getGlTextureId()), "png", new File("blockMap.png"));
			ImageIO.write(EnderFlightUtils.getImageFromGLTexture(items.getGlTextureId()), "png", new File("itemMap.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	protected void registerBlock(Block block, String name)
	{
		GameRegistry.registerBlock(block, this.blockItem, name);
	}

	protected void registerBlockWithoutItem(Block block, String name)
	{
		GameRegistry.registerBlock(block, null, name);
	}

	protected void registerItem(Item item, String name)
	{
		GameRegistry.registerItem(item, name);
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		EntityRegistry.registerGlobalEntityID(EntityEnderFlightPearl.class, "EnderFlightPearl", EntityRegistry.findGlobalUniqueEntityId());

		this.proxy.registerRenderers();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
	}

	public static PlayerData getPlayerData(EntityPlayer player)
	{
		return (PlayerData) player.getExtendedProperties(PLAYERDATA);
	}

	@SideOnly(Side.CLIENT)
	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public static void addInformation(ITipItem tipItem, ItemStack stack, EntityPlayer player, List info, boolean advanced)
	{
		String[] tips;
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			tips = tipItem.getShiftTip(player, stack);
			if (tips == null)
			{
				tips = tipItem.getTip(player, stack);
			}
		}
		else
		{
			tips = tipItem.getTip(player, stack);
			String[] shifttips = tipItem.getShiftTip(player, stack);
			if (shifttips != null && shifttips.length != 0)
			{
				List<String> moretips = new ArrayList<String>();
				for (int i = 0; i < tips.length; i++)
				{
					moretips.add(tips[i]);
				}
				if (!tips[tips.length - 1].isEmpty())
				{
					moretips.add("");
				}
				moretips.add(EnderFlight.shiftInfo);

				tips = moretips.toArray(tips);
			}
		}
		if (tips == null)
		{
			return;
		}
		boolean flag = false;
		for (int i = 0; i < tips.length; i++)
		{
			String tip = tips[i];
			String[] tiplines = EnderFlightUtils.wrapString(tip, 40);
			if (tiplines.length > 1 && flag)
			{
				info.add("");
			}
			for (int j = 0; j < tiplines.length; j++)
			{
				info.add(tiplines[j]);
			}
			if (tiplines.length > 1 && i < tips.length - 1)
			{
				info.add("");
				flag = false;
			}
			else
			{
				flag = true;
			}
		}
	}
}
