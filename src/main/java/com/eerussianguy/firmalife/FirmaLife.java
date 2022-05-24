package com.eerussianguy.firmalife;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import com.eerussianguy.firmalife.client.FLClientEvents;
import com.eerussianguy.firmalife.client.FLClientForgeEvents;
import com.eerussianguy.firmalife.common.FLForgeEvents;
import com.eerussianguy.firmalife.common.blockentities.FLBlockEntities;
import com.eerussianguy.firmalife.common.blocks.FLBlocks;
import com.eerussianguy.firmalife.common.items.FLFoodTraits;
import com.eerussianguy.firmalife.common.items.FLItems;
import com.eerussianguy.firmalife.common.network.FLPackets;
import com.eerussianguy.firmalife.common.recipes.FLRecipeSerializers;
import com.eerussianguy.firmalife.common.recipes.FLRecipeTypes;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

@Mod(Firmalife.MOD_ID)
public class Firmalife
{
    public static final String MOD_ID = "firmalife";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Firmalife()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        FLItems.ITEMS.register(bus);
        FLBlocks.BLOCKS.register(bus);
        FLBlockEntities.BLOCK_ENTITIES.register(bus);
        FLRecipeTypes.RECIPE_TYPES.register(bus);
        FLRecipeSerializers.RECIPE_SERIALIZERS.register(bus);

        FLPackets.init();

        bus.addListener(this::setup);

        FLForgeEvents.init();
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            FLClientEvents.init();
            FLClientForgeEvents.init();
        }

    }

    public void setup(FMLCommonSetupEvent event)
    {
        // Vanilla registries are not thread safe
        event.enqueueWork(() -> {
            FLFoodTraits.init();
        });
    }

}
