/**
 * Created by harrychen on 17/2/22.
 */
public class RMPSD {
    private static final int INF = Integer.MAX_VALUE;
    public static void main(String[] args) {
        double[] coordinates={-1.5,1.5,-1,0,5,0,4.5,3.5,4.6,-3,0,1,2,3,4,1,4,10,0,10,4,0,2,2,0,0,0,-10,4,-10};
        int[] index={10,20,30};
        rmp newrmp=new rmp(coordinates,index);
        //newrmp.findPath(0, 2);
        System.out.println(newrmp.getShortestDistance(0,2));
        int route[]=newrmp.getShortestRoute(0, 2);
        for(int i=0;i<route.length;i++)
        {
            if(route[i]!=-1)
            {System.out.print(route[i]+" ");}
            else{break;}
        }
        System.out.println();
        double coordinate[]=newrmp.getCoordinates(11);
        for(int i=0;i<coordinate.length;i++)
        {
            System.out.print(coordinate[i]+" ");
        }
        System.out.println();
    }
}
