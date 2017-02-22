import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by harrychen on 17/2/21.
 */
public class Test {
    public static void main(String[] args){
        ArrayList test=new ArrayList();
        int my1[] =new int[2];
        my1[0]=1;
        my1[1]=2;
        test.add(my1);
        int temp=((int[])test.get(0))[1];
        System.out.println(temp);
        my1[0]=3;
        my1[1]=4;
        test.add(my1);
        System.out.println(((int[])test.get(1))[0]);

    }
}
