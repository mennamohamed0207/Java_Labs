import mpi.*;
import java.util.*;
import java.io.*;


public class Requirement {
    public static int[] copyOfRange(int[] source, int from, int to) {
        if (from < 0 || to > source.length || from > to) {
            throw new IllegalArgumentException();
        }
        int[] destination = new int[to - from];
        for (int i = 0; i < destination.length; i++) {
            destination[i] = source[from + i];
        }
        return destination;
    }


    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int root=0;
        int [] sendbuf;
        int [] recvbuf;
        int[]A1;
        int []A2;
        int[]R;

        if(rank==root){
            //reading the file of A1.txt
            Scanner scanner = new Scanner(new File("A1.txt"));
            // scanner.useDelimiter(","); // set the delimiter to comma
            String content = scanner.next(); // read the entire file content as a string
            String[] stringArray = content.split(","); // split the string into an array of strings
            A1 = new int[stringArray.length];
            for (int i = 0; i < stringArray.length; i++) {
                A1[i] = Integer.parseInt(stringArray[i]); // parse each string into an integer
            }
            scanner.close();
            //reading the file A2.txt
            Scanner scanner2 = new Scanner(new File("A2.txt"));
            // scanner.useDelimiter(","); // set the delimiter to comma
            String content2 = scanner2.next(); // read the entire file content as a string
            String[] stringArray2 = content2.split(","); // split the string into an array of strings
            A2 = new int[stringArray2.length];
            for (int i = 0; i < stringArray2.length; i++) {
                A2[i] = Integer.parseInt(stringArray2[i]); // parse each string into an integer
            }
            scanner2.close();
            System.out.println("A2 is ");
            for (int i = 0; i < stringArray2.length; i++) {
                System.out.print(A2[i]+" ");
            }
            System.out.println();
            System.out.println("A1 is ");
            for (int i = 0; i < stringArray2.length; i++) {
                System.out.print(A1[i]+" ");
            }
            System.out.println();

            int sizeperprocess=stringArray.length/size;
            // System.out.println("size "+stringArray2.length+"process"+size);
            R = new int[stringArray.length];
            // splting the array to the remaining processes
            for (int i=1;i<size;i++)
            {
                MPI.COMM_WORLD.Send(new int[] {sizeperprocess}, 0, 1, MPI.INT, i, 0);


                int[] partA1=copyOfRange(A1,i*sizeperprocess,(i+1)*sizeperprocess);
                int[] partA2=copyOfRange(A2,i*sizeperprocess,(i+1)*sizeperprocess);
                MPI.COMM_WORLD.Send(partA1, 0, sizeperprocess, MPI.INT, i, 0);
                MPI.COMM_WORLD.Send(partA2, 0, sizeperprocess, MPI.INT, i, 0);

            }
            //take the part of the root
            int[] partA1=copyOfRange(A1,0,sizeperprocess);
            int[] partA2=copyOfRange(A2,0,sizeperprocess);
            int [] partsum=new int [stringArray.length];
            int [][] result =new int [size][sizeperprocess];
            for (int i=0;i<sizeperprocess;i++)
            {
                partsum[i]=partA1[i]+partA2[i];
                result[0][i]=partsum[i];


            }
//            for (int i=0;i<sizeperprocess;i++)
//            {
//                System.out.println(result[0][i]);
//
//
//            }
            for (int i=1;i<size;i++)
            {
                MPI.COMM_WORLD.Recv(result[i], 0, sizeperprocess, MPI.INT, i, 0);
            }
            System.out.println("Result Array:");
            for (int i = 0; i < size; i++) {
                for (int j=0;j<sizeperprocess;j++)
                    System.out.print(result[i][j] + " ");
                System.out.println();
            }








        }


        else {

            int[] sizeArray = new int[1];
            MPI.COMM_WORLD.Recv(sizeArray, 0, 1, MPI.INT, root, 0);
            int sizeperproc = sizeArray[0];


            int[]partA1 = new int[sizeperproc];
            int[]partA2  = new int[sizeperproc];
            int[]partsum  = new int[sizeperproc];

            MPI.COMM_WORLD.Recv(partA1, 0, sizeperproc, MPI.INT, root, 0);
            MPI.COMM_WORLD.Recv(partA2, 0, sizeperproc, MPI.INT, root, 0);
            for (int i=0;i<sizeperproc;i++)
            {
                partsum[i]=partA1[i]+partA2[i];
            }
            MPI.COMM_WORLD.Send(partsum, 0, sizeperproc, MPI.INT, root, 0);




        }

        MPI.Finalize();
    }
}
