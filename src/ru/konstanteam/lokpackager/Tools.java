package ru.konstanteam.lokpackager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Tools {

    public static int indexOf(byte[] array, byte[] target) {
        outer:
        for (int i = 0; i < array.length - target.length + 1; i++) {
            for (int j = 0; j < target.length; j++) {
                if (array[i + j] != target[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -1;
    }

    public static void putString(ByteBuffer byteBuffer, String string) {
        byte[] bytes = string.getBytes();
        byteBuffer.putInt(bytes.length);
        byteBuffer.put(bytes);
    }

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();

        return buffer.getLong();
    }

    public static String getString(ByteBuffer byteBuffer) {
        int length = byteBuffer.getInt();
        byte[] bytes = new byte[length];
        byteBuffer.get(bytes);

        return new String(bytes);
    }

    public static void writeFileToStream(long fileSize, BufferedOutputStream outputStream, BufferedInputStream inputStream) throws IOException {
        outputStream.write(longToBytes(fileSize));

        for (long i = 0; i < fileSize; i++) {
            byte fileByte = (byte) inputStream.read();
            outputStream.write(fileByte);
        }

        outputStream.flush();
    }

    public static void readFileFromStream(BufferedOutputStream outputStream, BufferedInputStream inputStream) throws IOException {
        byte[] fileSizeBytes = new byte[Long.BYTES];
        long fileSize;

        inputStream.read(fileSizeBytes);

        fileSize = bytesToLong(fileSizeBytes);

        for (long i = 0; i < fileSize; i++) {
            byte fileByte = (byte) inputStream.read();
            outputStream.write(fileByte);
        }

        outputStream.flush();
    }

}
