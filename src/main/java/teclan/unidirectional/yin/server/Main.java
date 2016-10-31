package teclan.unidirectional.yin.server;

public class Main {
    private static int tcpPort = 3770;
    private static int udpPort = 8081;

    public static void main(String[] args) {

        YinUdpServer udpServer = new YinUdpServer();
        YinTcpServer tcpServer = new YinTcpServer();

        if (args.length > 1) {
            tcpPort = Integer.valueOf(args[0]);
            udpPort = Integer.valueOf(args[1]);
        } else {
            note();
        }

        udpServer.setPort(udpPort);
        tcpServer.setPort(tcpPort);

        udpServer.run();
        tcpServer.run();

    }

    private static void note() {
        System.out.println("缺少参数，默认tcp监听端口 3770,upd监听端口 8081");
        System.out.println("=======================================");
        System.out.println("指令规范: java -jar xxx.jar [tcpPort] [udpPort] \n");
    }
}
