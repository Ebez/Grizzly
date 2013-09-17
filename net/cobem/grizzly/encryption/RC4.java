package net.cobem.grizzly.encryption;


public class RC4 {

   private int i = 0;
   private int j = 0;
   private int[] Table = new int[256];


   public void init(byte[] a) {
      int k = a.length;

      for(this.i = 0; this.i < 256; this.Table[this.i] = this.i++) {
         ;
      }

      this.j = 0;

      for(this.i = 0; this.i < 256; ++this.i) {
         this.j = (this.j + this.Table[this.i] + (a[this.i % k] & 255)) % 256;
         this.Swamp(this.i, this.j);
      }

      this.i = 0;
      this.j = 0;
   }

   public void parse(byte[] b) {
      for(int a = 0; a < b.length; ++a) {
         this.i = (this.i + 1) % 256;
         this.j = (this.j + this.Table[this.i]) % 256;
         this.Swamp(this.i, this.j);
         b[a] = (byte)(b[a] & 255 ^ this.Table[(this.Table[this.i] + this.Table[this.j]) % 256]);
      }

   }

   private void Swamp(int a1, int a2) {
      int k = this.Table[a1];
      this.Table[a1] = this.Table[a2];
      this.Table[a2] = k;
   }
}
