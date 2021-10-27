package qut.pm.spm.ppt;

public class PPTLoopImpl extends PPTNodeImpl {

	private double repetitions;
	
	public PPTLoopImpl(double repetitions) {
		super(PPTOperator.PROBLOOP);
		this.repetitions = repetitions;
	}

	@Override
	public void addChild(ProbProcessTree child) {
		if (children.isEmpty()) {
			super.addChild(child);
			return;
		}
		throw new ProcessTreeConsistencyException("Loops may only have one child");
	}
	
	public double getLoopRepetitions() {
		return repetitions;
	}

	@Override
	public String formatLabelForNode() {
		return operator.toString() + " " + getLabel() + "[r" + repetitions + "]";
	}
	
}
