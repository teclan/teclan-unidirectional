package teclan.unidirectional.yang.server;

import teclan.unidirectional.yang.server.YangTcpServer;

public class Main {
    private static int   tcpPort = 3770;

    public static String udpHost = "10.0.0.11";
    public static int    udpPort = 8081;

    public static void main(String[] args) {

        YangTcpServer apiServer = new YangTcpServer();

        if (args.length >= 1) {
            tcpPort = Integer.valueOf(args[0]);

        } else {
            note();
        }

        if (args.length > 2) {
            udpHost = args[1];
            udpPort = Integer.valueOf(args[2]);

        }
        apiServer.setPort(tcpPort);
        apiServer.run();
        System.out.println(String.format("yang-api 将所有数据转向UDP服务器: %s:%d\n",
                udpHost, udpPort));
    }

    private static void note() {
        System.out.println("缺少参数，默认tcp监听端口 3770,udp 地址 10.0.0.11:8081");
        System.out.println("=================================================");
        System.out.println(
                "指令规范: java -jar xxx.jar [tcpPort] [udpHost] [udpPort] \n");
    }
}
