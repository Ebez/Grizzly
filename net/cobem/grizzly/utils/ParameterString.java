package net.cobem.grizzly.utils;

import java.util.ArrayList;
import java.util.List;

public class ParameterString {

   private String Pattern = "{%s}";
   private String Text;
   private List<String> History = new ArrayList();


   public ParameterString(String Value) {
      this.Text = Value;
   }

   public ParameterString(String Value, String Pattern) {
      this.Text = Value;
      this.Pattern = Pattern;
   }

   public ParameterString append(Object ToFind, Object Replace) {
      String Old = this.Pattern.replace("%s", ToFind.toString());
      this.History.add("[" + Old + " => " + Replace + "]");
      this.Text = this.Text.replace(Old, Replace.toString());
      return this;
   }

   public List<String> getHistory() {
      return this.History;
   }

   public String toString() {
      return this.Text;
   }
}
