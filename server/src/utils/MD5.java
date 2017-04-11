package utils;

// Little class to hash in MD5
public class MD5 {
	public MD5(){}
	
	static public String hash(String md5){
	   try {
	        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
	        byte[] array = md.digest(md5.getBytes());
	        StringBuffer sb = new StringBuffer();
	        
	        for(int i = 0; i < array.length; ++i){
	        	sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	        }
	        
	        return sb.toString();
	   }
	   catch(java.security.NoSuchAlgorithmException e){}
	    
	   return null;
	}
}