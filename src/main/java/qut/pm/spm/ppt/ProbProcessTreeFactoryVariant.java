package qut.pm.spm.ppt;

public interface ProbProcessTreeFactoryVariant {

	ProbProcessTree copy(ProbProcessTree ppt);

	ProbProcessTreeNode copy(ProbProcessTreeNode node);

	ProbProcessTree createFrom(ProbProcessTree node, double newWeight);

	ProbProcessTreeNode createLoop(ProbProcessTree child, double repetitions);

	ProbProcessTreeNode createSequence();

	ProbProcessTreeNode createLoop(double repetitions);

	ProbProcessTreeNode createConcurrency();

	ProbProcessTreeNode createChoice();

	ProbProcessTreeNode createNode(PPTOperator operator);

	ProbProcessTree createLeaf(int type, double weight);

	ProbProcessTreeLeaf createLeaf(String activity, double weight);

	ProbProcessTree createSilent(double weight);

	
}
