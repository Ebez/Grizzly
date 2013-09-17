package net.cobem.grizzly.habbohotel.wired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventRequest;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.wired.WiredObject;
import net.cobem.grizzly.habbohotel.wired.WiredType;
import net.cobem.grizzly.habbohotel.wired.effects.ChangeState;
import net.cobem.grizzly.habbohotel.wired.effects.MoveAndRotate;
import net.cobem.grizzly.habbohotel.wired.effects.ShowMessage;
import net.cobem.grizzly.habbohotel.wired.effects.TransportUser;
import net.cobem.grizzly.habbohotel.wired.triggers.OnEnterRoom;
import net.cobem.grizzly.habbohotel.wired.triggers.SayMessage;
import net.cobem.grizzly.habbohotel.wired.triggers.ToggleFurniState;
import net.cobem.grizzly.habbohotel.wired.triggers.WalksOffFurni;
import net.cobem.grizzly.habbohotel.wired.triggers.WalksOnFurni;

public class WiredHandler {

   private Room Room;
   private List<FloorItem> Triggers = new ArrayList();
   private List<FloorItem> Effects = new ArrayList();
   private List<FloorItem> Conditions = new ArrayList();
   private Map<String, WiredObject> WiredClasses = new HashMap();
   private Map<Integer, String> WiredMovements = new HashMap();
   private Map<Integer, String> WiredRotations = new HashMap();


   public WiredHandler(Room mRoom) {
      this.Room = mRoom;
      this.registerWired();
      this.registerWiredClasses();
      this.registerWiredMovements();
   }

   private void registerWired() {
      Iterator var2 = this.Room.getFloors().values().iterator();

      while(var2.hasNext()) {
         FloorItem Item = (FloorItem)var2.next();
         if(Item.getBase().Interaction.contains("wf_")) {
            if(Item.getBase().Interaction.contains("_act_")) {
               this.Effects.add(Item);
            } else if(Item.getBase().Interaction.contains("_trg_")) {
               this.Triggers.add(Item);
            } else {
               this.Conditions.add(Item);
            }
         }
      }

   }

   private void registerWiredClasses() {
      this.WiredClasses.put(WiredType.TRIGGER_ENTERROOM, new OnEnterRoom());
      this.WiredClasses.put(WiredType.TRIGGER_SAYMESSAGE, new SayMessage());
      this.WiredClasses.put(WiredType.TRIGGER_STANDONFURNI, new WalksOnFurni());
      this.WiredClasses.put(WiredType.TRIGGER_STANDOFFFURNI, new WalksOffFurni());
      this.WiredClasses.put(WiredType.TRIGGER_CHANGEFURNISTATE, new ToggleFurniState());
      this.WiredClasses.put(WiredType.EFFECT_SENDALERT, new ShowMessage());
      this.WiredClasses.put(WiredType.EFFECT_CHANGEUSERPOSITION, new TransportUser());
      this.WiredClasses.put(WiredType.EFFECT_CHANGEFURNISTATE, new ChangeState());
      this.WiredClasses.put(WiredType.EFFECT_CHANGEITEMPOSITION, new MoveAndRotate());
   }

   private void registerWiredMovements() {
      this.WiredMovements.put(Integer.valueOf(0), "none");
      this.WiredMovements.put(Integer.valueOf(1), "random");
      this.WiredMovements.put(Integer.valueOf(2), "vertical");
      this.WiredMovements.put(Integer.valueOf(3), "horizontal");
      this.WiredMovements.put(Integer.valueOf(4), "right");
      this.WiredMovements.put(Integer.valueOf(5), "down");
      this.WiredMovements.put(Integer.valueOf(6), "left");
      this.WiredMovements.put(Integer.valueOf(7), "up");
      this.WiredRotations.put(Integer.valueOf(0), "none");
      this.WiredRotations.put(Integer.valueOf(1), "clockwise");
      this.WiredRotations.put(Integer.valueOf(2), "counter-clockwise");
      this.WiredRotations.put(Integer.valueOf(3), "random");
   }

   public boolean isEffect(FloorItem Item) {
      return Item.getBase().Interaction.contains("_act_");
   }

   public boolean isTrigger(FloorItem Item) {
      return Item.getBase().Interaction.contains("_trg_");
   }

   public boolean isCondition(FloorItem Item) {
      return Item.getBase().Interaction.contains("_cnd_");
   }

