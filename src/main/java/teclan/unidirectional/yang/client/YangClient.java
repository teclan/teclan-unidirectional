package teclan.unidirectional.yang.client;

import java.io.Console;
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

import teclan.unidirectional.yang.client.handler.YangClientHandler;

public class YangClient {
    private static final Logger LOGGER          = LoggerFactory
            .getLogger(YangClient.class);

    private static String       HOST            = "localhost";     // 127.0.0.1
    private static int          PORT            = 8080;
    private static final long   CONNECT_TIMEOUT = 30 * 1000L;      // 30 seconds

    private static Console      console         = System.console();

    private NioSocketConnector  connector;
    private YangClientHandler   ioHandler       = null;
    private IoSession           session;

    public void run() {

        connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);

        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"))));
        connector.getFilterChain().addLast("logger", new LoggingFilter());

        ioHandler = new YangClientHandler();
        connector.setHandler(ioHandler);

        ConnectFuture future = connector
                .connect(new InetSocketAddress(HOST, PORT));

        future.awaitUninterruptibly();

        session = future.getSession();

        send();

        session.getCloseFuture().awaitUninterruptibly();

    }

    public void stop() {
        connector.dispose();
        LOGGER.info("session closed!");
    }

    public void send() {
        String message = "";
        do {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            message = console.readLine(
                    "input the message to send (type \"Q!\" to exit): ");
            session.write(message);

        } while (!"Q!".equals(message));
        stop();
    }

    public void setAddress(String host, int port) {
        HOST = host;
        PORT = port;
    }
}
