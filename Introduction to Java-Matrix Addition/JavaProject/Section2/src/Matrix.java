public class Matrix implements Addable{
	//Data members
	private int N; //NO. of Columns
	private int M; //No. of Rows
	 int [][]mat;
	public int getM()
	{
		return M;
	}
	public int getN()
	{
		return N;
	}
	public void SetM(int m)
	{
		if (m>0)
			M=m;
	}
	public void SetN(int n)
	{
		if (n>0)
			N=n;
	}
	public int[][] getmat()
	{
		return mat;
	}
	public void  setmat(int [][]arr)
	{
		for (int i=0;i<this.M;i++)
		{
			for (int j=0;j<this.N;j++)
			{
				mat[i][j]=arr[i][j];
			}
		}
		
	}
	
	//Function Add
	@Override
	public Object Add (Object term2) {
		
		if (((Matrix)term2).getM()==this.M&&((Matrix)term2).getN()==this.N)
		{Matrix matresult=new Matrix(this.M,this.N);
		
		
		for (int i=0;i<this.M;i++)
		{
			for (int j=0;j<this.N;j++ )
			{
				matresult.mat[i][j]=((Matrix)term2).mat[i][j]+this.mat[i][j];
				//System.out.println(matresult.mat[i][j] +" ");
			}
		}
		
		
		
		return matresult;
		}else {System.out.println("Sizes of matrices are not the same ");
		return null;
		
		}
		
	}
	
	//Constructor with 2 inputs
	public Matrix(int rows,int cols) {
		
		this.M=rows;
		this.N=cols;
		mat=new int [rows][cols];
	}
	//Function Set Numbers 
	public boolean SetNumbers (int[]arr)
	{
		if (this.M*this.N==arr.length)
		{
			int k=0;
			for (int i=0;i<this.M;i++)
			{
				for (int j=0;j<this.N;j++)
				{
					mat[i][j]=arr[k];
					k++;
				}
			}
			return true;
		}else return false ;
		
	}
	public void Print()
	{
		System.out.println("The matrix is :");
		
		for (int i=0;i<this.M;i++)
		{
			for (int j=0;j<this.N;j++ )
			{
				System.out.print(this.mat[i][j]+" ");
			}
			System.out.println();
		}
		
}
	//Function Transpose 
	public void Transpose ()
	{
		
		Matrix out=new Matrix(this.M,this.N);
		out.mat=this.mat;
		int temp=this.M;
		this.M=this.N;
		this.N=temp;
		mat=null;
		mat=new int [this.M][this.N];
		for (int i=0;i<this.M;i++)
		{
			for (int j=0;j<this.N;j++)
			{
			    this.mat[i][j]=out.mat[j][i];
			}
			
		}
		
		
	}
	


//	public static void main(String[] args) {
//		Matrix mat10=new Matrix(3,2);
//		Matrix mat20=new Matrix(3,2);
//		
//		
//		
//		
//		for (int i=0;i<3;i++)
//		{
//			for (int j=0;j<2;j++)
//			{
//				mat10.mat[i][j]=1;
//				mat20.mat[i][j]=1;
//				
//			}
//		}
//		
//		
//		
//		
//	    Matrix result=(Matrix) mat10.Add(mat20);
//		
//		//result.Transpose();
//		result.Print();
//		
//		
//		
//	}

}