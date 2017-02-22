/**
 * Created by harrychen on 17/2/22.
 */

import java.awt.geom.Line2D;
import static java.lang.Math.sqrt;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 *
 * @author g2arc
 */
public class rmp {
    private double[] coordinates;
    private final int[] index;
    private final double [][] mMatrix;
    private final int[][] path;
    double[][] dist;
    private final int length;
    private final int index_length;
    private static final int INF = Integer.MAX_VALUE;
    public rmp(double[] coordinates,int[] index)
    {
        this.length=coordinates.length;
        this.coordinates=new double[length];
        mMatrix=new double[length][length];
        path=new int[length][length];
        dist=new double[length][length];
        this.index_length=index.length;
        this.coordinates=coordinates;
        this.index=index;
        floyd();
    }
    public int Polygon_Intersections(double x1,double y1,double x2,double y2,int polygon_vertex_from,int polygon_vertex_to,double[] polygon_coordinates)
    {
        int intersections=0; int i;

        if (Line2D.linesIntersect(x1, y1, x2, y2, polygon_coordinates[polygon_vertex_from], polygon_coordinates[polygon_vertex_from+1], polygon_coordinates[polygon_vertex_to-1], polygon_coordinates[polygon_vertex_to]))
        {
            intersections++;
            //  System.out.println(polygon_coordinates[polygon_vertex_from]+" " +polygon_coordinates[polygon_vertex_from+1]+" "+ polygon_coordinates[polygon_vertex_to-1]+" "+ polygon_coordinates[polygon_vertex_to]);
        }
        for(i=polygon_vertex_from;i<polygon_vertex_to-2;i+=2)
        {
            if (Line2D.linesIntersect(x1, y1, x2, y2, polygon_coordinates[i], polygon_coordinates[i+1], polygon_coordinates[i+2], polygon_coordinates[i+3]))
            {
                intersections++;
//               System.out.println(polygon_coordinates[i]+" " +polygon_coordinates[i+1]+" "+ polygon_coordinates[i+2]+" "+ polygon_coordinates[i+3]);
            }
        }
        if(isIntersectionAtVerticle(x1,y1,x2,y2,polygon_vertex_from,polygon_vertex_to,polygon_coordinates)==1)
        {intersections-=2;}
        return intersections;
    }
    public int isIntersectionAtVerticle(double x1,double y1,double x2,double y2,int polygon_vertex_from,int polygon_vertex_to,double[] polygon_coordinates)
    {
        for(int i=polygon_vertex_from;i<polygon_vertex_to;i+=2)
        {
            if((polygon_coordinates[i]==x1)&&(polygon_coordinates[i+1]==y1)) {return 1;}

            if((polygon_coordinates[i]==x2)&&(polygon_coordinates[i+1]==y2)) {return 1;}
        }
        return 0;
    }

    public double distance(double x1,double y1,double x2,double y2)
    {
        return sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }
    public  int isBlocked(double x1,double y1,double x2,double y2)
    {
        if(index[0]!=-1)
        {
            for(int i=0;i<index_length-1;i++)
            {
                if (Polygon_Intersections(x1,y1,x2,y2,index[i],index[i+1]-1,coordinates)>0)
                {
                    //System.out.println("Blocked!");
                    return 1;
                }
            }
            return 0;
        }
        else

        {
            return 0;
        }
    }
    public int isEdge(double x1,double y1,double x2,double y2)
    {
        for(int i=0;i<index_length-1;i++)
        {
            for(int j=index[i];j<index[i+1]-1;j+=2)
            {
                if (j==index[i]){
                    if((x1==coordinates[j])&&(y1==coordinates[j+1])&&(x2==coordinates[index[i+1]-2])&&(y2==coordinates[index[i+1]-1]))
                    { return 1;}
                    if((x1==coordinates[j])&&(y1==coordinates[j+1])&&(x2==coordinates[j+2])&&(y2==coordinates[j+3]))
                    {return 1;}
                }
                else
                {
                    if((x1==coordinates[j])&&(y1==coordinates[j+1])&&(x2==coordinates[j+2])&&(y2==coordinates[j+3]))
                    {return 1;}
                }
            }
        }
        return 0;
    }
    public void findPath(int p1,int p2)
    {
        int point=p1;
        while(point!=p2)
        {
            System.out.print(point+"-");
            point=path[point][p2];
        }

        System.out.println(p2);
    }
    public double getShortestDistance(int p1,int p2)
    {
        return dist[p1][p2];
    }
    public int[] getShortestRoute(int p1,int p2)
    {
        int[] route=new int[length/2];
        for (int i=0;i<route.length;i++)
        {
            route[i]=-1;
        }
        route[0]=p1;int counter=1;
        int point=p1;
        while(point!=p2)
        {
            point=path[point][p2];
            route[counter]=point;
            counter++;
        }
        return route;
    }
    public double[] getCoordinates(int p1)
    {
        double[] point_coordinates=new double[2];
        point_coordinates[0]=coordinates[p1*2];
        point_coordinates[1]=coordinates[p1*2+1];
        return point_coordinates;
    }
    public void printResult(ArrayList<int[]> array,int number)
    {
        int size=array.size();
        int robotnumber=number;
        int i,j;
        int robot,move;
        int result[][]=new int[robotnumber][robotnumber];
        int arraylength[]=new int[robotnumber];
        int position;
        System.out.println(size);
        // System.out.println(robotnumber);
        for(i=0;i<robotnumber;i++)
        {
            for(j=0;j<robotnumber;j++)
            {
                result[i][j]=-1;
            }
        }
        for (i=0;i<size;i++)
        {
            robot=array.get(i)[0];
            move=array.get(i)[1];
            System.out.println(robot+" "+move);
            position=arraylength[robot];
            result[robot][position]=move;
            arraylength[robot]++;
        }
        for(i=0;i<robotnumber;i++)
        {
            for(j=0;j<robotnumber;j++)
            {
                System.out.print(result[i][j]+ " ");
            }
            System.out.println();
        }

        for(i=0;i<robotnumber;i++)
        {

            for(j=0;j<robotnumber;j++)
            {
                if (result[i][j]==-1) {break;}
                else
                {
                    if (j==0){
                        int route[]=getShortestRoute(i,result[i][0]);
//                    for(int k=0;k<route.length;k++)
//                    {
//                        System.out.print(route[k]+" ");
//                    }
//                    System.out.println();
                        // findPath(i,result[i][0]);
                        for(int k=0;k<route.length;k++)
                        {
                            if(route[k]==-1){break;}
                            if(k!=0){System.out.print(",");}
                            double coor[]=getCoordinates(route[k]);
                            double x=coor[0]; double y=coor[1];
                            System.out.print("("+x+","+y+")");

                        }
                        // if(result[i][j+1]!=-1){System.out.print(",");}
                    }
                    if(result[i][j+1]==-1)
                    {
                        break;
                    }
                    else
                    { int route[]=getShortestRoute(result[i][j],result[i][j+1]);
                       // findPath(result[i][j],result[i][j+1]);
                        for(int k=0;k<route.length;k++)
                        {
                            if(route[k]==-1){break;}
                            if(k!=0){System.out.print(",");
                                double coor[]=getCoordinates(route[k]);
                                double x=coor[0]; double y=coor[1];
                                System.out.print("("+x+","+y+")");}
                        }
                    }


                }
            }
            if(result[i][0]!=-1) System.out.print(";");
        }
        System.out.println();
    }

