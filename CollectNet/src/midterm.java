
public class midterm {
	public static void main(String[] args) {
		if (args[0] == "-f") {
			if(args[2] == "-q") {
				genSnippet gs= new genSnippet();
				gs.genSnippet(args[1], args[3]);
			}
		}
	}
}
