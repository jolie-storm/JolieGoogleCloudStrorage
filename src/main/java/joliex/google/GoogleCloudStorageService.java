package joliex.google;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import jolie.runtime.*;
import jolie.runtime.embedding.RequestResponse;

import java.io.FileInputStream;
import java.io.IOException;


@CanUseJars({"google-cloud-storage-1.112.0.jar", "google-auth-library-oauth2-http-0.22.1.jar", "google-api-client-jackson2-1.20.0.jar" })

public class GoogleCloudStorageService extends JavaService {

    private Storage storage;
    private GoogleCloudStorageApi googleCloudStorageApi;

    @RequestResponse
    public void connect(Value request) {
        try {
            googleCloudStorageApi = new GoogleCloudStorageApi();
            storage = StorageOptions.newBuilder().setProjectId(request.getFirstChild("projectId").strValue())
                                                 .setCredentials(GoogleCredentials.fromStream(new FileInputStream(request.getFirstChild("certificateFilePath").strValue())))
                                                 .build().getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestResponse
    public void upload(Value request) throws IOException {

        try {
            googleCloudStorageApi.upload(storage,
                    request.getFirstChild("bucketName").strValue(),
                    request.getFirstChild("objectName").strValue(),
                    request.getFirstChild("content").byteArrayValue().getBytes(),
                    request.getFirstChild("public").boolValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestResponse
    public Value generateGetObjectSignedUrl(Value request) throws FaultException {
        Value response = Value.create();
        try {

            String urlString = googleCloudStorageApi.generateV4GetObjectSignedUrl(storage,
                    request.getFirstChild("bucketName").strValue(),
                    request.getFirstChild("objectName").strValue(),
                    request.getFirstChild("duration").intValue());
            response.getFirstChild("url").setValue(urlString);

        } catch (IOException e) {
            throw new FaultException( "IOException", e.getMessage() );
        }
        return response;
    }

    @RequestResponse
    public Value download(Value request) throws FaultException {
        Value response = Value.create();
        try {

            byte[] responseBytes = googleCloudStorageApi.download(storage,
                    request.getFirstChild("bucketName").strValue(),
                    request.getFirstChild("objectName").strValue());
            ByteArray byteArray = new ByteArray(responseBytes);

            response.getFirstChild("content").setValue(byteArray);


        } catch (StorageException e) {
            throw new FaultException( "IOException", e.getMessage() );
        }
        return response;
    }

    @RequestResponse
    public void copy(Value request) {

        googleCloudStorageApi.copy(storage,
                request.getFirstChild("bucketName").strValue(),
                request.getFirstChild("objectName").strValue(),
                request.getFirstChild("destinationBucketName").strValue());
    }

    @RequestResponse
    public void rename(Value request) throws FaultException {
        try {

            googleCloudStorageApi.rename(storage,
                    request.getFirstChild("bucketName").strValue(),
                    request.getFirstChild("objectName").strValue(),
                    request.getFirstChild("newObjectName").strValue());


        } catch (StorageException e) {
            throw new FaultException( "StorageException", e.getMessage() );
        }
    }

    @RequestResponse
    public void move(Value request) throws FaultException {

        try {

            googleCloudStorageApi.move(storage,
                    request.getFirstChild("bucketName").strValue(),
                    request.getFirstChild("objectName").strValue(),
                    request.getFirstChild("destinationBucketName").strValue());


        } catch (StorageException e) {
            throw new FaultException( "StorageException", e.getMessage() );
        }
    }


    @RequestResponse

    public void delete(Value request){
        googleCloudStorageApi.delete(storage,
                request.getFirstChild("bucketName").strValue(),
                request.getFirstChild("objectName").strValue());

    }

    @RequestResponse
    public void createBucket(Value request) throws FaultException {


        try {

            googleCloudStorageApi.createBucket(storage,
                    request.getFirstChild("bucketName").strValue(),
                    request.getFirstChild("area").strValue());


        } catch (StorageException e) {
            throw new FaultException( "StorageException", e.getMessage() );
        }
    }

    @RequestResponse
    public Value list (Value request){
        Value response = Value.create();
        Page<Blob> objects = googleCloudStorageApi.list( storage,
                                    request.getFirstChild("bucketName").strValue());
        for (Blob object : objects.iterateAll()) {
            Value objectValue = Value.create();
            objectValue.getFirstChild("name").setValue(object.getName());
            objectValue.getFirstChild("mime").setValue(object.getContentType());
            response.getChildren("objects").add(objectValue);
        }
        return response;
    }


}
