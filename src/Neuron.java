import java.util.Random;

public class Neuron {
	private int inputAmount;
	private double bias;
	private Value[] weights;
	
	public Neuron(int inputAmount) {
		this.inputAmount = inputAmount;
		weights = new Value[inputAmount];
		Random random = new Random();
		for (int i = 0; i < inputAmount; i++) {
			weights[i] = new Value((random.nextDouble() * 2) - 1);
		}
		bias = (random.nextDouble() * 2) - 1;
		
	}
	
	public Value fire(Value[] inputs) {
		if (inputs.length != inputAmount) {
			throw new IllegalArgumentException(
					"input amount must be: " + inputAmount + ", was: " + inputs.length
			);
		}
		Value sum = new Value(bias);
		for (int i = 0; i < inputAmount; i++) {
			sum = sum.add(inputs[i].multiply(weights[i]));
		}
		return sum.tanh();
	}
	
	
}
