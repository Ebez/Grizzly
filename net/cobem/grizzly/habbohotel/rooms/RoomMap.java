package net.cobem.grizzly.habbohotel.rooms;

import java.util.Iterator;
import net.cobem.grizzly.habbohotel.pathfinding.AffectedTile;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.Room;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.models.SquareState;

public class RoomMap {

   private Room Room;
   public byte[][] FurniCollisionMap;
   public boolean[][] UserCollisionMap;


   public RoomMap(Room mRoom) {
      this.Room = mRoom;
      this.FurniCollisionMap = new byte[this.Room.getModel().MapSizeX][this.Room.getModel().MapSizeY];
      this.UserCollisionMap = new boolean[this.Room.getModel().MapSizeX][this.Room.getModel().MapSizeY];

      for(int x = 0; x < this.Room.getModel().MapSizeX; ++x) {
         for(int y = 0; y < this.Room.getModel().MapSizeY; ++y) {
            this.FurniCollisionMap[x][y] = 1;
         }
      }

   }

   public void GenerateFurniCollisionMap() {
      for(int Item = 0; Item < this.Room.getModel().MapSizeX; ++Item) {
         for(int y = 0; y < this.Room.getModel().MapSizeY; ++y) {
            this.FurniCollisionMap[Item][y] = 1;
         }
      }

      try {
         Iterator var7 = this.Room.getFloors().values().iterator();

         while(var7.hasNext()) {
            FloorItem var6 = (FloorItem)var7.next();
            if(var6 != null) {
               if(var6.getBase().Width == 1 && var6.getBase().Length == 1) {
                  if(!var6.getBase().Sitable.booleanValue() && !var6.getBase().Walkable.booleanValue() && !var6.getBase().Layable.booleanValue()) {
                     this.FurniCollisionMap[var6.X][var6.Y] = 0;
                  }

                  if(var6.getBase().Sitable.booleanValue() || var6.getBase().Layable.booleanValue()) {
                     this.FurniCollisionMap[var6.X][var6.Y] = 2;
                  }

                  if(var6.getBase().Interaction.equals("gate") && var6.ExtraData.equals("1")) {
                     this.FurniCollisionMap[var6.X][var6.Y] = 1;
                  }
               } else {
                  Iterator var4 = AffectedTile.GetAffectedTiles(var6.getBase().Length, var6.getBase().Width, var6.X, var6.Y, var6.Rotation).iterator();

                  while(var4.hasNext()) {
                     AffectedTile Tile = (AffectedTile)var4.next();
                     if(!var6.getBase().Sitable.booleanValue() && !var6.getBase().Walkable.booleanValue() && !var6.getBase().Layable.booleanValue()) {
                        this.FurniCollisionMap[Tile.X][Tile.Y] = 0;
                     }

                     if(var6.getBase().Sitable.booleanValue() || var6.getBase().Layable.booleanValue()) {
                        this.FurniCollisionMap[Tile.X][Tile.Y] = 2;
                     }
                  }
               }
            }
         }
      } catch (Exception var5) {
         ;
      }

   }

   public void ReGenerateCollisionMap() {
      this.Room.getPathfinder().ApplyCollisionMap(this.GenerateCollisionMap(), this.GenerateFurniHeightMap());
      this.Room.getGhostPathfinder().ApplyCollisionMap(this.GenerateCollisionMap(), this.GenerateFurniHeightMap());
      this.GenerateFurniCollisionMap();
   }

   public byte[][] GenerateCollisionMap() {
      byte[][] CollisionMap = new byte[this.Room.getModel().MapSizeX][this.Room.getModel().MapSizeY];

      for(int x = 0; x < this.Room.getModel().MapSizeX; ++x) {
         for(int y = 0; y < this.Room.getModel().MapSizeY; ++y) {
            if(this.FurniCollisionMap[x][y] == 0) {
               CollisionMap[x][y] = this.FurniCollisionMap[x][y];
            } else if(this.Room.getModel().Squares[x][y] == SquareState.WALKABLE) {
               if(this.UserCollisionMap[x][y]) {
                  CollisionMap[x][y] = 0;
               } else {
                  CollisionMap[x][y] = 1;
               }
            } else {
               CollisionMap[x][y] = 0;
            }
         }
      }

      return CollisionMap;
   }

   public float[][] GenerateFurniHeightMap() {
      float[][] FurniHeightMap = new float[this.Room.getModel().MapSizeX][this.Room.getModel().MapSizeY];

      for(int x = 0; x < this.Room.getModel().MapSizeX; ++x) {
         for(int y = 0; y < this.Room.getModel().MapSizeY; ++y) {
            if(this.FurniCollisionMap[x][y] == 0) {
               FurniHeightMap[x][y] = (float)this.Room.getModel().SquareHeight[x][y];
            } else {
               FloorItem ex;
               try {
                  for(Iterator var5 = this.Room.getFloors().values().iterator(); var5.hasNext(); FurniHeightMap[x][y] = ex.Height.floatValue()) {
                     ex = (FloorItem)var5.next();
                  }
               } catch (Exception var6) {
                  FurniHeightMap[x][y] = (float)this.Room.getModel().SquareHeight[x][y];
               }
            }
         }
      }

      return FurniHeightMap;
   }

   public FloorItem GetItemAtPosition(Position Pos) {
      FloorItem Return = null;
      Float LowestHeight = Float.valueOf(0.0F);
      boolean HeightNotSet = true;
      Iterator var6 = this.Room.getItemsByPosition(Pos).values().iterator();

      while(var6.hasNext()) {
         FloorItem Item = (FloorItem)var6.next();
         if(HeightNotSet) {
            LowestHeight = Item.Height;
            HeightNotSet = false;
         }

         if(Item.Height.floatValue() <= LowestHeight.floatValue()) {
            if(Item.getBase().Width == 1 && Item.getBase().Length == 1) {
               Return = Item;
            } else {
               for(Iterator var8 = AffectedTile.GetAffectedTiles(Item.getBase().Length, Item.getBase().Width, Item.X, Item.Y, Item.Rotation).iterator(); var8.hasNext(); Return = Item) {
                  AffectedTile Tile = (AffectedTile)var8.next();
               }
            }
         }
      }

      return Return;
   }

   public boolean realTileOpen(Position Position) {
      Iterator var3 = this.Room.getItemsByPosition(Position).values().iterator();

      while(var3.hasNext()) {
         FloorItem Item = (FloorItem)var3.next();
         if(!Item.getBase().Walkable.booleanValue()) {
            return false;
         }
      }

      return true;
   }
}
