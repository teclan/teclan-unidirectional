package teclan.unidirectional.yang.server.udpclient;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YangUdpClient extends IoHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(YangUdpClient.class);

    private static String       HOST   = "10.0.0.11";
    private static int          PORT   = 8081;

    private IoSession           session;
    private IoConnector         connector;

    public void run() {
        connector = new NioDatagramConnector();
        connector.setHandler(this);

        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"))));

        ConnectFuture connectFuture = connector
                .connect(new InetSocketAddress(HOST, PORT));

        connectFuture.awaitUninterruptibly();

        connectFuture.addListener(new IoFutureListener<ConnectFuture>() {
            public void operationComplete(ConnectFuture future) {
                if (future.isConnected()) {
                    session = future.getSession();
                    LOGGER.info("session created, {}", session);
                }
            }
        });
    }

    public void sendToYin(Object message) {

        if (session == null) {
            System.out.println("The connection is unavailable");
            return;
        }

        IoBuffer buffer = IoBuffer.allocate(16);
        buffer.setAutoExpand(true);
        buffer.putObject(message);
        buffer.flip();
        session.write(buffer);

        LOGGER.info("write message to yin with udp, {}", message);
    }

    public static void main(String[] a) {

        YangUdpClient udpClient = new YangUdpClient();

        udpClient.run();

        udpClient.sendToYin("XXXXXXXXXX");

    }

    public void setUdpAddress(String host, int port) {
        HOST = host;
        PORT = port;
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        cause.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        // System.out.println("Receive :" + message);
    }

    public void messageSent(IoSession session) throws Exception {
        // System.out.println("message sent ...");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // System.out.println("session close ...");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        // System.out.println("session create ...");
    }

}
