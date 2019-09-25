
public class MyPow{
    //time complexity(O(logm))
    //space O(n)
    public double myPow(double x, int n){
        if (n>0){
            return pow(x,n);
        }
        else {
            return 1.0/pow(x,-n);
        }
    }

    public double pow(double x, int n){
        if (n==0){
            return 1;
        }
        double y = pow(x, n/2);
        if (n%2==0){
            return y*y;
        }
        else {
            return y*y*x;
        }
    }

    public static void main(String[]args){
        MyPow mp  = new MyPow();
        double res = mp.myPow(4,2);
        System.out.println(res);
    }

}