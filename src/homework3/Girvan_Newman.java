package homework3;

import java.util.ArrayList;
import java.util.Arrays;

public class Girvan_Newman {
	
	public static ArrayList<ArrayList<ArrayList<Integer>>> newview(int[][] tp)
	{
		ArrayList<ArrayList<ArrayList<Integer>>> view=new ArrayList<ArrayList<ArrayList<Integer>>>();
		//the view contain all the iteration
		ArrayList<ArrayList<Integer>> iter=new ArrayList<ArrayList<Integer>>();
		//the view of each iteration. contain layer.
		ArrayList<Integer> layer= new ArrayList<Integer>();
		//contain nodes of each layer in one iteration.
		ArrayList<Integer> layerbuff= new ArrayList<Integer>();
		for(int i=0;i<tp.length;i++)//generate view for each node
		{
			
			iter.clear();
			layer.clear();
			layer.add(i);
			//System.out.println(i);
			iter.add(new ArrayList<Integer>(layer));
			layer.clear();
			int t=1;
			int l=0;
			//initial node candidate
					ArrayList<Integer> node=new ArrayList<Integer>();
					node.clear();
					for(int j=0;j<tp.length ;j++)
					{
						node.add(j);
					}
					node.set(i, null);
			while(t>0)//start from layer 1
			{
				
				layerbuff.clear();
				ArrayList<Integer> lay=new ArrayList<Integer>(iter.get(l));
				t=t-1;
				//System.out.println(lay);
				
				for(Integer s:lay)
				{
					
					//System.out.println(s);
					for (Integer j:node)//search the node candidate 
					{
						if(j!=null)
						{
					if (tp[s][j]==1)
					{
						layerbuff.add(j);
						//System.out.println(layerbuff);
						//update node candidate
						node.set(j,null);
					}
						}
					}
					
					
				}
				l+=1;
					if(layerbuff.iterator().hasNext())
					{
					layer= new ArrayList<Integer>(layerbuff);
					iter.add(new ArrayList<Integer>(layer));
					t=t+1;
					}
				
			}
			
			
			view.add(new ArrayList<ArrayList<Integer>>(iter));//add iteration in view
		}
		
		
		return view;
	}
	
	public static ArrayList<ArrayList<ArrayList<Integer>>> SP_number(ArrayList<ArrayList<ArrayList<Integer>>> view,int[][] tp_t)
	{
		ArrayList<ArrayList<ArrayList<Integer>>> shortest_path=new ArrayList<ArrayList<ArrayList<Integer>>>();
		ArrayList<ArrayList<Integer>> subgraph=new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> layer=new ArrayList<Integer>();
		int[] buff= new int[tp_t.length ];//store shortest path information inside.	
		int[] buff_u=new int[tp_t.length ];//used to update buff
		ArrayList<Integer> b=new ArrayList<Integer>();
		int c=0;//count shortest path
		int p=0;//count layer
		for(ArrayList<ArrayList<Integer>> s:view)
		{
			
			Arrays.fill(buff, 0);
			Arrays.fill(buff_u, 0);
			b.clear();
			
			p=0;
			
			for(ArrayList<Integer> l:s)
			{
				
				layer.clear();
				for(int i:l)
				{c=0;
					if(p==0)
					{
						layer.add(1);
						buff_u[i]=1;
					}
					else
					{
						for(int t:b)
						{
							if(tp_t[i][t]==1)
							{
								c+=buff[t];
							}
						}
						layer.add(c);
						buff_u[i]=c;
					}
					
					
				}
				//update buff
				
				buff=buff_u;
				p+=1;
				b=new ArrayList<Integer>(l);
				//System.out.println(Arrays.toString(buff));
				//System.out.println(layer);
				subgraph.add(new ArrayList<Integer> (layer));
				
			}
			
			shortest_path.add(new ArrayList<ArrayList<Integer>>(subgraph));
			subgraph.clear();
		}
		
		
		return shortest_path;
	}
	
public static double[][] cflow(ArrayList<ArrayList<ArrayList<Integer>>> view,int[][] tp_t,ArrayList<ArrayList<ArrayList<Integer>>> sp)
{
	int s=tp_t.length;
	int ne=0;//number of edge;
	for(int i=0;i<s;i++)
	{
		for(int j=0;j<s;j++)
		{
			ne+=tp_t[i][j];
			
		}
	}
	ne=ne/2;
	double[][] flow=new double[sp.size()][ne];
	double[]flow_in_node=new double[s];
	double[][] ff=new double[s][s];
	char nn='A';
	//[ab,ac,ad,bc,bd,ce,de,df,ef]
	for(int i=0;i<sp.size();i++)//iterate for every subgraph
	{//System.out.println(i);
		Arrays.fill(flow_in_node, 1);
        for(int e=0;e<s;e++)
        {
        	Arrays.fill(ff[e], 0);
        }

		
		for(int l=sp.get(i).size()-1;l>0;l--)//iterate in each layer bottom up		
		{//System.out.println(l);
			for(int n=0;n<sp.get(i).get(l).size();n++)
			{
				//System.out.println(n);
				int node=view.get(i).get(l).get(n);
				int pa=sp.get(i).get(l).get(n);
				
				
					double f=flow_in_node[node]/pa;
					for(int u=0;u<s;u++)
					{
						//System.out.println(u);
						if(view.get(i).get(l-1).contains(u)&&tp_t[node][u]==1)
						{
							ff[node][u]=f;
							flow_in_node[u]+=f;
						}
					}
				
			}
			
			}
		/*
			for(int jj=0;jj<s;jj++){
				System.out.println("a"+Arrays.toString(ff[jj]));
		}
		*/
		
		System.out.println("total flow in each nodes of the subgraph start with node "+nn+" "+Arrays.toString(flow_in_node));
		nn++;
		int c=0;
		for(int v=0;v<s;v++)
		{
			for(int u=v;u<s;u++)
			{
				if(tp_t[v][u]==1)
				{
					if(ff[u][v]==0&&ff[v][u]==0)
					{
					flow[i][c]=0;
					}
					else{
						flow[i][c]=ff[u][v]+ff[v][u];
					}
				c=c+1;	
				}
			}
		}
		
	}
	
	
	
	
	return flow;
}
	public static int[][] inv(int[][] arr)
	{   
		int a= arr.length;
		int[][] b =new int[a][a];
		for (int i=0;i<a;i++)
		{
			for(int j=0;j<a;j++)
			{
				b[j][i]=arr[i][j];
							
			}
					
		}
		return b;
	}
	
}
