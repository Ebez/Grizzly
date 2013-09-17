package net.cobem.grizzly.communication.events.user;

import java.util.Iterator;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.communication.composers.user.SendBadgesComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.badges.BadgeObject;

public class WearBadgeEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      boolean RemovingBadge = false;
      int RemovedSlot = 0;
      String Code = null;

      for(int Badge = 1; Badge < Session.getHabbo().getEquippedBadges().size() + (5 - Session.getHabbo().getEquippedBadges().size()); ++Badge) {
         int Obj = Request.readInt().intValue();
         Code = Request.readString();
         if(Code.length() >= 3) {
            if(Session.getHabbo().getEquippedBadgeByCode(Code) == null) {
               break;
            }
         } else if(Session.getHabbo().getBadgeBySlot(Obj) != null) {
            RemovingBadge = true;
            RemovedSlot = Obj;
            break;
         }
      }

      BadgeObject var9;
      if(!RemovingBadge) {
         if(Session.getHabbo().getEquippedBadges().size() == 5) {
            return;
         }

         var9 = null;
         Iterator var8 = Session.getHabbo().getBadges().iterator();

         while(var8.hasNext()) {
            BadgeObject var10 = (BadgeObject)var8.next();
            if(var10.Code.equals(Code)) {
               var9 = var10;
            }
         }

         var9.Slot = Session.getHabbo().getEquippedBadges().size() + 1;
         var9.Equipped = true;
         var9.append();
      } else {
         var9 = Session.getHabbo().getBadgeBySlot(RemovedSlot);
         var9.Slot = 0;
         var9.Equipped = false;
         var9.append();
      }

      Session.sendResponse(SendBadgesComposer.compose(Session.getHabbo().getBadges(), Session.getHabbo().getEquippedBadges()));
   }
}
