package net.cobem.grizzly.habbohotel.roleplay.commands;

import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.composers.rooms.SendRoomShoutComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomWhisperComposer;
import net.cobem.grizzly.habbohotel.effects.EffectType;
import net.cobem.grizzly.habbohotel.misc.ChatCommand;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class Hit implements ChatCommand {

   public int rank() {
      return 0;
   }

   public void execute(Session session, String[] args) {
      if(args.length != 1) {
         session.sendResponse(SendRoomWhisperComposer.compose(session.getHabbo().getID(), "Invalid number of arguments!", 0));
      } else {
         Session Target = Grizzly.getHabboHotel().getSessions().getByName(args[0]);
         if(Target == null) {
            session.sendResponse(SendRoomWhisperComposer.compose(session.getHabbo().getID(), "Can\'t find " + Target, 0));
         } else if(!Target.getActor().CurrentRoom.equals(session.getActor().CurrentRoom)) {
            session.sendResponse(SendRoomWhisperComposer.compose(session.getHabbo().getID(), "You can\'t cross-room hit someone!", 0));
         } else if(Target.equals(session)) {
            session.sendResponse(SendRoomWhisperComposer.compose(session.getHabbo().getID(), "You\'re a loser..!", 0));
         } else if(Target.getRoleplay().isLeaving()) {
            session.sendResponse(SendRoomWhisperComposer.compose(session.getHabbo().getID(), args[0] + " is signing off..", 0));
         } else {
            String Text;
            if(!Target.getActor().CurrentPosition.TilesTouching(session.getActor().CurrentPosition)) {
               Text = "Tries to punch " + args[0] + ", but misses!";
               session.getActor().CurrentRoom.sendMessage(SendRoomShoutComposer.compose(session.getHabbo().getID(), Text, 0, 22));
            } else if(Grizzly.rand(0, 1) == 1) {
               int Text2 = session.getRoleplay().getStrength() * (Grizzly.rand(1, 3) + Grizzly.rand(1, 13));
               Grizzly.write(Integer.valueOf(session.getRoleplay().getStrength()));
               Grizzly.write(Integer.valueOf(Text2));
               Grizzly.write(Integer.valueOf(Target.getRoleplay().getHealth()));
               String Text1;
               if(Target.getRoleplay().getHealth() - Text2 <= 0) {
                  Text1 = "Knocks " + args[0] + " out, sending them to the hospital..";
                  session.getActor().CurrentRoom.sendMessage(SendRoomShoutComposer.compose(session.getHabbo().getID(), Text1, 0, 22));
                  Target.getRoleplay().setHealth(100);
                  session.getRoleplay().upgradeStrength(1);
               } else {
                  Text1 = "Punches " + args[0] + " for " + Text2 + " damage!";
                  session.getActor().CurrentRoom.sendMessage(SendRoomShoutComposer.compose(session.getHabbo().getID(), Text1, 0, 22));
                  Target.getRoleplay().decreaseHealth(Text2);
                  if(Target.getRoleplay().getHealth() <= 25) {
                     Target.effect(EffectType.BIRDIES_OVER_HEAD);
                     Target.sendResponse(SendRoomWhisperComposer.compose(Target.getHabbo().getID(), "You\'re low on health, get to a hospital!!", 0));
                  }
               }

            } else {
               Text = "Tries to punch " + args[0] + ", but misses!";
               session.getActor().CurrentRoom.sendMessage(SendRoomShoutComposer.compose(session.getHabbo().getID(), Text, 0, 22));
            }
         }
      }
   }
}
