package utility;

import com.google.common.primitives.Bytes;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class ClientUDP {
    private DatagramChannel client;
    private final InetSocketAddress inetAddress;
    private final int PACKET_SIZE = 512;
    private final int DATA_SIZE = PACKET_SIZE - 1;
    public ClientUDP(InetAddress address, int port) throws IOException {
        this.inetAddress = new InetSocketAddress(address, port);
        this.client = DatagramChannel.open().bind(null).connect(inetAddress);
        this.client.configureBlocking(false);
    }

    public Response sendAndAskResponse(Request request) throws IOException {
        var data = SerializationUtils.serialize(request);
        byte[] responseBytes = sendAndReceiveData(data);
        return SerializationUtils.deserialize(responseBytes);
    }

    private void sendData(byte[] data) throws IOException {
        byte[][] allData = new byte[(int)Math.ceil(data.length / (double)DATA_SIZE)][DATA_SIZE];
        int start = 0;
        for(int i = 0; i < allData.length; i++) {
            allData[i] = Arrays.copyOfRange(data, start, start + DATA_SIZE);
            start += DATA_SIZE;
        }
        for (int i = 0; i < allData.length; i++) {
            byte[] temp = allData[i];
            if (i == allData.length - 1) {
                byte[] finish = Bytes.concat(temp, new byte[]{1}); // добавляем 1, чтобы понять, последнее "сообщение" пришло
                client.send(ByteBuffer.wrap(finish), inetAddress);
            } else {
                byte[] finish = Bytes.concat(temp, new byte[]{0});
                client.send(ByteBuffer.wrap(finish), inetAddress);
            }
        }
    }

    private byte[] receiveData() throws IOException {
        boolean received = false;
        byte[] result = new byte[0];
        while(!received) {
            byte[] data = receiveData(PACKET_SIZE);
            if (data[data.length - 1] == 1) {
                received = true;
            }
            result = Bytes.concat(result, Arrays.copyOf(data, data.length - 1));
        }
        return result;
    }

    private byte[] receiveData(int bufferSize) throws IOException {
        var buffer = ByteBuffer.allocate(bufferSize);
        SocketAddress address = null;
        while(address == null) {
            address = client.receive(buffer);
        }
        return buffer.array();
    }

    private byte[] sendAndReceiveData(byte[] data) throws IOException {
        sendData(data);
        return receiveData();
    }
}