    private void floyd() {


        int numOfPoints=coordinates.length/2;
        //Not in use at the moment
//   int points[]=new int[numOfPoints];
//    for (int i=0;i<numOfPoints;i++)
//    {
//        points[i]=i;
//    }
        //Initialization
        for (int i =0;i<numOfPoints;i++){
            for(int j=i+1;j<numOfPoints;j++){
                if(isBlocked(coordinates[i*2],coordinates[i*2+1],coordinates[j*2],coordinates[j*2+1])==1&&isEdge(coordinates[i*2],coordinates[i*2+1],coordinates[j*2],coordinates[j*2+1])==0)
                {
                    mMatrix[i][j]=INF;
                    mMatrix[j][i]=INF;
                }
                else
                {
                    mMatrix[i][j]=distance(coordinates[i*2],coordinates[i*2+1],coordinates[j*2],coordinates[j*2+1]);
                    mMatrix[j][i]=distance(coordinates[i*2],coordinates[i*2+1],coordinates[j*2],coordinates[j*2+1]);
                }
            }
        }
        //Output the distance of two points without any obstacle present
//    for (int i=0;i<numOfPoints;i++)
//    {
//        for(int j=0;j<numOfPoints;j++)
//        {
//         if(mMatrix[i][j]!=INF)
//         {System.out.println("From "+i+ " to "+j+" is "+mMatrix[i][j]);}
//            //System.out.println("If not blocked, distance is "+ distance(coordinates[i*2],coordinates[i*2+1],coordinates[j*2],coordinates[j*2+1]));
//        }
//    }

        for (int i = 0; i <numOfPoints; i++) {
            for (int j = 0;j < numOfPoints; j++) {
                dist[i][j] = mMatrix[i][j];
                if(dist[i][j]!=INF)
                {
                    path[i][j] = j;

                }
                else {
                    path[i][j]=-1;
                }
            }
        }

        // S
        for (int k = 0; k < numOfPoints; k++) {
            for (int i = 0; i < numOfPoints; i++) {
                for (int j = 0; j < numOfPoints; j++) {

                    double tmp=dist[i][k]+dist[k][j];
                    if (dist[i][j] > tmp) {

                        dist[i][j] = tmp;
                        path[i][j] = path[i][k];
                    }
                }
            }
        }
//   System.out.printf("floyd: \n");
//    for (int i = 0; i < numOfPoints; i++) {
//        for (int j = 0; j <numOfPoints; j++)
//             System.out.println("From "+i+" to "+j+" is "+ dist[i][j]);
//        System.out.printf("\n");
//    }
//    for (int i=0; i<numOfPoints;i++)
//    {
//        for(int j=0;j<numOfPoints;j++)
//        {
//            System.out.print(path[i][j]+" ");
//        }
//        System.out.println();
//    }
       // System.out.println("if it is blocked?"+isBlocked(2,0,4,1));
       // System.out.println("if it is blocked?"+isBlocked(4,1,6,2));
    }
}