   public List<FloorItem> getWiredForInteraction(String Type) {
      ArrayList Collection = new ArrayList();
      Iterator var4 = this.Room.getFloors().values().iterator();

      while(var4.hasNext()) {
         FloorItem Item = (FloorItem)var4.next();
         if(Item.getBase().Interaction.equals(Type)) {
            Collection.add(Item);
         }
      }

      return Collection;
   }

   public boolean onUserEnter(Session Session) {
      List Triggers = this.getWiredForInteraction(WiredType.TRIGGER_ENTERROOM);
      Iterator var4 = Triggers.iterator();

      while(var4.hasNext()) {
         FloorItem Item = (FloorItem)var4.next();
         Iterator var6 = this.Room.getItemsAbove(Item).iterator();

         while(var6.hasNext()) {
            FloorItem ItemOnTop = (FloorItem)var6.next();
            if(this.isEffect(ItemOnTop)) {
               Grizzly.getHabboHotel().getInteractions().onTrigger(ItemOnTop.getBase().Interaction, Session, ItemOnTop, 2);
            }
         }
      }

      return true;
   }

   public boolean onUserWalkOnFurni(Session Session, FloorItem mItem) {
      boolean FoundItem = false;
      List Triggers = this.getWiredForInteraction(WiredType.TRIGGER_STANDONFURNI);
      Iterator var6 = Triggers.iterator();

      while(var6.hasNext()) {
         FloorItem Item = (FloorItem)var6.next();
         String[] var10;
         int var9 = (var10 = Item.ExtraData.split(";")).length;
         int var8 = 0;

         while(true) {
            if(var8 < var9) {
               String ItemOnTop = var10[var8];
               if(mItem.ID != (new Integer(ItemOnTop)).intValue()) {
                  ++var8;
                  continue;
               }

               FoundItem = true;
            }

            if(FoundItem) {
               Iterator var12 = this.Room.getItemsAbove(Item).iterator();

               while(var12.hasNext()) {
                  FloorItem var11 = (FloorItem)var12.next();
                  if(this.isEffect(var11)) {
                     Grizzly.getHabboHotel().getInteractions().onTrigger(var11.getBase().Interaction, Session, var11, 2);
                  }
               }
            }
            break;
         }
      }

      return false;
   }

   public boolean onToggleFurniState(Session Session, FloorItem mItem) {
      boolean FoundItem = false;
      List Triggers = this.getWiredForInteraction(WiredType.TRIGGER_CHANGEFURNISTATE);
      Iterator var6 = Triggers.iterator();

      while(var6.hasNext()) {
         FloorItem Item = (FloorItem)var6.next();
         String[] var10;
         int var9 = (var10 = Item.ExtraData.split(";")).length;
         int var8 = 0;

         while(true) {
            if(var8 < var9) {
               String ItemOnTop = var10[var8];
               if(mItem.ID != (new Integer(ItemOnTop)).intValue()) {
                  ++var8;
                  continue;
               }

               FoundItem = true;
            }

            if(FoundItem) {
               Iterator var12 = this.Room.getItemsAbove(Item).iterator();

               while(var12.hasNext()) {
                  FloorItem var11 = (FloorItem)var12.next();
                  if(this.isEffect(var11)) {
                     Grizzly.getHabboHotel().getInteractions().onTrigger(var11.getBase().Interaction, Session, var11, 2);
                  }
               }
            }
            break;
         }
      }

      return false;
   }

   public boolean onUserWalkOffFurni(Session Session, FloorItem mItem) {
      boolean FoundItem = false;
      List Triggers = this.getWiredForInteraction(WiredType.TRIGGER_STANDOFFFURNI);
      Iterator var6 = Triggers.iterator();

      while(var6.hasNext()) {
         FloorItem Item = (FloorItem)var6.next();
         String[] var10;
         int var9 = (var10 = Item.ExtraData.split(";")).length;
         int var8 = 0;

         while(true) {
            if(var8 < var9) {
               String ItemOnTop = var10[var8];
               if(mItem.ID != (new Integer(ItemOnTop)).intValue()) {
                  ++var8;
                  continue;
               }

               FoundItem = true;
            }

            if(FoundItem) {
               Iterator var12 = this.Room.getItemsAbove(Item).iterator();

               while(var12.hasNext()) {
                  FloorItem var11 = (FloorItem)var12.next();
                  if(this.isEffect(var11)) {
                     Grizzly.getHabboHotel().getInteractions().onTrigger(var11.getBase().Interaction, Session, var11, 2);
                  }
               }
            }
            break;
         }
      }

      return false;
   }

