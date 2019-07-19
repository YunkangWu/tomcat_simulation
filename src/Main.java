import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static int PORT = 8080;

    /**
     * 1、多个浏览器同时访问（多线程）
     * 2、如何提供服务（socket）
     * 3、如何返回响应（io）
     * 模拟tomcat发布服务
     * @param args
     */
    public static void main(String[] args) {
        //通过命令行设置端口
        int p = (args.length > 0) ? Integer.parseInt(args[0]) : PORT;
        new Main().start(p);
    }

    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("----------------监听[" + port + "]端口的服务器启动----------------");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("有客户端请求");
                //1、线程的方法，效率一般
                //new Thread(new HandlerRquestThread(socket));

                //2、创建线程池，将任务提交给线程池处理
                //线程池，预计100个同时来,有请求进来，就从池子里取出一个线程
                ExecutorService pool = Executors.newFixedThreadPool(100);
                pool.submit(new HandlerRquestThread(socket));
            }

        } catch (IOException e) {
            System.out.println("启动失败:"+e.getMessage());
        }
    }
}
