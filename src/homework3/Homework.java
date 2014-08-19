package homework3;

import java.util.ArrayList;
import java.util.Arrays;

public class Homework {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[][] tp=new int[][]{{0,1,1,1,0,0},
				               {1,0,1,1,0,0},
				               {1,1,0,0,1,0},
				               {1,1,0,0,1,1},
				               {0,0,1,1,0,1},
				               {0,0,0,1,1,0}
				
		};
		
		ArrayList<ArrayList<ArrayList<Integer>>> view=Girvan_Newman.newview(tp);
		
		System.out.println("nodes in each layer of each subgraph:"+view);
		int[][] tp_t=Girvan_Newman.inv(tp);
		ArrayList<ArrayList<ArrayList<Integer>>> sp=Girvan_Newman.SP_number(view, tp_t);
		System.out.println("number of path in each subgraph:"+sp);
		double[][] flow=Girvan_Newman.cflow(view, tp_t, sp);
		System.out.println("\n"+"betweenness of each edge: ");
		System.out.println("[ ab,  ac,  ad,  bc,  bd,  ce,  de,  df,  ef ]");
		for(int i=0;i<tp_t.length ;i++)
		{
			System.out.println(Arrays.toString(flow[i]));
		}
		double[] bt_sum=new double[flow[0].length ];
		Arrays.fill(bt_sum, 0);
		for(int i=0;i<flow[0].length ;i++)
		{
			for(int j=0;j<tp_t.length ;j++)
			{
				bt_sum[i]+=flow[j][i];
			}
			
		}
		System.out.println("\nsum of betweenness of each edge : ");
		System.out.println(Arrays.toString(bt_sum));
	}

}
