package teclan.unidirectional.yang.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teclan.unidirectional.yang.server.handler.YangApiServerHandler;

public class YangTcpServer {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(YangTcpServer.class);
    private static int          PORT   = 8080;

    private IoAcceptor          acceptor;

    public void run() {
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.setHandler(new YangApiServerHandler());
        acceptor.getSessionConfig().setReadBufferSize(2048);

        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        try {
            acceptor.bind(new InetSocketAddress(PORT));
            LOGGER.info("yang-api start listen on {}", PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        acceptor.dispose();
    }

    public void setPort(int pORT) {
        PORT = pORT;
    }
}
