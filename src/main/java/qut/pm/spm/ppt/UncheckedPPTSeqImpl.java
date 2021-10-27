package qut.pm.spm.ppt;

public class UncheckedPPTSeqImpl extends PPTNodeImpl {

	public UncheckedPPTSeqImpl() {
		super(PPTOperator.SEQUENCE);
	}

	@Override
	public void addChild(ProbProcessTree child) {
		if (children.isEmpty()) {
			weight += child.getWeight();			
		}
		children.add(child);
		size += child.size();
	}
	

}
