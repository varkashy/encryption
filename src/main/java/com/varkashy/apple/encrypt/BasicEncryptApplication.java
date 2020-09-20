package com.varkashy.apple.encrypt;

import com.varkashy.apple.encrypt.api.CryptoApi;
import com.varkashy.apple.encrypt.api.SimpleMathsApi;
import com.varkashy.apple.encrypt.resources.SimpleMathsResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class BasicEncryptApplication extends Application<BasicEncryptConfiguration> {

    public static void main(final String[] args) throws Exception {
        new BasicEncryptApplication().run(args);
    }

    @Override
    public String getName() {
        return "BasicEncrypt";
    }

    @Override
    public void initialize(final Bootstrap<BasicEncryptConfiguration> bootstrap) {
    }

    @Override
    public void run(final BasicEncryptConfiguration configuration,
                    final Environment environment) {
        CryptoApi cryptoApi = new CryptoApi();
        SimpleMathsApi _simpleMathsApi = new SimpleMathsApi(cryptoApi);
        environment.jersey().register(new SimpleMathsResource(_simpleMathsApi));
    }

}
