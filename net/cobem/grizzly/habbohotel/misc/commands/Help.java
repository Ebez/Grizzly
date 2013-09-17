package net.cobem.grizzly.habbohotel.misc.commands;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.composers.misc.SendNotificationComposer;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class Help implements ChatCommand {

   public int rank() {
      return 1;
   }

   public void execute(Session session, String[] args) {
      StringBuilder Alert = new StringBuilder();
      Alert.append("Grizzly " + Grizzly.Version.String() + "\r").append(Grizzly.HabboBuild + "\r").append(Grizzly.getStorage().QueryCount + " queries ran since bootup.\r").append("Memory Use: " + Grizzly.getMemory() + "\r").append("Emulator created by Cobe Makarov\r");
      session.sendResponse(SendNotificationComposer.compose(Alert.toString()));
   }
}
