import mpi.MPI;
import mpi.Status;

public class DiffusionAnneau {
	
	public static int me;
	public static int size;
	public static String bufferString[] = new String[1];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MPI.Init(args);
    	me = MPI.COMM_WORLD.Rank();
    	size = MPI.COMM_WORLD.Size();
     
		int numDiffuseurRecup = 2 ;
		System.out.println("appel diffCentraliser");
		diffAnneau (numDiffuseurRecup);
    	MPI.Finalize();

	}
	
	public static void diffAnneau  (int numDiffuseur)
	{
		int size = MPI.COMM_WORLD.Size();
		if(me == 0)
		{
			MPI.COMM_WORLD.Send(bufferString, 0, 1, MPI.OBJECT, 0 , 99);
			System.out.println("\n-->I'm <"+ 1 +">: send " + bufferString[0] + "#####");
			
		    MPI.COMM_WORLD.Send(bufferString, 0, 1, MPI.OBJECT, size , 99);
		    System.out.println("\n-->I'm <"+ size +">: send " + bufferString[0] + "#####");
		}
		
		if (size/2 + 1 >= me) {

		    Status mps = MPI.COMM_WORLD.Recv( bufferString, 0, 1, MPI.OBJECT, (me - 1) % size , 99 );
		    System.out.println("<--- I'm <"+(me - 1) +">: receive " + bufferString[0]);
		    
		    if (me != (size +1)) {
		    	MPI.COMM_WORLD.Send(bufferString, 0, 1, MPI.OBJECT, me + 1 , 99);
		    	System.out.println("\n-->I'm <"+(me + 1 )+">: send " + bufferString[0] + "#####");
		    }	        
		        
		}
		
		else
		{
			Status mps = MPI.COMM_WORLD.Recv( bufferString, 0, 1, MPI.OBJECT, (me+1) % size , 99 );
			System.out.println("<--- I'm <"+ (me + 1) +">: receive " + bufferString[0]);
			if (me != me -1)
		        MPI.COMM_WORLD.Send(bufferString, 0, 1, MPI.OBJECT, me - 1 , 99);
				System.out.println("\n-->I'm <"+( me - 1 )+">: send " + bufferString[0] + "#####");
		}
		    

	}

}
