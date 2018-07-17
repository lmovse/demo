package info.lmovse.netty4.protocol.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.InputStream;
import java.io.OutputStream;

public class KryoSerialization {

    public Object deserialization(final InputStream inputStream) {
        Kryo kryo = new Kryo();
        Input input = new Input(inputStream);
        Object object = kryo.readClassAndObject(input);
        input.close();
        return object;
    }

    public void serialization(final OutputStream outputStream, final Object data) {
        Kryo kryo = new Kryo();
        Output output = new Output(outputStream);
        kryo.writeClassAndObject(output, data);
        output.close();
    }

}
