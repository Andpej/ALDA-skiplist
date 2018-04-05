package alda.skiplist;

import java.util.Random;

public class SkipList<T  extends Comparable<T>> {

	private Node<T> firstNode;

	public SkipList(int level) {

		firstNode = new Node<T>(null, level);
	}

	public boolean add(T data) {

		if (!contains(data)) {

			Node<T> currentNode = firstNode;
			Node<T> nodeToAdd = new Node<T>(data, setLevel());

			int level = nodeToAdd.getLevel() - 1;

			for (int i = level; i >= 0; i--) {
				while (currentNode.nextNode[i] != null && currentNode.nextNode[i].getData().compareTo(data) < 0) {
					currentNode = currentNode.nextNode[i];
				}
				nodeToAdd.nextNode[i] = currentNode.nextNode[i];
				currentNode.nextNode[i] = nodeToAdd;
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean contains(T data) {

		Node<T> currentNode = firstNode;
		int level = currentNode.getLevel() - 1;

		for (int i = level; i >= 0; i--) {
			while (currentNode.nextNode[i] != null && currentNode.nextNode[i].getData().compareTo(data) <= 0) {

				if (currentNode.nextNode[i].getData().equals(data)) {
					return true;
				} else {
					currentNode = currentNode.nextNode[i];
				}
			}
		}
		return false;
	}

	public boolean remove(T data) {

		Node<T> currentNode = firstNode;
		boolean isRemoved = false;
		int level = currentNode.getLevel() - 1;

		for (int i = level; i >= 0; i--) {
			while (currentNode.nextNode[i] != null && currentNode.nextNode[i].getData().compareTo(data) <= 0) {

				if (currentNode.nextNode[i].getData().equals(data)) {
					currentNode.nextNode[i] = currentNode.nextNode[i].nextNode[i];
					isRemoved = true;
				} else {
					currentNode = currentNode.nextNode[i];
				}
			}
		}
		return isRemoved;
	}

	public int setLevel() {

		int level = 1;
		Random rnd = new Random();

		while (level < firstNode.getLevel() && (int) (rnd.nextDouble() * 2) == 1) {
			level++;
		}
		return level;
	}

	private static class Node<T> {

		private T data;
		private int level;
		private Node<T>[] nextNode;

		public Node(T data, int level) {
			this.data = data;
			this.level = level;
			this.nextNode = (Node<T>[]) new Node[level];
		}

		public T getData() {
			return this.data;
		}

		public int getLevel() {
			return this.level;
		}
	}
}