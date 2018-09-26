package com.qjd.rry.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
/**
 * @program: rry
 * @description: swagger页面关于pageable参数显示的配置
 * @author: XiaoYu
 * @create: 2018-03-21 18:39
 **/
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class PageableParameterBuilder implements ParameterBuilderPlugin {

    private final TypeResolver typeResolver;

    @Autowired
    public PageableParameterBuilder(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return DocumentationType.SWAGGER_2.equals(delimiter);
    }

    @Override
    public void apply(ParameterContext parameterContext) {
        MethodParameter param = parameterContext.methodParameter();
        if (param.getParameterType().equals(Pageable.class)) {
            List<Parameter> parameters = newArrayList();
            parameters.add(parameterContext.parameterBuilder()
                    .parameterType("query").name("page").modelRef(new ModelRef("int"))
                    .description("分页页码（默认为0）").build());
            parameters.add(parameterContext.parameterBuilder()
                    .parameterType("query").name("size").modelRef(new ModelRef("int"))
                    .description("分页大小（默认为20）").build());
            parameters.add(parameterContext.parameterBuilder()
                    .parameterType("query").name("sort").modelRef(new ModelRef("string")).allowMultiple(true)
                    .description("排序方式").build());
            parameterContext.getOperationContext().operationBuilder().parameters(parameters);
        }
    }
}