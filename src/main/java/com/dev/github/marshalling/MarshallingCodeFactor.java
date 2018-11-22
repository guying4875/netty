package com.dev.github.marshalling;/**
 * @description 构建Marshalling编码，解码对象
 * @author zhhy
 * @date 2018-11-18-11-19 上午10:02
 */

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 *
 * @description
 * @author zhhy
 * @date 2018-11-18-11-19 上午10:02
 *
 */
public class MarshallingCodeFactor {
    /**
     * 创建marshalling 的解码类
     * @return
     */
    public static MarshallingDecoder buildMarshallingDecoder(){
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory,configuration);
        MarshallingDecoder decoder = new MarshallingDecoder(provider,1024);
        return decoder;
    }


    /**
     * 创建Marshalling 编码类对象
     * @return
     */
    public static MarshallingEncoder buildMarshallingEncoder(){

        MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration config = new MarshallingConfiguration();
        config.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(factory,config);
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }
}






























