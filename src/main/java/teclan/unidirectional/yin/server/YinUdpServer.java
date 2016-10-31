package teclan.unidirectional.yin.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teclan.unidirectional.yin.server.udp.server.handler.UdpServerHandler;

public class YinUdpServer {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(YinUdpServer.class);
    private static int          PORT   = 8081;

    private UdpServerHandler    udpServerHandler;

    public void run() {

        NioDatagramAcceptor acceptor = new NioDatagramAcceptor();

        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();

        udpServerHandler = new UdpServerHandler();

        acceptor.setHandler(udpServerHandler);

        chain.addLast("logger", new LoggingFilter());

        DatagramSessionConfig dcfg = acceptor.getSessionConfig();
        dcfg.setReuseAddress(true);

        try {
            acceptor.bind(new InetSocketAddress(PORT));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info("yin-api udp server listen on port {}", PORT);

    }

    public static void main(String[] a) {
        new YinUdpServer().run();
    }

    public void setPort(int port) {
        PORT = port;
    }

}