   public boolean onUserTalk(Session Session, String Message) {
      List Triggers = this.getWiredForInteraction(WiredType.TRIGGER_SAYMESSAGE);
      boolean TriggerHasErrors = true;
      Iterator var6 = Triggers.iterator();

      while(var6.hasNext()) {
         FloorItem Item = (FloorItem)var6.next();
         if(Message.equals(Item.ExtraData)) {
            for(Iterator var8 = this.Room.getItemsAbove(Item).iterator(); var8.hasNext(); TriggerHasErrors = false) {
               FloorItem ItemOnTop = (FloorItem)var8.next();
               if(this.isEffect(ItemOnTop)) {
                  Grizzly.getHabboHotel().getInteractions().onTrigger(ItemOnTop.getBase().Interaction, Session, ItemOnTop, 2);
               }
            }
         }
      }

      if(!TriggerHasErrors) {
         return true;
      } else if(Triggers.size() == 0) {
         return false;
      } else {
         return false;
      }
   }

   public void removeFurni(FloorItem Item) {
      String Str = Item.ID + ";";
      Iterator var4 = this.Room.getFloors().values().iterator();

      while(var4.hasNext()) {
         FloorItem mItem = (FloorItem)var4.next();
         if(mItem.ExtraData.contains(Str)) {
            mItem.ExtraData = mItem.ExtraData.replace(Str, "").trim();
            mItem.saveState();
         }
      }

   }

   public void saveWired(Session Session, FloorItem Item, EventRequest Message) {
      if(this.WiredClasses.containsKey(Item.getBase().Interaction)) {
         ((WiredObject)this.WiredClasses.get(Item.getBase().Interaction)).save(Session, Item, Message);
      }

   }

   public Position calculateMovement(int MovementKey, Position CurrentPosition) {
      int X = 0;
      int Y = 0;
      String Movement = (String)this.WiredMovements.get(Integer.valueOf(MovementKey));
      switch(Movement.hashCode()) {
      case -1984141450:
         if(Movement.equals("vertical")) {
            if(Grizzly.rand(0, 1) == 0) {
               X = CurrentPosition.X;
               Y = CurrentPosition.Y - 1;
            } else {
               X = CurrentPosition.X;
               Y = CurrentPosition.Y + 1;
            }
         }
         break;
      case -938285885:
         if(Movement.equals("random")) {
            Position Pos = this.Room.getModel().getRandomSquare();
            X = Pos.X;
            Y = Pos.Y;
         }
         break;
      case 3739:
         if(Movement.equals("up")) {
            X = CurrentPosition.X;
            Y = CurrentPosition.Y - 1;
         }
         break;
      case 3089570:
         if(Movement.equals("down")) {
            X = CurrentPosition.X;
            Y = CurrentPosition.Y + 1;
         }
         break;
      case 3317767:
         if(Movement.equals("left")) {
            X = CurrentPosition.X - 1;
            Y = CurrentPosition.Y;
         }
         break;
      case 108511772:
         if(Movement.equals("right")) {
            X = CurrentPosition.X + 1;
            Y = CurrentPosition.Y;
         }
         break;
      case 1387629604:
         if(Movement.equals("horizontal")) {
            if(Grizzly.rand(0, 1) == 0) {
               X = CurrentPosition.X + 1;
               Y = CurrentPosition.Y;
            } else {
               X = CurrentPosition.X - 1;
               Y = CurrentPosition.Y;
            }
         }
      }

      return new Position(X, Y, CurrentPosition.Z);
   }

   public int calculateRotation(int MovementKey, int CurrentRotation) {
      int Rotation = 0;
      String Movement = (String)this.WiredRotations.get(Integer.valueOf(MovementKey));
      switch(Movement.hashCode()) {
      case -938285885:
         if(Movement.equals("random")) {
            if(Grizzly.rand(0, 1) == 1) {
               Rotation = CurrentRotation - 2;
               if(Rotation < 0) {
                  Rotation = 6;
               }
            } else {
               Rotation = CurrentRotation + 2;
               if(Rotation > 6) {
                  Rotation = 0;
               }
            }
         }
         break;
      case -933964366:
         if(Movement.equals("clockwise")) {
            Rotation = CurrentRotation + 2;
            if(Rotation > 6) {
               Rotation = 0;
            }
         }
         break;
      case 1108263713:
         if(Movement.equals("counter-clockwise")) {
            Rotation = CurrentRotation - 2;
            if(Rotation < 0) {
               Rotation = 6;
            }
         }
      }

      return Rotation;
   }
}
