package net.cobem.grizzly.habbohotel.guild;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.User;
import net.cobem.grizzly.habbohotel.users.UserDAO;
import net.cobem.grizzly.storage.DatabaseResult;

public class Guild {

   private int ID;
   private int Owner;
   private int Room;
   private int First_Color;
   private int Second_Color;
   private int Base;
   private int Base_Color;
   private String States;
   private String Title;
   private String Description;
   private Date Creation_Date = new Date();


   public Guild(ResultSet Set) {
      this.FillClass(Set);
   }

   public Guild(int ID) {
      this.FillClass(Grizzly.getStorage().query("SELECT * FROM server_guilds WHERE id = \'" + ID + "\'").getRow());
   }

   private boolean FillClass(ResultSet Set) {
      try {
         this.ID = Set.getInt("id");
         this.Owner = Set.getInt("owner");
         this.Room = Set.getInt("room");
         this.First_Color = Set.getInt("first_color");
         this.Second_Color = Set.getInt("second_color");
         this.Base = Set.getInt("base");
         this.Base_Color = Set.getInt("base_color");
         this.States = Set.getString("states");
         this.Title = Set.getString("title");
         this.Description = Set.getString("Description");
         this.Creation_Date.setTime(Set.getLong("creation_date"));
      } catch (SQLException var3) {
         Grizzly.write(var3.getMessage());
      }

      return true;
   }

   public int getID() {
      return this.ID;
   }

   public int getOwner() {
      return this.Owner;
   }

   public User getRealOwner() {
      return UserDAO.generateUser(this.Owner);
   }

   public Session getOwnerSession() {
      return Grizzly.getHabboHotel().getSessions().getByID(this.Owner);
   }

   public int getRoom() {
      return this.Room;
   }

   public Room getRealRoom() {
      return Grizzly.getHabboHotel().getRooms().getByID(this.Room);
   }

   public int getFirstColor() {
      return this.First_Color;
   }

   public int getSecondColor() {
      return this.Second_Color;
   }

   public int getBase() {
      return this.Base;
   }

   public int getBaseColor() {
      return this.Base_Color;
   }

   public String getTitle() {
      return this.Title;
   }

   public String getDescription() {
      return this.Description;
   }

   public String getStates() {
      return this.States;
   }

   public Date getCreationDate() {
      return this.Creation_Date;
   }

   public String getImage() {
      ArrayList states = new ArrayList();
      String[] var5;
      int var4 = (var5 = this.States.split(";")).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         String s = var5[var3];
         states.add(new Integer(s));
      }

      return Grizzly.getHabboHotel().getGuilds().getImage(this.Base, this.Base_Color, states);
   }

   public String getFirstHex() {
      return Grizzly.getHabboHotel().getGuilds().getHEX(this.First_Color);
   }

   public String getSecondHex() {
      return Grizzly.getHabboHotel().getGuilds().getHEX(this.Second_Color);
   }

   public List<User> getMembers() {
      ArrayList members = new ArrayList();
      DatabaseResult Users = Grizzly.getStorage().preparedQuery("SELECT user FROM server_guild_members WHERE guild = ?", Arrays.asList(new String[]{String.valueOf(this.ID)}));
      ResultSet row = Users.getRow();

      try {
         while(row.next()) {
            members.add(UserDAO.generateUser(row.getInt("user")));
         }

         return members;
      } catch (SQLException var5) {
         return new ArrayList();
      }
   }
}
