public class tStack{

    private tNode Top = new tNode();

    private class tNode {
        private int data;
        private tNode next;

        tNode() {
            //стек всегда инициализируется при создании
            this.data = 0;
            this.next = null;
        }

        public int getData() {
            return data;
        }
        public tNode getNext() {
            return next;
        }
    }

    public void pushStack(int data) {
        tNode newTop = new tNode();
        newTop.data = data;
        newTop.next = Top;
        Top = newTop;
    }

    public int popStack() {
        tNode oldTop = Top;
        Top = Top.next;

        int bufData = oldTop.data;
        oldTop = null;
        return bufData;
    }

    public void showTopElement() {
        if (Top != null) {
            System.out.println(Top.getData());
        } else {
            System.out.println("Null");
        }
    }

    public void clear() {

    }

    public boolean stackNotEmpty() {
        return Top != null;
    }

}
