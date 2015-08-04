public class Digest {
	private static String[] algorithms = {"MD5", "SHA-1", "SHA-256"};
	public static void main(String[] args) {
		if (args.length < 2 || args[0].equals("--help")) {
			printUsageAndExit();
		}
		String algorithm;
		try {
			algorithm = checkAlgorithm(args[0]);
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			printUsageAndExit();
			return;
		}
	}
	
	private static String checkAlgorithm(String algorithm) {
		for (String elem : algorithms) {
			if (algorithm.equals(elem)) {
				return algorithm;
			}
		}
		throw new IllegalArgumentException("Unrecognised algorithm: " + algorithm);
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