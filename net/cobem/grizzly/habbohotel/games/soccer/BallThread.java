package net.cobem.grizzly.habbohotel.games.soccer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.actors.Actor;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class BallThread implements Runnable {

   private boolean Rolling;
   private Thread Roller;
   private FloorItem RoomItem;
   private List<Position> Path = new ArrayList();
   private Session Session;
   private Actor Actor;
   private Position ItemPosition;


   public BallThread(FloorItem Item, List<Position> Path, Session User) {
      this.RoomItem = Item;
      this.Path = Path;
      this.Session = User;
      this.Actor = this.Session.getActor();
      this.ItemPosition = new Position(Item.X, Item.Y, (double)Item.Height.floatValue());
      this.Roller = new Thread(this);
      this.Roller.start();
      this.Rolling = true;
   }

   public void run() {
      while(this.Rolling) {
         Iterator var2 = this.Path.iterator();

         while(var2.hasNext()) {
            Position New = (Position)var2.next();
            if(New == this.Path.get(this.Path.size() - 1)) {
               Grizzly.getStorage().execute("UPDATE server_items SET x = \'" + New.X + "\', y = \'" + New.Y + "\', rotation = \'" + this.RoomItem.Rotation + "\', height = \'" + this.RoomItem.Height + "\' WHERE id = \'" + this.RoomItem.ID + "\'");
            }

            EventResponse Message = new EventResponse();
            Message.Initialize(HeaderLibrary.BallRollAnimationEvent);
            Message.addInt(Integer.valueOf(this.RoomItem.ID));
            Message.addInt(Integer.valueOf(this.RoomItem.getBase().Sprite));
            Message.addInt(Integer.valueOf(this.ItemPosition.X));
            Message.addInt(Integer.valueOf(this.ItemPosition.Y));
            Message.addInt(Integer.valueOf(this.RoomItem.Rotation));
            Message.addString(this.RoomItem.Height.toString().replace(',', '.'));
            Message.addInt(Integer.valueOf(0));
            Message.addInt(Integer.valueOf(0));
            Message.addString(Integer.valueOf(this.Path.size() * 11));
            Message.addInt(Integer.valueOf(-1));
            Message.addInt(Integer.valueOf(0));
            Message.addInt(Integer.valueOf(753651));
            this.Session.getActor().CurrentRoom.sendMessage(Message);
            Message.Initialize(HeaderLibrary.UpdateItemOnRollerEvent);
            Message.addInt(Integer.valueOf(this.ItemPosition.X));
            Message.addInt(Integer.valueOf(this.ItemPosition.Y));
            Message.addInt(Integer.valueOf(New.X));
            Message.addInt(Integer.valueOf(New.Y));
            Message.addInt(Integer.valueOf(1));
            Message.addInt(Integer.valueOf(this.RoomItem.ID));
            Message.addString(this.RoomItem.Height.toString().replace(',', '.'));
            Message.addString(Double.toString(this.Actor.CurrentRoom.getModel().getTileHeight(New.X, New.Y)).replace(',', '.'));
            Message.addInt(Integer.valueOf(0));
            this.Actor.CurrentRoom.getFloors().remove(Integer.valueOf(this.RoomItem.ID));
            this.Actor.CurrentRoom.getFloors().put(Integer.valueOf(this.RoomItem.ID), new FloorItem(this.RoomItem.ID));
            this.Session.getActor().CurrentRoom.sendMessage(Message);
            this.ItemPosition = new Position(New.X, New.Y, (double)this.RoomItem.Height.floatValue());

            try {
               Thread.sleep(300L);
            } catch (InterruptedException var5) {
               ;
            }
         }

         this.Rolling = false;
      }

   }
}
