package net.cobem.grizzly.habbohotel.rooms;

import gnu.trove.map.hash.THashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.communication.composers.navigation.SendHotelViewComposer;
import net.cobem.grizzly.communication.composers.rooms.LeaveRoomComposer;
import net.cobem.grizzly.communication.composers.rooms.RemoveRoomUserEntityComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomRobotStatusesComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomRobotsComposer;
import net.cobem.grizzly.communication.composers.rooms.SendRoomUsersComposer;
import net.cobem.grizzly.habbohotel.pathfinding.AffectedTile;
import net.cobem.grizzly.habbohotel.pathfinding.IPathfinder;
import net.cobem.grizzly.habbohotel.pathfinding.Position;
import net.cobem.grizzly.habbohotel.pathfinding.ghost.Ghost_PathfinderLogic;
import net.cobem.grizzly.habbohotel.pathfinding.ihi.IHI_PathfinderLogic;
import net.cobem.grizzly.habbohotel.rooms.RoomMap;
import net.cobem.grizzly.habbohotel.rooms.actors.ActorHandler;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.rooms.items.RoomItemCycle;
import net.cobem.grizzly.habbohotel.rooms.items.WallItem;
import net.cobem.grizzly.habbohotel.rooms.models.Model;
import net.cobem.grizzly.habbohotel.rooms.robots.Robot;
import net.cobem.grizzly.habbohotel.rooms.robots.RobotHandler;
import net.cobem.grizzly.habbohotel.sessions.Session;
import net.cobem.grizzly.habbohotel.wired.WiredHandler;
import net.cobem.grizzly.storage.DatabaseResult;
import org.jboss.netty.channel.group.DefaultChannelGroup;

public class Room {

   public int ID;
   public int Owner;
   public int State;
   public String Floor;
   public String Description;
   public String Model;
   public String Title;
   public String Wallpaper;
   public String Landscape;
   public String OwnerByName;
   private THashMap<Integer, Session> Party = new THashMap();
   private List<Integer> RightsHolders = new ArrayList();
   private THashMap<Integer, Position> UserPositions = new THashMap();
   public Model mModel;
   private IPathfinder Pathfinder;
   private IPathfinder GhostPathfinder;
   private THashMap<Integer, FloorItem> FloorItems = new THashMap();
   private THashMap<Integer, WallItem> WallItems = new THashMap();
   private ActorHandler ActorHandler;
   private RobotHandler RobotHandler;
   private RoomItemCycle RoomItemCycle;
   private RoomMap RoomMap;
   private WiredHandler WiredHandler;


   public Room(ResultSet Room) {
      this.FillClass(Room);
      DatabaseResult GrabOwner = Grizzly.getStorage().query("SELECT username FROM server_users WHERE id = \'" + this.Owner + "\'");
      this.OwnerByName = GrabOwner.getString();
   }

   public Room(int ID) {
      DatabaseResult GrabRoom = Grizzly.getStorage().query("SELECT * FROM server_rooms WHERE id = \'" + ID + "\'");
      ResultSet Room = GrabRoom.getRow();
      this.FillClass(Room);
      DatabaseResult GrabOwner = Grizzly.getStorage().query("SELECT username FROM server_users WHERE id = \'" + this.Owner + "\'");
      this.OwnerByName = GrabOwner.getString();
   }

