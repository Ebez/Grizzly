package net.cobem.grizzly.habbohotel.users.items;

import gnu.trove.map.hash.THashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.furni.Furniture;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.items.InventoryItem;
import net.cobem.grizzly.storage.DatabaseResult;

public class ItemHandler {

   private int ID;
   private THashMap<Integer, InventoryItem> Inventory;


   public ItemHandler(int ID) throws SQLException {
      this.ID = ID;
      this.Inventory = new THashMap();
      DatabaseResult GrabItems = Grizzly.getStorage().query("SELECT * FROM server_items WHERE user = \'" + ID + "\' AND room = \'0\'");
      if(GrabItems.getTable() != null) {
         ResultSet Items = GrabItems.getTable();

         while(Items.next()) {
            String Extra = Items.getString("extra").length() > 1?Items.getString("extra"):null;
            this.Inventory.put(Integer.valueOf(Items.getInt("id")), new InventoryItem(Items.getInt("id"), Items.getInt("user"), Items.getInt("base"), Extra));
         }
      }

   }

   public boolean remove(int ID, boolean Perm) {
      if(Perm) {
         Grizzly.getStorage().execute("DELETE FROM server_items WHERE id = \'" + ID + "\' AND user = \'" + this.ID + "\'");
      }

      this.Inventory.remove(Integer.valueOf(ID));
      return true;
   }

   public int add(Furniture Item, boolean Perm, String Extra) {
      Session Session = Grizzly.getHabboHotel().getSessions().getByID(this.ID);
      int LastID;
      if(Perm) {
         Grizzly.getStorage().execute("INSERT INTO server_items (room, user, base, extra) VALUES (\'0\', \'" + this.ID + "\', \'" + Item.ID + "\', \'" + Extra + "\')");
         if(Item.Interaction.equals("teleport")) {
            LastID = Grizzly.getStorage().getLastEntry("server_items", "user = \'" + this.ID + "\'");
            this.Inventory.put(Integer.valueOf(LastID), new InventoryItem(LastID, this.ID, Item.ID, Extra));
            Grizzly.getStorage().execute("INSERT INTO server_items (room, user, base, extra) VALUES (\'0\', \'" + this.ID + "\', \'" + Item.ID + "\', \'" + Extra + "\')");
            int Two = Grizzly.getStorage().getLastEntry("server_items", "user = \'" + this.ID + "\'");
            this.Inventory.put(Integer.valueOf(Two), new InventoryItem(Two, this.ID, Item.ID, Extra));
            Grizzly.getHabboHotel().getCatalog().createTeleporters(LastID, Two);
            return LastID;
         }
      }

      LastID = Grizzly.getStorage().getLastEntry("server_items", "user = \'" + this.ID + "\' AND base = \'" + Item.ID + "\'");
      this.Inventory.put(Integer.valueOf(LastID), new InventoryItem(LastID, this.ID, Item.ID, Extra));
      return LastID;
   }

   public THashMap<Integer, InventoryItem> getInventory() {
      return this.Inventory;
   }

   public THashMap<Integer, InventoryItem> getFloors() {
      THashMap Items = new THashMap();
      Iterator var3 = this.Inventory.values().iterator();

      while(var3.hasNext()) {
         InventoryItem Item = (InventoryItem)var3.next();
         if(Item.getBase().Type.contains("s")) {
            Items.put(Integer.valueOf(Item.ID), Item);
         }
      }

      return Items;
   }

   public THashMap<Integer, InventoryItem> getWalls() {
      THashMap Items = new THashMap();
      Iterator var3 = this.Inventory.values().iterator();

      while(var3.hasNext()) {
         InventoryItem Item = (InventoryItem)var3.next();
         if(Item.getBase().Type.contains("i")) {
            Items.put(Integer.valueOf(Item.ID), Item);
         }
      }

      return Items;
   }

   public InventoryItem getItem(int id) {
      return (InventoryItem)this.Inventory.get(Integer.valueOf(id));
   }
}
