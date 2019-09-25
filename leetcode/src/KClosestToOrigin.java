import java.util.PriorityQueue;
//973. K Closest Points to Origin
//We have a list of points on the plane.  Find the K closest points to the origin (0, 0).
//(Here, the distance between two points on a plane is the Euclidean distance.)
//You may return the answer in any order.  The answer is guaranteed to be unique (except for the order that it is in.)
public class KClosestToOrigin {
    public int[][] kClosest(int[][] points, int K) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a,b)->
                dist(a)<dist(b)?-1:(dist(a)==dist(b)?0:1));
        for(int [] point:points){
            priorityQueue.add(point);
        }
        int[][]res = new int[K][2];
        for (int i=0;i<K;i++){
            res[i] = priorityQueue.poll();
        }

        return res;
    }

    public int dist(int [] point){
        return point[0]*point[0]+point[1]*point[1];
    }

    public static void main(String[]args){
        int [][]pointss = new int[][]{{3,3},{5,-1},{-2,4}};
        KClosestToOrigin k = new KClosestToOrigin();
        int [][] points = k.kClosest(pointss,3);
        for (int[]point: points){
            for (int elem:point){
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }
}