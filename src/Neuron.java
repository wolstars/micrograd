import java.io.Serializable;
import java.util.*;

public class Neuron implements Serializable {
	private final int inputAmount;
	private Value bias;
	private Value[] weights;
	
	public Neuron(int inputAmount) {
		this.inputAmount = inputAmount;
		weights = new Value[inputAmount];
		Random random = new Random();
		for (int i = 0; i < inputAmount; i++) {
			weights[i] = new Value((random.nextDouble() * 2) - 1);
		}
		bias = new Value ((random.nextDouble() * 2) - 1);
		
	}
	
	public Value fire(Value[] inputs) {
		if (inputs.length != inputAmount) {
			throw new IllegalArgumentException(
					"input amount must be: " + inputAmount + ", was: " + inputs.length
			);
		}
		Value sum = new Value(bias.getValue());
		for (int i = 0; i < inputAmount; i++) {
			sum = sum.add(inputs[i].multiply(weights[i]));
		}
		return sum.tanh();
	}

	public ArrayList<Value> parameters(ArrayList<Value> list) {
		for (Value v : weights) {
			list.add(v);
		}
		list.add(bias);
		return list;
	}
	
}
