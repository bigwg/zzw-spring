package com.zzw.trta.arithmetic;

import java.util.*;

/**
 * 带虚拟节点的一致性Hash算法
 *
 * @author zhaozhiwe
 */
public class ConsistentHash {
    /**
     * 待添加入Hash环的服务器列表
     */
    private static String[] servers = {"192.168.0.0", "192.168.0.1", "192.168.0.2"};

    /**
     * 真实结点列表,考虑到服务器上线、下线的场景，即添加、删除的场景会比较频繁，这里使用LinkedList会更好
     */
    private static List<String> realNodes = new LinkedList<>();

    /**
     * 虚拟节点，key表示虚拟节点的hash值，value表示虚拟节点的名称
     */
    private static TreeMap<Integer, String> virtualNodes = new TreeMap<>();

    /**
     * 虚拟节点的数目，这里写死，为了演示需要，一个真实结点对应5个虚拟节点
     */
    private static final int VIRTUAL_NODES = 200;

    static {
        // 先把原始的服务器添加到真实结点列表中
        realNodes.addAll(Arrays.asList(servers));
        // 再添加虚拟节点，遍历LinkedList使用foreach循环效率会比较高
        for (String str : realNodes) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                String virtualNodeName = str + "&&VN" + i;
                int hash = getHash(virtualNodeName);
                virtualNodes.put(hash, virtualNodeName);
            }
        }
    }

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
     */
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    /**
     * 得到应当路由到的结点
     */
    private static String getServer(String node) {
        // 得到带路由的结点的Hash值
        int hash = getHash(node);
        // 得到大于该Hash值的hash
        Map.Entry<Integer, String> serverEntry = virtualNodes.higherEntry(hash);
        // 第一个Key就是顺时针过去离node最近的那个结点
        String virtualNode;
        if (serverEntry != null) {
            virtualNode = serverEntry.getValue();
        } else {
            virtualNode = virtualNodes.firstEntry().getValue();
        }
        // 返回对应的虚拟节点名称，这里字符串稍微截取一下
        return virtualNode.substring(0, virtualNode.indexOf("&&"));
    }

    public static void distributeTest() {
        String[] nodes = {"127.0.0.1", "221.226.0.1", "10.211.0.1", "222.168.192.5", "192.168.130.11"};
        Map<String, Integer> serverHit = new HashMap<>(10);
        int testSize = 50000;
        for (String node : nodes) {
            for (int i = 0; i < testSize; i++) {
                String testNode = node + i;
                String server = getServer(testNode);
                if (serverHit.containsKey(server)) {
                    serverHit.put(server, serverHit.get(server) + 1);
                } else {
                    serverHit.put(server, 1);
                }
            }
        }
        Set<Map.Entry<String, Integer>> entries = serverHit.entrySet();
        for (Map.Entry entry : entries) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    public static void serverDistributeTest() {
        List<String> nodes = Arrays.asList("192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111",
                "192.168.0.3:111", "192.168.0.4:111");
        List<Integer> virtualNodeHashs = new ArrayList<>();
        int virtualNodeNum = 200;
        String virtualString = "&&VN";
        for (String node : nodes) {
            for (int i = 0; i < virtualNodeNum; i++) {
                String virtualNodeNo = node + virtualString + i;
                Integer virtualNodeHash = getHash(virtualNodeNo);
                System.out.println(virtualNodeHash);
                virtualNodeHashs.add(virtualNodeHash);
            }
        }
        virtualNodeHashs.sort(Comparator.comparingInt(value -> value));
        System.out.println(Integer.MAX_VALUE);
        System.out.println("虚拟节点大小=" + virtualNodeHashs.size());
        System.out.println("第一个节点hash值=" + virtualNodeHashs.get(0));
        System.out.println("最后一个节点hash值=" + virtualNodeHashs.get(virtualNodeHashs.size() - 1));
        System.out.println("物理节点跨度=" + (virtualNodeHashs.get(virtualNodeHashs.size() - 1) - virtualNodeHashs.get(0)));
    }

    public static void simpleTest() {
        String[] nodes = {"127.0.0.1", "221.226.0.1", "10.211.0.1", "222.168.192.5", "192.168.130.11"};
        for (String node : nodes) {
            System.out.println("[" + node + "]的hash值为" +
                    getHash(node) + ", 被路由到结点[" + getServer(node) + "]");
        }
    }

    public static void main(String[] args) {
//        simpleTest();
//        distributeTest();
        serverDistributeTest();
    }
}