package net.cobem.grizzly.habbohotel.rooms.items;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.communication.iSerializeEvent;
import net.cobem.grizzly.habbohotel.furni.Furniture;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.storage.DatabaseResult;

public class FloorItem implements iSerializeEvent {

   public int ID;
   public int Base;
   public int X;
   public int Y;
   public int Rotation;
   public int Room;
   public Float Height;
   public String StringHeight;
   public String ExtraData;
   public boolean UpdateNeeded = false;
   public int UpdateCounter = 0;
   public Session Interactor = null;


   public FloorItem(ResultSet Set) {
      this.FillClass(Set);
   }

   private boolean FillClass(ResultSet Set) {
      try {
         this.ID = Set.getInt("id");
         this.Base = Set.getInt("base");
         this.X = Set.getInt("x");
         this.Y = Set.getInt("y");
         this.Rotation = Set.getInt("rotation");
         this.Room = Set.getInt("room");
         this.Height = Float.valueOf(Set.getFloat("height"));
         this.StringHeight = "" + this.Height;
         this.ExtraData = Set.getString("extra");
         return true;
      } catch (SQLException var3) {
         Grizzly.write(var3.toString());
         return false;
      }
   }

   public FloorItem(int ID) {
      DatabaseResult GrabItem = Grizzly.getStorage().query("SELECT * FROM server_items WHERE id = \'" + ID + "\'");
      ResultSet Results = GrabItem.getRow();
      this.FillClass(Results);
   }

   public Furniture getBase() {
      return Grizzly.getHabboHotel().getFurniture().getByID(this.Base);
   }

   public void serialize(EventResponse Message) {
      Message.addInt(Integer.valueOf(this.ID));
      Message.addInt(Integer.valueOf(this.getBase().Sprite));
      Message.addInt(Integer.valueOf(this.X));
      Message.addInt(Integer.valueOf(this.Y));
      Message.addInt(Integer.valueOf(this.Rotation));
      Message.addString(Float.toString(this.Height.floatValue()));
      if(this.getBase().ItemTitle.equals("ads_background") && this.ExtraData.length() > 10) {
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(1));
         if(!this.ExtraData.equals("")) {
            String[] Split = this.ExtraData.split("  ");
            Message.addInt(Integer.valueOf(5));

            for(int i = 0; i <= Split.length - 1; ++i) {
               Message.addString(Split[i].trim());
            }
         } else {
            Message.addInt(Integer.valueOf(0));
         }

         Message.addInt(Integer.valueOf(-1));
         Message.addInt(Integer.valueOf(1));
      } else {
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(0));
         Message.addString(this.ExtraData);
         Message.addInt(Integer.valueOf(-1));
         Message.addInt(Integer.valueOf(this.getBase().Interaction.equals("default")?1:0));
      }

   }

   public boolean changeState() {
      if((this.getBase().Interaction.equals("default") || this.getBase().Interaction.equals("gate")) && this.getBase().InteractionModesCount > 1) {
         if(this.ExtraData.isEmpty() || this.ExtraData.equals(" ")) {
            this.ExtraData = "0";
         }

         Integer Temp = Integer.valueOf(Integer.parseInt(this.ExtraData) + 1);
         if(Temp.intValue() > this.getBase().InteractionModesCount) {
            this.ExtraData = "0";
         } else {
            this.ExtraData = "" + Temp;
         }

         return true;
      } else {
         return false;
      }
   }

   public void saveState() {
      Grizzly.getStorage().execute("UPDATE server_items SET extra = \'" + this.ExtraData + "\' WHERE id = \'" + this.ID + "\'");
   }

   public void serializeUpdate(Session Session) {
      Session.getResponse().Initialize(HeaderLibrary.UpdateFloorExtraDataEvent);
      Session.getResponse().addString(Integer.valueOf(this.ID));
      Session.getResponse().addInt(Integer.valueOf(0));
      Session.getResponse().addString(this.ExtraData);
      Session.getActor().CurrentRoom.sendMessage(Session.getResponse());
   }

   public void serializeUpdate(Room CurrentRoom) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.UpdateFloorExtraDataEvent);
      Message.addString(Integer.valueOf(this.ID));
      Message.addInt(Integer.valueOf(0));
      Message.addString(this.ExtraData);
      CurrentRoom.sendMessage(Message);
   }

   public void cycle() {
      if(this.UpdateNeeded && this.UpdateCounter >= 1) {
         Grizzly.getHabboHotel().getInteractions().onCycle(this.getBase().Interaction, this);
         --this.UpdateCounter;
         if(this.UpdateCounter <= 0) {
            this.UpdateCounter = 0;
            this.UpdateNeeded = false;
         }
      }

   }
}
