import mpi.*;


public class DiffusionCentraliser {
	
	public static int me;
	public static int size;
	public static String bufferString[] = new String[1];
    public static void main(String args[]) throws Exception 
    {
    	MPI.Init(args);
    	me = MPI.COMM_WORLD.Rank();
    	size = MPI.COMM_WORLD.Size();
     
		int numDiffuseurRecup = 2 ;
		System.out.println("appel diffCentraliser");
		diffCentraliser (numDiffuseurRecup);
    	MPI.Finalize();
		
	}
    
    public static void diffCentraliser (int numDiffuseur) 
    {
    	System.out.println("dans diffCentraliser et numDiffuseur :" +numDiffuseur);
    	
    	if(me == numDiffuseur)
    	{
    		
    	    bufferString[0] = "coucou";
    	    System.out.println("\n-->I'm <"+me+">: send " + bufferString[0] + "#####");
    	    for(int i=0; i<size; i++)
    	    {
    	    	if(i != numDiffuseur) {
    	    		MPI.COMM_WORLD.Send(bufferString, 0, 1, MPI.OBJECT, i, 99);
    	    	}	    	
    	    }
    	}
    	else
    	{
    	    Status mps = MPI.COMM_WORLD.Recv( bufferString, 0, 1, MPI.OBJECT, MPI.ANY_SOURCE, 99 );
    	    System.out.println("<--- I'm <"+me+">: receive " + bufferString[0]);
    	}
    }

	
}




