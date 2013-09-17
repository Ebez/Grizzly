package net.cobem.grizzly.habbohotel.furni;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Furniture {

   public int ID;
   public int Width;
   public int Length;
   public int Sprite;
   public int InteractionModesCount;
   public String PublicTitle;
   public String ItemTitle;
   public String Type;
   public String Interaction;
   public Float StackHeight;
   public Boolean Stackable;
   public Boolean Sitable;
   public Boolean Walkable;
   public Boolean Recyclable;
   public Boolean Tradeable;
   public Boolean Sellable;
   public Boolean Giftable;
   public Boolean Layable;
   public List<Integer> Vendors = new ArrayList();


   public Furniture(ResultSet Furni) {
      this.FillClass(Furni);
   }

   private boolean FillClass(ResultSet Set) {
      try {
         this.ID = Set.getInt("id");
         this.Width = Set.getInt("width");
         this.Length = Set.getInt("length");
         this.Sprite = Set.getInt("sprite_id");
         this.InteractionModesCount = Set.getInt("interaction_modes_count");
         this.PublicTitle = Set.getString("public_name");
         this.ItemTitle = Set.getString("item_name");
         this.Type = Set.getString("type");
         this.Interaction = Set.getString("interaction_type");
         this.StackHeight = Float.valueOf(Set.getFloat("stack_height"));
         this.Stackable = Boolean.valueOf(Set.getInt("can_stack") == 1);
         this.Sitable = Boolean.valueOf(Set.getInt("can_sit") == 1);
         this.Walkable = Boolean.valueOf(Set.getInt("is_walkable") == 1);
         this.Recyclable = Boolean.valueOf(Set.getInt("allow_recycle") == 1);
         this.Tradeable = Boolean.valueOf(Set.getInt("allow_trade") == 1);
         this.Sellable = Boolean.valueOf(Set.getInt("allow_marketplace_sell") == 1);
         this.Giftable = Boolean.valueOf(Set.getInt("allow_gift") == 1);
         this.Layable = Boolean.valueOf(Set.getString("interaction_type").equalsIgnoreCase("bed"));
         String[] var5;
         int var4 = (var5 = Set.getString("vending_ids").split(",")).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            String Ex = var5[var3];
            this.Vendors.add(new Integer(Ex.replace(" ", "")));
         }

         return true;
      } catch (SQLException var6) {
         return false;
      }
   }

   public int getRandomVendor() {
      int Key = 1;
      HashMap Drinks = new HashMap();
      if(this.Vendors.size() == 1) {
         return ((Integer)this.Vendors.iterator().next()).intValue();
      } else {
         for(Iterator var4 = this.Vendors.iterator(); var4.hasNext(); ++Key) {
            int ID = ((Integer)var4.next()).intValue();
            Drinks.put(Integer.valueOf(Key), Integer.valueOf(ID));
         }

         return ((Integer)Drinks.get(Integer.valueOf((new SecureRandom()).nextInt(Drinks.size() - 1) + 1))).intValue();
      }
   }
}
