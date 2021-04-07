import java.util.*;
import java.util.stream.Collectors;

/**
 * 1. 用全部N(N<=10)个0-9的数字组成一个“有效”整数（没有前置0的整数），求这些组成的数中能被K（0<K<10^10）整除的最小数字
 * 2. 输出满足条件的最小的数（不含前置0），如果没有满足条件的数输出 -1。
 *
 * @Author jiang.he
 * @Version 1.0.0 RELEASE
 * @Date 2021/4/7 19:17
 */
public class Leecode1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<String> in = new ArrayList<>();
        Set<String> set = new HashSet<>();
        long k = input(input, in);
        getNumberSet(in, set, "");
        Long res = getResult(set, k);
        if (res == null) {
            res = -1L;
        }
        System.out.println(res);
    }

    /**
     * 将String转换为Long，并且获取被K(0<K<10^10)整除的最小数字
     *
     * @param set set
     * @param k   k
     * @return 结果
     */
    private static Long getResult(Set<String> set, long k) {
        Set<Long> collect = set.stream().map(Long::parseLong).sorted(Comparator.comparingLong(a -> a)).collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println(collect);
        for (Long item : collect) {
            if (item % k == 0) {
                return item;
            }
        }
        return null;
    }

    /**
     * 实现用户的输入
     *
     * @param input input
     * @param in    in
     * @return K
     */
    private static long input(Scanner input, List<String> in) {
        int n;
        do {
            System.out.print("请输入N (0<N<=10)：");
            n = input.nextInt();
        } while ((n > 10 || n <= 0));
        long k;
        do {
            System.out.print("请输入K (0<K<10^10)：");
            k = input.nextLong();
        } while (k <= 0 || !(k < Math.pow(10, 10)));
        System.out.println("------------------分割线--------------------");
        for (int i = 0; i < n; i++) {
            System.out.print("请输入第" + (i + 1) + "个数：");
            in.add(input.next());
        }
        return k;
    }

    /**
     * 使用递归来实现一个数字一个数字地拼接，最终生成我们需要的数
     *
     * @param in  传入的单个数字的集合
     * @param set 将最终的结果添加到set中
     * @param pre 拼接一个一个的字符，最终组合成我们需要的数
     */
    private static void getNumberSet(List<String> in, Set<String> set, String pre) {
        String item;
        String res = null;
        //定义一个暂时对象，用于接收一下in，并且将之排除掉已经使用的值后，传入到下一个递归
        List<String> temp = null;
        for (int i = 0; i < in.size(); i++) {
            item = in.get(i);
            //生成的数第一位不能为0
            if (pre.equals("") && item.equals("0")) {
                continue;
            }
            res = pre + item;
            //这里写得不好，如果有很多次循环，就会重新创建很多个对象，有点耗性能，得优化
            temp = new ArrayList<>(in);
            temp.remove(i);
            //当temp不等于0的时候，才传入下一个递归，否则就是递归的终点，不进入下一次递归
            if (temp.size() != 0) {
                getNumberSet(temp, set, res);
            }
        }
        //这里也是只有在递归终点的时候，才会将值放入set里面
        if (temp == null || temp.size() == 0) {
            set.add(res);
        }
    }
}
