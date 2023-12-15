public class Main
{
	public static void main(String[] args)
	{
		try {
			GenericMatrix<String> stringMatrix = new GenericMatrix<>(2, 2);
			stringMatrix.setValue(1, 1, "Hello");
			stringMatrix.setValue(1, 2, "World");
			stringMatrix.setValue(2, 2, "Java");
			stringMatrix.setValue(2, 1, "nothing");
			stringMatrix.setValue(2, 2, "nothing");

			System.out.println("String Matrix:");

		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
