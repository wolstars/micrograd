import java.util.*;
public class Main {
	public static void main(String[] args) {
		Value a = new Value(2);
		Value b = new Value(3);
		Value c = a.exponent(b);

		Value[][] dataset = {	new Value[] {a, b, c}};/**,
		 new Value[] {b,a,c},
		 new Value[] {c, a, b}
		 };
		 */
		Value[] targets = new Value[] {new Value(-1)};//, new Value(1), new Value(1)};

		MLP mlp = new MLP(3, new int[] {3,4,1});
		mlp.iterate(dataset, targets, 10, 0.1);
//		System.out.println(mlp.fire(dataset[0]));
	}
}