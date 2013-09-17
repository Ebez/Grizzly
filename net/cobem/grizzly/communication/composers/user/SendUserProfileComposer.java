package net.cobem.grizzly.communication.composers.user;

import java.util.Iterator;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.guild.Guild;
import net.cobem.grizzly.habbohotel.users.User;

public class SendUserProfileComposer {

   public static EventResponse compose(User Habbo, boolean IsFriend, boolean Online) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.InitializeProfileEvent);
      Message.addInt(Integer.valueOf(Habbo.getID()));
      Message.addString(Habbo.getUsername());
      Message.addString(Habbo.getLook());
      Message.addString(Habbo.getMotto());
      Message.addString(Habbo.getLastOnline());
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(Habbo.getMessenger().GrabFriends().size()));
      Message.addBool(Boolean.valueOf(IsFriend));
      Message.addBool(Boolean.valueOf(false));
      Message.addBool(Boolean.valueOf(Online));
      Message.addInt(Integer.valueOf(Habbo.getGuilds().size()));
      Iterator var5 = Habbo.getGuilds().iterator();

      while(var5.hasNext()) {
         Guild guild = (Guild)var5.next();
         Message.addInt(Integer.valueOf(guild.getID()));
         Message.addString(guild.getTitle());
         Message.addString(guild.getImage());
         Message.addString(guild.getFirstHex());
         Message.addString(guild.getSecondHex());
         Message.addBool(Boolean.valueOf(Habbo.getGuild() == guild.getID()));
      }

      Message.addInt(Integer.valueOf(0));
      Message.addBool(Boolean.valueOf(true));
      return Message;
   }
}
