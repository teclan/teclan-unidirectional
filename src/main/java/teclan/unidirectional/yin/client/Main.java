package teclan.unidirectional.yin.client;

public class Main {
    private static String host = "172.18.11.16";
    private static int    port = 3770;

    public static void main(String[] args) {

        YinClient yinClient = new YinClient();

        if (args.length < 2) {
            note();
        } else {
            host = args[0];
            port = Integer.valueOf(args[1]);

        }

        yinClient.setAddress(host, port);

        yinClient.run();

    }

    private static void note() {
        System.out.println("缺少参数，默认连接 172.18.11.16:3770");
        System.out.println("=======================================");
        System.out.println("指令规范: java -jar xxx.jar [host] [port] \n");
    }

}
