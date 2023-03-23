package net.guidebookbutton.mixin.access;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.util.Identifier;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

@Mixin(BookRegistry.class)
public interface BookRegistryAccessor {

    @Accessor(value = "books",remap = false)
    Map<Identifier, Book> getBooks();
}
