package qut.pm.spm.ppt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProbProcessTreeFactoryTest {

	// private static Logger LOGGER = LogManager.getLogger(); 
	
	@Test
	public void createLeaf() {
		ProbProcessTreeFactory.initActivityRegistry(new String[]{"a","b"});
		ProbProcessTreeLeaf leaf = ProbProcessTreeFactory.createLeaf("a",2.0);
		assertEquals(5, leaf.getType() );
		assertEquals("a", leaf.getLabel() );
		assertEquals(2.0, leaf.getWeight(), 0.01 );
	}

	@Test
	public void createLeafNewType() {
		ProbProcessTreeFactory.initActivityRegistry(new String[]{"a","b"});
		ProbProcessTreeLeaf leaf = ProbProcessTreeFactory.createLeaf("c",2.0);
		assertEquals(7, leaf.getType() );
		assertEquals("c", leaf.getLabel() );
		assertEquals(2.0, leaf.getWeight(), 0.01 );
	}
	
	@Test
	public void createLeafByType() {
		ProbProcessTreeFactory.initActivityRegistry(new String[]{"a","b"});
		ProbProcessTreeLeaf leaf = (ProbProcessTreeLeaf)ProbProcessTreeFactory.createLeaf(5,3.0);
		assertEquals(5, leaf.getType() );
		assertEquals(4, PPTOperator.values().length);
		assertEquals("a", leaf.getLabel() );
		assertEquals(3.0, leaf.getWeight(), 0.01 );
		leaf = (ProbProcessTreeLeaf)ProbProcessTreeFactory.createLeaf(6,3.0);
		assertEquals(6, leaf.getType() );
		assertEquals("b", leaf.getLabel() );
		assertEquals(3.0, leaf.getWeight(), 0.01 );
	}

	@Test
	public void createSilentByType() {
		ProbProcessTreeFactory.initActivityRegistry(new String[]{"a","b"});
		ProbProcessTree silent = ProbProcessTreeFactory.createLeaf(4,3.0);
		assertEquals(PPTOperator.values().length, silent.getType() );
		assertTrue(silent.isSilent() );
		assertEquals(3.0, silent.getWeight(), 0.01 );
	}

	@Test
	public void createNode() {
		ProbProcessTreeFactory.initActivityRegistry(new String[]{"a","b"});
		ProbProcessTreeNode oper = ProbProcessTreeFactory.createNode(PPTOperator.SEQUENCE);
		assertEquals(0, oper.getType() );
		assertFalse(oper.isSilent() );
		assertFalse(oper.isLeaf() );
		oper = ProbProcessTreeFactory.createNode(PPTOperator.CHOICE);
		assertEquals(1, oper.getType() );
		assertFalse(oper.isSilent() );
		assertFalse(oper.isLeaf() );
		oper = ProbProcessTreeFactory.createNode(PPTOperator.CONCURRENCY);
		assertEquals(2, oper.getType() );
		assertFalse(oper.isSilent() );
		assertFalse(oper.isLeaf() );
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void createNodeLoop() {
		ProbProcessTreeFactory.createNode(PPTOperator.PROBLOOP);
	}
	
	@Test
	public void createLoop() {
		ProbProcessTreeNode oper = ProbProcessTreeFactory.createLoop(4.3);
		assertEquals(3, oper.getType() );
		assertFalse(oper.isSilent() );
		assertFalse(oper.isLeaf() );
	}
	
	@Test
	public void copyLeaf() {
		ProbProcessTreeFactory.initActivityRegistry(new String[]{"a","b"});
		ProbProcessTreeLeaf leaf = ProbProcessTreeFactory.createLeaf("a",2.0);
		ProbProcessTree leaf2 =  ProbProcessTreeFactory.copy(leaf);
		assertEquals(leaf,leaf2);
	}
	
	@Test
	public void copyLoop() {
		ProbProcessTreeFactory.initActivityRegistry(new String[]{"a","b"});
		ProbProcessTreeNode loop = ProbProcessTreeFactory.createLoop(4.3);
		loop.addChild( ProbProcessTreeFactory.createLeaf("b",2.0) );
		ProbProcessTree loop2 =  ProbProcessTreeFactory.copy(loop);
		assertEquals(loop,loop2);
	}
}
