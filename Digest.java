/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Henry Elliott
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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