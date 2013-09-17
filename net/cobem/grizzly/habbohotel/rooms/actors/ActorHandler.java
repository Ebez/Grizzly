package net.cobem.grizzly.habbohotel.rooms.actors;

import java.util.Collection;
import java.util.Iterator;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class ActorHandler {

   private Room Room;


   public ActorHandler(Room mRoom) {
      this.Room = mRoom;
   }

   public void cycle() {
      try {
         Iterator var2 = this.Room.getParty().values().iterator();

         while(var2.hasNext()) {
            Session Session = (Session)var2.next();
            if(Session.getActor().hasStatus("mv") && !Session.getActor().IsMoving) {
               Session.getActor().removeStatus("mv", Boolean.valueOf(true));
            }

            if(Session.getActor().hasStatus("mv") && this.Room.getItemByPosition(Session.getActor().CurrentPosition) != null && this.Room.getItemByPosition(Session.getActor().CurrentPosition).getBase().Sitable.booleanValue()) {
               Session.getActor().removeStatus("mv", Boolean.valueOf(true));
               Session.getActor().addStatus("sit", Float.valueOf(Session.getActor().SitHeight), true);
               Session.getActor().SitHeight = 0.0F;
            }

            if(Session.getActor().CycleInteraction) {
               if(Session.getActor().InteractionItem.getBase().Interaction.equals("roller")) {
                  Session.getActor().IsRolling = true;
                  if(Session.getActor().RollerCycle > 0) {
                     --Session.getActor().RollerCycle;
                     continue;
                  }
               }

               Grizzly.getHabboHotel().getInteractions().onTrigger(Session.getActor().InteractionItem.getBase().Interaction, Session, Session.getActor().InteractionItem, 0);
               Session.getActor().CycleInteraction = false;
            }

            if(Session.getActor().IsMoving) {
               Session.getActor().IdleTime = 0;
               Collection NextItem;
               if(Session.getActor().Ghost) {
                  NextItem = Session.getActor().CurrentRoom.getGhostPathfinder().Path((byte)Session.getActor().CurrentPosition.X, (byte)Session.getActor().CurrentPosition.Y, (byte)Session.getActor().GoalPosition.X, (byte)Session.getActor().GoalPosition.Y, Grizzly.getHabboHotel().getRooms().MaxDrop, Grizzly.getHabboHotel().getRooms().MaxJump);
               } else {
                  NextItem = Session.getActor().CurrentRoom.getPathfinder().Path((byte)Session.getActor().CurrentPosition.X, (byte)Session.getActor().CurrentPosition.Y, (byte)Session.getActor().GoalPosition.X, (byte)Session.getActor().GoalPosition.Y, Grizzly.getHabboHotel().getRooms().MaxDrop, Grizzly.getHabboHotel().getRooms().MaxJump);
               }

               if(NextItem.size() == 0) {
                  Session.getActor().IsMoving = false;
               }

               byte[] ItemInFront = (byte[])null;
               byte[] NextNextStep = (byte[])null;
               int Key = 0;
               Iterator CurrentItem = NextItem.iterator();

               while(CurrentItem.hasNext()) {
                  byte[] NextItem1 = (byte[])CurrentItem.next();
                  if(Key == 0) {
                     ItemInFront = NextItem1;
                     ++Key;
                  } else if(Key == 1) {
                     NextNextStep = NextItem1;
                     break;
                  }
               }

               if(ItemInFront == null) {
                  continue;
               }

               FloorItem var13;
               try {
                  var13 = this.Room.getMap().GetItemAtPosition(new Position(ItemInFront[0], ItemInFront[1], 0.0D));
               } catch (Exception var9) {
                  var13 = null;
               }

               Session.getActor().Rotation = Session.getActor().CurrentPosition.Calculate(ItemInFront[0], ItemInFront[1]);
               if(var13 != null) {
                  if(var13.getBase().Sitable.booleanValue()) {
                     Session.getActor().CurrentPosition.Z = (double)var13.Height.floatValue();
                  } else {
                     Session.getActor().CurrentPosition.Z = (double)(var13.Height.floatValue() + var13.getBase().StackHeight.floatValue());
                  }
               } else {
                  Session.getActor().CurrentPosition.Z = this.Room.getModel().getTileHeight(ItemInFront[0], ItemInFront[1]);
               }

               this.Room.getPositions().remove(Integer.valueOf(Session.getHabbo().getID()));
               this.Room.getMap().UserCollisionMap[Session.getActor().CurrentPosition.X][Session.getActor().CurrentPosition.Y] = false;
               if(Session.CurrentEffect != 0 && !Session.GlobalEffect) {
                  var13 = Session.getActor().CurrentRoom.getItemForInteraction(Session.getActor().CurrentPosition);
                  if(var13 == null || var13.getBase().Sitable.booleanValue()) {
                     Session.effect(0);
                  }
               }

               FloorItem var14 = null;
               if(Session.getActor().CurrentRoom.getItemByPosition(Session.getActor().CurrentPosition) != null) {
                  var14 = Session.getActor().CurrentRoom.getItemByPosition(Session.getActor().CurrentPosition);
               }

               Session.getActor().removeStatus("sit", Boolean.valueOf(false));
               Session.getActor().removeStatus("lay", Boolean.valueOf(false));
               Session.getActor().addStatus("mv", ItemInFront[0] + "," + ItemInFront[1] + "," + Double.toString(Session.getActor().CurrentPosition.Z), true);
               Session.getActor().CurrentPosition.X = ItemInFront[0];
               Session.getActor().CurrentPosition.Y = ItemInFront[1];
               this.Room.getPositions().put(Integer.valueOf(Session.getHabbo().getID()), Session.getActor().CurrentPosition);
               if(Grizzly.roleplayEnabled() && Session.getRoleplay().isWorkingOut()) {
                  Session.getRoleplay().stopWorkingOut();
               }

               if(var14 != null) {
                  this.Room.getWiredUtility().onUserWalkOffFurni(Session, var14);
               }

               if(NextNextStep == null) {
                  var13 = Session.getActor().CurrentRoom.getItemForInteraction(Session.getActor().CurrentPosition);
                  if(var13 != null) {
                     if(var13.getBase().Sitable.booleanValue()) {
                        Session.getActor().Rotation = var13.Rotation;
                        if(var13.Height.floatValue() != 0.0F) {
                           Session.getActor().SitHeight = var13.Height.floatValue() + var13.getBase().StackHeight.floatValue();
                        } else {
                           Session.getActor().SitHeight = var13.getBase().StackHeight.floatValue();
                        }
                     } else {
                        Session.getActor().CycleInteraction = true;
                        Session.getActor().InteractionItem = var13;
                        if(var13.getBase().Interaction.equals("roller")) {
                           Session.getActor().RollerCycle = 1;
                        }
                     }

                     Session.getActor().CurrentRoom.getWiredUtility().onUserWalkOnFurni(Session, var13);
                  } else {
                     Session.getActor().IsMoving = false;
                     if(NextItem.size() > 1) {
                        Session.getActor().removeStatus("mv", Boolean.valueOf(true));
                     }
                  }
               }
            } else if(Session.getActor().IsRolling) {
               if(Session.getActor().RollerCycle > 0) {
                  continue;
               }

               FloorItem var11 = Session.getActor().CurrentRoom.getItemForInteraction(Session.getActor().CurrentPosition);
               if(var11 != null) {
                  if(var11.getBase().Sitable.booleanValue()) {
                     Session.getActor().Rotation = var11.Rotation;
                     if(var11.Height.floatValue() != 0.0F) {
                        Session.getActor().SitHeight = var11.Height.floatValue() + var11.getBase().StackHeight.floatValue();
                     } else {
                        Session.getActor().SitHeight = var11.getBase().StackHeight.floatValue();
                     }
                  } else {
                     Session.getActor().CycleInteraction = true;
                     Session.getActor().InteractionItem = var11;
                     if(var11.getBase().Interaction.equals("roller")) {
                        Session.getActor().RollerCycle = 1;
                     }
                  }
               } else {
                  FloorItem var12 = Session.getActor().CurrentRoom.getItemByPosition(Session.getActor().CurrentPosition.GetPositionInFront(Session.getActor().Rotation));
                  if(var12.getBase().Interaction.equals("ball")) {
                     Session.getActor().CycleInteraction = true;
                     Session.getActor().InteractionItem = var12;
                  }

                  Session.getActor().IsRolling = false;
               }
            }

            if(Session.getActor().SignTimer > 0) {
               if(Session.getActor().SignTimer == 1) {
                  Session.getActor().removeStatus("sign", Boolean.valueOf(true));
               }

               --Session.getActor().SignTimer;
            }

            if(Session.getActor().DrinkTimer > 0) {
               if(Session.getActor().DrinkTimer == 1) {
                  Session.getActor().Drink = 0;
                  Session.getActor().carry(0);
               }

               --Session.getActor().DrinkTimer;
            }

            ++Session.getActor().IdleTime;
            if(Session.getActor().IdleTime > (new Integer(Grizzly.getConfig().get("habbo.user.idle.time"))).intValue()) {
               Session.getActor().idle();
            }
         }
      } catch (Exception var10) {
         ;
      }

   }
}
