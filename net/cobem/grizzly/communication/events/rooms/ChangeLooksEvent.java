package net.cobem.grizzly.communication.events.rooms;

import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.UpdateRoomUserInformationComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.GenderType;

public class ChangeLooksEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      GenderType Gender = GenderType.valueOf(Request.readString());
      String Look = Request.readString();
      Session.getHabbo().setGender(Gender);
      Session.getHabbo().setLook(Look);
      Session.getActor().CurrentRoom.sendMessage(UpdateRoomUserInformationComposer.compose(Session.getHabbo().getID(), Session.getHabbo().getLook(), Session.getHabbo().getGender().toString(), Session.getHabbo().getMotto()));
      Session.sendResponse(UpdateRoomUserInformationComposer.compose(-1, Session.getHabbo().getLook(), Session.getHabbo().getGender().toString(), Session.getHabbo().getMotto()));
      Session.getHabbo().append();
   }
}
