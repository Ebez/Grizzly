package net.cobem.grizzly.habbohotel.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.composers.user.SendBadgesComposer;
import net.cobem.grizzly.habbohotel.guild.Guild;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.GenderType;
import net.cobem.grizzly.habbohotel.users.badges.BadgeObject;
import net.cobem.grizzly.habbohotel.users.items.ItemHandler;
import net.cobem.grizzly.habbohotel.users.items.trading.TradeObject;
import net.cobem.grizzly.habbohotel.users.messenger.MessengerHandler;
import net.cobem.grizzly.storage.DatabaseResult;

public class User {

   private int ID;
   private int Credits;
   private int Pixels;
   private int Currency;
   private int Rank;
   private int Respect;
   private int DailyRespect;
   private int HomeRoom;
   private int NameChanges;
   private int Guild;
   private String Username;
   private String Mail;
   private String Look;
   private String Motto;
   private String Ticket;
   private String LastOnline;
   private GenderType Gender;
   private MessengerHandler Messenger;
   private ItemHandler Inventory;
   private List<Integer> IgnoredUsers = new ArrayList();
   private TradeObject TradeObject = null;
   private List<BadgeObject> Badges = new ArrayList();


   public User(String Ticket) {
      DatabaseResult GrabUser = Grizzly.getStorage().preparedQuery("SELECT * FROM server_users WHERE client_key = ? LIMIT 1", Arrays.asList(new String[]{Ticket}));
      ResultSet Habbo = GrabUser.getRow();
      if(Habbo != null) {
         this.FillClass(Habbo);
         this.Ticket = Ticket;
      }
   }

   public User(int ID) {
      DatabaseResult GrabUser = Grizzly.getStorage().preparedQuery("SELECT * FROM server_users WHERE id = ?", Arrays.asList(new String[]{String.valueOf(ID)}));
      ResultSet Habbo = GrabUser.getRow();
      this.FillClass(Habbo);
   }

   public User(ResultSet Set) {
      this.FillClass(Set);
   }

   private boolean FillClass(ResultSet Set) {
      try {
         this.ID = Set.getInt("id");
         this.Credits = Set.getInt("credits");
         this.Pixels = Set.getInt("pixels");
         this.Currency = Set.getInt("currency");
         this.Rank = Set.getInt("rank");
         this.Respect = Set.getInt("respect_points");
         this.DailyRespect = Set.getInt("daily_respect");
         this.HomeRoom = Set.getInt("home_room");
         this.NameChanges = Set.getInt("active_name_changes");
         this.Username = Set.getString("username");
         this.Mail = Set.getString("email");
         this.Look = Set.getString("look");
         this.Motto = Set.getString("motto");
         this.LastOnline = Set.getString("last_active");
         this.Gender = GenderType.valueOf(Set.getString("gender"));
         this.Inventory = new ItemHandler(this.ID);
         this.Guild = Set.getInt("primary_guild");
         DatabaseResult E = Grizzly.getStorage().query("SELECT * FROM server_user_ignores WHERE user = \'" + this.ID + "\'");
         ResultSet Ignores = E.getTable();

         while(Ignores.next()) {
            this.IgnoredUsers.add(Integer.valueOf(Ignores.getInt("ignored_user")));
         }

         DatabaseResult GetBadges = Grizzly.getStorage().query("SELECT * FROM server_user_badges WHERE user = \'" + this.ID + "\'");
         ResultSet Badges = GetBadges.getTable();

         while(Badges.next()) {
            this.Badges.add(new BadgeObject(Badges));
         }

         return true;
      } catch (Exception var6) {
         return false;
      }
   }

   public void refreshMessenger(boolean Online) {
      this.startMessenger();
      this.getMessenger().UpdateStatus(Online);
   }

   public void startMessenger() {
      this.Messenger = new MessengerHandler(this.ID);
   }

   public int getID() {
      return this.ID;
   }

   public int getCredits() {
      return this.Credits;
   }

   public void setCredits(int Value) {
      this.Credits = Value;
   }

   public int getPixels() {
      return this.Pixels;
   }

   public void setPixels(int Value) {
      this.Pixels = Value;
   }

   public int getCurrency() {
      return this.Currency;
   }

   public void setCurrency(int Value) {
      this.Currency = Value;
   }

   public int getRank() {
      return this.Rank;
   }

   public void setRank(int Value) {
      this.Rank = Value;
   }

   public int getRespect() {
      return this.Respect;
   }

   public void setRespect(int Value) {
      this.Respect = Value;
   }

   public int getDailyRespect() {
      return this.DailyRespect;
   }

   public void setDailyRespect(int Value) {
      this.DailyRespect = Value;
   }

   public int getHome() {
      return this.HomeRoom;
   }

   public void setHome(int Value) {
      this.HomeRoom = Value;
   }

   public int getNameChanges() {
      return this.NameChanges;
   }

   public void setNameChanges(int Value) {
      this.NameChanges = Value;
   }

   public String getUsername() {
      return this.Username;
   }

   public void setUsername(String Value) {
      this.Username = Value;
   }

   public String getMail() {
      return this.Mail;
   }

   public void setMail(String Value) {
      this.Mail = Value;
   }

   public String getLook() {
      return this.Look;
   }

   public void setLook(String Value) {
      this.Look = Value;
   }

   public String getMotto() {
      return this.Motto;
   }

