package net.guidebookbutton;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.guidebookbutton.network.GuidebookButtonClientPacket;

@Environment(EnvType.CLIENT)
public class GuidebookButtonClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GuidebookButtonClientPacket.init();
    }

}
