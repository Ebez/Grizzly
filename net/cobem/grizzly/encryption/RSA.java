package net.cobem.grizzly.encryption;

import java.math.BigInteger;

public class RSA {

   public int Exponent;
   public BigInteger n;
   public BigInteger Private;
   public BigInteger p;
   public BigInteger q;
   public BigInteger dmp1;
   public BigInteger dmq1;
   public BigInteger coeff;
   public boolean Decryptable;
   public boolean Encryptable;
   private BigInteger Zero = new BigInteger("0");


   public RSA(BigInteger N, int E, BigInteger D, BigInteger P, BigInteger Q, BigInteger DP, BigInteger DQ, BigInteger C) {
      this.n = N;
      this.Exponent = E;
      this.Private = D;
      this.p = P;
      this.q = Q;
      this.dmp1 = DP;
      this.dmq1 = DQ;
      this.coeff = C;
      this.Encryptable = this.n != null && this.n != this.Zero && this.Exponent != 0;
      this.Decryptable = this.Encryptable && this.Private != this.Zero && this.Private != null;
   }

   public int GetBlockSize() {
      return (this.n.bitLength() + 7) / 8;
   }

   public BigInteger DoPublic(BigInteger x) {
      return this.Encryptable?x.modPow(new BigInteger(String.valueOf(this.Exponent)), this.n):this.Zero;
   }

   public String Encrypt(String text) {
      BigInteger m = new BigInteger(this.pkcs1pad2(text.getBytes(), this.GetBlockSize()));
      if(m.equals(this.Zero)) {
         return null;
      } else {
         BigInteger c = this.DoPublic(m);
         if(c.equals(this.Zero)) {
            return null;
         } else {
            String result = c.toString(16);
            return (result.length() & 1) == 0?result:"0" + result;
         }
      }
   }

   private byte[] pkcs1pad2(byte[] data, int n) {
      byte[] bytes = new byte[n];

      for(int i = data.length - 1; i >= 0 && n > 11; bytes[n] = data[i--]) {
         --n;
      }

      --n;

      for(bytes[n] = 0; n > 2; bytes[n] = 1) {
         --n;
      }

      --n;
      bytes[n] = 2;
      --n;
      bytes[n] = 0;
      return bytes;
   }

   public BigInteger DoPrivate(BigInteger x) {
      return this.Decryptable?x.modPow(this.Private, this.n):this.Zero;
   }

   public String Decrypt(String ctext) {
      BigInteger c = new BigInteger(ctext, 16);
      BigInteger m = this.DoPrivate(c);
      if(m.equals(this.Zero)) {
         return null;
      } else {
         byte[] bytes = this.pkcs1unpad2(m, this.GetBlockSize());
         return bytes == null?null:new String(bytes);
      }
   }

   private byte[] pkcs1unpad2(BigInteger src, int n) {
      byte[] bytes = src.toByteArray();

      int i;
      for(i = 0; i < bytes.length && bytes[i] == 0; ++i) {
         ;
      }

      if(bytes.length - i == n - 1 && bytes[i] <= 2) {
         ++i;

         while(bytes[i] != 0) {
            ++i;
            if(i >= bytes.length) {
               return null;
            }
         }

         byte[] out = new byte[bytes.length - i + 1];
         int p = 0;

         while(true) {
            ++i;
            if(i >= bytes.length) {
               return out;
            }

            out[p++] = bytes[i];
         }
      } else {
         return null;
      }
   }
}
