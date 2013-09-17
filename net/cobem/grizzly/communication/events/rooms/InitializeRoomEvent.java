package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendRoomFullAlertComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomModelComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomOwnerRightsComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomPapersComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomRightsComposer;
import net.cobem.grizzly.communication.events.rooms.LeaveRoomEvent;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class InitializeRoomEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      int ID;
      if(Session.OverideID != 0) {
         ID = Session.OverideID;
         Session.OverideID = 0;
      } else {
         ID = Request.readInt().intValue();
      }

      Session.getActor().GoalRoom = ID;
      (new LeaveRoomEvent()).compose(Session, Request);
      if(Grizzly.getHabboHotel().getRooms().getByID(ID) == null) {
         try {
            Grizzly.getHabboHotel().getRooms().getRooms().put(Integer.valueOf(ID), new Room(ID));
         } catch (Exception var5) {
            return;
         }
      }

      Room mRoom = Grizzly.getHabboHotel().getRooms().getByID(ID);
      if(mRoom.getParty().size() == 50) {
         Session.sendResponse(SendRoomFullAlertComposer.compose());
      }

      Session.getActor().CurrentRoom = mRoom;
      mRoom.getParty().put(Integer.valueOf(Session.getHabbo().getID()), Session);
      if(Session.getActor().OverridePosition != null) {
         Session.getActor().CurrentPosition = Session.getActor().OverridePosition;
         Session.getActor().OverridePosition = null;
      } else {
         Session.getActor().CurrentPosition = new Position(mRoom.getModel().DoorX, mRoom.getModel().DoorY, (double)mRoom.getModel().DoorZ);
      }

      Session.getActor().Rotation = mRoom.getModel().DoorRot;
      Session.sendResponse(SendRoomModelComposer.compose(mRoom.Model, mRoom.ID));
      if(!mRoom.Wallpaper.equals("0.0")) {
         Session.sendResponse(SendRoomPapersComposer.compose("wallpaper", mRoom.Wallpaper));
      }

      if(!mRoom.Floor.equals("0.0")) {
         Session.sendResponse(SendRoomPapersComposer.compose("floor", mRoom.Floor));
      }

      Session.sendResponse(SendRoomPapersComposer.compose("landscape", mRoom.Landscape));
      Session.getActor().clearStatus();
      if(mRoom.Owner != Session.getHabbo().getID() && Session.getHabbo().getRank() < 6) {
         if(!Session.getActor().CurrentRoom.getRightHolders().contains(Integer.valueOf(Session.getHabbo().getID())) && Session.getHabbo().getRank() < 5) {
            Session.sendResponse(SendRoomRightsComposer.compose(0));
            Session.getActor().addStatus("flatctrl 0", "", true);
         } else {
            Session.sendResponse(SendRoomRightsComposer.compose(1));
            Session.getActor().addStatus("flatctrl 1", "", true);
         }
      } else {
         Session.sendResponse(SendRoomOwnerRightsComposer.compose());
         Session.sendResponse(SendRoomRightsComposer.compose(4));
         Session.getActor().addStatus("flatctrl 4", "useradmin", true);
      }

      Session.getActor().IsMoving = false;
      Session.getActor().GoalPosition = null;
   }
}
