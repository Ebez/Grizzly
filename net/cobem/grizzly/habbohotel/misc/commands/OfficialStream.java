package net.cobem.grizzly.habbohotel.misc.commands;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.composers.misc.SendNotificationComposer;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class OfficialStream implements ChatCommand {

   public int rank() {
      return 6;
   }

   public void execute(Session session, String[] args) {
      if(args.length < 1) {
         session.sendResponse(SendNotificationComposer.compose("Invalid number of arguments!"));
      } else {
         String Message = null;
         int Key = 0;
         String[] var8 = args;
         int var7 = args.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            String Whatever = var8[var6];
            if(Key == 0) {
               Message = Whatever;
            } else {
               Message = Message + " " + Whatever;
            }

            ++Key;
         }

         Grizzly.getHabboHotel().getStream().addOfficial(Message);
      }
   }
}
