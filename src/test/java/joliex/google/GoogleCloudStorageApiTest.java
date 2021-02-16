package joliex.google;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GoogleCloudStorageApiTest {

    @Test
    void upload() throws IOException {

        Storage storage = StorageOptions.newBuilder().setProjectId("jvproject-289212").setCredentials(GoogleCredentials.fromStream(new FileInputStream("jvproject-289212-ce3b03a915fd.json"))).build().getService();
        GoogleCloudStorageApi googleCloudStorageApi = new GoogleCloudStorageApi();
        try {
            FileInputStream stream = new FileInputStream("test.txt");
            byte[] contents =  stream.readAllBytes();
            googleCloudStorageApi.upload(storage , "example_bucket_balint" , "test.txt" , contents , true );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void generateV4GetObjectSignedUrl() {
        GoogleCloudStorageApi googleCloudStorageApi = new GoogleCloudStorageApi();
        try {
            Storage storage = StorageOptions.newBuilder().setProjectId("jvproject-289212").setCredentials(GoogleCredentials.fromStream(new FileInputStream("jvproject-289212-ce3b03a915fd.json"))).build().getService();
            String urlString  = googleCloudStorageApi.generateV4GetObjectSignedUrl(storage , "example_bucket_balint" , "10.pdf" , 10 );
            System.out.println(urlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void download() {
        GoogleCloudStorageApi googleCloudStorageApi = new GoogleCloudStorageApi();
        try {
            Storage storage = StorageOptions.newBuilder().setProjectId("jvproject-289212").setCredentials(GoogleCredentials.fromStream(new FileInputStream("jvproject-289212-ce3b03a915fd.json"))).build().getService();
            byte[] responseBytes  = googleCloudStorageApi.download(storage , "example_bucket_balint" , "10.pdf");
            FileOutputStream stream = new FileOutputStream("10_download.pdf");
            stream.write(responseBytes);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void copy() {
    }

    @Test
    void rename() {
        GoogleCloudStorageApi googleCloudStorageApi = new GoogleCloudStorageApi();
        try {
            Storage storage = StorageOptions.newBuilder().setProjectId("jvproject-289212").setCredentials(GoogleCredentials.fromStream(new FileInputStream("jvproject-289212-ce3b03a915fd.json"))).build().getService();
            googleCloudStorageApi.rename(storage,"example_bucket_balint", "10.pdf","new_10.pdf");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void move() {
        GoogleCloudStorageApi googleCloudStorageApi = new GoogleCloudStorageApi();
        try {
            Storage storage = StorageOptions.newBuilder().setProjectId("jvproject-289212").setCredentials(GoogleCredentials.fromStream(new FileInputStream("jvproject-289212-ce3b03a915fd.json"))).build().getService();
            googleCloudStorageApi.move(storage,"example_bucket_balint", "10.pdf","example_bucket_balint");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createBucket() {

        GoogleCloudStorageApi googleCloudStorageApi = new GoogleCloudStorageApi();
        try {
            Storage storage = StorageOptions.newBuilder().setProjectId("jvproject-289212").setCredentials(GoogleCredentials.fromStream(new FileInputStream("jvproject-289212-ce3b03a915fd.json"))).build().getService();
            googleCloudStorageApi.createBucket(storage,"example_bucket_balint","EU");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void list() {
        GoogleCloudStorageApi googleCloudStorageApi = new GoogleCloudStorageApi();
        try {
            Storage storage = StorageOptions.newBuilder().setProjectId("jvproject-289212").setCredentials(GoogleCredentials.fromStream(new FileInputStream("jvproject-289212-ce3b03a915fd.json"))).build().getService();
            Page<Blob> objects = googleCloudStorageApi.list(storage, "example_bucket_balint");

            for (Blob object : objects.iterateAll()) {
                System.out.println(object.getName());
                System.out.println(object.getContentType());
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}