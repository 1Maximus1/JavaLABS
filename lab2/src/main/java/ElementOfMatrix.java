public class ElementOfMatrix<T> implements ElementOfMatrixInface<T> {
    private T value;

    public ElementOfMatrix(T value) {
        this.value = value;
    }
    @Override
    public T getValue() {
        return value;
    }
    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
    
}