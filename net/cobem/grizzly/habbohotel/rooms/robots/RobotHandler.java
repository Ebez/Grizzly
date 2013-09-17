package net.cobem.grizzly.habbohotel.rooms.robots;

import gnu.trove.map.hash.THashMap;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.robots.Robot;
import net.cobem.grizzly.utils.DateFormat;

public class RobotHandler {

   private Room Room;
   public THashMap<Integer, Robot> Robots = new THashMap();


   public RobotHandler(Room Room) {
      this.Room = Room;
   }

   public void cycle() {
      Iterator var2 = this.Robots.values().iterator();

      while(var2.hasNext()) {
         Robot Robot = (Robot)var2.next();
         if(!Robot.AI.Terminate) {
            if(Grizzly.getDateHandler().GetDateFrom(Robot.LastUpdate, DateFormat.Seconds) >= 5) {
               Boolean Path = Boolean.valueOf((new Random()).nextInt(2) == 1);
               if(Path.booleanValue()) {
                  Robot.AI.sayPhrase();
               } else {
                  Robot.AI.move();
               }

               Robot.LastUpdate = new Date();
            }

            if(Robot.Status.contains("mv") && !Robot.AI.IsMoving) {
               Robot.updateStatus("");
            }

            if(Robot.AI.IsMoving) {
               Robot.LastUpdate = new Date();
               Collection var10 = Robot.CurrentRoom.getPathfinder().Path((byte)Robot.X, (byte)Robot.Y, (byte)Robot.AI.NewX, (byte)Robot.AI.NewY, Grizzly.getHabboHotel().getRooms().MaxDrop, Grizzly.getHabboHotel().getRooms().MaxJump);
               if(var10.size() == 0) {
                  Robot.AI.IsMoving = false;
                  return;
               }

               byte[] NextStep = (byte[])null;
               byte[] NextNextStep = (byte[])null;
               int Key = 0;
               Iterator Ex = var10.iterator();

               while(true) {
                  if(Ex.hasNext()) {
                     byte[] NextItem = (byte[])Ex.next();
                     if(Key == 0) {
                        NextStep = NextItem;
                        ++Key;
                        continue;
                     }

                     if(Key != 1) {
                        continue;
                     }

                     NextNextStep = NextItem;
                  }

                  FloorItem var11;
                  try {
                     var11 = Robot.CurrentRoom.getMap().GetItemAtPosition(new Position(NextStep[0], NextStep[1], 0.0D));
                  } catch (Exception var9) {
                     var11 = null;
                  }

                  Robot.Rotation = (new Position(Robot.X, Robot.Y, 0.0D)).Calculate(NextStep[0], NextStep[1]);
                  if(var11 != null) {
                     Robot.Height = (float)var11.Height.intValue();
                  } else {
                     Robot.Height = 0.0F;
                  }

                  this.Room.getPositions().remove(Integer.valueOf(Robot.ID));
                  this.Room.getMap().UserCollisionMap[Robot.X][Robot.Y] = false;
                  Robot.updateStatus("mv " + NextStep[0] + "," + NextStep[1] + "," + Double.toString((double)Robot.Height));
                  Robot.X = NextStep[0];
                  Robot.Y = NextStep[1];
                  this.Room.getPositions().put(Integer.valueOf(Robot.ID), new Position(Robot.X, Robot.Y, 0.0D));
                  if(NextNextStep == null) {
                     var11 = Robot.CurrentRoom.getItemForInteraction(new Position(Robot.X, Robot.Y, 0.0D));
                     if(var11 != null) {
                        if(var11.getBase().Sitable.booleanValue()) {
                           Robot.Rotation = var11.Rotation;
                           Robot.updateStatus("sit " + var11.getBase().StackHeight);
                        }
                     } else {
                        Robot.AI.IsMoving = false;
                     }
                  }
                  break;
               }
            }
         }
      }

   }

   public boolean add(Robot Robot) {
      this.Robots.put(Integer.valueOf(Robot.ID), Robot);
      return true;
   }

   public int count() {
      return this.Robots.size();
   }

   public THashMap<Integer, Robot> getRobots() {
      return this.Robots;
   }
}
