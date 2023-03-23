package net.guidebookbutton.network;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.guidebookbutton.GuidebookButtonMain;
import net.guidebookbutton.mixin.access.BookRegistryAccessor;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;


public class GuidebookButtonServerPacket {

    public static final Identifier SEND_PATCHOULI_SCREEN_PACKET = new Identifier("patchoulibutton", "send_patchouli_screen_packet");
    public static final Identifier OPEN_MODPACK_SCREEN_PACKET = new Identifier("patchoulibutton", "open_modpack_screen_packet");

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(SEND_PATCHOULI_SCREEN_PACKET, (server, player, handler, buffer, sender) -> {
            server.execute(() -> {
                writeS2CModpackScreenPacket(player);
            });
        });
    }

    public static void writeS2CModpackScreenPacket(ServerPlayerEntity serverPlayerEntity) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        Iterator<Book> iterator = ((BookRegistryAccessor) BookRegistry.INSTANCE).getBooks().values().iterator();

        List blacklistedBooks = Arrays.asList(GuidebookButtonMain.CONFIG.blackList());
        String[] whitelistedBooks = GuidebookButtonMain.CONFIG.whiteList();

        while (iterator.hasNext()) {
            Book book = iterator.next();
            boolean blacked = blacklistedBooks.contains(book.id.toString());
            if (!blacked) {
                buf.writeIdentifier(book.id);
                Item item = Registry.ITEM.get(book.model);
                if (item == null || item.equals(Items.AIR))
                    item = Items.BOOK;

                buf.writeIdentifier(Registry.ITEM.getId(item));
                buf.writeString(book.name);
            }
        }

//        if (PatchouliButtonMain.isBYGLoaded) {
//            Identifier identifier = new Identifier("byg", "biomepedia");
//            buf.writeIdentifier(identifier);
//            buf.writeIdentifier(identifier);
//            buf.writeString(Registry.ITEM.get(identifier).getName().getString());
//        }

        if (whitelistedBooks[0].contains(":")) {
            for (int i = 0; i < whitelistedBooks.length; i++) {
                String[] namespaceTemp = whitelistedBooks[i].split(":");
                if (FabricLoader.getInstance().isModLoaded(namespaceTemp[0])) {
                    Identifier identifier = new Identifier(namespaceTemp[0], namespaceTemp[1]);
                    buf.writeIdentifier(identifier);
                    buf.writeIdentifier(identifier);
                    buf.writeString(Registry.ITEM.get(identifier).getName().getString());
                }
            }
    }
        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(OPEN_MODPACK_SCREEN_PACKET, buf);
        serverPlayerEntity.networkHandler.sendPacket(packet);
    }
}
