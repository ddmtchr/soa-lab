package com.ddmtchr.soalab.config;

import com.ddmtchr.soalab.exception.DetailSoapFaultDefinitionExceptionResolver;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.server.ServerErrorException;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.Properties;

@EnableWs
@Configuration
public class SoapConfig {

    @Bean
    public SoapFaultMappingExceptionResolver exceptionResolver(){
        SoapFaultMappingExceptionResolver exceptionResolver = new DetailSoapFaultDefinitionExceptionResolver();

        SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
        faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
        exceptionResolver.setDefaultFault(faultDefinition);

        Properties errorMappings = new Properties();
        errorMappings.setProperty(ServerErrorException.class.getName(), SoapFaultDefinition.SERVER.toString());
//        errorMappings.setProperty(ResponseStatusException.class.getName(), SoapFaultDefinition.CLIENT.toString());
        errorMappings.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());

        exceptionResolver.setExceptionMappings(errorMappings);
        exceptionResolver.setOrder(1);
        return exceptionResolver;
    }

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext context) {

        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "schema")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("SchemaPort");
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace("http://com/ddmtchr/soalab/schema"); // todo
        wsdl.setSchema(schema);
        return wsdl;
    }

    @Bean
    public SimpleXsdSchema xsdSchema() {
        return new SimpleXsdSchema(new ClassPathResource("META-INF/schemas/schema.xsd"));
    }

    @Bean
    public PayloadValidatingInterceptor validatingInterceptor(SimpleXsdSchema schema) {
        PayloadValidatingInterceptor interceptor = new CustomPayloadValidatingInterceptor();
        interceptor.setValidateRequest(true);
        interceptor.setXsdSchema(schema);
        return interceptor;
    }
}
