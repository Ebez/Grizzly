package net.cobem.grizzly.communication.events.misc;

import java.util.Arrays;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.storage.DatabaseResult;

public class ValidateNewNameEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      String Name = Request.readString();
      byte ErrorCode = 0;
      if(Name.length() > 32) {
         ErrorCode = 1;
      }

      if(Name.length() <= 2) {
         ErrorCode = 2;
      }

      DatabaseResult CheckName = Grizzly.getStorage().preparedQuery("SELECT * FROM server_users WHERE username = ?", Arrays.asList(new String[]{Name}));
      if(CheckName.rowCount() >= 1) {
         ErrorCode = 5;
      }

      if(Name.equals(Session.getHabbo().getUsername())) {
         ErrorCode = 0;
      }

      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.CheckNameResponseEvent);
      Message.addInt(Integer.valueOf(ErrorCode));
      Message.addString(Name);
      if(ErrorCode > 0) {
         Message.addInt(Integer.valueOf(3));
         Message.addString(Name + Grizzly.rand(1, 9));
         Message.addString("Epic" + Name);
         Message.addString(Grizzly.getHabboHotel().generateName());
      } else {
         Message.addInt(Integer.valueOf(0));
      }

      Session.sendResponse(Message);
      Grizzly.write(Message.body());
      Grizzly.write(Message.SimpleResponse);
   }
}
