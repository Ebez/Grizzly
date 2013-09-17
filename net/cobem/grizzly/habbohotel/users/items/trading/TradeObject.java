package net.cobem.grizzly.habbohotel.users.items.trading;

import gnu.trove.map.hash.THashMap;
import java.util.Iterator;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.communication.composers.inventory.DisposeItemFromInventoryComposer;
import net.cobem.grizzly.communication.composers.inventory.UpdateUserInventoryComposer;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.users.items.InventoryItem;

public class TradeObject {

   private Session Partner;
   private Session Me;
   THashMap<Integer, InventoryItem> OfferedItems;
   private boolean Accepted;
   private boolean Confirmed;


   public TradeObject(Session Me, Session Partner) {
      this.Partner = Partner;
      this.Me = Me;
      this.OfferedItems = new THashMap();
   }

   public boolean addOffer(InventoryItem Item) {
      this.OfferedItems.put(Integer.valueOf(Item.ID), Item);
      return true;
   }

   public boolean removeOffer(InventoryItem Item) {
      this.OfferedItems.remove(Integer.valueOf(Item.ID));
      return true;
   }

   public THashMap<Integer, InventoryItem> getOffers() {
      return this.OfferedItems;
   }

   public boolean cancelTrade() {
      EventResponse msg = new EventResponse();
      msg.Initialize(HeaderLibrary.CloseTradeEvent);
      msg.addInt(Integer.valueOf(this.Me.getHabbo().getID()));
      msg.addInt(Integer.valueOf(2));
      this.Me.sendResponse(msg);
      msg.Initialize(HeaderLibrary.CloseTradeEvent);
      msg.addInt(Integer.valueOf(this.Partner.getHabbo().getID()));
      msg.addInt(Integer.valueOf(2));
      this.Partner.sendResponse(msg);
      this.Me.getHabbo().setTrade((TradeObject)null);
      this.Partner.getHabbo().setTrade((TradeObject)null);
      this.Me.getActor().removeStatus("trd", Boolean.valueOf(true));
      this.Partner.getActor().removeStatus("trd", Boolean.valueOf(true));
      return true;
   }

   public boolean completeTrade() {
      this.Confirmed = true;
      if(this.Accepted && this.Partner.getHabbo().getTrade().accepted() && this.Partner.getHabbo().getTrade().confirmed()) {
         Iterator var2 = this.OfferedItems.values().iterator();

         InventoryItem msg;
         while(var2.hasNext()) {
            msg = (InventoryItem)var2.next();
            this.Me.getHabbo().getItems().remove(msg.ID, true);
            this.Me.sendResponse(DisposeItemFromInventoryComposer.compose(msg.ID));
            this.Partner.getHabbo().getItems().add(msg.getBase(), true, (String)null);
         }

         var2 = this.Partner.getHabbo().getTrade().getOffers().values().iterator();

         while(var2.hasNext()) {
            msg = (InventoryItem)var2.next();
            this.Partner.getHabbo().getItems().remove(msg.ID, true);
            this.Partner.sendResponse(DisposeItemFromInventoryComposer.compose(msg.ID));
            this.Me.getHabbo().getItems().add(msg.getBase(), true, (String)null);
         }

         this.Me.sendResponse(UpdateUserInventoryComposer.compose());
         this.Partner.sendResponse(UpdateUserInventoryComposer.compose());
         EventResponse msg1 = new EventResponse();
         msg1.Initialize(HeaderLibrary.CloseCompletedTradeEvent);
         this.Me.sendResponse(msg1);
         this.Partner.sendResponse(msg1);
         this.Me.getHabbo().setTrade((TradeObject)null);
         this.Partner.getHabbo().setTrade((TradeObject)null);
         this.Me.getActor().removeStatus("trd", Boolean.valueOf(true));
         this.Partner.getActor().removeStatus("trd", Boolean.valueOf(true));
      }

      return true;
   }

   public boolean acceptTrade(boolean accept) {
      this.Accepted = accept;
      EventResponse msg = new EventResponse();
      msg.Initialize(HeaderLibrary.AcceptTradeEvent);
      msg.addInt(Integer.valueOf(this.Me.getHabbo().getID()));
      msg.addInt(Integer.valueOf(accept?1:0));
      this.Me.sendResponse(msg);
      this.Partner.sendResponse(msg);
      if(accept && this.Partner.getHabbo().getTrade().accepted()) {
         msg.Initialize(HeaderLibrary.CompleteTradeEvent);
         this.Me.sendResponse(msg);
         this.Partner.sendResponse(msg);
      }

      return true;
   }

   public boolean updateUsers() {
      EventResponse msg = new EventResponse();
      msg.Initialize(HeaderLibrary.UpdateTradeEvent);
      msg.addInt(Integer.valueOf(this.Me.getHabbo().getID()));
      msg.addInt(Integer.valueOf(this.OfferedItems.size()));
      Iterator var3 = this.OfferedItems.values().iterator();

      InventoryItem item;
      while(var3.hasNext()) {
         item = (InventoryItem)var3.next();
         msg.addInt(Integer.valueOf(item.ID));
         msg.addString(item.getBase().Type.toLowerCase());
         msg.addInt(Integer.valueOf(item.ID));
         msg.addInt(Integer.valueOf(item.getBase().Sprite));
         msg.addInt(Integer.valueOf(0));
         msg.addBool(Boolean.valueOf(true));
         msg.addInt(Integer.valueOf(0));
         msg.addString("");
         msg.addInt(Integer.valueOf(0));
         msg.addInt(Integer.valueOf(0));
         msg.addInt(Integer.valueOf(0));
         if(item.getBase().Type.equals("s")) {
            msg.addInt(Integer.valueOf(0));
         }
      }

      msg.addInt(Integer.valueOf(this.Partner.getHabbo().getID()));
      msg.addInt(Integer.valueOf(this.Partner.getHabbo().getTrade().getOffers().size()));
      var3 = this.Partner.getHabbo().getTrade().getOffers().values().iterator();

      while(var3.hasNext()) {
         item = (InventoryItem)var3.next();
         msg.addInt(Integer.valueOf(item.ID));
         msg.addString(item.getBase().Type.toLowerCase());
         msg.addInt(Integer.valueOf(item.ID));
         msg.addInt(Integer.valueOf(item.getBase().Sprite));
         msg.addInt(Integer.valueOf(0));
         msg.addBool(Boolean.valueOf(true));
         msg.addInt(Integer.valueOf(0));
         msg.addString("");
         msg.addInt(Integer.valueOf(0));
         msg.addInt(Integer.valueOf(0));
         msg.addInt(Integer.valueOf(0));
         if(item.getBase().Type.equals("s")) {
            msg.addInt(Integer.valueOf(0));
         }
      }

      this.Me.sendResponse(msg);
      this.Partner.sendResponse(msg);
      return true;
   }

   public boolean accepted() {
      return this.Accepted;
   }

   public boolean confirmed() {
      return this.Confirmed;
   }
}
