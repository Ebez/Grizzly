package net.cobem.grizzly.habbohotel.pathfinding.suelake;


public class Suelake_PathfinderNode {

   public short X;
   public short Y;
   public short parentX;
   public short parentY;
   public int H;
   public int F;
   public int G;


   public Suelake_PathfinderNode() {}

   public Suelake_PathfinderNode(short X, short Y) {
      this.X = X;
      this.Y = Y;
   }
}
