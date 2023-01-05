import java.util.Arrays;

public class MLP {
	private Layer[] layers;
	private int inputAmount;

	public MLP(int inputAmount, int[] size) {
		this.inputAmount = inputAmount;
		int len = size.length;
		layers = new Layer[len];
		layers[0] = new Layer(inputAmount, size[0]);
		for (int i = 0; i + 1 < len; i++) {
			layers[i + 1] = new Layer(size[i], size[i + 1]);
		}
		
	}
	
	public Value[] fire(Value[] inputs) {
		if (inputs.length != inputAmount) {
			throw new IllegalArgumentException(
					"input amount must be: " + inputAmount + ", was: " + inputs.length
			);
		}
		int len = layers.length;
		for (int i = 0; i < len; i++) {
			inputs = layers[i].fire(inputs);
		}
		return inputs;
		
	}
	
}
