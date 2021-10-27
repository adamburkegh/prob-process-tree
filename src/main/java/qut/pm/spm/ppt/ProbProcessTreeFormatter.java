package qut.pm.spm.ppt;

public class ProbProcessTreeFormatter {

	private static final int SPACES = 2;

	public String oneLine(ProbProcessTree ppt) {
		return ppt.toString();
	}
	
	public String textTree(ProbProcessTree ppt) {
		return textTreeOffset(ppt,new StringBuilder(), 0);
	}

	protected String textTreeOffset(ProbProcessTree ppt, StringBuilder builder, int offset) {
		builder.append( ppt.formatLabelForNode() + " " + ppt.getWeight());
		for (ProbProcessTree child: ppt.getChildren()) {
			nlpad( builder, offset+1);
			textTreeOffset(child,builder,offset+1);
		}
		return builder.toString();
	}

	protected void nlpad(StringBuilder builder, int offset) {
		builder.append("\n");
		pad(builder, offset);
	}

	protected void pad(StringBuilder builder, int offset) {
		for (int i=0; i<offset*SPACES; i++) {
			builder.append(" ");
		}
	}

	
}
