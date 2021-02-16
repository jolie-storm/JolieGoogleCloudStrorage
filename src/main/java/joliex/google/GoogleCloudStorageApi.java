package joliex.google;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ByteArray;
import com.google.cloud.storage.*;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GoogleCloudStorageApi {
    public void upload( Storage storage , String bucketName, String objectName, byte[] content , boolean publicVisibility) throws IOException {


        BlobId blobId = BlobId.of(bucketName, objectName);
        Path path = new File(objectName).toPath();
        String mimeType = Files.probeContentType(path);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(mimeType).build();
        storage.create(blobInfo,content);
        if (publicVisibility){
            storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        }

    }

    public static String generateV4GPutObjectSignedUrl( Storage storage , String bucketName, String objectName , int duration ) throws StorageException {


        // Define Resource
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build();

        // Generate Signed URL
        Map<String, String> extensionHeaders = new HashMap<>();
        extensionHeaders.put("Content-Type", "application/octet-stream");

        URL url =
                storage.signUrl(
                        blobInfo,
                        duration,
                        TimeUnit.MINUTES,
                        Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                        Storage.SignUrlOption.withExtHeaders(extensionHeaders),
                        Storage.SignUrlOption.withV4Signature());


        return url.toString();


    }


    public static String generateV4GetObjectSignedUrl(Storage storage, String bucketName, String objectName , int duration) throws StorageException, IOException {

        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build();
        URL url = storage.signUrl(blobInfo, duration, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());
        return url.toString();

    }

    public static byte[] download (Storage storage , String bucketName, String objectName){
        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        return blob.getContent(Blob.BlobSourceOption.generationMatch());
    }

    public static void copy (Storage storage, String bucketName, String objectName , String destinationBucket ){
        Blob blob = storage.get(bucketName, objectName);
        blob.copyTo(destinationBucket);

    }

    public static void rename (Storage storage, String bucketName, String objectName , String newObjectName ){
        Blob blob = storage.get(bucketName, objectName);
        blob.copyTo(bucketName,newObjectName );
        blob.delete();
    }

    public static void move (Storage storage, String bucketName, String objectName , String destinationBucket ){
        Blob blob = storage.get(bucketName, objectName);
        blob.copyTo(destinationBucket);
        blob.delete();

    }

    public static void  createBucket (Storage storage, String bucketName , String area){
        StorageClass storageClass = StorageClass.COLDLINE;

        Bucket bucket =
                storage.create(
                        BucketInfo.newBuilder(bucketName)
                                .setStorageClass(storageClass)
                                .setLocation(area)
                                .build());

    }

    public static void delete (Storage storage, String bucketName, String objectName){
        Blob blob = storage.get(bucketName, objectName);
        blob.delete();
    }

    public static Page<Blob> list (Storage storage, String bucketName){
        Bucket bucket = storage.get(bucketName);
        return bucket.list();
    }



}