   private boolean FillClass(ResultSet Set) {
      try {
         this.ID = Set.getInt("id");
         this.Owner = Set.getInt("owner");
         this.State = Set.getInt("status");
         this.Floor = Set.getString("floor");
         this.Description = Set.getString("description");
         this.Model = Set.getString("model");
         this.Title = Set.getString("name");
         this.Wallpaper = Set.getString("wallpaper");
         this.Landscape = Set.getString("outside");
         this.Pathfinder = new IHI_PathfinderLogic();
         this.GhostPathfinder = new Ghost_PathfinderLogic();
         this.loadRightHolders();
         DatabaseResult Ex = Grizzly.getStorage().query("SELECT * FROM server_items WHERE room = \'" + this.ID + "\'");

         try {
            ResultSet Item = Ex.getTable();

            while(Item.next()) {
               if(Item.getInt("floor") == 1) {
                  this.FloorItems.put(Integer.valueOf(Item.getInt("id")), new FloorItem(Item));
               } else {
                  this.WallItems.put(Integer.valueOf(Item.getInt("id")), new WallItem(Item));
               }
            }
         } catch (SQLException var7) {
            ;
         }

         this.RoomMap = new RoomMap(this);
         this.ActorHandler = new ActorHandler(this);
         this.RobotHandler = new RobotHandler(this);
         this.RoomItemCycle = new RoomItemCycle(this);
         Iterator var4 = this.FloorItems.values().iterator();

         while(var4.hasNext()) {
            FloorItem Item1 = (FloorItem)var4.next();
            if(Item1.getBase().Width == 1 && Item1.getBase().Length == 1) {
               if(!Item1.getBase().Sitable.booleanValue() && !Item1.getBase().Walkable.booleanValue() && !Item1.getBase().Layable.booleanValue()) {
                  this.RoomMap.FurniCollisionMap[Item1.X][Item1.Y] = 0;
               }

               if(Item1.getBase().Sitable.booleanValue() || Item1.getBase().Layable.booleanValue()) {
                  this.RoomMap.FurniCollisionMap[Item1.X][Item1.Y] = 2;
               }
            } else {
               Iterator var6 = AffectedTile.GetAffectedTiles(Item1.getBase().Length, Item1.getBase().Width, Item1.X, Item1.Y, Item1.Rotation).iterator();

               while(var6.hasNext()) {
                  AffectedTile Tile = (AffectedTile)var6.next();
                  if(!Item1.getBase().Sitable.booleanValue() && !Item1.getBase().Walkable.booleanValue() && !Item1.getBase().Layable.booleanValue()) {
                     this.RoomMap.FurniCollisionMap[Tile.X][Tile.Y] = 0;
                  }

                  if(Item1.getBase().Sitable.booleanValue() || Item1.getBase().Layable.booleanValue()) {
                     this.RoomMap.FurniCollisionMap[Tile.X][Tile.Y] = 2;
                  }
               }
            }
         }

         this.WiredHandler = new WiredHandler(this);
         return true;
      } catch (SQLException var8) {
         Grizzly.write(var8.toString());
         return false;
      }
   }

   public THashMap<Integer, Session> getParty() {
      return this.Party;
   }

   public List<Integer> getRightHolders() {
      return this.RightsHolders;
   }

   public Model getModel() {
      return Grizzly.getHabboHotel().getModels().get(this.Model);
   }

   public void sendMessage(EventResponse Message) {
      DefaultChannelGroup Room = new DefaultChannelGroup();
      Iterator var4 = this.getParty().values().iterator();

      while(var4.hasNext()) {
         Session mSession = (Session)var4.next();
         Room.add(mSession.getChannel());
      }

      Room.write(Message);
   }

   public boolean loadRightHolders() {
      DatabaseResult GrabRights = Grizzly.getStorage().query("SELECT * FROM server_room_rights WHERE room = \'" + this.ID + "\'");
      ResultSet Whatever = GrabRights.getTable();

      try {
         while(Whatever.next()) {
            this.RightsHolders.add(Integer.valueOf(Whatever.getInt("user")));
         }
      } catch (SQLException var4) {
         ;
      }

      return true;
   }

   public boolean sendStatuses(Session Session) {
      Iterator var3 = this.getParty().values().iterator();

      while(var3.hasNext()) {
         Session Actor = (Session)var3.next();
         Session.sendResponse(Actor.getActor().getStatus());
      }

      return true;
   }

   public EventResponse getActorStatus() {
      return SendRoomUsersComposer.compose(this.getParty());
   }

   public EventResponse getRoomStatus() {
      EventResponse Message = new EventResponse();
      return Message;
   }

   public THashMap<Integer, Position> getPositions() {
      return this.UserPositions;
   }

   public ActorHandler getActors() {
      return this.ActorHandler;
   }

   public RobotHandler getRobots() {
      return this.RobotHandler;
   }

   public void cleanPositions() {
      ArrayList PositionsToRemove = new ArrayList();

      while(this.getPositions().entrySet().iterator().hasNext()) {
         Entry ID = (Entry)this.getPositions().entrySet().iterator().next();
         int UserID = ((Integer)ID.getKey()).intValue();
         Position Position = (Position)ID.getValue();
         Session Session = Grizzly.getHabboHotel().getSessions().getByID(UserID);
         if(Session == null) {
            PositionsToRemove.add(Integer.valueOf(UserID));
         } else if(Session.getActor().CurrentPosition != Position) {
            PositionsToRemove.remove(UserID);
         }
      }

      Iterator UserID1 = PositionsToRemove.iterator();

      while(UserID1.hasNext()) {
         int ID1 = ((Integer)UserID1.next()).intValue();
         this.getPositions().remove(Integer.valueOf(ID1));
      }

   }

