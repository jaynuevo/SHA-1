import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Scanner;
	
public class Digest {
	static int[] H = {0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0};
	static int[] K = {0x5A827999, 0x6ED9EBA1, 0x8F1BBCDC, 0xCA62C1D6};
	 static int A,B,C,D,E,F;
	 int j, repetition;
	static int temp;
	int num;
	 Scanner input = new Scanner(System.in);
		String string;
		int ascii;
		String binaryString, bString="", bString1, endString;
		String[] chunk = new String[16];
		static String[] w = new String [80];
		static long[] wi = new long[80];
		static int[] wInt = new int[80];
		//String digest="";
	
	public Digest() {
		String padMsg;
		String digest = null;
		
		while(true){
			H[0] = 0x67452301;
			H[1] = 0xEFCDAB89;
			H[2] = 0x98BADCFE;
			H[3] = 0x10325476;
			H[4] = 0xC3D2E1F0;

			 repetition = 0;
			 binaryString = "";
			 bString="";
			 bString1 = "";
			 endString = "";
			
		System.out.println("Enter String: (Type 'x' to exit)");
		string = input.nextLine();
		
		if (string.equals("x"))
			break;
		padMsg = padMessage(string);
		
		
		if(repetition >1){
			for(int i=0; i < repetition; i++){
				digest = words(padMsg.substring(i*512, 512*(i+1)));
		
			}
		}
		else
			digest = words(padMsg);
		System.out.println("DIGEST IS: " +digest);
		}
	}
	
	
	public String padMessage(String string){
		String pMsg;
		for(int i=0; i<string.length(); i++){
			//character to ascii
			ascii = string.charAt(i);

			//number to binary
			binaryString = Integer.toBinaryString(ascii);
			
			while(binaryString.length() != 8){
				binaryString = "0" +binaryString;
			}
		
			bString+= binaryString;
			
			
		
		}
		
		//append 1 to end
		bString1 = bString + "1";
		
		
		
		
		//num = (448 - bString1.length()) ;
		//System.out.println(num);
		num = bString1.length();
		System.out.println("num is" +num);
		
		while(true){
			if(num > 448){
				num = bString1.length() - 448;
			}
			
			else{
				break;
			}
		}
		
		if(bString1.length()<=448)
			num = (448 - bString1.length());
		
		
		
		System.out.println("NUM IS: " +num);
		System.out.println(bString1.length());
		if(bString1.length() > 448){
			for(int i=num; i<512; i++){
				bString1 += "0";
				
			}
		}
		else{
			for(int i=0; i<num; i++){
				bString1 += "0";
				
			}
		}
		
			
		System.out.println("TOTAL IS: " +bString1.length());
		
		//length of msg in binary
		endString = Integer.toBinaryString(bString.length());
		
		int length = 64-endString.length();
		
		for(int i=0; i<length; i++){
			endString = "0" +endString;
		}
		
		//add all
		pMsg = bString1 + endString;
		
		
		
		System.out.println("LENGHTHHHTHT IS: " +pMsg.length());
		
		repetition = pMsg.length()/512;
			
		System.out.println(pMsg);
		return pMsg;
		
	}
	
	private static boolean bitOf(char in) {
	    return (in == '1');
	}
	
