package net.cobem.grizzly.habbohotel.catalog;


public enum CatalogLayout {

   default_3x3("default_3x3", 0),
   frontpage3("frontpage3", 1),
   guild_frontpage("guild_frontpage", 2),
   spaces_new("spaces_new", 3),
   recycler("recycler", 4),
   recycler_info("recycler_info", 5),
   recycler_prizes("recycler_prizes", 6),
   trophies("trophies", 7),
   plasto("plasto", 8),
   marketplace("marketplace", 9),
   marketplace_own_items("marketplace_own_items", 10),
   pets("pets", 11),
   pets2("pets2", 12),
   club_buy("club_buy", 13),
   club_gifts("club_gifts", 14),
   roomads("roomads", 15);
   // $FF: synthetic field
   private static final CatalogLayout[] ENUM$VALUES = new CatalogLayout[]{default_3x3, frontpage3, guild_frontpage, spaces_new, recycler, recycler_info, recycler_prizes, trophies, plasto, marketplace, marketplace_own_items, pets, pets2, club_buy, club_gifts, roomads};


   private CatalogLayout(String var1, int var2) {}
}