   public void setMotto(String Value) {
      this.Motto = Value;
   }

   public String getTicket() {
      return this.Ticket;
   }

   public void setTicket(String Value) {
      this.Ticket = Value;
   }

   public String getLastOnline() {
      return this.LastOnline;
   }

   public void setLastOnline(String Value) {
      this.LastOnline = Value;
   }

   public GenderType getGender() {
      return this.Gender;
   }

   public void setGender(GenderType Value) {
      this.Gender = Value;
   }

   public int getGuild() {
      return this.Guild;
   }

   public void setGuild(int ID) {
      this.Guild = ID;
   }

   public ItemHandler getItems() {
      return this.Inventory;
   }

   public MessengerHandler getMessenger() {
      return this.Messenger;
   }

   public List<Integer> getIgnored() {
      return this.IgnoredUsers;
   }

   public TradeObject getTrade() {
      return this.TradeObject;
   }

   public void setTrade(TradeObject Obj) {
      this.TradeObject = Obj;
   }

   public List<Integer> getIgnorers() {
      ArrayList Return = new ArrayList();
      Iterator var3 = Grizzly.getHabboHotel().getSessions().getSessions().values().iterator();

      while(var3.hasNext()) {
         Session mSession = (Session)var3.next();
         if(mSession.getHabbo().IgnoredUsers.contains(Integer.valueOf(this.ID))) {
            Return.add(Integer.valueOf(mSession.getHabbo().ID));
         }
      }

      return Return;
   }

   public List<BadgeObject> getBadges() {
      return this.Badges;
   }

   public List<BadgeObject> getEquippedBadges() {
      ArrayList ReturnList = new ArrayList();
      Iterator var3 = this.Badges.iterator();

      while(var3.hasNext()) {
         BadgeObject Obj = (BadgeObject)var3.next();
         if(Obj.Equipped) {
            ReturnList.add(Obj);
         }
      }

      return ReturnList;
   }

   public boolean addBadge(String Code) {
      if(this.hasBadge(Code)) {
         return false;
      } else {
         Grizzly.getStorage().execute("INSERT INTO server_user_badges (user, code) VALUES (\'" + this.ID + "\', \'" + Code + "\')");
         int LastID = Grizzly.getStorage().getLastEntry("server_user_badges", "user = \'" + this.ID + "\' AND code = \'" + Code + "\'");
         this.Badges.add(new BadgeObject(LastID));
         Grizzly.getHabboHotel().getSessions().getByID(this.ID).sendResponse(SendBadgesComposer.compose(Grizzly.getHabboHotel().getSessions().getByID(this.ID).getHabbo().getBadges(), Grizzly.getHabboHotel().getSessions().getByID(this.ID).getHabbo().getEquippedBadges()));
         return true;
      }
   }

   public boolean hasBadge(String Code) {
      Iterator var3 = this.Badges.iterator();

      while(var3.hasNext()) {
         BadgeObject Obj = (BadgeObject)var3.next();
         if(Obj.Code.equals(Code)) {
            return true;
         }
      }

      return false;
   }

   public BadgeObject getBadgeByID(int ID) {
      Iterator var3 = this.Badges.iterator();

      while(var3.hasNext()) {
         BadgeObject Obj = (BadgeObject)var3.next();
         if(Obj.ID == ID) {
            return Obj;
         }
      }

      return null;
   }

   public BadgeObject getBadgeBySlot(int Slot) {
      Iterator var3 = this.Badges.iterator();

      while(var3.hasNext()) {
         BadgeObject Obj = (BadgeObject)var3.next();
         if(Obj.Slot == Slot) {
            return Obj;
         }
      }

      return null;
   }

   public BadgeObject getEquippedBadgeByCode(String Code) {
      Iterator var3 = this.getEquippedBadges().iterator();

      while(var3.hasNext()) {
         BadgeObject Obj = (BadgeObject)var3.next();
         if(Obj.Code.equals(Code)) {
            return Obj;
         }
      }

      return null;
   }

   public List<Guild> getGuilds() {
      ArrayList guilds = new ArrayList();
      DatabaseResult Guilds = Grizzly.getStorage().preparedQuery("SELECT * FROM server_guild_members WHERE user = ?", Arrays.asList(new String[]{String.valueOf(this.ID)}));
      ResultSet table = Guilds.getTable();

      try {
         while(table.next()) {
            guilds.add(new Guild(table.getInt("guild")));
         }

         return guilds;
      } catch (SQLException var5) {
         return new ArrayList();
      }
   }

   public boolean append() {
      Grizzly.getStorage().execute("UPDATE server_users SET credits = \'" + this.Credits + "\'," + "pixels = \'" + this.Pixels + "\'," + "currency = \'" + this.Currency + "\'," + "rank = \'" + this.Rank + "\'," + "username = \'" + this.Username + "\'," + "email = \'" + this.Mail + "\'," + "look = \'" + this.Look + "\'," + "motto = \'" + this.Motto + "\'," + "gender = \'" + this.Gender.toString().toUpperCase() + "\'," + "respect_points = \'" + this.Respect + "\'," + "daily_respect = \'" + this.DailyRespect + "\'," + "active_name_changes = \'" + this.NameChanges + "\'," + "home_room = \'" + this.HomeRoom + "\'," + "primary_guild = \'" + this.Guild + "\' WHERE id = \'" + this.ID + "\'");
      return true;
   }
}
