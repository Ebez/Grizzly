package net.cobem.grizzly.habbohotel.pathfinding.ihi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import net.cobem.grizzly.habbohotel.pathfinding.IPathfinder;
import net.cobem.grizzly.habbohotel.pathfinding.ihi.IHI_PathfinderValues;

public class IHI_PathfinderLogic implements IPathfinder {

   public byte[][] collisionMap;
   private float[][] height;


   public void ApplyCollisionMap(byte[][] collisionMap, float[][] height) {
      if(this.collisionMap != null) {
         byte[][] var3 = this.collisionMap;
         synchronized(this.collisionMap) {
            this.collisionMap = collisionMap;
            this.height = height;
         }
      } else {
         this.collisionMap = collisionMap;
         this.height = height;
      }

   }

   public Collection<byte[]> Path(byte startX, byte startY, byte endX, byte endY, float maximumFall, float maximumJump) {
      byte[][] path = this.collisionMap;
      IHI_PathfinderValues values;
      synchronized(this.collisionMap) {
         values = new IHI_PathfinderValues(this.collisionMap, this.height, maximumFall, maximumJump);
         if(endX >= this.collisionMap.length || endY >= this.collisionMap[0].length || startX > this.collisionMap.length - 1 || startY > this.collisionMap[0].length - 1 || this.collisionMap[endX][endY] == 0 || startX == endX && startY == endY) {
            return new ArrayList();
         }

         ++values.count;
         values.binaryHeap[values.count] = values.lastID;
         values.X[values.lastID] = startX;
         values.Y[values.lastID] = startY;
         values.H[values.lastID] = (short)GetH(startX, startY, endX, endY);
         values.parent[values.lastID] = 0;
         values.G[values.lastID] = 0;
         values.F[values.lastID] = (short)(values.G[values.lastID] + values.H[values.lastID]);

         while(values.count != 0) {
            values.location = values.binaryHeap[1];
            if(values.X[values.location] == endX && values.Y[values.location] == endY) {
               break;
            }

            Move(values);

            try {
               this.Add(-1, 0, endX, endY, values);
               this.Add(0, -1, endX, endY, values);
               this.Add(1, 0, endX, endY, values);
               this.Add(0, 1, endX, endY, values);
               this.Add(-1, -1, endX, endY, values);
               this.Add(-1, 1, endX, endY, values);
               this.Add(1, -1, endX, endY, values);
               this.Add(1, 1, endX, endY, values);
            } catch (ArrayIndexOutOfBoundsException var10) {
               ;
            }
         }
      }

      if(values.count == 0) {
         return new ArrayList();
      } else {
         ArrayList var12;
         for(var12 = new ArrayList(); values.X[values.parent[values.location]] != startX || values.Y[values.parent[values.location]] != startY; values.location = values.parent[values.location]) {
            var12.add(new byte[]{values.X[values.location], values.Y[values.location]});
         }

         var12.add(new byte[]{values.X[values.location], values.Y[values.location]});
         Collections.reverse(var12);
         return var12;
      }
   }

   private static int GetH(int x, int y, int endX, int endY) {
      return Math.abs(x + endX) + Math.abs(y + endY);
   }

   private void Add(int x, int y, byte endX, byte endY, IHI_PathfinderValues values) {
      byte x2 = (byte)(values.X[values.location] + x);
      byte y2 = (byte)(values.Y[values.location] + y);
      if(x2 >= 0 && y2 >= 0) {
         short parent = values.location;
         if(x2 < this.collisionMap.length && y2 < this.collisionMap[0].length) {
            if(values.tiles[x2][y2] != 2) {
               if(this.collisionMap[x2][y2] != 0 && (this.collisionMap[x2][y2] != 2 || x2 == endX && y2 == endY)) {
                  float z = values.Z[x2][y2];
                  float z2 = values.Z[values.X[parent]][values.Y[parent]];
                  if(z <= z2 + values.maximumJump && z >= z2 - values.maximumFall) {
                     if(parent > 0 && values.X[parent] != x2 && values.Y[parent] != y2) {
                        if(this.collisionMap[x2][values.Y[parent]] == 0 || this.collisionMap[x2][values.Y[parent]] == 2) {
                           return;
                        }

                        if(this.collisionMap[values.X[parent]][y2] == 0 || this.collisionMap[values.X[parent]][y2] == 2) {
                           return;
                        }
                     }

                     short c;
                     if(values.tiles[x2][y2] == 1) {
                        for(c = 1; c <= values.count && (values.X[c] != x2 || values.Y[c] != y2); ++c) {
                           ;
                        }

                        if(values.X[c] != endX && values.Y[c] != endY) {
                           if(14 + values.G[parent] < values.G[c]) {
                              values.parent[c] = parent;
                           }
                        } else if(10 + values.G[parent] < values.G[c]) {
                           values.parent[c] = parent;
                        }

                     } else {
                        ++values.lastID;
                        ++values.count;
                        values.binaryHeap[values.count] = values.lastID;
                        values.X[values.lastID] = x2;
                        values.Y[values.lastID] = y2;
                        values.H[values.lastID] = (short)GetH(x2, y2, endX, endY);
                        values.parent[values.lastID] = parent;
                        if(x2 != values.X[parent] && y2 != values.Y[parent]) {
                           values.G[values.lastID] = (short)(14 + values.G[parent]);
                        } else {
                           values.G[values.lastID] = (short)(10 + values.G[parent]);
                        }

                        values.F[values.lastID] = (short)(values.G[values.lastID] + values.H[values.lastID]);

                        for(c = values.count; c != 1 && values.F[values.binaryHeap[c]] <= values.F[values.binaryHeap[c / 2]]; c = (short)(c / 2)) {
                           short temp = values.binaryHeap[c / 2];
                           values.binaryHeap[c / 2] = values.binaryHeap[c];
                           values.binaryHeap[c] = temp;
                        }

                        values.tiles[x2][y2] = 1;
                     }
                  }
               }
            }
         }
      }
   }

   private static void Move(IHI_PathfinderValues values) {
      values.tiles[values.X[values.binaryHeap[1]]][values.Y[values.binaryHeap[1]]] = 2;
      values.binaryHeap[1] = values.binaryHeap[values.count];
      --values.count;
      short location = 1;

      while(true) {
         short high = location;
         if(2 * location + 1 <= values.count) {
            if(values.F[values.binaryHeap[location]] >= values.F[values.binaryHeap[2 * location]]) {
               location = (short)(2 * location);
            }

            if(values.F[values.binaryHeap[location]] >= values.F[values.binaryHeap[2 * high + 1]]) {
               location = (short)(2 * high + 1);
            }
         } else if(2 * location <= values.count && values.F[values.binaryHeap[location]] >= values.F[values.binaryHeap[2 * location]]) {
            location = (short)(2 * location);
         }

         if(location == location) {
            return;
         }

         short temp = values.binaryHeap[high];
         values.binaryHeap[high] = values.binaryHeap[location];
         values.binaryHeap[location] = temp;
      }
   }
}
