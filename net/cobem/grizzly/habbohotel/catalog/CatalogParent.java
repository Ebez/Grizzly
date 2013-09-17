package net.cobem.grizzly.habbohotel.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.catalog.CatalogItem;
import net.cobem.grizzly.habbohotel.catalog.CatalogLayout;
import net.cobem.grizzly.storage.DatabaseResult;

public class CatalogParent {

   public int ID;
   public int Order;
   public int IconColor;
   public int IconImage;
   public int Rank;
   public CatalogLayout Layout;
   public String Title;
   public List<String> Headers = new ArrayList();
   public List<String> Texts = new ArrayList();
   public boolean Enabled;


   public CatalogParent(ResultSet Page) {
      this.FillClass(Page);
   }

   private boolean FillClass(ResultSet Set) {
      try {
         this.ID = Set.getInt("id");
         this.Order = Set.getInt("order");
         this.IconColor = Set.getInt("icon_color");
         this.IconImage = Set.getInt("icon_image");
         this.Rank = Set.getInt("minimum_rank");
         this.Layout = CatalogLayout.valueOf(Set.getString("layout"));
         this.Title = Set.getString("title");
         this.Enabled = Set.getInt("enabled") == 1;
         this.initOtherData();
         return true;
      } catch (SQLException var3) {
         return false;
      }
   }

   private boolean initOtherData() {
      DatabaseResult GetHeaders = Grizzly.getStorage().preparedQuery("SELECT * FROM server_store_page_headers WHERE page_id = ? AND is_parent = ?", Arrays.asList(new String[]{String.valueOf(this.ID), String.valueOf(1)}));
      ResultSet Headers = GetHeaders.getTable();

      try {
         while(Headers.next()) {
            this.Headers.add(Headers.getString("value"));
         }
      } catch (SQLException var7) {
         ;
      }

      DatabaseResult GetTexts = Grizzly.getStorage().preparedQuery("SELECT * FROM server_store_page_texts WHERE page_id = ? AND is_parent = ?", Arrays.asList(new String[]{String.valueOf(this.ID), String.valueOf(1)}));
      ResultSet Texts = GetTexts.getTable();

      try {
         while(Texts.next()) {
            this.Texts.add(Texts.getString("value"));
         }
      } catch (SQLException var6) {
         ;
      }

      return true;
   }

   public boolean containsLTD() {
      Iterator var2 = Grizzly.getHabboHotel().getCatalog().getItems(this.ID).values().iterator();

      while(var2.hasNext()) {
         CatalogItem Item = (CatalogItem)var2.next();
         if(Item.LTD.booleanValue()) {
            return true;
         }
      }

      return false;
   }
}
