package net.guidebookbutton.config;

import blue.endless.jankson.Comment;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "guidebookbutton")
@Config(name = "guidebookbutton", wrapperName = "guidebookButtonConfigWrapper")
public class GuidebookButtonConfig {

    @Comment("Patchouli book identifier")
    public String bookIdentifier = "";
    public int posX = 127;
    public int posY = 61;
    @Comment("If false, open directly modpack book")
    public boolean openAllBooksScreen = true;

//    public String[] blackListedBooks = {""};
    @Comment("Array of book ids to ignore")
    public String[] blackList = {""};

    @Comment("Array of book item ids to add (in progress, may or may not work)")
    public String[] whiteList = {"byg:biomepedia","ftbquests:book"};
}
