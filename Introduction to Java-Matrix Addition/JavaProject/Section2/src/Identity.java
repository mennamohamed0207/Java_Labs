
public class Identity extends Matrix{

	public Identity(int N) {
		//if (N==M)
		super (N,N);
		for (int i=0;i<N;i++)
		{
			for (int j=0;j<N;j++ )
			{
				if (i==j)
					this.mat[i][j]=1;
					
			}
			
		}
	}
	@Override
	public boolean SetNumbers(int [] arr)
	{
		if (this.getM()*this.getN()==arr.length)
		{
			int k=0;
			for (int i=0;i<this.getM();i++)
			{
				for (int j=0;j<this.getN();j++)
				{
					if ((i==j&&arr[k]==1)||(i!=j&&arr[k]==0))
					{
					k++;
					}else return false ;
				}
			}
		}else return false ;
		
		
			int k=0;
			for (int i=0;i<this.getM();i++)
			{
				for (int j=0;j<this.getN();j++)
				{
					
					mat[i][j]=arr[k];
					k++;
					
				}
			}
			return true;
		
	}
	@Override
	public void Transpose()
	{
		super.Transpose();
		System.out.println("The Matrix is the same as it is identity matrix ");
		
	}
	
//	for (int i=0;i<3;i++)
//	{
//		for (int j=0;j<2;j++)
//		{
//			mat10.mat[i][j]=2;
//			mat20.mat[i][j]=1;
//			
//		}
//	}
//	
	
	public static void main(String[] args) {
		

		//the beginning of the main 
		Matrix mat10=new Matrix(3,2);
		Matrix mat20=new Matrix(3,2);
		int [][]arr11= {{1,2},{3,4},{5,6}};
		int [][]arr12= {{2,7},{3,6},{1,2}};
		mat10.mat=arr11;
		//test for constructor and print function
		mat20.mat=arr12;
		mat20.Print();
		///////////////////////////////////////////////////////////////////
		//test for add function
	    Matrix result=(Matrix)mat10.Add(mat20);
	    if (result!=null)
	    result.Print();
	    /////////////////////////////////////////////////////////////////
	    //test for function set numbers 
	    int [] arr= {1,0,0,0,1,0,0,0,1};
	    if (result.SetNumbers(arr))
		{
			System.out.println("the array is setted correctly");
			
		}else System.out.println("the array is invalid ");
	    result.Print();
	    ////////////////////////////////////////////////////////////////
	    //test for transpose function 
	    result.Transpose();
	    result.Print();
	    ///////////////////////////////////////////////////////////////
	    //test for identity class
	    
	    Identity d1=new Identity(3);
     	d1.Print();
     	//////////////////////////////////////////////////////////////
     	//test for set numbers in identity
     	 if (d1.SetNumbers(arr))
 		{
 			System.out.println("the array is setted correctly");
 			
 		}else System.out.println("the array is invalid ");
     	 d1.Print();
     	 /////////////////////////////////////////////////////////////
     	 //test for transpose function 
        d1.Transpose();
        d1.Print();
        
	
	
	
	}
}
