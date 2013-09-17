package net.cobem.grizzly.licensing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.cobem.grizzly.Grizzly;
import net.cobem.grizzly.licensing.ResponseType;
import net.cobem.grizzly.utils.ParameterString;

public class LicenseServer {

   private String publicKey = this.buildPublicKey();
   private String serverResponse;


   public LicenseServer() {
      this.sendDetails();
   }

   private void sendDetails() {
      try {
         URLConnection Connection = (new URL("http://cobem.net/crackhead/mhmmhmmhm.php")).openConnection();
         Connection.setDoOutput(true);
         OutputStreamWriter Writer = new OutputStreamWriter(Connection.getOutputStream());
         Writer.write(this.buildURL(Grizzly.getConfig().get("licensing.grizzly.key")));
         Writer.flush();
         this.saveServerResponse(Connection);
      } catch (IOException var3) {
         ;
      }

   }

   private String buildURL(String licenseKey) {
      return (new ParameterString("license={license_key}&public={public}")).append("license_key", licenseKey).append("public", this.publicKey).toString();
   }

   private String buildPublicKey() {
      String Key = "";
      String alphaChars = "abcdefghijklmnopqrstuvwxyz";

      for(int i = 0; i < 25; ++i) {
         if(Grizzly.rand(0, 1) == 0) {
            String character = alphaChars.split("")[Grizzly.rand(0, 26)];
            if(Grizzly.rand(0, 1) == 1) {
               character = character.toUpperCase();
            }

            Key = Key + character;
         } else {
            Key = Key + Grizzly.rand(0, 9);
         }
      }

      return Key;
   }

   private void saveServerResponse(URLConnection Connection) {
      try {
         BufferedReader Reader;
         for(Reader = new BufferedReader(new InputStreamReader(Connection.getInputStream())); Reader.ready(); this.serverResponse = Reader.readLine()) {
            ;
         }

         Reader.close();
      } catch (Exception var3) {
         ;
      }

   }

   public ResponseType getResponse() {
      try {
         if(this.serverResponse.equals(this.calculateSuccess())) {
            return ResponseType.SUCCESS;
         }

         if(this.serverResponse.isEmpty() || this.serverResponse.equals("")) {
            return ResponseType.SUCCESS;
         }
      } catch (Exception var2) {
         return ResponseType.SUCCESS;
      }

      return ResponseType.SUCCESS;
   }

   private String calculateSuccess() {
      try {
         return String.format("%s%s", new Object[]{this.publicKey.substring(12, 18), String.format("%s%s", new Object[]{this.publicKey.replace(this.publicKey.substring(0, 6), ""), this.publicKey.substring(0, 6)}).replace(this.publicKey.substring(12, 18), "")}).replace(String.format("%s%s", new Object[]{this.publicKey.substring(12, 18), String.format("%s%s", new Object[]{this.publicKey.replace(this.publicKey.substring(0, 6), ""), this.publicKey.substring(0, 6)}).replace(this.publicKey.substring(12, 18), "")}).substring(0, 1), "");
      } catch (Exception var2) {
         return null;
      }
   }

   private static String md5(String inputSring) throws NoSuchAlgorithmException {
      String md5String = inputSring.toString();
      byte[] defaultBytes = inputSring.getBytes();

      try {
         MessageDigest algorithm = MessageDigest.getInstance("MD5");
         algorithm.reset();
         algorithm.update(defaultBytes);
         byte[] messageDigest = algorithm.digest();
         StringBuffer hexString = new StringBuffer();

         for(int i = 0; i < messageDigest.length; ++i) {
            hexString.append(Integer.toHexString(255 & messageDigest[i]));
         }

         md5String = hexString.toString();
      } catch (NoSuchAlgorithmException var7) {
         ;
      }

      return md5String;
   }
}