	private static char charOf(boolean in) {
	    return (in) ? '1' : '0';
	}
	
	
	public static String words(String s){
		StringBuilder sb = new StringBuilder();
		String str = "";
		char ch;
		String dig="";
		
		for(int i=0; i <16; i++){
			w[i] = s.substring(i*32, (i*32)+32);
			System.out.println(w[i]);
		}
		
		//System.out.println(w[14]);
		
		for(int j = 16; j < 80 ; j++){
			for (int k = 0; k < w[4].length(); k++) {
							
		        str+=(charOf(bitOf(w[j-3].charAt(k)) ^ bitOf(w[j-8].charAt(k))
		                ^ bitOf(w[j-14].charAt(k)) ^ bitOf(w[j-16].charAt(k))));		        
		    }
			ch = str.charAt(0);
			str = str.substring(1);
			
			if(ch == '0')
				str = str + "0";
			
			else
				str = str + "1";
			
			w[j] = str;
			str ="";
			
			System.out.println("W " + j +"iS : " +w[j]);
		}
		
		for(int i=0; i <80; i++){
			wi[i] = Long.parseLong(w[i], 2);
			wInt[i] = (int) wi[i];
		}
		
		
		
		   A = H[0];
           B = H[1];
           C = H[2];
           D = H[3];
           E = H[4];

           for (int j = 0; j < 20; j++) {
               F = (B & C) | ((~B) & D);
               //	K = 0x5A827999;
               temp = rotateLeft(A, 5) + F + E + K[0] + wInt[j];
               System.out.println(Integer.toHexString(K[0]));
               E = D;
               D = C;
               C = rotateLeft(B, 30);
               B = A;
               A = temp;
           }
           
           for (int j = 20; j < 40; j++) {
               F = B ^ C ^ D;
               //   K = 0x6ED9EBA1;
               temp = rotateLeft(A, 5) + F + E + K[1] + wInt[j];
               System.out.println(Integer.toHexString(K[1]));
               E = D;
               D = C;
               C = rotateLeft(B, 30);
               B = A;
               A = temp;
           }

           for (int j = 40; j < 60; j++) {
               F = (B & C) | (B & D) | (C & D);
               //   K = 0x8F1BBCDC;
               temp = rotateLeft(A, 5) + F + E + K[2] + wInt[j];
               E = D;
               D = C;
               C = rotateLeft(B, 30);
               B = A;
               A = temp;
           }

           for (int j = 60; j < 80; j++) {
               F = B ^ C ^ D;
               //   K = 0xCA62C1D6;
               temp = rotateLeft(A, 5) + F + E + K[3] + wInt[j];
               E = D;
               D = C;
               C = rotateLeft(B, 30);
               B = A;
               A = temp;
           }

           H[0] += A;
           H[1] += B;
           H[2] += C;
           H[3] += D;
           H[4] += E;
		
		
           System.out.println("H0:" + Integer.toHexString(H[0]));
           System.out.println("H0:" + Integer.toHexString(H[1]));
           System.out.println("H0:" + Integer.toHexString(H[2]));
           System.out.println("H0:" + Integer.toHexString(H[3]));
           System.out.println("H0:" + Integer.toHexString(H[4]));
		dig = Integer.toHexString(H[0]) + Integer.toHexString(H[1]) + Integer.toHexString(H[2]) + Integer.toHexString(H[3]) + Integer.toHexString(H[4]);
		return dig;
		
	}
	
	   final static int rotateLeft(int a2, int bits) {
           int q = (a2 << bits) | (a2 >>> (32 - bits));
           return q;
       }
	   
	   private String intArrayToHexStr(int[] data) {
	        String output = "";
	        String tempStr = "";
	        int tempInt = 0;
	        for (int cnt = 0; cnt < data.length; cnt++) {
	 
	            tempInt = data[cnt];
	 
	            tempStr = Integer.toHexString(tempInt);
	 
	            if (tempStr.length() == 1) {
	                tempStr = "0000000" + tempStr;
	            } else if (tempStr.length() == 2) {
	                tempStr = "000000" + tempStr;
	            } else if (tempStr.length() == 3) {
	                tempStr = "00000" + tempStr;
	            } else if (tempStr.length() == 4) {
	                tempStr = "0000" + tempStr;
	            } else if (tempStr.length() == 5) {
	                tempStr = "000" + tempStr;
	            } else if (tempStr.length() == 6) {
	                tempStr = "00" + tempStr;
	            } else if (tempStr.length() == 7) {
	                tempStr = "0" + tempStr;
	            }
	            output = output + tempStr;
	        }//end for loop
	        return output;
	    }//end intArrayToHexStr

	   
	   static final String toHexString(final ByteBuffer bb) {
	        final StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < bb.limit(); i += 4) {
	            if (i % 4 == 0) {
	                sb.append('\n');
	            }
	        }
	        sb.append('\n');
	        return sb.toString();
	    }
	   
	   
	   public static void main(String[]  args){
		   new Digest();
	   }
}
