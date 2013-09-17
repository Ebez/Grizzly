package net.cobem.grizzly.habbohotel.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.furni.Furniture;

public class CatalogItem {

   public int ID;
   public int Page;
   public int Cost;
   public int PixelCost;
   public int CurrencyCost;
   public int Quantity;
   public int Unique;
   public String Title;
   public Boolean LTD;
   public int LTD_Stock;
   public int LTD_Purchased;


   public CatalogItem(ResultSet Page) {
      this.FillClass(Page);
   }

   private boolean FillClass(ResultSet Set) {
      try {
         this.ID = Set.getInt("id");
         this.Page = Set.getInt("page");
         this.Cost = Set.getInt("cost");
         this.PixelCost = Set.getInt("cost_pixels");
         this.CurrencyCost = Set.getInt("cost_currency");
         this.Quantity = Set.getInt("quantity");
         this.Unique = Set.getInt("unique_id");
         this.LTD = Boolean.valueOf(Set.getInt("ltd") == 1);
         this.Title = Set.getString("store_title");
         this.LTD_Stock = Set.getInt("ltd_stock");
         this.LTD_Purchased = Set.getInt("ltd_purchased");
         return true;
      } catch (SQLException var3) {
         return false;
      }
   }

   public Furniture getBase() {
      return Grizzly.getHabboHotel().getFurniture().getByID(this.Unique);
   }

   public String getPresetFlag() {
      return this.Title.split("_").length >= 3?this.Title.split("_")[2]:"";
   }
}
