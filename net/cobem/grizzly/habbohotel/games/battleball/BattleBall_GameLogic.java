package net.cobem.grizzly.habbohotel.games.battleball;

import gnu.trove.map.hash.THashMap;
import java.util.Iterator;
import net.cobem.grizzly.habbohotel.games.battleball.BattleBall_TeamLogic;
import net.cobem.grizzly.habbohotel.games.battleball.BattleBall_TeamType;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class BattleBall_GameLogic {

   private Room Room;
   private BattleBall_TeamLogic RedTeam;
   private BattleBall_TeamLogic BlueTeam;
   private BattleBall_TeamLogic GreenTeam;
   private BattleBall_TeamLogic YellowTeam;
   private THashMap<Integer, FloorItem> Tiles = new THashMap();
   private BattleBall_TeamLogic[] TileOwners;
   private BattleBall_TeamLogic[] ColorCodes;


   public BattleBall_GameLogic(Room Room) {
      this.Room = Room;
      this.RedTeam = new BattleBall_TeamLogic(BattleBall_TeamType.Red);
      this.BlueTeam = new BattleBall_TeamLogic(BattleBall_TeamType.Blue);
      this.GreenTeam = new BattleBall_TeamLogic(BattleBall_TeamType.Green);
      this.YellowTeam = new BattleBall_TeamLogic(BattleBall_TeamType.Yellow);
      this.ColorCodes[0] = null;
      this.ColorCodes[1] = null;
      this.ColorCodes[2] = null;
      this.ColorCodes[3] = null;
      this.ColorCodes[4] = null;
   }

   private boolean initTiles() {
      Iterator var2 = this.Room.getFloors().values().iterator();

      while(var2.hasNext()) {
         FloorItem Item = (FloorItem)var2.next();
         if(Item.getBase().Interaction.equals("bb_patch")) {
            this.Tiles.put(Integer.valueOf(Item.ID), Item);
         }
      }

      return true;
   }

   public boolean ownOrUpdateTile(Session Player, int ItemID) {
      FloorItem Tile = (FloorItem)this.Tiles.get(Integer.valueOf(ItemID));
      if(Tile == null) {
         return false;
      } else {
         if(this.TileOwners[ItemID] != null) {
            if(this.getPlayersTeam(Player) == this.TileOwners[ItemID] && Tile.ExtraData.equals("3")) {
               return false;
            }
         } else {
            this.TileOwners[ItemID] = this.getPlayersTeam(Player);
         }

         return true;
      }
   }

   public BattleBall_TeamLogic getPlayersTeam(Session Player) {
      return this.RedTeam.hasPlayer(Player)?this.RedTeam:(this.BlueTeam.hasPlayer(Player)?this.BlueTeam:(this.GreenTeam.hasPlayer(Player)?this.GreenTeam:(this.YellowTeam.hasPlayer(Player)?this.YellowTeam:null)));
   }
}
