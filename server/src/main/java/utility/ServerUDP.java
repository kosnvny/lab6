package utility;

import com.google.common.primitives.Bytes;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ServerUDP {
    private final int PACKET_SIZE = 512;
    private final int DATA_SIZE = PACKET_SIZE - 1;
    private final InetSocketAddress inetSocketAddress;
    private final DatagramSocket datagramSocket;
    public ServerUDP(InetAddress inetAddress, int port) throws SocketException {
        this.inetSocketAddress = new InetSocketAddress(inetAddress, port);
        this.datagramSocket = new DatagramSocket(inetSocketAddress);
        this.datagramSocket.setReuseAddress(true);
    }

    public void connectToClient(SocketAddress socketAddress) throws SocketException {
        datagramSocket.connect(socketAddress);
    }

    public void disconnectWithClient() {
        datagramSocket.disconnect();
    }

    public void close() {
        datagramSocket.close();
    }

    public Pair<Byte[], SocketAddress> receiveData() throws IOException {
        boolean received = false;
        byte[] result = new byte[0];
        SocketAddress socketAddress = null;
        while (!received) {
            byte[] data = new byte[DATA_SIZE];
            DatagramPacket datagramPacket = new DatagramPacket(data, PACKET_SIZE);
            datagramSocket.receive(datagramPacket);
            socketAddress = datagramPacket.getSocketAddress();
            if (data[data.length - 1] == 1) {
                received = true;
            }
            result = Bytes.concat(result, Arrays.copyOf(data, data.length - 1));
        }
        return new ImmutablePair<>(ArrayUtils.toObject(result), socketAddress);
    }

    public void sendData(byte[] data, SocketAddress socketAddress) throws IOException {
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
                DatagramPacket datagramPacket = new DatagramPacket(finish, PACKET_SIZE, socketAddress);
                datagramSocket.send(datagramPacket);
            } else {
                DatagramPacket datagramPacket = new DatagramPacket(ByteBuffer.allocate(PACKET_SIZE).put(temp).array(), PACKET_SIZE, socketAddress);
                datagramSocket.send(datagramPacket);
            }
        }
    }
}
