package net.cobem.grizzly.communication.composers.user;

import java.util.Iterator;
import java.util.List;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.users.badges.BadgeObject;

public class SendBadgesComposer {

   public static EventResponse compose(List<BadgeObject> Badges, List<BadgeObject> EquippedBadges) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeBadgesEvent);
      Message.addInt(Integer.valueOf(Badges.size()));
      Iterator var4 = Badges.iterator();

      BadgeObject Obj;
      while(var4.hasNext()) {
         Obj = (BadgeObject)var4.next();
         Message.addInt(Integer.valueOf(0));
         Message.addString(Obj.Code);
      }

      Message.addInt(Integer.valueOf(EquippedBadges.size()));
      var4 = EquippedBadges.iterator();

      while(var4.hasNext()) {
         Obj = (BadgeObject)var4.next();
         Message.addInt(Integer.valueOf(Obj.Slot));
         Message.addString(Obj.Code);
      }

      return Message;
   }
}
