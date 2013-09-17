package net.cobem.grizzly.habbohotel.roleplay.commands;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.composers.misc.SendNotificationComposer;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class Taxi implements ChatCommand {

   public int rank() {
      return 0;
   }

   public void execute(Session session, String[] args) {
      if(args.length == 0) {
         session.sendResponse(SendNotificationComposer.compose("Where do you want to go?"));
      } else {
         try {
            new Integer(args[0]);
         } catch (Exception var6) {
            ;
         }

         int RoomID = (new Integer(args[0])).intValue();
         if(Grizzly.getHabboHotel().getRooms().getByID(RoomID) == null) {
            try {
               Grizzly.getHabboHotel().getRooms().getRooms().put(Integer.valueOf(RoomID), new Room(RoomID));
            } catch (Exception var5) {
               session.sendResponse(SendNotificationComposer.compose("That room doesn\'t exist!"));
               return;
            }
         }

         Room mRoom = Grizzly.getHabboHotel().getRooms().getByID(RoomID);
         if(session.getRoleplay() != null) {
            session.travel(RoomID);
         } else {
            session.getActor().say("*Calls a taxi to " + mRoom.Title + " [" + RoomID + "]*", false);
            session.getRoleplay().callTaxi(RoomID);
         }

      }
   }
}
