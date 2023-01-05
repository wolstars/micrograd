public class Value {
	interface Gradient {
		void run(Value current);
	}
	private double value;
	private double gradient = 0;
	private Value[] children = null;
	private String operation;
	private Gradient lambda = null;
	
	
	public Value(double value) {
		this.value = value;
	}
	
	public Value(double value, Value child1, Value child2, String operation, Gradient lambda) {
		this.value = value;
		this.children = new Value[] {child1, child2};
		this.operation = operation;
		this.lambda = lambda;
	}
	
	public Value getChild(int i) {
		if (children[i] != null) {
			return children[i];
		} else {
			return null;
		}
	}

	public double getGradient() {
		return gradient;
	}
	public double getValue() {
		return value;
	}
	
	public String toString() {
		return "" + value;
	}
	
	public void setGradient(double gradient) {
		this.gradient = gradient;
	}
	
	public void addGradient(double gradient) {
		this.gradient += gradient;
	}
	
	public void updateGradient() {
		if (lambda == null) return;
		lambda.run(this);
		for (Value v : children) {
			if (v == null) continue;
			v.updateGradient();
		}
	}
	
	public Value add(Value other) {
		Gradient lambda = (Value current) -> {
			current.getChild(0).addGradient(current.getGradient());
			current.getChild(1).addGradient(current.getGradient());
		};
		Value result = new Value(value + other.getValue(), this, other, "+", lambda);
		return result;
	}
	
	public Value subtract(Value other) {
		Gradient lambda = (Value current) -> {
			current.getChild(0).addGradient(current.getGradient());
			current.getChild(1).addGradient(-current.getGradient());
		};
		Value result = new Value(value - other.getValue(), this, other, "-", lambda);
		return result;
	}
	
	public Value multiply(Value other) {
		Value[] children = new Value[] {this, other};
		Gradient lambda = (Value current) -> {
			current.getChild(0).addGradient(current.getChild(1).getValue() * current.getGradient());
			current.getChild(1).addGradient(current.getChild(0).getValue() * current.getGradient());
		};
		return new Value(value * other.getValue(), this, other, "*", lambda);
		
	}

	public Value divide(Value other) {
		Gradient lambda = (Value current) -> {
			current.getChild(0).addGradient(Math.pow(current.getChild(1).getValue(), -1) * current.getGradient());
			current.getChild(1).addGradient(-1 * (Math.pow(current.getChild(1).getValue(), -2) * current.getChild(0).getValue()) * current.getGradient());
		};
		return new Value(value / other.getValue(), this, other, "/", lambda);
	}
	
	public Value exponent(Value other) {
		Gradient lambda = (Value current) -> {
			current.getChild(0).addGradient((current.getChild(1).getValue() * Math.pow(current.getChild(0).getValue(), current.getChild(1).getValue() - 1)) * current.getGradient());
			current.getChild(1).addGradient((Math.pow(current.getChild(0).getValue(),current.getChild(1).getValue())*Math.log(children[1].getValue())) * current.getGradient());
		};
		return new Value(Math.pow(value, other.getValue()), this, other, "^", lambda);
	}
	
	public Value tanh() {
		Gradient lambda = (Value current) -> {
			current.getChild(0).addGradient((1 - Math.pow(value, 2)) * current.getGradient());
		};
		return new Value((Math.exp(2 * value) - 1) / (Math.exp(2 * value) + 1), this, null, "tanh", lambda);
	}
	

}
