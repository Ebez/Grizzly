package net.cobem.grizzly.communication.composers.stream;

import java.util.Iterator;
import java.util.List;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.stream.StreamObject;
import net.cobem.grizzly.habbohotel.users.User;
import net.cobem.grizzly.habbohotel.users.UserDAO;

public class InitializeFriendStreamComposer {

   public static EventResponse compose() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeFriendStreamEvent);
      List Regular = Grizzly.getHabboHotel().getStream().getRegular();
      List Official = Grizzly.getHabboHotel().getStream().getOfficial();
      Message.addInt(Integer.valueOf(Regular.size() + Official.size()));
      Iterator var4 = Regular.iterator();

      StreamObject Obj;
      while(var4.hasNext()) {
         Obj = (StreamObject)var4.next();
         Message.addInt(Integer.valueOf(Obj.ID));
         Message.addInt(Integer.valueOf(Obj.Type));
         Message.addString(Integer.valueOf(Obj.getUser().getID()));
         Message.addString(Obj.getUser().getUsername());
         Message.addString(Obj.getUser().getGender().toString().toLowerCase());
         Message.addString("http://localhost/Storage/Heads/look.gif?figure=" + Obj.getUser().getLook() + ".gif");
         Message.addInt(Integer.valueOf(Obj.getMinutesFrom()));
         if(Obj.Type == 0) {
            Message.addInt(Integer.valueOf(5));
            Message.addInt(Integer.valueOf(Obj.Likes));
            Message.addBool(Boolean.valueOf(true));
            Message.addBool(Boolean.valueOf(true));
            Message.addBool(Boolean.valueOf(false));
            User Friend = UserDAO.generateUser((new Integer(Obj.Message)).intValue());
            if(Friend != null) {
               Message.addString(Integer.valueOf(Friend.getID()));
               Message.addString(Friend.getUsername());
            }
         }

         if(Obj.Type == 3) {
            Message.addInt(Integer.valueOf(4));
            Message.addInt(Integer.valueOf(Obj.Likes));
            Message.addBool(Boolean.valueOf(true));
            Message.addBool(Boolean.valueOf(true));
            Message.addBool(Boolean.valueOf(false));
            Message.addString(Obj.getUser().getMotto());
         }

         if(Obj.Type == 5) {
            Message.addInt(Integer.valueOf(1));
            Message.addInt(Integer.valueOf(Obj.Likes));
            Message.addBool(Boolean.valueOf(true));
            Message.addBool(Boolean.valueOf(true));
            Message.addBool(Boolean.valueOf(false));
            Message.addString(Obj.Message);
         }
      }

      var4 = Official.iterator();

      while(var4.hasNext()) {
         Obj = (StreamObject)var4.next();
         Message.addInt(Integer.valueOf(Obj.ID));
         Message.addInt(Integer.valueOf(4));
         Message.addString(Integer.valueOf(Obj.ID));
         Message.addString("");
         Message.addString("");
         Message.addString("");
         Message.addInt(Integer.valueOf(Obj.getMinutesFrom()));
         Message.addInt(Integer.valueOf(6));
         Message.addInt(Integer.valueOf(Obj.Likes));
         Message.addBool(Boolean.valueOf(true));
         Message.addBool(Boolean.valueOf(true));
         Message.addBool(Boolean.valueOf(false));
         Message.addString(Obj.Message);
         Message.addString("#");
      }

      return Message;
   }
}
