public class utils {
	
	
	public static Value loss(Value[] predictions, Value[] results) {
		Value loss = new Value(0);
		int len = predictions.length;
		Value[] error = new Value[len];
		Value exp = new Value(2);
		for (int i = 0; i < len; i++) {
			error[i] = (predictions[i].subtract(results[i])).exponent(exp);
		}
		return exp;
	}
	
}
