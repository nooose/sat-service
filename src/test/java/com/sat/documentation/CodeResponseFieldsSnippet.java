package com.sat.documentation;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.io.IOException;
import java.util.List;

public class CodeResponseFieldsSnippet extends AbstractFieldsSnippet {

    public CodeResponseFieldsSnippet(String type, List<FieldDescriptor> descriptors, boolean ignoreUndocumentedFields) {
        super(type, descriptors, null, ignoreUndocumentedFields);
    }

    public static CodeResponseFieldsSnippet of(String snippetFilePrefix, List<FieldDescriptor> fieldDescriptors) {
        return new CodeResponseFieldsSnippet(snippetFilePrefix, fieldDescriptors, true);
    }

    @Override
    protected MediaType getContentType(Operation operation) {
        return operation.getResponse().getHeaders().getContentType();
    }

    @Override
    protected byte[] getContent(Operation operation) throws IOException {
        return operation.getResponse().getContent();
    }
}
