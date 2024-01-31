import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTraining {
    public static void main(String[] args) {
        int[] values1 = {1, 2, 3, 3, 2, 3};
        List<Integer> list = Arrays.asList(3,5,7,8,10);
        System.out.println(minValue(values1));
        System.out.println(oddOrEven(list));
    }

    public static int minValue(int[] values) {

        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (int1, int2) -> int1 * 10 + int2);


    }
    static List<Integer> oddOrEven(List<Integer> integers){
        Map<Boolean,List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(n -> n%2 == 0, Collectors.toList()));

        return map.get((integers.stream().mapToInt(Integer::intValue).sum()) % 2 ==0);
    }
}
