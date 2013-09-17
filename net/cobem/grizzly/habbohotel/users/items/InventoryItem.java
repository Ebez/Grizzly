package net.cobem.grizzly.habbohotel.users.items;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.furni.Furniture;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class InventoryItem {

   public int ID;
   private Session Owner;
   private Furniture BaseItem;
   public String Extra;


   public InventoryItem(int mID, int Owner, int ItemID, String Extra) {
      this.ID = mID;
      this.Owner = Grizzly.getHabboHotel().getSessions().getByID(Owner);
      this.BaseItem = Grizzly.getHabboHotel().getFurniture().getByID(ItemID);
      this.Extra = Extra;
   }

   public Session getOwner() {
      return this.Owner;
   }

   public Furniture getBase() {
      return this.BaseItem;
   }
}
