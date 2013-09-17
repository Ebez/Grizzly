package net.cobem.grizzly.habbohotel.rooms.items.interactions;

import java.util.HashMap;
import java.util.Map;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.BedInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.GateInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.RollerInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.TeleportInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.games.football.BallInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.misc.RollerSkateInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.misc.WaterInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.randoms.DiceInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.randoms.VendingInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.randoms.WheelInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.roleplay.WorkoutMachineInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.effects.WiredChangeFurniStateInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.effects.WiredMoveAndRotateFurniInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.effects.WiredShowUserMessageInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.effects.WiredTransportUserInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.triggers.WiredToggleStateInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.triggers.WiredUserEnterRoomInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.triggers.WiredUserSaysKeyWordInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.triggers.WiredUserWalksOffFurniInteraction;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.wired.triggers.WiredUserWalksOnFurniInteraction;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.wired.WiredType;

public class InteractionHandler {

   public Map<String, Interaction> Interactions = new HashMap();


   public InteractionHandler() {
      this.register();
   }

   private boolean register() {
      this.Interactions.put("dice", new DiceInteraction());
      this.Interactions.put("bed", new BedInteraction());
      this.Interactions.put("gate", new GateInteraction());
      this.Interactions.put("habbowheel", new WheelInteraction());
      this.Interactions.put("vendingmachine", new VendingInteraction());
      this.Interactions.put("teleport", new TeleportInteraction());
      this.Interactions.put("water", new WaterInteraction());
      this.Interactions.put("rollerskate", new RollerSkateInteraction());
      this.Interactions.put("ball", new BallInteraction());
      this.Interactions.put("roller", new RollerInteraction());
      this.Interactions.put(WiredType.EFFECT_SENDALERT, new WiredShowUserMessageInteraction());
      this.Interactions.put(WiredType.EFFECT_CHANGEUSERPOSITION, new WiredTransportUserInteraction());
      this.Interactions.put(WiredType.EFFECT_CHANGEFURNISTATE, new WiredChangeFurniStateInteraction());
      this.Interactions.put(WiredType.EFFECT_CHANGEITEMPOSITION, new WiredMoveAndRotateFurniInteraction());
      this.Interactions.put(WiredType.TRIGGER_ENTERROOM, new WiredUserEnterRoomInteraction());
      this.Interactions.put(WiredType.TRIGGER_SAYMESSAGE, new WiredUserSaysKeyWordInteraction());
      this.Interactions.put(WiredType.TRIGGER_STANDONFURNI, new WiredUserWalksOnFurniInteraction());
      this.Interactions.put(WiredType.TRIGGER_STANDOFFFURNI, new WiredUserWalksOffFurniInteraction());
      this.Interactions.put(WiredType.TRIGGER_CHANGEFURNISTATE, new WiredToggleStateInteraction());
      if(Grizzly.roleplayEnabled()) {
         this.Interactions.put("workout_machine", new WorkoutMachineInteraction());
      }

      return true;
   }

   public boolean onPlace(String Interaction, Session Session, Object Item) {
      if(!this.Interactions.containsKey(Interaction)) {
         return false;
      } else {
         ((Interaction)this.Interactions.get(Interaction)).onPlace(Session, Item);
         return true;
      }
   }

   public boolean onRemove(String Interaction, Session Session, Object Item) {
      if(!this.Interactions.containsKey(Interaction)) {
         return false;
      } else {
         ((Interaction)this.Interactions.get(Interaction)).onRemove(Session, Item);
         return true;
      }
   }

   public boolean onTrigger(String Interaction, Session Session, Object Item, int Request) {
      if(!this.Interactions.containsKey(Interaction)) {
         return false;
      } else {
         ((Interaction)this.Interactions.get(Interaction)).onTrigger(Session, Item, Request);
         return true;
      }
   }

   public boolean onCycle(String Interaction, Object Item) {
      if(!this.Interactions.containsKey(Interaction)) {
         return false;
      } else {
         ((Interaction)this.Interactions.get(Interaction)).onCycle(Item);
         return true;
      }
   }
}
