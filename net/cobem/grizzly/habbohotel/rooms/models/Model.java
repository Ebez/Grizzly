package net.cobem.grizzly.habbohotel.rooms.models;

import gnu.trove.map.hash.THashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.rooms.models.SquareState;

public class Model {

   public String Name;
   public String Heightmap;
   public String SerializeMap = "";
   public String SerializeRelativeMap = "";
   public int DoorX;
   public int DoorY;
   public int DoorZ;
   public int DoorRot;
   public int MapSizeX;
   public int MapSizeY;
   public int MapSize;
   public int OPEN = 0;
   public int CLOSED = 1;
   public List<String> Lines;
   public SquareState[][] Squares;
   public double[][] SquareHeight;
   public int[][] SqState;


   public Model(ResultSet Set) {
      try {
         this.Name = Set.getString("id");
         this.Heightmap = Set.getString("heightmap");
         this.DoorX = Set.getInt("door_x");
         this.DoorY = Set.getInt("door_y");
         this.DoorZ = Set.getInt("door_z");
         this.DoorRot = Set.getInt("door_dir");
      } catch (SQLException var7) {
         ;
      }

      String[] Temporary = this.Heightmap.split(Character.toString('\r'));
      this.MapSizeX = Temporary[0].length();
      this.MapSizeY = Temporary.length;
      this.Lines = new ArrayList();
      this.Lines = new ArrayList(Arrays.asList(this.Heightmap.split(Character.toString('\r'))));
      this.SqState = new int[this.MapSizeX][this.MapSizeY];
      this.SquareHeight = new double[this.MapSizeX][this.MapSizeY];
      this.Squares = new SquareState[this.MapSizeX][this.MapSizeY];

      int x;
      for(int MapLine = 0; MapLine < this.MapSizeY; ++MapLine) {
         if(MapLine > 0) {
            Temporary[MapLine] = Temporary[MapLine].substring(1);
         }

         for(x = 0; x < this.MapSizeX; ++x) {
            String Square = Temporary[MapLine].substring(x, x + 1).trim().toLowerCase();
            if(Square.equals("x")) {
               this.Squares[x][MapLine] = SquareState.CLOSED;
               this.SqState[x][MapLine] = this.CLOSED;
            } else if(this.isNumeric(Square)) {
               this.Squares[x][MapLine] = SquareState.WALKABLE;
               this.SqState[x][MapLine] = this.OPEN;
               this.SquareHeight[x][MapLine] = Double.parseDouble(Square);
               ++this.MapSize;
            }

            if(this.DoorX == x && this.DoorY == MapLine) {
               this.Squares[x][MapLine] = SquareState.WALKABLE;
               this.SerializeRelativeMap = this.SerializeRelativeMap + this.DoorZ;
            } else if(!Square.isEmpty() && Square != null) {
               this.SerializeRelativeMap = this.SerializeRelativeMap + Square;
            }
         }

         this.SerializeRelativeMap = this.SerializeRelativeMap + '\r';
      }

      String[] var6;
      int var9 = (var6 = this.Heightmap.split("\r\n")).length;

      for(x = 0; x < var9; ++x) {
         String var8 = var6[x];
         if(!var8.isEmpty() && var8 != null) {
            this.SerializeMap = this.SerializeMap + var8 + '\r';
         }
      }

   }

   public String heightmap() {
      return this.SerializeMap;
   }

   public String relativeHeightMap() {
      return this.SerializeRelativeMap;
   }

   private boolean isNumeric(String Input) {
      try {
         Integer.parseInt(Input);
         return true;
      } catch (Exception var3) {
         return false;
      }
   }

   public double getTileHeight(int X, int Y) {
      return this.SquareHeight[X][Y];
   }

   public Position getRandomSquare() {
      THashMap WalkableSquares = new THashMap();

      for(int x = 1; x < this.MapSizeX - 1; ++x) {
         for(int y = 1; y < this.MapSizeX - 1; ++y) {
            if(this.Squares[x][y] != null && this.Squares[x][y] == SquareState.WALKABLE) {
               WalkableSquares.put(Integer.valueOf(WalkableSquares.size() + 1), new Position(x, y, 0.0D));
            }
         }
      }

      return (Position)WalkableSquares.get(Integer.valueOf(Grizzly.rand(1, WalkableSquares.size())));
   }
}
