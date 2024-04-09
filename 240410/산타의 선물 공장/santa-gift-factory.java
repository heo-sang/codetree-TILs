import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Box{
        int id;
        int w;
        Box(int id, int w){
            this.id= id;
            this.w= w;
        }
    }
    static Scanner sc;
    static LinkedList<Box>[] factory;

    static int n,m;
    static boolean[] broken = new boolean[100001];
    public static HashMap<Integer, Integer> belt_num = new HashMap<>();
    public static void init_factory(){
        n = sc.nextInt();
        m = sc.nextInt();
        factory = new LinkedList[m+1];
        for (int i = 1; i <= m; i++) {
            factory[i] = new LinkedList<>();
        }
        int[] ids = new int[n];
        int[] ws = new int[n];
        for (int i = 0; i < n; i++) {
            ids[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            ws[i] = sc.nextInt();
        }
        int idx = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j < n/m; j++) {
                factory[i].addLast(new Box(ids[idx],ws[idx]));
                belt_num.put(ids[idx],i);
                idx++;
            }
        }
    }
    static void box_drop(){
        int w_max = sc.nextInt();
        int sum = 0;
        for (int i = 1; i <= m; i++) {
            if(broken[i]) continue;
            Box box = factory[i].pollFirst();
            if(box.w<=w_max) {
                sum+=box.w;
                belt_num.remove(box.id);
            }
            else{
                factory[i].add(box);
            }
        }
        System.out.println(sum);
    }
    static void box_remove(){
        int r_id = sc.nextInt();
        int belt_id = belt_num.getOrDefault(r_id,0);
        if(belt_id==0) {
            System.out.println(-1);
            return;
        }
        belt_num.remove(r_id);
        for (int i = 0; i < factory[belt_id].size(); i++) {
            if(factory[belt_id].get(i).id!=r_id) continue;
            factory[belt_id].remove(i);
            System.out.println(r_id);
            return;
        }
    }
    static void box_check(){
        int f_id = sc.nextInt();
        int belt_id = belt_num.getOrDefault(f_id,0);
        if(belt_id==0) {
            System.out.println(-1);
            return;
        }
        for (int i = 0; i < factory[belt_id].size(); i++) {
            if(factory[belt_id].get(0).id==f_id) break;
            Box box = factory[belt_id].pollFirst();
            factory[belt_id].addLast(box);
        }
        System.out.println(belt_id);
    }

    static void belt_broken(){
        int b_num = sc.nextInt();
        if(broken[b_num]) {
            System.out.println(-1);
            return;
        }
        int next_idx = b_num;
        broken[b_num] = true;
        while(broken[next_idx]){
            next_idx = next_idx%m + 1;
        }
        while(!factory[b_num].isEmpty()){
            Box box = factory[b_num].pollFirst();
            belt_num.replace(box.id,next_idx);
            factory[next_idx].addLast(box);
        }
        System.out.println(b_num);

    }

    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StringTokenizer st = new StringTokenizer(br.readLine());
        sc = new Scanner(System.in);
        int q = sc.nextInt();
        for (int i = 0; i < q; i++) {
            int cmd = sc.nextInt();
            switch (cmd){
                case 100:
                    init_factory();
                    break;
                case 200:
                    box_drop();
                    break;
                case 300:
                    box_remove();
                    break;
                case 400:
                    box_check();
                    break;
                default:
                    belt_broken();
                    break;
            }
        }
    }
}