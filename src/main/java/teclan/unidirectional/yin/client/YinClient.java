package teclan.unidirectional.yin.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teclan.unidirectional.yin.client.handler.YinClientHandler;

public class YinClient {
    private static final Logger LOGGER          = LoggerFactory
            .getLogger(YinClient.class);

    private static String       HOST            = "localhost"; // 127.0.0.1
    private static int          PORT            = 8082;
    private static final long   CONNECT_TIMEOUT = 30 * 1000L;  // 30 seconds

    private NioSocketConnector  connector;
    private YinClientHandler    ioHandler       = null;
    private IoSession           session;

    public void run() {

        connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);

        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"))));
        connector.getFilterChain().addLast("logger", new LoggingFilter());

        ioHandler = new YinClientHandler();
        connector.setHandler(ioHandler);

        ConnectFuture future = connector
                .connect(new InetSocketAddress(HOST, PORT));

        future.awaitUninterruptibly();

        session = future.getSession();

        session.getCloseFuture().awaitUninterruptibly();

    }

    public void stop() {
        connector.dispose();
        LOGGER.info("session closed!");
    }

    public void setAddress(String host, int port) {
        HOST = host;
        PORT = port;
    }

}
