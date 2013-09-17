package net.cobem.grizzly.habbohotel.rooms;

import gnu.trove.map.hash.THashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.RoomMonitor;
import net.cobem.grizzly.storage.DatabaseResult;

public class RoomHandler {

   private THashMap<Integer, Room> Rooms = new THashMap();
   private RoomMonitor Monitor = new RoomMonitor();
   public float MaxJump = (new Float(Grizzly.getConfig().get("habbo.pathfinder.maxjump"))).floatValue();
   public float MaxDrop = (new Float(Grizzly.getConfig().get("habbo.pathfinder.maxdrop"))).floatValue();


   public Room getByID(int ID) {
      return this.Rooms.containsKey(new Integer(ID))?(Room)this.Rooms.get(new Integer(ID)):null;
   }

   public THashMap<Integer, Room> getByOwner(int User) {
      THashMap Result = new THashMap();
      Iterator var4 = this.Rooms.values().iterator();

      while(var4.hasNext()) {
         Room UserRoom = (Room)var4.next();
         if(UserRoom.Owner == User) {
            Result.put(Integer.valueOf(UserRoom.ID), UserRoom);
         }
      }

      return Result;
   }

   public THashMap<Integer, Room> getByOwner(int User, boolean CheckDB) {
      THashMap Result = this.getByOwner(User);
      DatabaseResult GrabRooms = Grizzly.getStorage().query("SELECT * FROM server_rooms WHERE owner = \'" + User + "\'");

      try {
         ResultSet Results = GrabRooms.getTable();

         while(Results.next()) {
            if(!Result.containsKey(new Integer(Results.getInt("id")))) {
               Result.put(Integer.valueOf(Results.getInt("id")), new Room(Results));
            }
         }
      } catch (SQLException var6) {
         ;
      }

      return Result;
   }

   public THashMap<Integer, Room> getPopulated() {
      THashMap Result = new THashMap();
      Iterator var3 = this.Rooms.values().iterator();

      while(var3.hasNext()) {
         Room UserRoom = (Room)var3.next();
         if(UserRoom.getParty().size() >= 1) {
            Result.put(Integer.valueOf(UserRoom.ID), UserRoom);
         }
      }

      return Result;
   }

   public THashMap<Integer, Room> getRooms() {
      return this.Rooms;
   }

   public boolean dispose(int Room, boolean Perm) {
      if(Perm) {
         Grizzly.getStorage().execute("DELETE FROM server_rooms WHERE id = \'" + Room + "\'");
      }

      this.Rooms.remove(Integer.valueOf(Room));
      return true;
   }

   public int create(String Name, int Owner, String Model) {
      Grizzly.getStorage().execute("INSERT INTO server_rooms (name, owner, description, status, password, model, wallpaper, floor, outside) VALUES (\'" + Name + "\', \'" + Owner + "\', \'" + Name + "\', \'0\', \' \', \'" + Model + "\', \'0.0\', \'0.0\', \'0.0\')");
      DatabaseResult GrabLastID = Grizzly.getStorage().query("SELECT id FROM server_rooms ORDER BY id DESC LIMIT 1");
      int LastID = GrabLastID.getInt().intValue();
      Grizzly.getStorage().execute("INSERT INTO server_room_rights (room, user) VALUES (\'" + LastID + "\', \'" + Owner + "\')");
      this.Rooms.put(Integer.valueOf(LastID), new Room(LastID));
      return LastID;
   }
}
