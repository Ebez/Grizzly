package net.cobem.grizzly.communication.events.handshake;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.communication.GameEvent;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ReadReleaseEvent implements GameEvent {

   public void compose(Session Session, EventRequest Request) {
      String SWFBUILD = Request.readString();
      if(!SWFBUILD.contains(Grizzly.HabboBuild) && !SWFBUILD.contains("Cracked")) {
         Grizzly.write("Build Mismatch; May hit some un-runnable actions!");
         Grizzly.write("Grizzly\'s SWF Build: " + Grizzly.HabboBuild);
         Grizzly.write("Your SWF Build: " + SWFBUILD);
      }

   }
}
