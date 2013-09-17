package net.cobem.grizzly.habbohotel.users;

import java.sql.ResultSet;
import java.util.Date;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.users.User;
import net.cobem.grizzly.storage.DatabaseResult;

public class UserDAO {

   public static User generateUser(int ID) {
      return new User(ID);
   }

   public static void banUser(int ID, Date d) {
      Grizzly.getStorage().execute("INSERT INTO server_bans (user, time) VALUES (\'" + ID + "\', \'" + d.getTime() + "\')");
   }

   public static boolean isBanned(int ID) {
      DatabaseResult GrabBan = Grizzly.getStorage().query("SELECT time FROM server_bans WHERE user = \'" + ID + "\'");
      if(GrabBan.rowCount() == 0) {
         return false;
      } else {
         long now = (new Date()).getTime();
         long bantime = (new Long(GrabBan.getString())).longValue();
         return now < bantime;
      }
   }

   public static User generateUser(String Username) {
      DatabaseResult GrabUser = Grizzly.getStorage().query("SELECT * FROM server_users WHERE username = \'" + Username + "\'");
      ResultSet Habbo = GrabUser.getRow();
      return new User(Habbo);
   }
}
