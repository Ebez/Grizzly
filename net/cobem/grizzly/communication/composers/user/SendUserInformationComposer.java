package net.cobem.grizzly.communication.composers.user;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class SendUserInformationComposer {

   public static EventResponse compose(Session Session) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.ShowUserInformationEvent);
      Message.addInt(Integer.valueOf(Session.getHabbo().getID()));
      Message.addString(Session.getHabbo().getUsername());
      Message.addString(Session.getHabbo().getLook());
      Message.addString(Session.getHabbo().getGender().toString());
      Message.addString(Session.getHabbo().getMotto());
      Message.addString(Session.getHabbo().getUsername().toLowerCase());
      Message.addBool(Boolean.valueOf(false));
      Message.addInt(Integer.valueOf(Session.getHabbo().getRespect()));
      Message.addInt(Integer.valueOf(Session.getHabbo().getDailyRespect()));
      Message.addInt(Integer.valueOf(Session.getHabbo().getDailyRespect()));
      Message.addBool(Boolean.valueOf(true));
      Message.addString(Session.getHabbo().getLastOnline());
      Message.addBool(Boolean.valueOf(Session.getHabbo().getNameChanges() > 0));
      Message.addBool(Boolean.valueOf(false));
      return Message;
   }
}
