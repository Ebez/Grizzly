package net.cobem.grizzly.communication.events.user;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.rooms.UpdateRoomUserInformationComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.utils.UserInputFilter;

public class ChangeMottoEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      String NewMotto = UserInputFilter.filterString(Request.readString(), false);
      if(!Session.getHabbo().getMotto().equals(NewMotto)) {
         if(Session.getActor().inRoom().booleanValue()) {
            Session.getHabbo().setMotto(NewMotto);
            Session.getActor().CurrentRoom.sendMessage(UpdateRoomUserInformationComposer.compose(Session.getHabbo().getID(), Session.getHabbo().getLook(), Session.getHabbo().getGender().toString(), Session.getHabbo().getMotto()));
            Session.sendResponse(UpdateRoomUserInformationComposer.compose(-1, Session.getHabbo().getLook(), Session.getHabbo().getGender().toString(), Session.getHabbo().getMotto()));
            Grizzly.getHabboHotel().getStream().changeMotto(Session);
            Session.getHabbo().append();
         }
      }
   }
}
