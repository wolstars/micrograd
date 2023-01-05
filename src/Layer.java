public class Layer {
	private int inputAmount;
	private Neuron[] neurons;
	
	public Layer(int inputAmount, int outputAmount) {
		this.inputAmount = inputAmount;
		neurons = new Neuron[outputAmount];
		for (int i = 0; i < outputAmount; i++) {
			neurons[i] = new Neuron(inputAmount);
		}
	}
	
	public Value[] fire(Value[] inputs) {
		if (inputs.length != inputAmount) {
			throw new IllegalArgumentException(
					"input amount must be: " + inputAmount + ", was: " + inputs.length
			);
		}
		int len = neurons.length;
		Value[] output = new Value[len];
		for (int i = 0; i < len; i++) {
			output[i] = neurons[i].fire(inputs);
		}
		return output;
	}
}
