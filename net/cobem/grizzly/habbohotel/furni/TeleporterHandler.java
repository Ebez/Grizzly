package net.cobem.grizzly.habbohotel.furni;

import gnu.trove.map.hash.THashMap;
import java.sql.ResultSet;
import net.cobem.grizzly.Grizzly;

public class TeleporterHandler {

   public THashMap<Integer, Integer> Links = new THashMap();


   public TeleporterHandler() {
      ResultSet Teleporters = Grizzly.getStorage().query("SELECT * FROM server_teleporters").getTable();

      try {
         while(Teleporters.next()) {
            this.Links.put(Integer.valueOf(Teleporters.getInt("teleporter_one")), Integer.valueOf(Teleporters.getInt("teleporter_two")));
            this.Links.put(Integer.valueOf(Teleporters.getInt("teleporter_two")), Integer.valueOf(Teleporters.getInt("teleporter_one")));
         }
      } catch (Exception var3) {
         ;
      }

   }

   public int getPair(int ID) {
      return this.Links.get(Integer.valueOf(ID)) != null?((Integer)this.Links.get(Integer.valueOf(ID))).intValue():0;
   }

   public void addPair(int Furni, int Pair) {
      this.Links.put(Integer.valueOf(Furni), Integer.valueOf(Pair));
      this.Links.put(Integer.valueOf(Pair), Integer.valueOf(Furni));
   }
}
