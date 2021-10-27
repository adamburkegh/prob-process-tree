package qut.pm.spm.ppt;

public class UnsafeProbProcessTreeFactory extends SafeProbProcessTreeFactory{

	@Override
	public  ProbProcessTreeNode createSequence() {
		 return new UncheckedPPTSeqImpl();
	}

}
