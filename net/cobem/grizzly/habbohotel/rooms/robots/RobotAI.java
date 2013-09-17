package net.cobem.grizzly.habbohotel.rooms.robots;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.robots.Robot;

public class RobotAI {

   public Robot Robot;
   public Map<Integer, String> RandomPhrases = new HashMap();
   public int NewX;
   public int NewY;
   public boolean Terminate;
   public boolean IsMoving;


   public RobotAI(Robot mRobot) {
      this.Robot = mRobot;
      this.populatePhrases();
   }

   private void populatePhrases() {
      this.RandomPhrases.put(Integer.valueOf(1), "I am Frankenstein v1");
      this.RandomPhrases.put(Integer.valueOf(2), "Why the hell is the gas so much?!?!");
      this.RandomPhrases.put(Integer.valueOf(3), "King Henry my ass!");
      this.RandomPhrases.put(Integer.valueOf(4), "Dude, Y.O.L.O!");
      this.RandomPhrases.put(Integer.valueOf(5), "I went do to the dishes... but, then I got high..");
      this.RandomPhrases.put(Integer.valueOf(6), "Maximillian Robespierre is my hero!!");
      this.RandomPhrases.put(Integer.valueOf(7), "I had nothing else to say, so I said this.");
      this.RandomPhrases.put(Integer.valueOf(8), "GRIZZLAY!!!");
      this.RandomPhrases.put(Integer.valueOf(9), "If I was a butler, I would say: \'Would you like a drink?\'");
      this.RandomPhrases.put(Integer.valueOf(10), "A black, asian and hispanic walk into a bar..");
      this.RandomPhrases.put(Integer.valueOf(11), "And then he says... here I am!");
      this.RandomPhrases.put(Integer.valueOf(12), "Corpis Die!");
   }

   public void sayPhrase() {
      int Response = (new Random()).nextInt(this.RandomPhrases.size());
      if(Response == 0) {
         ++Response;
      }

      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendRoomChatEvent);
      Message.addInt(Integer.valueOf(this.Robot.ID));
      Message.addString(this.RandomPhrases.get(Integer.valueOf(Response)));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(2));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(-1));
      this.Robot.CurrentRoom.sendMessage(Message);
   }

   public void say(String Statement) {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.SendRoomChatEvent);
      Message.addInt(Integer.valueOf(this.Robot.ID));
      Message.addString(Statement);
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(2));
      Message.addInt(Integer.valueOf(0));
      Message.addInt(Integer.valueOf(-1));
      this.Robot.CurrentRoom.sendMessage(Message);
   }

   public void leave() {
      this.IsMoving = false;
      this.Terminate = true;
      this.say("I gotta jet!");
   }

   public void move() {
      this.IsMoving = true;
      Position NewPosition = this.Robot.CurrentRoom.getModel().getRandomSquare();
      this.NewX = NewPosition.X;
      this.NewY = NewPosition.Y;
   }
}
