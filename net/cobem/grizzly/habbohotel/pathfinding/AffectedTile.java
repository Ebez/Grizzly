package net.cobem.grizzly.habbohotel.pathfinding;

import java.util.ArrayList;
import java.util.List;

public class AffectedTile {

   public int X;
   public int Y;
   public int I;


   public AffectedTile() {
      this.X = 0;
      this.Y = 0;
      this.I = 0;
   }

   public AffectedTile(int x, int y, int i) {
      this.X = x;
      this.Y = y;
      this.I = i;
   }

   public static List<AffectedTile> GetAffectedTiles(int Length, int Width, int PosX, int PosY, int Rotation) {
      ArrayList PointList = new ArrayList();
      int i;
      int j;
      if(Length > 1) {
         if(Rotation != 0 && Rotation != 4) {
            if(Rotation == 2 || Rotation == 6) {
               for(i = 0; i < Length; ++i) {
                  PointList.add(new AffectedTile(PosX + i, PosY, i));

                  for(j = 0; j < Width; ++j) {
                     PointList.add(new AffectedTile(PosX + i, PosY + j, i < j?j:i));
                  }
               }
            }
         } else {
            for(i = 0; i < Length; ++i) {
               PointList.add(new AffectedTile(PosX, PosY + i, i));

               for(j = 0; j < Width; ++j) {
                  PointList.add(new AffectedTile(PosX + j, PosY + i, i < j?j:i));
               }
            }
         }
      }

      if(Width > 1) {
         if(Rotation != 0 && Rotation != 4) {
            if(Rotation == 2 || Rotation == 6) {
               for(i = 0; i < Width; ++i) {
                  PointList.add(new AffectedTile(PosX, PosY + i, i));

                  for(j = 0; j < Length; ++j) {
                     PointList.add(new AffectedTile(PosX + j, PosY + i, i < j?j:i));
                  }
               }
            }
         } else {
            for(i = 0; i < Width; ++i) {
               PointList.add(new AffectedTile(PosX + i, PosY, i));

               for(j = 0; j < Length; ++j) {
                  PointList.add(new AffectedTile(PosX + i, PosY + j, i < j?j:i));
               }
            }
         }
      }

      return PointList;
   }
}
