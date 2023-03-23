package net.guidebookbutton.util;

import dev.ftb.mods.ftbquests.FTBQuests;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import potionstudios.byg.client.gui.biomepedia.screen.BiomepediaHomeScreen;

@Environment(EnvType.CLIENT)
public class ScreenCompat {

    public static void setBiomepediaScreen(MinecraftClient client) {
        client.setScreen(new BiomepediaHomeScreen(Text.literal("")));
    }

    public static void setFTBQuestsScreen(MinecraftClient client) {
            FTBQuests.PROXY.openGui();
    }

    public static void setParadiseLostScreen(MinecraftClient client) {
        FTBQuests.PROXY.openGui();
    }

}
