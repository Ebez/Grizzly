package net.cobem.grizzly.habbohotel.misc.commands;

import java.util.Random;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.rooms.robots.Robot;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class CreateBot implements ChatCommand {

   public int rank() {
      return 5;
   }

   public void execute(Session session, String[] args) {
      int ID = (new Random()).nextInt(999) + 9000;
      String Name = Grizzly.getHabboHotel().generateName();
      String Motto = Name + "; Owned by " + session.getHabbo().getUsername();
      String Look = Grizzly.getHabboHotel().generateLook();
      Robot Bot = new Robot(session, ID, Name, Motto, Look, session.getActor().CurrentRoom);
      Bot.X = session.getActor().CurrentRoom.getModel().DoorX;
      Bot.Y = session.getActor().CurrentRoom.getModel().DoorY;
      Bot.Rotation = session.getActor().CurrentRoom.getModel().DoorRot;
      session.getActor().CurrentRoom.generateRobot(Bot);
      session.getActor().say("Generates the bot " + Name, false);
   }
}
