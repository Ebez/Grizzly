package net.cobem.grizzly.habbohotel.pathfinding.suelake;

import java.util.Collection;
import net.cobem.grizzly.habbohotel.pathfinding.IPathfinder;

public abstract class Suelake_PathfinderLogic implements IPathfinder {

   public void ApplyCollisionMap(byte[][] Map, float[][] Height) {}

   public Collection<byte[]> Path(byte StartX, byte StartY, byte EndX, byte EndY, float MaxDrop, float MaxJump) {
      return null;
   }
}
