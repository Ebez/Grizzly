package net.cobem.grizzly.habbohotel.rooms.robots;

import java.util.Date;
import net.cobem.grizzly.communication.composers.rooms.SendRoomUserStatusComposer;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.robots.RobotAI;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class Robot {

   public int ID;
   public int X;
   public int Y;
   public int Rotation;
   public String Name;
   public String Motto;
   public String Look;
   public String Status;
   public float Height;
   public Date LastUpdate;
   public RobotAI AI;
   public Session Owner;
   public Room CurrentRoom;


   public Robot(Session Owner, int ID, String Name, String Motto, String Look, Room Room) {
      this.ID = ID;
      this.Name = Name;
      this.Motto = Motto;
      this.Look = Look;
      this.LastUpdate = new Date();
      this.AI = new RobotAI(this);
      this.Owner = Owner;
      this.CurrentRoom = Room;
      this.Status = "";
   }

   public void updateStatus(String Status) {
      if(this.CurrentRoom != null) {
         this.CurrentRoom.sendMessage(SendRoomUserStatusComposer.compose(this.ID, new Position(this.X, this.Y, (double)this.Height), this.Rotation, "/flatctrl 4/" + Status + "//"));
      }

      this.Status = Status;
   }
}
