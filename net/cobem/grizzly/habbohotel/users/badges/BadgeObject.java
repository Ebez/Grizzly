package net.cobem.grizzly.habbohotel.users.badges;

import java.sql.ResultSet;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.storage.DatabaseResult;

public class BadgeObject {

   public int ID;
   public int User;
   public int Slot;
   public String Code;
   public boolean Equipped;


   public BadgeObject(ResultSet Set) {
      try {
         this.ID = Set.getInt("id");
         this.User = Set.getInt("user");
         this.Slot = Set.getInt("slot");
         this.Code = Set.getString("code");
         this.Equipped = Set.getInt("equipped") == 1;
      } catch (Exception var3) {
         ;
      }

   }

   public BadgeObject(int ID) {
      DatabaseResult GetBadges = Grizzly.getStorage().query("SELECT * FROM server_user_badges WHERE id = \'" + ID + "\'");
      ResultSet Set = GetBadges.getTable();

      try {
         while(Set.next()) {
            this.ID = Set.getInt("id");
            this.User = Set.getInt("user");
            this.Slot = Set.getInt("slot");
            this.Code = Set.getString("code");
            this.Equipped = Set.getInt("equipped") == 1;
         }
      } catch (Exception var5) {
         ;
      }

   }

   public boolean append() {
      Grizzly.getStorage().execute("UPDATE server_user_badges SET user = \'" + this.User + "\', slot = \'" + this.Slot + "\', equipped = \'" + (this.Equipped?1:0) + "\' WHERE id = \'" + this.ID + "\'");
      return true;
   }
}
