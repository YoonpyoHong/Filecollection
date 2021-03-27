

public class kuir {
	public static void main(String[] args) {
		System.out.println(args[0]);
		
		if (args[0].equals("-c")) {
			makeCollection mc =  new makeCollection();
			mc.collection(args[1]);
			System.out.println("-c");
		}
		else if(args[0].equals("-k") ) {
			makeKeyword mk = new makeKeyword();
			mk.keyword(args[1]);
		}
		else if(args[0].equals("-i")) {
			indexer id = new indexer();
			id.index(args[1]);
		}
	}
}
