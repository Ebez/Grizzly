package net.cobem.grizzly.encryption;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {

   public int BITLENGTH = 30;
   public BigInteger Prime;
   public BigInteger Generator;
   public BigInteger PrivateKey;
   public BigInteger PublicKey;
   public BigInteger PublicClientKey;
   public BigInteger SharedKey;
   private BigInteger zero = new BigInteger("0");


   public DiffieHellman() {
      this.InitDH();
   }

   public DiffieHellman(int b) {
      this.BITLENGTH = b;
      this.InitDH();
   }

   private void InitDH() {
      for(this.PublicKey = new BigInteger("0"); this.PublicKey.equals(this.zero); this.PublicKey = this.Generator.modPow(this.PrivateKey, this.Prime)) {
         this.Prime = BigInteger.probablePrime(this.BITLENGTH, new Random());
         this.Generator = BigInteger.probablePrime(this.BITLENGTH, new Random());
         this.PrivateKey = new BigInteger(GenerateRandomHexString(30), 16);
         if(this.Generator.intValue() > this.Prime.intValue()) {
            BigInteger temp = this.Prime;
            this.Prime = this.Generator;
            this.Generator = temp;
         }
      }

   }

   public DiffieHellman(BigInteger prime, BigInteger generator) {
      this.Prime = prime;
      this.Generator = generator;
      this.PrivateKey = new BigInteger(GenerateRandomHexString(this.BITLENGTH), 16);
      if(this.Generator.intValue() > this.Prime.intValue()) {
         BigInteger temp = this.Prime;
         this.Prime = this.Generator;
         this.Generator = temp;
      }

      this.PublicKey = this.Generator.modPow(this.PrivateKey, this.Prime);
   }

   public void GenerateSharedKey(String ckey) {
      this.PublicClientKey = new BigInteger(ckey, 10);
      this.SharedKey = this.PublicClientKey.modPow(this.PrivateKey, this.Prime);
   }

   public static String GenerateRandomHexString(int len) {
      boolean rand = false;
      String result = "";
      Random rnd = new Random();

      for(int i = 0; i < len; ++i) {
         int var5 = 1 + (int)(rnd.nextDouble() * 254.0D);
         result = result + Integer.toString(var5, 16);
      }

      return result;
   }
}
