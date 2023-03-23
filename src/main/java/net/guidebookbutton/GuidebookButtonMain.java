package net.guidebookbutton;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.guidebookbutton.config.guidebookButtonConfigWrapper;
import net.guidebookbutton.network.GuidebookButtonServerPacket;

public class GuidebookButtonMain implements ModInitializer {

    public static final Identifier PATCHOULI_BUTTON = new Identifier("guidebookbutton", "textures/gui/patchouli_button.png");

    public static final guidebookButtonConfigWrapper CONFIG = guidebookButtonConfigWrapper.createAndLoad();

    public static final boolean isBYGLoaded = FabricLoader.getInstance().isModLoaded("byg");

    @Override
    public void onInitialize() {

        GuidebookButtonServerPacket.init();
    }

}
