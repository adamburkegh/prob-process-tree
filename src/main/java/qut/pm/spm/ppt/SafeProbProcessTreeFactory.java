package qut.pm.spm.ppt;

public class SafeProbProcessTreeFactory implements ProbProcessTreeFactoryVariant{

	@Override
	public  ProbProcessTree createSilent(double weight) {
		return new PPTSilentImpl(weight);
	}
	
	@Override
	public ProbProcessTreeLeaf createLeaf(String activity, double weight) {
		return ProbProcessTreeFactory.createLeaf(activity,weight);
	}
	
	@Override
	public ProbProcessTree createLeaf(int type, double weight) {
		return ProbProcessTreeFactory.createLeaf(type,weight);
	}
	
	@Override
	public  ProbProcessTreeNode createNode(PPTOperator operator) {
		switch(operator) {
		case PROBLOOP:
			throw new UnsupportedOperationException("Loops require repetition parameter");
		case SEQUENCE:
			return createSequence();
		default: 
			return new PPTNodeImpl(operator);
		}
	}
	
	@Override
	public  ProbProcessTreeNode createChoice() {
		return createNode(PPTOperator.CHOICE);
	}
	
	@Override
	public  ProbProcessTreeNode createConcurrency() {
		return createNode(PPTOperator.CONCURRENCY);
	}
	
	@Override
	public  ProbProcessTreeNode createLoop(double repetitions) {
		 return new PPTLoopImpl(repetitions);
	}

	@Override
	public  ProbProcessTreeNode createSequence() {
		 return new PPTSeqImpl();
	}

	@Override
	public  ProbProcessTreeNode createLoop(ProbProcessTree child, double repetitions) {
		ProbProcessTreeNode loop = createLoop(repetitions);
		loop.addChild(child);
		return loop;
	}

	@Override
	public  ProbProcessTree createFrom(ProbProcessTree node, double newWeight) {
		if (node instanceof ProbProcessTreeLeaf )
			return createLeaf(node.getLabel(),newWeight);
		if (node instanceof PPTSilentImpl )
			return createSilent(newWeight);
		return createNode(((ProbProcessTreeNode)node).getOperator());
	}

	@Override
	public  ProbProcessTreeNode copy(ProbProcessTreeNode node) {
		ProbProcessTreeNode result = createNode(node.getOperator());
		for (ProbProcessTree child: node.getChildren()) {
			result.addChild( copy(child) );
		}
		return result;
	}
	
	@Override
	public  ProbProcessTree copy(ProbProcessTree ppt) {
		if (ppt instanceof ProbProcessTreeLeaf )
			return createLeaf(ppt.getLabel(),ppt.getWeight());
		if (ppt instanceof PPTSilentImpl )
			return createSilent(ppt.getWeight());
		return copy(((ProbProcessTreeNode)ppt));
		
	}

	
}
