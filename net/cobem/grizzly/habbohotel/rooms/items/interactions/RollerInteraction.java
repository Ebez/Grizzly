package net.cobem.grizzly.habbohotel.rooms.items.interactions;

import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.interactions.Interaction;
import net.cobem.grizzly.habbohotel.rooms.models.SquareState;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class RollerInteraction implements Interaction {

   public void onPlace(Session Session, Object Item) {}

   public void onRemove(Session Session, Object Item) {}

   public void onTrigger(Session Session, Object Item, int Request) {
      Position NextPosition = Session.getActor().CurrentPosition.GetPositionInFront(((FloorItem)Item).Rotation);
      if(Session.getActor().CurrentRoom.getModel().Squares[NextPosition.X][NextPosition.Y] == SquareState.CLOSED) {
         Session.getActor().IsRolling = false;
         Session.getActor().RollerCycle = 0;
      } else {
         EventResponse Message = new EventResponse();
         Message.Initialize(HeaderLibrary.UpdateItemOnRollerEvent);
         Message.addInt(Integer.valueOf(Session.getActor().CurrentPosition.X));
         Message.addInt(Integer.valueOf(Session.getActor().CurrentPosition.Y));
         Message.addInt(Integer.valueOf(NextPosition.X));
         Message.addInt(Integer.valueOf(NextPosition.Y));
         Message.addInt(Integer.valueOf(0));
         Message.addInt(Integer.valueOf(((FloorItem)Item).ID));
         Message.addInt(Integer.valueOf(2));
         Message.addInt(Integer.valueOf(Session.getHabbo().getID()));
         Message.addString(Double.valueOf(Session.getActor().CurrentPosition.Z));
         Session.getActor().CurrentPosition = Session.getActor().CurrentPosition.GetPositionInFront(((FloorItem)Item).Rotation);
         if(Session.getActor().CurrentRoom.getItemByPosition(NextPosition) == null) {
            Message.addString(Double.valueOf(Session.getActor().CurrentRoom.getModel().getTileHeight(NextPosition.X, NextPosition.Y)));
            Session.getActor().CurrentPosition.Z = Session.getActor().CurrentRoom.getModel().getTileHeight(NextPosition.X, NextPosition.Y);
         } else {
            Message.addString(Double.valueOf((double)Session.getActor().CurrentRoom.getItemByPosition(NextPosition).Height.floatValue() + 0.55D));
            Session.getActor().CurrentPosition.Z = (double)Session.getActor().CurrentRoom.getItemByPosition(NextPosition).Height.floatValue() + 0.55D;
         }

         Session.getActor().CurrentRoom.sendMessage(Message);
         Session.getActor().CurrentRoom.getPositions().remove(Integer.valueOf(Session.getHabbo().getID()));
         Session.getActor().CurrentRoom.getMap().UserCollisionMap[Session.getActor().CurrentPosition.X][Session.getActor().CurrentPosition.Y] = false;
      }
   }

   public void onCycle(Object Item) {}
}
