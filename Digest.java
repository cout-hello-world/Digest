import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest {
	private static String[] algorithms = {"MD5", "SHA-1", "SHA-256"};
	public static void main(String[] args) {
		if (args.length < 2 || args[0].equals("--help")) {
			printUsageAndExit();
			return;
		}
		String algorithm = args[0];
		// Main loop.
		for (int i = 1; i < args.length; i++) {
			try {
				File input = new File(args[i]);
				byte[] digest = computeDigest(input, algorithm);
				printDigest(digest, args[i]);
				System.out.println(" " + input);
			} catch (IOException ex) {
				System.err.println("Warning: Error reading file: " + args[i]);
			} catch (NoSuchAlgorithmException ex) {
				System.err.println("Unknown algorithm: " + algorithm);
				printUsageAndExit();
				return;
			}
		}
	}

	private static byte[] computeDigest(File input, String algorithm)
	        throws IOException, NoSuchAlgorithmException {
		try (DigestInputStream stream = new DigestInputStream(
		        new BufferedInputStream(new FileInputStream(input)),
		        MessageDigest.getInstance(algorithm))) {
			while (stream.read() != -1);
			MessageDigest digest = stream.getMessageDigest();
			return digest.digest();
		}
	}

	private static void printDigest(byte[] digest, String fileName) {
		for (int i = 0; i < digest.length; i++) {
			System.out.printf("%02x", digest[i]);
		}
	}

	private static void printUsageAndExit() {
		System.err.println("Usage: java Digest <function_name> <file1> file2> ...\n" +
		                   "    <function_name> options include: ");
		for (String algorithm : algorithms) {
			System.err.println("                                    " + algorithm);
		}
		System.exit(1);
	}
}