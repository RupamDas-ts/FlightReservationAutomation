package Utility;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TOTPGenerator {
  private static final String HMAC_ALGORITHM = "HmacSHA1";
  private static final int DIGITS = 6;
  private static final int TIME_STEP_SECONDS = 30;
  private static final int T0 = 0;

  public static void main(String[] args) {
    String secretKey = "4QBP Y33X XQ2K MUQ4 NIDA KDSM I3CE H4TM RTIK OPJT UHC2 2HHJ I5DA"; // Replace this with your secret key
    long currentTime = System.currentTimeMillis() / 1000;

    int otp = generateTOTP(secretKey, currentTime, TIME_STEP_SECONDS, T0, DIGITS);
    System.out.println("Generated OTP: " + otp);
  }

  private static int generateTOTP(String secretKey, long currentTime, int timeStepSeconds, int t0, int digits) {
    long counter = (currentTime - t0) / timeStepSeconds;
    byte[] counterBytes = ByteBuffer.allocate(8).putLong(counter).array();
    byte[] secretKeyBytes = Base32.decode(secretKey);

    try {
      Mac mac = Mac.getInstance(HMAC_ALGORITHM);
      mac.init(new SecretKeySpec(secretKeyBytes, HMAC_ALGORITHM));
      byte[] hash = mac.doFinal(counterBytes);

      // Generate 4 bytes OTP from hash starting at the offset
      int offset = hash[hash.length - 1] & 0xF;
      int binary = ((hash[offset] & 0x7F) << 24) |
        ((hash[offset + 1] & 0xFF) << 16) |
        ((hash[offset + 2] & 0xFF) << 8) |
        (hash[offset + 3] & 0xFF);

      int otp = binary % (int) Math.pow(10, digits);
      return otp;
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      e.printStackTrace();
      return -1;
    }
  }

  // Base32 decoder
  private static class Base32 {
    private static final String BASE32_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";

    public static byte[] decode(String encoded) {
      encoded = encoded.trim().toUpperCase();
      byte[] decoded = new byte[encoded.length() * 5 / 8];

      int buffer = 0;
      int next = 0;
      int bitsLeft = 0;
      for (char c : encoded.toCharArray()) {
        int value = BASE32_CHARS.indexOf(c);
        if (value < 0) continue;
        buffer <<= 5;
        buffer |= value & 0x1F;
        bitsLeft += 5;
        if (bitsLeft >= 8) {
          decoded[next++] = (byte) (buffer >> (bitsLeft - 8));
          bitsLeft -= 8;
        }
      }
      return decoded;
    }
  }
}

