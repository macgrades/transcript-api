package com.function.ritual17;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import Courses.Course;
import PDF.CourseParser;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Function {

    @FunctionName("upload")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        
        Optional<String> requestBody = request.getBody();
        if (!requestBody.isPresent()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("No PDF file found in the request body.")
                    .build();
        }
        
        try {
            
            byte[] pdfData = Base64.getDecoder().decode(requestBody.get());
            List<Course> courseList = parseTranscript(pdfData);
            
            return request.createResponseBuilder(HttpStatus.OK).body(courseList).build();

        } catch (IOException e) {
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    private List<Course> parseTranscript(byte[] file) throws IOException {
        PDDocument pdDoc = Loader.loadPDF(file);
        PDFTextStripper reader = new PDFTextStripper();
        String pageText = reader.getText(pdDoc);
        pdDoc.close();
        String[] lines = pageText.split("\n");
        return CourseParser.getGradedCourses(lines);
    }
}
