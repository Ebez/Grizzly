package net.cobem.grizzly.habbohotel.furni;

import gnu.trove.map.hash.THashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.furni.Furniture;
import net.cobem.grizzly.habbohotel.furni.TeleporterHandler;
import net.cobem.grizzly.storage.DatabaseResult;

public class FurnitureHandler {

   private THashMap<Integer, Furniture> Furniture;
   private TeleporterHandler TeleporterHandler;


   public FurnitureHandler() throws SQLException {
      Grizzly.write("Loading furniture... ", true, false);
      this.Furniture = new THashMap();
      DatabaseResult GrabFurni = Grizzly.getStorage().query("SELECT * FROM server_furni");
      ResultSet Furni = GrabFurni.getTable();

      while(Furni.next()) {
         this.Furniture.put(Integer.valueOf(Furni.getInt("id")), new Furniture(Furni));
      }

      Grizzly.write(Integer.valueOf(this.Furniture.size()), false, true);
      this.TeleporterHandler = new TeleporterHandler();
   }

   public Furniture getByID(int ID) {
      return this.Furniture.containsKey(Integer.valueOf(ID))?(Furniture)this.Furniture.get(Integer.valueOf(ID)):null;
   }

   public TeleporterHandler getTeleporters() {
      return this.TeleporterHandler;
   }
}
