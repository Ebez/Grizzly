package net.cobem.grizzly.communication.events.rooms;

import java.util.Iterator;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.SendFloorItemsComposer;
import net.cobem.grizzly.communication.composers.rooms.SendMoreRoomInformationComposer;
import net.cobem.grizzly.communication.composers.rooms.SendNewRoomUserInformationComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomSettingsInterfaceComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomUserRightsComposer;
import net.cobem.grizzly.communication.composers.rooms.SendWallItemsComposer;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class FinishRoomLoadEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      Room mRoom = Session.getActor().CurrentRoom;
      Session.sendResponse(SendFloorItemsComposer.compose(mRoom.Owner, mRoom.OwnerByName, mRoom.getFloors()));
      Session.sendResponse(SendWallItemsComposer.compose(mRoom.Owner, mRoom.OwnerByName, mRoom.getWalls()));
      Session.sendResponse(SendNewRoomUserInformationComposer.compose(Session.getHabbo().getID(), Session.getHabbo().getUsername()));
      mRoom.sendMessage(mRoom.getActorStatus());
      mRoom.sendMessage(mRoom.getRoomStatus());
      mRoom.sendStatuses(Session);
      Iterator var5 = Session.getHabbo().getIgnored().iterator();

      while(var5.hasNext()) {
         Integer Ignore = (Integer)var5.next();
         if(mRoom.getParty().containsKey(Ignore)) {
            Session Message = (Session)mRoom.getParty().get(Ignore);
            Session.getResponse().Initialize(2600);
            Session.getResponse().addInt(Integer.valueOf(1));
            Session.getResponse().addString(Message.getHabbo().getUsername());
            Session.sendResponse();
         }
      }

      var5 = mRoom.getParty().values().iterator();

      while(var5.hasNext()) {
         Session Ignore1 = (Session)var5.next();
         if(Session.getHabbo().getIgnorers().contains(Integer.valueOf(Ignore1.getHabbo().getID()))) {
            EventResponse Message1 = new EventResponse();
            Message1.Initialize(2600);
            Message1.addInt(Integer.valueOf(1));
            Message1.addString(Session.getHabbo().getUsername());
            Ignore1.sendResponse(Message1);
         }
      }

      Session.sendResponse(SendRoomSettingsInterfaceComposer.compose(mRoom.ID));
      Session.sendResponse(SendMoreRoomInformationComposer.compose(mRoom));
      Session.sendResponse(SendRoomUserRightsComposer.compose(mRoom));

      int Ignore2;
      for(var5 = mRoom.getRightHolders().iterator(); var5.hasNext(); Ignore2 = ((Integer)var5.next()).intValue()) {
         ;
      }

      if(Grizzly.getConfig().get("grizzly.rooms.log").equals("1")) {
         Grizzly.write(Session.getHabbo().getUsername() + " entered room " + mRoom.ID);
      }

      Session.getActor().GoalPosition = null;
      mRoom.refreshRobots();
      mRoom.getMap().ReGenerateCollisionMap();
      mRoom.getWiredUtility().onUserEnter(Session);
   }
}
