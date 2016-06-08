package ir.oveissi.simplevolleysample.data;

/**
 * Created by USER on 11/13/2015.
 */
public interface MyNetworkListener<T> {
   public void getResult(T result);
   public void getException(NetworkExceptionHandler error);
}
