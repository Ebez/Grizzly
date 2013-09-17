package net.cobem.grizzly.habbohotel.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.habbohotel.catalog.CatalogItem;
import net.cobem.grizzly.habbohotel.catalog.CatalogPage;
import net.cobem.grizzly.habbohotel.catalog.CatalogParent;
import net.cobem.grizzly.storage.DatabaseResult;

public class CatalogHandler {

   private Map<Integer, CatalogPage> Pages;
   private Map<Integer, CatalogItem> Items;
   private LinkedHashMap<Integer, CatalogParent> Parents;
   private Map<Integer, EventResponse> PageCache;


   public CatalogHandler() throws SQLException {
      Grizzly.write("Loading catalog parents... ", true, false);
      this.Parents = new LinkedHashMap();
      DatabaseResult GrabParents = Grizzly.getStorage().query("SELECT * FROM server_store_parents ORDER BY `order` ASC");
      ResultSet CatalogParents = GrabParents.getTable();

      while(CatalogParents.next()) {
         this.Parents.put(Integer.valueOf(CatalogParents.getInt("id")), new CatalogParent(CatalogParents));
      }

      Grizzly.write(Integer.valueOf(this.Parents.size()), false, true);
      Grizzly.write("Loading catalog pages... ", true, false);
      this.Pages = new HashMap();
      DatabaseResult GrabPages = Grizzly.getStorage().query("SELECT * FROM server_store_pages ORDER BY `order` ASC");
      ResultSet CatalogPages = GrabPages.getTable();

      while(CatalogPages.next()) {
         this.Pages.put(Integer.valueOf(CatalogPages.getInt("id")), new CatalogPage(CatalogPages));
      }

      Grizzly.write(Integer.valueOf(this.Pages.size()), false, true);
      Grizzly.write("Loading catalog items... ", true, false);
      this.Items = new HashMap();
      DatabaseResult GrabItems = Grizzly.getStorage().query("SELECT * FROM server_store_items");
      ResultSet CatalogItems = GrabItems.getTable();

      while(CatalogItems.next()) {
         this.Items.put(Integer.valueOf(CatalogItems.getInt("id")), new CatalogItem(CatalogItems));
      }

      Grizzly.write(Integer.valueOf(this.Items.size()), false, true);
      this.PageCache = new HashMap();
   }

   public Map<Integer, CatalogPage> getPages() {
      return this.Pages;
   }

   public Map<Integer, EventResponse> getCache() {
      return this.PageCache;
   }

   public LinkedHashMap<Integer, CatalogParent> getPrimary() {
      return this.Parents;
   }

   public Map<Integer, CatalogPage> getSecondary(int Parent) {
      LinkedHashMap Primaries = new LinkedHashMap();
      Iterator var4 = this.Pages.values().iterator();

      while(var4.hasNext()) {
         CatalogPage Page = (CatalogPage)var4.next();
         if(Page.Parent == Parent) {
            Primaries.put(Integer.valueOf(Page.ID), Page);
         }
      }

      return Primaries;
   }

   public Object getPageByID(int ID) {
      return this.Parents.containsKey(Integer.valueOf(ID))?this.Parents.get(Integer.valueOf(ID)):(this.Pages.containsKey(Integer.valueOf(ID))?this.Pages.get(Integer.valueOf(ID)):null);
   }

   public Map<Integer, CatalogItem> getItems(int ID) {
      HashMap Itemz = new HashMap();
      Iterator var4 = this.Items.values().iterator();

      while(var4.hasNext()) {
         CatalogItem Item = (CatalogItem)var4.next();
         if(Item.Page == ID) {
            Itemz.put(Integer.valueOf(Item.ID), Item);
         }
      }

      return Itemz;
   }

   public CatalogItem getItemByID(int ID) {
      Iterator var3 = this.Items.values().iterator();

      while(var3.hasNext()) {
         CatalogItem Item = (CatalogItem)var3.next();
         if(Item.ID == ID) {
            return Item;
         }
      }

      return null;
   }

   public void createTeleporters(int FurniID, int Pair) {
      Grizzly.getStorage().execute("INSERT INTO server_teleporters (teleporter_one, teleporter_two) VALUES (\'" + FurniID + "\', \'" + Pair + "\')");
      Grizzly.getHabboHotel().getFurniture().getTeleporters().addPair(FurniID, Pair);
   }

   public void refresh() {
      try {
         this.Parents = new LinkedHashMap();
         DatabaseResult GrabParents = Grizzly.getStorage().query("SELECT * FROM server_store_parents ORDER BY \'order\' ASC");
         ResultSet CatalogParents = GrabParents.getTable();

         while(CatalogParents.next()) {
            this.Parents.put(Integer.valueOf(CatalogParents.getInt("id")), new CatalogParent(CatalogParents));
         }

         this.Pages = new HashMap();
         DatabaseResult GrabPages = Grizzly.getStorage().query("SELECT * FROM server_store_pages ORDER BY \'order\' ASC");
         ResultSet CatalogPages = GrabPages.getTable();

         while(CatalogPages.next()) {
            this.Pages.put(Integer.valueOf(CatalogPages.getInt("id")), new CatalogPage(CatalogPages));
         }

         this.Items = new HashMap();
         DatabaseResult GrabItems = Grizzly.getStorage().query("SELECT * FROM server_store_items");
         ResultSet CatalogItems = GrabItems.getTable();

         while(CatalogItems.next()) {
            this.Items.put(Integer.valueOf(CatalogItems.getInt("id")), new CatalogItem(CatalogItems));
         }

         this.PageCache = new HashMap();
      } catch (Exception var7) {
         ;
      }

   }
}
