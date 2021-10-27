package qut.pm.spm.ppt;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProbProcessTreeFormatterTest {

	@Test
	public void leaf() {
		ProbProcessTree la = new PPTLeafImpl("a",2.0d);
		String result = new ProbProcessTreeFormatter().textTree(la);
		String expected = "a 2.0";
		assertEquals(expected,result);
	}
	
	@Test
	public void oneLevelSequence() {
		ProbProcessTree la = new PPTLeafImpl("a",2.0d);
		ProbProcessTree lb = new PPTLeafImpl("b",2.0d);
		ProbProcessTree lc = new PPTLeafImpl("c",2.0d);
		ProbProcessTreeNode seq = new PPTNodeImpl(PPTOperator.SEQUENCE);
		seq.addChild(la);
		seq.addChild(lb);
		seq.addChild(lc);
		String result = new ProbProcessTreeFormatter().textTree(seq);
		String expected = "->  6.0\n"
				+ "  a 2.0\n"
				+ "  b 2.0\n"
				+ "  c 2.0";
		assertEquals(expected,result);
	}

	@Test
	public void twoLevelSequence() {
		ProbProcessTree la = new PPTLeafImpl("a",2.0d);
		ProbProcessTreeNode choice = new PPTNodeImpl(PPTOperator.CHOICE);
		ProbProcessTree lb = new PPTLeafImpl("b",2.0d);
		ProbProcessTree lc = new PPTLeafImpl("c",2.0d);
		ProbProcessTree ld = new PPTLeafImpl("d",2.0d);
		choice.addChild(lb);
		choice.addChild(lc);
		ProbProcessTreeNode seq = new PPTNodeImpl(PPTOperator.SEQUENCE);
		seq.addChild(la);
		seq.addChild(choice);
		seq.addChild(ld);
		String result = new ProbProcessTreeFormatter().textTree(seq);
		String expected = "->  8.0\n"
				+ "  a 2.0\n"
				+ "  \\/  4.0\n"
				+ "    b 2.0\n"
				+ "    c 2.0\n"
				+ "  d 2.0";
		assertEquals(expected,result);
	}

	
}
