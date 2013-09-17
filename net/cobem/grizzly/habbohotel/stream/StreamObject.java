package net.cobem.grizzly.habbohotel.stream;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.users.User;
import net.cobem.grizzly.habbohotel.users.UserDAO;
import net.cobem.grizzly.utils.DateFormat;

public class StreamObject {

   public int ID;
   public int Type;
   public int User;
   public int Likes;
   public Date DatePosted = new Date();
   public String Message;
   public boolean Official;


   public StreamObject(ResultSet Set) {
      try {
         this.ID = Set.getInt("id");
         this.Type = Set.getInt("type");
         this.User = Set.getInt("user");
         this.Likes = Set.getInt("likes");
         this.DatePosted.setTime(Set.getLong("date"));
         this.Message = Set.getString("message");
         this.Official = Set.getInt("official") == 1;
      } catch (SQLException var3) {
         Grizzly.write(var3.getMessage());
      }

   }

   public User getUser() {
      return UserDAO.generateUser(this.User);
   }

   public int getMinutesFrom() {
      return Grizzly.getDateHandler().GetDateFrom(this.DatePosted, DateFormat.Minutes);
   }
}
