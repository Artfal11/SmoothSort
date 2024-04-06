package org.example;

import java.util.ArrayList;
import java.util.List;

public class SmoothSort {
    public int operations;
    // поиск Леонардовых чисел для заданного массива
    public ArrayList<Integer> LeonardoNumbers(int[] arr) {
        ArrayList<Integer> leonardoNumbers = new ArrayList<>();
        leonardoNumbers.add(1);
        leonardoNumbers.add(1);
        operations++;
        operations++;
        for (int i = 2; leonardoNumbers.get(i - 1) < arr.length; i++) {
            // каждый элемент это Ln = Ln-2 + Ln-1 + 1
            leonardoNumbers.add(leonardoNumbers.get(i - 1) + leonardoNumbers.get(i - 2) + 1);
            operations++;
        }
        return leonardoNumbers;
    }

    // смена двух значений
    public void swap(Node c1, Node c2) {
        int temp = c2.getValue();
        c2.setValue(c1.getValue());
        c1.setValue(temp);
        operations++;
    }

    // просейка внутри одного узла
    public Node InnerSift(Node root) {
        if (root != null && root.left != null) {
            Node maxNode = null;
            // проверка, что левый потомок больше корня
            // и что он не меньше правого потомка
            if (root.getLeft().getValue() > root.getValue()
                    && root.getLeft().getValue() >= root.getRight().getValue()) {
                maxNode = root.getLeft();
                operations++;
            }
            // если же правый больше, то он будет maxNode
            else if (root.getRight().getValue() > root.getValue()
                    && root.getRight().getValue() >= root.getLeft().getValue()) {
                maxNode = root.getRight();
                operations++;
            }
            // если мы смогли найти узел с большим значением, то меняем его с корнем
            // далее просейка еще вниз
            if (maxNode != null) {
                swap(maxNode, root);
                return InnerSift(maxNode);
            }
        }
        return root;
    }

    // просейка между двумя кучами
    public Node BetweenSift(Node root, Node current, Node result) {
        Node lastRoot = current.getLastRoot();
        if (lastRoot != null) {
            if (lastRoot.getValue() > result.getValue()) {
                result = lastRoot;
                operations++;
            }
            // рекурсивно идем вниз
            return BetweenSift(root, lastRoot, result);
        }
        if (root != result) {
            swap(root, result);
            InnerSift(result);
        }
        return root;
    }

    public Node getMax(Node root) {
        BetweenSift(root, root, root);
        return root;
    }

    public void setArr(int[] array, Node root, int bound) {
        // bound - сколько нам уже не нужно сортировать
        Node currentRoot = root;
        for (int i = array.length - 1; i >= array.length - bound; i--) {
            array[i] = getMax(currentRoot).getValue();
            operations++;
            if (currentRoot.getRight() != null) {
                currentRoot = currentRoot.getRight();
            } else {
                currentRoot = currentRoot.getLastRoot();
            }
        }
        for (int i = array.length - bound - 1; i >= 0; i--) {
            array[i] = currentRoot.getValue();
            if (currentRoot.getRight() != null) {
                currentRoot = currentRoot.getRight();
            } else {
                currentRoot = currentRoot.getLastRoot();
            }
        }
    }

    public void sort(int[] array, int bound) {
        if (array.length > 1) {
            List<Integer> leonardoNumbers = LeonardoNumbers(array);
            Node root1 = new Node(array[0]);
            Node root2 = new Node(array[1]);
            operations += 2;
            // связываем 2 корня
            root2.setLastRoot(root1);
            // начинаем с третьего элемента
            for (int i = 2; i < array.length; i++) {
                Node node = new Node(array[i]);
                // если 2 кучи есть и их размеры - это соседние леонардовы числа, то дополнительно связываем их через новый корень
                if (root1 != null && (Math.abs(leonardoNumbers.indexOf(root1.getSizeOfHeap()) -
                        leonardoNumbers.lastIndexOf(root2.getSizeOfHeap())) == 1)) {
                    // меняем размер кучи
                    node.setSizeOfHeap(root1.getSizeOfHeap() + root2.getSizeOfHeap() + 1);
                    node.setLeft(root1);
                    node.setRight(root2);
                    // если же все - таки было не только две кучи
                    if (root1.getLastRoot() != null) {
                        // указатель на созданную кучу
                        node.setLastRoot(root1.getLastRoot());
                        // прежний указатель на ту, которая справа
                        root1 = root1.getLastRoot();
                    }
                    else {
                        root1 = null;
                    }
                    // просеивание по новой куче
                    InnerSift(node);
                }
                else {
                    // общий корень для двух куч, если же не выполнилось условие
                    root1 = root2;
                    // создаём связь между кучами
                    node.setLastRoot(root1);
                }
                root2 = node;
                operations++;
            }
            setArr(array, root2, bound);
        }
    }

    public static class Node {
        private int value;
        private Node left;
        private Node right;
        private Node lastRoot;
        private int sizeOfHeap = 1;
        public Node(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
        public Node getLastRoot() {
            return lastRoot;
        }

        public void setLastRoot(Node lastRoot) {
            this.lastRoot = lastRoot;
        }

        public int getSizeOfHeap() {
            return sizeOfHeap;
        }

        public void setSizeOfHeap(int sizeOfHeap) {
            this.sizeOfHeap = sizeOfHeap;
        }
    }
}
