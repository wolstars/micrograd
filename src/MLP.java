import java.io.Serializable;
import java.util.*;

public class MLP implements Serializable {
	private Layer[] layers;
	private final int inputAmount;
	private int time;

	public MLP(int inputAmount, int[] size) {
		this.inputAmount = inputAmount;
		int len = size.length;
		layers = new Layer[len];
		layers[0] = new Layer(inputAmount, size[0]);
		for (int i = 0; i + 1 < len; i++) {
			layers[i + 1] = new Layer(size[i], size[i + 1]);
		}
		
	}
	
	public Value fire(Value[] inputs) {
		if (inputs.length != inputAmount) {
			throw new IllegalArgumentException(
					"input amount must be: " + inputAmount + ", was: " + inputs.length
			);
		}
		int len = layers.length;
		for (int i = 0; i < len; i++) {
			inputs = layers[i].fire(inputs);
		}
		return inputs[0];
	}

	public void parameters(ArrayList<Value> list) {
		for (Layer l : layers) {
			l.parameters(list);
		}
	}

	public void iterate(Value[][] dataset, Value[] targets, int iterations, double step) {
		ArrayList<Value> list = new ArrayList<>();
		parameters(list);
		Value[] predictions = new Value[dataset.length];
		Value l;
		for (int i = 0; i < iterations; i++) {

			for (int j = 0; j < dataset.length; j++) {
				predictions[j] = fire(dataset[j]);
			}

			l = loss(targets, predictions);

			for (Value v : list) {
				v.setGradient(0);
			}

			DFS(list);
			Collections.sort(list);
			list.get(0).setGradient(1);
			for (Value v : list) {
				v.updateGradient();
			}

			for (Value v : list) {
				v.addValue(-step * v.getGradient());
			}
			System.out.println(l);

		}
	}

	public static Value loss(Value[] targets, Value[] predictions) {
		Value loss = new Value(0);
		int len = targets.length;
		Value diff;
		for (int i = 0; i < len; i++) {
			diff = targets[i].subtract(predictions[i]);
			loss = loss.add(diff.multiply(diff));
		}
		return loss;
	}

	public void DFS(ArrayList<Value> list) {
		time = 0;
		for (Value v : list) {
			v.done(false);
			v.finish(0);
		}
		for (Value v : list) {
			if (! v.isDone()) {
				DFSVisit(v);
			}
		}

	}

	public void DFSVisit(Value v) {
			time++;
			v.done(true);
			for (Value c : v.getChildren()) {
				if (! c.isDone()) {
					DFSVisit(c);
				}
			}
			time++;
			v.finish(time);
	}

}
