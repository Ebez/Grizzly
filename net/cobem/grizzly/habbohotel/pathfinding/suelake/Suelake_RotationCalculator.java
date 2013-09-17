package net.cobem.grizzly.habbohotel.pathfinding.suelake;


public abstract class Suelake_RotationCalculator {

   public static byte calculateHumanDirection(short X, short Y, short toX, short toY) {
      byte result = 0;
      if(X > toX && Y > toY) {
         result = 7;
      } else if(X < toX && Y < toY) {
         result = 3;
      } else if(X > toX && Y < toY) {
         result = 5;
      } else if(X < toX && Y > toY) {
         result = 1;
      } else if(X > toX) {
         result = 6;
      } else if(X < toX) {
         result = 2;
      } else if(Y < toY) {
         result = 4;
      } else if(Y > toY) {
         result = 0;
      }

      return result;
   }

   public static byte calculateHumanMoveDirection(short X, short Y, short toX, short toY) {
      return (byte)(X == toX?(Y < toY?4:0):(X > toX?(Y == toY?6:(Y < toY?5:7)):(X < toX?(Y == toY?2:(Y < toY?3:1)):0)));
   }

   public static double calculateDistance(short x1, short y1, short x2, short y2) {
      return Math.sqrt((double)((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
   }
}
