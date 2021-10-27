package qut.pm.spm.ppt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
 
public class ProbProcessTreeFactory {

	private static final int NO_ACTIVITY_TYPE = -1;
	public static final int OFFSET = PPTOperator.values().length+1;
	public static final int TAU = OFFSET;
	private static String[] labels = new String[] {};
	private static Map<String,Integer> labelMap = new HashMap<>();

	private static boolean validation = true;
	private static ProbProcessTreeFactoryVariant DELEGATE = new SafeProbProcessTreeFactory();
	
	public static void initActivityRegistry(String[] labelArray) {
		labels = labelArray;
		labelMap = new HashMap<>(labelArray.length);
		for (int i=0; i<labels.length; i++) {
			labelMap.put(labels[i],i);
		}
	}
	
	public static int getActivityType(String activity) {
		return labelMap.getOrDefault(activity,NO_ACTIVITY_TYPE);
	}

	/**
	 * Not thread-safe. Intended for configuration time.
	 * @param val
	 */
	public static void setStrictValidation(boolean val) {
		if (val == validation)
			return;
		if (val) {
			DELEGATE = new SafeProbProcessTreeFactory();
		}else {
			DELEGATE = new UnsafeProbProcessTreeFactory();
		}
	}
	
	public static ProbProcessTree createSilent(double weight) {
		return DELEGATE.createSilent(weight);
	}
	
	public static ProbProcessTreeLeaf createLeaf(String activity, double weight) {
		Integer existing = labelMap.get(activity);
		if (existing == null) {
			String[] labelArray = Arrays.copyOf(labels, labels.length+1);
			existing = labels.length;
			labelArray[existing] = activity;
			initActivityRegistry(labelArray);
		}
		return new PPTLeafImpl(activity,existing+OFFSET, weight);
	}
	
	public static ProbProcessTree createLeaf(int type, double weight) {
		int arrIndex = type-OFFSET;
		if (arrIndex == -1)
			return createSilent(weight);
		if (arrIndex < 0 || arrIndex > labels.length)
			throw new ProcessTreeConsistencyException("Invalid type:" + type);
		String activity = labels[arrIndex];
		return new PPTLeafImpl(activity,type,weight);
	}
	
	public static ProbProcessTreeNode createNode(PPTOperator operator) {
		return DELEGATE.createNode(operator);
	}
	
	public static ProbProcessTreeNode createChoice() {
		return DELEGATE.createChoice();
	}
	
	public static ProbProcessTreeNode createConcurrency() {
		return DELEGATE.createConcurrency();
	}
	
	public static ProbProcessTreeNode createLoop(double repetitions) {
		 return DELEGATE.createLoop(repetitions);
	}

	public static ProbProcessTreeNode createSequence() {
		 return DELEGATE.createSequence();
	}

	public static ProbProcessTreeNode createLoop(ProbProcessTree child, double repetitions) {
		return DELEGATE.createLoop(child,repetitions);
	}

	public static ProbProcessTree createFrom(ProbProcessTree node, double newWeight) {
		return DELEGATE.createFrom(node,newWeight);
	}

	public static ProbProcessTreeNode copy(ProbProcessTreeNode node) {
		return DELEGATE.copy(node);
	}
	
	public static ProbProcessTree copy(ProbProcessTree ppt) {
		return DELEGATE.copy(ppt);
	}
	
}
