package net.cobem.grizzly.habbohotel.rooms.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.rooms.models.Model;
import net.cobem.grizzly.storage.DatabaseResult;

public class ModelHandler {

   private Map<String, Model> Models;


   public ModelHandler() {
      Grizzly.write("Loading room models... ", true, false);
      this.Models = new HashMap();
      DatabaseResult GrabModels = Grizzly.getStorage().query("SELECT * FROM server_room_models");

      try {
         ResultSet Results = GrabModels.getTable();

         while(Results.next()) {
            this.Models.put(Results.getString("id"), new Model(Results));
         }
      } catch (SQLException var3) {
         ;
      }

      Grizzly.write(Integer.valueOf(this.Models.size()), false, true);
   }

   public Model get(String Name) {
      return (Model)this.Models.get(Name);
   }
}
