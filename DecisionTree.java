/**
 * This class enables the construction of a decision tree
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawa
 * @author Guy-Vincent Jourdan, University of Ottawa
 *
 */

public class DecisionTree {

	private static class Node<E> {
		E data;
		Node<E>[] children;

		Node(E data) {
			this.data = data;
		}
	}

	Node<VirtualDataSet> root;

	/**
	 * @param data is the training set (instance of ActualDataSet) over which a
	 *             decision tree is to be built
	 */
	public DecisionTree(ActualDataSet data) {
		root = new Node<VirtualDataSet>(data.toVirtual());
		build(root);
	}

	/**
	 * The recursive tree building function
	 * 
	 * @param node is the tree node for which a (sub)tree is to be built
	 */
	@SuppressWarnings("unchecked")
	private void build(Node<VirtualDataSet> node) {
		// WRITE YOUR CODE HERE!

		//edge case 1.1: null
		if(node == null || node.data == null){
			throw new NullPointerException();
		}
		//edge case 1.2: no attribute
		if(node.data.getNumberOfAttributes()==0){
			throw new IllegalArgumentException();
		}
		//edge case 1.3 no datapoint
		if(node.data.getNumberOfDatapoints()==0){
			throw new IllegalArgumentException();
		}
		VirtualDataSet dataSet = node.data;
		//base case 2.1: one attribute
		if(dataSet.getNumberOfAttributes()==1){
			return;
		}
		//base case 2.2: unique value
		if(dataSet.getAttribute(dataSet.getNumberOfAttributes()-1).getValues().length ==1){
			return;
		}
		//base case 2.3: all attribute(except) has only one value
		boolean split = false;
		for(int i=0; i< dataSet.getNumberOfAttributes()-1;i++){
			if(dataSet.getAttribute(i).getValues().length!=1) {
				split = true;
				break;
			}
		}
		if(split == false){
			return;
		}
		//recursive part 3.1 get a_max
		GainInfoItem a_max = InformationGainCalculator.calculateAndSortInformationGains(dataSet)[0];

		//recursive part 3.2 splitting
		int index = dataSet.getAttributeIndex(a_max.getAttributeName());
		VirtualDataSet[] partitions = null;
		AttributeType type = a_max.getAttributeType();
		if(type == AttributeType.NOMINAL){
			partitions = dataSet.partitionByNominallAttribute(index);
		}
		else{
			int valueindex = -1;
			String[] values = dataSet.getAttribute(a_max.getAttributeName()).getValues();
			for(int i = 0;i< values.length;i++){
				if(values[i].equals(a_max.getSplitAt())){
					valueindex = i;
					break;
				}
			}
			partitions = dataSet.partitionByNumericAttribute(index,valueindex);
		}
		//recursive part 3.3 & 3.4 instantiate
		node.children = new Node[partitions.length];
		for (int i = 0;i< partitions.length;i++){
			node.children[i] = new Node<>(partitions[i]);
		}
		//recursive part 3.5 build
		for (int i = 0;i< node.children.length;i++){
			build(node.children[i]);
		}
	}

	@Override
	public String toString() {
		return toString(root, 0);
	}

	/**
	 * The recursive toString function
	 * 
	 * @param node        is the tree node for which an if-else representation is to
	 *                    be derived
	 * @param indentDepth is the number of indenting spaces to be added to the
	 *                    representation
	 * @return an if-else representation of node
	 */
	private String toString(Node<VirtualDataSet> node, int indentDepth) {
		// WRITE YOUR CODE HERE!
		StringBuffer buffer = new StringBuffer();

		for (int i = 0;i<node.children.length;i++){
			Node<VirtualDataSet> current = node.children[i];
			VirtualDataSet now = node.children[i].data;
			buffer.append(createIndent(indentDepth));
			if(i==0){
				buffer.append("if(").append(now.getCondition()).append(") {").append(System.lineSeparator());
			}else{
				buffer.append("else if(").append(now.getCondition()).append(") {").append(System.lineSeparator());
			}
			if(current.children == null){
				Attribute attribute = now.getAttribute(now.getNumberOfAttributes()-1);
				buffer.append(createIndent(indentDepth+1));
				buffer.append(attribute.getName()).append(" = ").append(attribute.getValues()[0]).append(System.lineSeparator());
			}else{
				//recursive for children
				buffer.append(toString(current,indentDepth+1));
			}
			buffer.append(createIndent(indentDepth)).append("}").append(System.lineSeparator());
		}
		// Remove the following line once you have implemented the method
		return buffer.toString();
	}

	/**
	 * @param indentDepth is the depth of the indentation
	 * @return a string containing indentDepth spaces; the returned string (composed
	 *         of only spaces) will be used as a prefix by the recursive toString
	 *         method
	 */
	private static String createIndent(int indentDepth) {
		if (indentDepth <= 0) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < indentDepth; i++) {
			buffer.append(' ');
		}
		return buffer.toString();
	}

	public static void main(String[] args) throws Exception {
	
		StudentInfo.display();

		if (args == null || args.length == 0) {
			System.out.println("Expected a file name as argument!");
			System.out.println("Usage: java DecisionTree <file name>");
			return;
		}

		String strFilename = args[0];

		ActualDataSet data = new ActualDataSet(new CSVReader(strFilename));

		DecisionTree dtree = new DecisionTree(data);

		System.out.println(dtree);
	}
}