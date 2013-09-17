package net.cobem.grizzly.communication.composers.user;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;

public class SendAllowancesComposer {

   public static EventResponse compose(boolean HasBadge) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.CheckAllowancesEvent);
      Message.addInt(Integer.valueOf(HasBadge?7:5));
      Message.addString("VOTE_IN_COMPETITIONS");
      Message.addBool(Boolean.valueOf(true));
      Message.addString("");
      Message.addString("TRADE");
      Message.addBool(Boolean.valueOf(true));
      Message.addString("");
      Message.addString("CITIZEN");
      Message.addBool(Boolean.valueOf(true));
      Message.addString("");
      if(HasBadge) {
         Message.addString("SAFE_CHAT");
         Message.addBool(Boolean.valueOf(true));
         Message.addString("");
         Message.addString("FULL_CHAT");
         Message.addBool(Boolean.valueOf(true));
         Message.addString("");
      }

      Message.addString("CALL_ON_HELPERS");
      Message.addBool(Boolean.valueOf(true));
      Message.addString("");
      Message.addString("USE_GUIDE_TOOL");
      Message.addBool(Boolean.valueOf(false));
      Message.addString("requirement.unfulfilled.helper_level_4");
      Message.addString("JUDGE_CHAT_REVIEWS");
      Message.addBool(Boolean.valueOf(false));
      Message.addString("requirement.unfulfilled.helper_level_6");
      return Message;
   }
}
