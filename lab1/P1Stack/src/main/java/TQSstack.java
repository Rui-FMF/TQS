import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TQSstack<T> {
    private LinkedList<T> stack;
    private int upperBound = -1;

    public TQSstack(){
        this.stack = new LinkedList<>();
    }

    public TQSstack(int upperBound){
        this.upperBound = upperBound;
        this.stack = new LinkedList<>();
    }

    public void push(T val){
        if (this.upperBound > 0){
            if (this.stack.size() < upperBound){
                this.stack.push(val);
            } else {
                throw new IllegalStateException();
            }
        } else{
            this.stack.push(val);
        }
    }

    public T pop(){
        return stack.pop();
    }

    public T peek(){
        if (stack.size()==0){
            throw new NoSuchElementException();
        }
        return stack.peek();
    }

    public int size(){
        return stack.size();
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }
}
