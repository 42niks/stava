package main;

public class GetSootArgs {
	public String[] get(String[] args) {
		// correct one
		/*
		 * args[0] -> java install path (for rt.jar and jce.jar)
		 * args[1] -> whether benchmark or not
		 * args[2] -> relevant directory
		 * args[3] -> main class
		 * args[4] -> output directory
		 */

		if (args[1].contains("true") || args[1].contains("True")) {
			// this is a benchmark
			if (args[3].contains("Harness")) {
				// the benchmark is dacapo
				return dacapo(args);
			} else {
				return normal(args);
			}
		} else {
			// this is a standard application
			return normal(args);
		}
	}
	private String[] dacapo(String[] args){
		String dir = args[2] + "/out";
		String refl_log = "reflection-log:" + dir + "/refl.log";
		String cp = args[0] + "/jre/lib/rt.jar:" + args[0] + "/jre/lib/jce.jar:" + dir + ":" + args[2] + "/dacapo-9.12-MR1-bach.jar";
		String[] sootArgs = {
				// "-whole-program",
				"-app",
				"-allow-phantom-refs",
				"-keep-bytecode-offset",
				"-keep-offset",
				"-soot-classpath", cp, "-prepend-classpath",
				"-keep-line-number",
				"-main-class", args[3],
				"-process-dir", dir,
				"-p", "cg", refl_log,
				"-output-dir", args[4],
				"-output-format", "jimple",
				"-include", "org.apache.",
				"-include", "org.w3c."
		};
		return sootArgs;
	}
	private String[] normal(String[] args){
		String cp = args[0] + "/jre/lib/rt.jar:" + args[0] + "/jre/lib/jce.jar";
		String[] sootArgs = {
//				"-whole-program",
				"-app",
				"-allow-phantom-refs",
				"-keep-bytecode-offset",
				"-p","cg.spark","on",
				"-p","cg","all-reachable",
				"-keep-offset",
				"-soot-classpath", cp, "-prepend-classpath",
				"-keep-line-number",
				"-main-class", args[3],
				"-process-dir", args[2],
				"-output-dir", args[4],
				"-output-format", "jimple",
		};
		return sootArgs;
	}
}
