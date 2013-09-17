package net.cobem.grizzly.habbohotel.games.battleball;

import java.util.List;
import net.cobem.grizzly.habbohotel.games.battleball.BattleBall_TeamType;
import net.cobem.grizzly.habbohotel.rooms.items.FloorItem;
import net.cobem.grizzly.habbohotel.sessions.Session;

public class BattleBall_TeamLogic {

   private BattleBall_TeamType Color;
   private List<Session> Players;
   private int Score;
   private FloorItem Scoreboard;


   public BattleBall_TeamLogic(BattleBall_TeamType Color) {
      this.Color = Color;
   }

   public BattleBall_TeamType getColor() {
      return this.Color;
   }

   public List<Session> getPlayers() {
      return this.Players;
   }

   public Integer getScore() {
      return Integer.valueOf(this.Score);
   }

   public FloorItem getScoreboard() {
      return this.Scoreboard;
   }

   public boolean addPlayer(Session Player) {
      if(this.getPlayers().size() == 4) {
         return false;
      } else {
         this.getPlayers().add(Player);
         return true;
      }
   }

   public boolean setScoreboard(FloorItem Item) {
      this.Scoreboard = Item;
      return true;
   }

   public boolean setScore(int Score, boolean ReturnIfWon) {
      this.Score = Score;
      return ReturnIfWon?this.Score >= 400:true;
   }

   public boolean updateScore() {
      this.getScoreboard().ExtraData = Integer.toString(this.Score);
      return true;
   }

   public boolean hasPlayer(Session Player) {
      return this.getPlayers().contains(Player);
   }
}
