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
	public  ProbProcessTree createFrom(ProbProcessTree tree, double newWeight) {
		if (tree instanceof ProbProcessTreeLeaf )
			return createLeaf(tree.getLabel(),newWeight);
		if (tree instanceof PPTSilentImpl )
			return createSilent(newWeight);
		ProbProcessTreeNode node = (ProbProcessTreeNode)tree;
		return createNodeFrom(node,newWeight);
	}
	
	@Override
	public  ProbProcessTree createFrom(ProbProcessTree node) {
		return createFrom(node,node.getWeight());
	}

	@Override
	public ProbProcessTreeNode createNodeFrom(ProbProcessTreeNode node, double newWeight) {
		if (PPTOperator.PROBLOOP ==  node.getOperator()) {
			PPTLoopNode loop = (PPTLoopNode)(node);
			return createLoop(loop.getLoopRepetitions());
		}
		return createNode(((ProbProcessTreeNode)node).getOperator());
	}

	@Override
	public ProbProcessTreeNode createNodeFrom(ProbProcessTreeNode node) {
		return createNodeFrom(node,node.getWeight());
	}
	
	@Override
	public  ProbProcessTreeNode copy(ProbProcessTreeNode node) {
		if (node instanceof PPTLoopImpl ) {
			return (ProbProcessTreeNode)copyLoop(node);
		}
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
		if (ppt instanceof PPTLoopImpl ) {
			return copyLoop(ppt);
		}
		return copy(((ProbProcessTreeNode)ppt));
		
	}

	private ProbProcessTree copyLoop(ProbProcessTree ppt) {
		PPTLoopImpl loop = (PPTLoopImpl)ppt;
		ProbProcessTree child = copy(loop.getChildren().get(0));
		ProbProcessTreeNode newLoop = createLoop(loop.getLoopRepetitions());
		newLoop.addChild(child);
		return newLoop;
	}



	
}
