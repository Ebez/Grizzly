package net.cobem.grizzly.communication.composers.user;

import java.util.Iterator;
import java.util.List;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.users.badges.BadgeObject;

public class SendUserBadgesComposer {

   public static EventResponse compose(int User, List<BadgeObject> Badges) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.ViewUserBadgesEvent);
      Message.addInt(Integer.valueOf(User));
      Message.addInt(Integer.valueOf(Badges.size()));
      Iterator var4 = Badges.iterator();

      while(var4.hasNext()) {
         BadgeObject Obj = (BadgeObject)var4.next();
         Message.addInt(Integer.valueOf(Obj.Slot));
         Message.addString(Obj.Code);
      }

      return Message;
   }
}