   public void removeUser(Session Session, boolean LeavingHotel) {
      this.getParty().remove(Integer.valueOf(Session.getHabbo().getID()));
      this.getPositions().remove(Integer.valueOf(Session.getHabbo().getID()));
      if(Grizzly.roleplayEnabled() && LeavingHotel) {
         Session.getRoleplay().startLeaveHotel();
         Session.getActor().CurrentRoom = null;
      } else {
         if(!LeavingHotel) {
            Session.sendResponse(RemoveRoomUserEntityComposer.compose());
         }

         this.sendMessage(LeaveRoomComposer.compose(Session.getHabbo().getID()));
         if(Session.getActor().GoalRoom == 0 && Session.getChannel().isOpen()) {
            Session.sendResponse(SendHotelViewComposer.compose());
         }

         Session.getActor().CurrentRoom = null;
      }

      if(Grizzly.getConfig().get("grizzly.rooms.log").equals("1")) {
         Grizzly.write(Session.getHabbo().getUsername() + " left room " + this.ID);
      }

   }

   public void removeRobot(Robot Robot) {
      Robot.AI.leave();
      this.RobotHandler.getRobots().values().remove(Robot);
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.LeaveRoomEvent);
      Message.addString(Integer.valueOf(Robot.ID));
      this.sendMessage(Message);
   }

   public void removeAllRobots() {
      Iterator var2 = this.RobotHandler.getRobots().values().iterator();

      while(var2.hasNext()) {
         Robot Robot = (Robot)var2.next();
         this.removeRobot(Robot);
      }

   }

   public boolean refreshRobots() {
      this.sendMessage(SendRoomRobotsComposer.compose(this.RobotHandler));
      this.sendMessage(SendRoomRobotStatusesComposer.compose(this.RobotHandler));
      return true;
   }

   public boolean generateRobot(Robot Robot) {
      this.RobotHandler.add(Robot);
      this.refreshRobots();
      return true;
   }

   public boolean generateItem(int ID) {
      DatabaseResult GrabItems = Grizzly.getStorage().query("SELECT * FROM server_items WHERE id = \'" + ID + "\'");

      try {
         ResultSet Results = GrabItems.getTable();

         while(Results.next()) {
            if(Results.getInt("floor") == 1) {
               this.FloorItems.put(Integer.valueOf(ID), new FloorItem(Results));
            } else {
               this.WallItems.put(Integer.valueOf(ID), new WallItem(Results));
            }
         }
      } catch (SQLException var4) {
         ;
      }

      this.RoomMap.ReGenerateCollisionMap();
      return true;
   }

   public THashMap<Integer, FloorItem> getFloors() {
      return this.FloorItems;
   }

   public THashMap<Integer, WallItem> getWalls() {
      return this.WallItems;
   }

   public FloorItem getItemByID(int ID) {
      FloorItem Return = null;
      Iterator var4 = this.FloorItems.values().iterator();

      while(var4.hasNext()) {
         FloorItem Item = (FloorItem)var4.next();
         if(Item.ID == ID) {
            Return = Item;
         }
      }

      return Return;
   }

   public THashMap<Integer, FloorItem> getItemsByPosition(Position Position) {
      THashMap Return = new THashMap();
      Iterator var4 = this.FloorItems.values().iterator();

      while(var4.hasNext()) {
         FloorItem Item = (FloorItem)var4.next();
         if(Item.X == Position.X && Item.Y == Position.Y) {
            Return.put(Integer.valueOf(Item.ID), Item);
         }
      }

      return Return;
   }

   public FloorItem getItemByPosition(Position Position) {
      FloorItem Return = null;
      Iterator var4 = this.FloorItems.values().iterator();

      while(var4.hasNext()) {
         FloorItem Item = (FloorItem)var4.next();
         if(Item.X == Position.X && Item.Y == Position.Y) {
            Return = Item;
            break;
         }
      }

      return Return;
   }

   public FloorItem getItemByHeight(Position Position, int Key) {
      FloorItem Return = null;
      int ClassKey = 0;

      for(Iterator var6 = this.FloorItems.values().iterator(); var6.hasNext(); ++ClassKey) {
         FloorItem Item = (FloorItem)var6.next();
         if(Item.X == Position.X && Item.Y == Position.Y && ClassKey == Key) {
            Return = Item;
            break;
         }
      }

      return Return;
   }

   public FloorItem getItemForInteraction(Position Position) {
      FloorItem Return = null;
      Iterator var4 = this.getFloors().values().iterator();

      while(var4.hasNext()) {
         FloorItem Item = (FloorItem)var4.next();
         if(Item.getBase().Width == 1 && Item.getBase().Length == 1) {
            if(Item.X == Position.X && Item.Y == Position.Y) {
               Return = Item;
            }
         } else {
            Iterator var6 = AffectedTile.GetAffectedTiles(Item.getBase().Length, Item.getBase().Width, Item.X, Item.Y, Item.Rotation).iterator();

            while(var6.hasNext()) {
               AffectedTile Tile = (AffectedTile)var6.next();
               if(Tile.X == Position.X && Tile.Y == Position.Y) {
                  Return = Item;
               }
            }
         }
      }

      return Return;
   }

   public FloorItem getItemAbove(FloorItem Item) {
      FloorItem Return = null;
      float Height = Item.Height.floatValue();
      Iterator var5 = this.getItemsByPosition(new Position(Item.X, Item.Y, 0.0D)).values().iterator();

      while(var5.hasNext()) {
         FloorItem item = (FloorItem)var5.next();
         if(item.Height.floatValue() > Height) {
            Return = item;
            break;
         }
      }

      return Return;
   }

   public List<FloorItem> getItemsAbove(FloorItem Item) {
      ArrayList Return = new ArrayList();
      float Height = Item.Height.floatValue();
      Iterator var5 = this.getItemsByPosition(new Position(Item.X, Item.Y, 0.0D)).values().iterator();

      while(var5.hasNext()) {
         FloorItem item = (FloorItem)var5.next();
         if(item.Height.floatValue() > Height) {
            Return.add(item);
         }
      }

      return Return;
   }

   public double getTopHeight(Position Position, FloorItem mItem) {
      double height = 0.0D;
      Iterator var6 = this.getItemsByPosition(Position).values().iterator();

      while(var6.hasNext()) {
         FloorItem item = (FloorItem)var6.next();
         if(mItem == null || mItem.ID != item.ID) {
            if(height == 0.0D) {
               if((double)item.Height.floatValue() == 0.0D) {
                  height = (double)item.getBase().StackHeight.floatValue();
               } else {
                  height = (double)(item.Height.floatValue() + item.getBase().StackHeight.floatValue());
               }
            } else if((double)item.Height.floatValue() > height) {
               if((double)item.Height.floatValue() == 0.0D) {
                  height = (double)item.getBase().StackHeight.floatValue();
               } else {
                  height = (double)(item.Height.floatValue() + item.getBase().StackHeight.floatValue());
               }
            }
         }
      }

      return height;
   }

   public boolean nonStackableTile(Position Position) {
      Iterator var3 = this.getItemsByPosition(Position).values().iterator();

      while(var3.hasNext()) {
         FloorItem fItem = (FloorItem)var3.next();
         if(!fItem.getBase().Stackable.booleanValue()) {
            return true;
         }
      }

      return false;
   }

   public void cleanTiles() {
      ArrayList removedPositions = new ArrayList();
      Iterator var3 = this.UserPositions.values().iterator();

      Position Pos;
      while(var3.hasNext()) {
         Pos = (Position)var3.next();
         if(!this.realTileOpen(Pos)) {
            this.getMap().UserCollisionMap[Pos.X][Pos.Y] = false;
            removedPositions.add(Pos);
         }
      }

      var3 = removedPositions.iterator();

      while(var3.hasNext()) {
         Pos = (Position)var3.next();
         this.UserPositions.remove(Pos);
      }

   }

   private boolean realTileOpen(Position Pos) {
      boolean TileOpen = true;
      Iterator var4 = this.Party.values().iterator();

      while(var4.hasNext()) {
         Session mSession = (Session)var4.next();
         if(mSession.getChannel().isOpen() && mSession.getActor().CurrentPosition.X == Pos.X && mSession.getActor().CurrentPosition.Y == Pos.Y) {
            TileOpen = false;
         }
      }

      return TileOpen;
   }

   public IPathfinder getPathfinder() {
      return this.Pathfinder;
   }

   public IPathfinder getGhostPathfinder() {
      return this.GhostPathfinder;
   }

   public RoomMap getMap() {
      return this.RoomMap;
   }

   public WiredHandler getWiredUtility() {
      return this.WiredHandler;
   }

   public void process() {
      try {
         this.ActorHandler.cycle();
         this.RobotHandler.cycle();
         this.RoomItemCycle.cycle();
      } catch (Exception var3) {
         ;
      }

      Position Position;
      for(Iterator var2 = this.getPositions().values().iterator(); var2.hasNext(); this.getMap().UserCollisionMap[Position.X][Position.Y] = true) {
         Position = (Position)var2.next();
      }

      this.getMap().GenerateFurniCollisionMap();
      this.Pathfinder.ApplyCollisionMap(this.getMap().GenerateCollisionMap(), this.getMap().GenerateFurniHeightMap());
      this.cleanTiles();
   }
}
