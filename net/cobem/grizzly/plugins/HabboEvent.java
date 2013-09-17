package net.cobem.grizzly.plugins;


public enum HabboEvent {

   OnChat("OnChat", 0),
   OnWalk("OnWalk", 1),
   OnConnect("OnConnect", 2),
   OnDisconnect("OnDisconnect", 3);
   // $FF: synthetic field
   private static final HabboEvent[] ENUM$VALUES = new HabboEvent[]{OnChat, OnWalk, OnConnect, OnDisconnect};


   private HabboEvent(String var1, int var2) {}
}
