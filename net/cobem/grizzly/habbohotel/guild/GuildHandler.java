package net.cobem.grizzly.habbohotel.guild;

import java.util.List;
import net.cobem.grizzly.habbohotel.guild.GuildPiece;
import net.cobem.grizzly.habbohotel.guild.GuildPieceHandler;

public class GuildHandler {

   private GuildPieceHandler GuildPieceHandler = new GuildPieceHandler();


   public GuildPieceHandler getGuildPieces() {
      return this.GuildPieceHandler;
   }

   public String getImage(int GuildBase, int GuildBaseColor, List<Integer> GStates) {
      List list = GStates;
      String str = "";
      boolean num = false;
      String str2 = "b";
      if(String.valueOf(GuildBase).length() >= 2) {
         str2 = str2 + GuildBase;
      } else {
         str2 = str2 + "0" + GuildBase;
      }

      str = String.valueOf(GuildBaseColor);
      if(str.length() >= 2) {
         str2 = str2 + str;
      } else if(str.length() <= 1) {
         str2 = str2 + "0" + str;
      }

      byte num2 = 0;
      if(((Integer)GStates.get(9)).intValue() != 0) {
         num2 = 4;
      } else if(((Integer)GStates.get(6)).intValue() != 0) {
         num2 = 3;
      } else if(((Integer)GStates.get(3)).intValue() != 0) {
         num2 = 2;
      } else if(((Integer)GStates.get(0)).intValue() != 0) {
         num2 = 1;
      }

      byte num3 = 0;

      for(int i = 0; i < num2; ++i) {
         str2 = str2 + "s";
         int var12 = ((Integer)list.get(num3)).intValue() - 20;
         if(String.valueOf(var12).length() >= 2) {
            str2 = str2 + var12;
         } else {
            str2 = str2 + "0" + var12;
         }

         int num5 = ((Integer)list.get(1 + num3)).intValue();
         str = String.valueOf(num5);
         if(str.length() >= 2) {
            str2 = str2 + str;
         } else if(str.length() <= 1) {
            str2 = str2 + "0" + str;
         }

         str2 = str2 + String.valueOf(list.get(2 + num3));
         switch(num3) {
         case 0:
            num3 = 3;
         case 1:
         case 2:
         case 4:
         case 5:
         default:
            break;
         case 3:
            num3 = 6;
            break;
         case 6:
            num3 = 9;
         }
      }

      return str2;
   }

   public String getHEX(int Color) {
      return ((GuildPiece)this.getGuildPieces().getThirdColors().get(Color)).FirstPiece;
   }
}
