package net.cobem.grizzly.habbohotel.misc.commands;

import java.util.Arrays;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.composers.user.SendBadgesComposer;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.storage.DatabaseResult;
import net.cobem.grizzly.utils.UserInputFilter;

public class Badge implements ChatCommand {

   public int rank() {
      return 5;
   }

   public void execute(Session session, String[] args) {
      if(args.length >= 2) {
         String Username = args[0];
         String Code = UserInputFilter.filterString(args[1], false);
         Session User = Grizzly.getHabboHotel().getSessions().getByName(Username);
         if(User == null) {
            DatabaseResult GetUser = Grizzly.getStorage().preparedQuery("SELECT id FROM server_users WHERE username = ?", Arrays.asList(new String[]{Username}));
            Grizzly.getStorage().execute("INSERT INTO server_user_badges (user, code) VALUES (\'" + GetUser.getInt() + "\', \'" + Code + "\')");
         } else {
            User.getHabbo().addBadge(Code);
            session.sendResponse(SendBadgesComposer.compose(session.getHabbo().getBadges(), session.getHabbo().getEquippedBadges()));
         }

      }
   }
}
