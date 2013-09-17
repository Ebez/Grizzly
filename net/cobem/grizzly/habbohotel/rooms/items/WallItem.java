package net.cobem.grizzly.habbohotel.rooms.items;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.communication.iSerializeEvent;
import net.cobem.grizzly.habbohotel.furni.Furniture;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.storage.DatabaseResult;

public class WallItem implements iSerializeEvent {

   public int ID;
   public int Base;
   public int Room;
   public String Position;
   public String ExtraData;
   public boolean UpdateNeeded = false;
   public int UpdateCounter = 0;


   public WallItem(ResultSet Set) {
      this.FillClass(Set);
   }

   private boolean FillClass(ResultSet Set) {
      try {
         this.ID = Set.getInt("id");
         this.Base = Set.getInt("base");
         this.ExtraData = Set.getString("extra");
         this.Position = Set.getString("position");
         this.Room = Set.getInt("room");
         return true;
      } catch (SQLException var3) {
         Grizzly.write(var3.toString());
         return false;
      }
   }

   public WallItem(int ID) {
      DatabaseResult GrabItem = Grizzly.getStorage().query("SELECT * FROM server_items WHERE id = \'" + ID + "\'");
      ResultSet Results = GrabItem.getRow();
      this.FillClass(Results);
   }

   public Furniture getBase() {
      return Grizzly.getHabboHotel().getFurniture().getByID(this.Base);
   }

   public void serialize(EventResponse Message) {
      Message.addString(Integer.valueOf(this.ID));
      Message.addInt(Integer.valueOf(this.getBase().Sprite));
      Message.addString(this.Position);
      Message.addString(this.ExtraData);
      Message.addInt(Integer.valueOf(this.getBase().Interaction.equals("default")?1:0));
   }

   public void serializeUpdate(Room CurrentRoom) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.UpdateWallExtraDataEvent);
      Message.addBody(this);
      Message.addInt(Integer.valueOf(0));
      Message.addString(CurrentRoom.OwnerByName);
      CurrentRoom.sendMessage(Message);
   }

   public boolean changeState() {
      if(this.ExtraData.isEmpty()) {
         this.ExtraData = "1";
      } else if(this.ExtraData.contains("1")) {
         this.ExtraData = "0";
      } else if(this.ExtraData.contains("0")) {
         this.ExtraData = "1";
      }

      return true;
   }

   public void saveState() {
      Grizzly.getStorage().execute("UPDATE server_items SET extra = \'" + this.ExtraData + "\' WHERE id = \'" + this.ID + "\'");
   }

   public void cycle() {
      if(this.UpdateNeeded && this.UpdateCounter >= 1) {
         Grizzly.getHabboHotel().getInteractions().onCycle(this.getBase().Interaction, this);
         --this.UpdateCounter;
         if(this.UpdateCounter <= 0) {
            this.UpdateNeeded = false;
         }
      }

   }
}
