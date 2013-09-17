package net.cobem.grizzly.habbohotel.pathfinding;

import java.util.Collection;

public interface IPathfinder {

   void ApplyCollisionMap(byte[][] var1, float[][] var2);

   Collection<byte[]> Path(byte var1, byte var2, byte var3, byte var4, float var5, float var6);
}
