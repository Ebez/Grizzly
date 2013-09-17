package net.cobem.grizzly.habbohotel.guild;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.communication.EventResponse;
import net.cobem.grizzly.communication.HeaderLibrary;
import net.cobem.grizzly.habbohotel.guild.GuildPiece;

public class GuildPieceHandler {

   private List<GuildPiece> Bases = new ArrayList();
   private List<GuildPiece> Symbols = new ArrayList();
   private List<GuildPiece> First_Color = new ArrayList();
   private List<GuildPiece> Second_Color = new ArrayList();
   private List<GuildPiece> Third_Color = new ArrayList();


   public GuildPieceHandler() {
      ResultSet Pieces = Grizzly.getStorage().query("SELECT * FROM server_guild_pieces").getTable();

      try {
         while(Pieces.next()) {
            String Type = Pieces.getString("type");
            switch(Type.hashCode()) {
            case -887523944:
               if(Type.equals("symbol")) {
                  this.Symbols.add(new GuildPiece(Pieces.getInt("id"), Pieces.getString("data_piece_one"), Pieces.getString("data_piece_two")));
               }
               break;
            case -705322732:
               if(Type.equals("first_color")) {
                  this.First_Color.add(new GuildPiece(Pieces.getInt("id"), Pieces.getString("data_piece_one"), Pieces.getString("data_piece_two")));
               }
               break;
            case -376524840:
               if(Type.equals("second_color")) {
                  this.Second_Color.add(new GuildPiece(Pieces.getInt("id"), Pieces.getString("data_piece_one"), Pieces.getString("data_piece_two")));
               }
               break;
            case 3016401:
               if(Type.equals("base")) {
                  this.Bases.add(new GuildPiece(Pieces.getInt("id"), Pieces.getString("data_piece_one"), Pieces.getString("data_piece_two")));
               }
               break;
            case 427896459:
               if(Type.equals("third_color")) {
                  this.Third_Color.add(new GuildPiece(Pieces.getInt("id"), Pieces.getString("data_piece_one"), Pieces.getString("data_piece_two")));
               }
            }
         }
      } catch (SQLException var4) {
         ;
      }

   }

   public EventResponse Serialize() {
      EventResponse Message = new EventResponse();
      Message.Initialize(HeaderLibrary.ShowGuildPiecesEvent);
      Message.addInt(Integer.valueOf(this.Bases.size()));
      Iterator var3 = this.Bases.iterator();

      GuildPiece Piece;
      while(var3.hasNext()) {
         Piece = (GuildPiece)var3.next();
         Message.addInt(Integer.valueOf(Piece.ID));
         Message.addString(Piece.FirstPiece);
         Message.addString(Piece.SecondPiece);
      }

      Message.addInt(Integer.valueOf(this.Symbols.size()));
      var3 = this.Symbols.iterator();

      while(var3.hasNext()) {
         Piece = (GuildPiece)var3.next();
         Message.addInt(Integer.valueOf(Piece.ID));
         Message.addString(Piece.FirstPiece);
         Message.addString(Piece.SecondPiece);
      }

      Message.addInt(Integer.valueOf(this.First_Color.size()));
      var3 = this.First_Color.iterator();

      while(var3.hasNext()) {
         Piece = (GuildPiece)var3.next();
         Message.addInt(Integer.valueOf(Piece.ID));
         Message.addString(Piece.FirstPiece);
      }

      Message.addInt(Integer.valueOf(this.Second_Color.size()));
      var3 = this.Second_Color.iterator();

      while(var3.hasNext()) {
         Piece = (GuildPiece)var3.next();
         Message.addInt(Integer.valueOf(Piece.ID));
         Message.addString(Piece.FirstPiece);
      }

      Message.addInt(Integer.valueOf(this.Third_Color.size()));
      var3 = this.Third_Color.iterator();

      while(var3.hasNext()) {
         Piece = (GuildPiece)var3.next();
         Message.addInt(Integer.valueOf(Piece.ID));
         Message.addString(Piece.FirstPiece);
      }

      return Message;
   }

   public List<GuildPiece> getBases() {
      return this.Bases;
   }

   public List<GuildPiece> getSymbols() {
      return this.Symbols;
   }

   public List<GuildPiece> getFirstColors() {
      return this.First_Color;
   }

   public List<GuildPiece> getSecondColors() {
      return this.Second_Color;
   }

   public List<GuildPiece> getThirdColors() {
      return this.Third_Color;
   }
}
