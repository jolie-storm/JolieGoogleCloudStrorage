
type ConnectRequest:void{
  projectId:string
  certificateFilePath:string
}

type ConnectResponse:void

type UploadRequest:void{
 bucketName:string
 objectName:string
 content:raw
 public:bool
}

type UploadResponse:void

type DownloadRequest:void{
   bucketName:string
   objectName:string
}

type DownloadResponse:void{
   content:raw
}

type CopyRequest:void{
   bucketName:string
   objectName:string
   destinationBucketName:string
}

type CopyResponse:void


type RenameRequest:void{
      bucketName:string
      objectName:string
      newObjectName:string
}

type RenameResponse:void

type MoveRequest:void{
      bucketName:string
      objectName:string
      destinationBucketName:string
}

type MoveResponse:void

type CreateBucketRequest:void{
    bucketName:string
    area:string
}


type CreateBucketResponse:void


type GenerateGetObjectSignedUrlRequest:void{
     bucketName:string
     objectName:string
}



type GenerateGetObjectSignedUrlResponse:void{
    url:string
}


type DeleteRequest:void{
     bucketName:string
     objectName:string
}

type DeleteResponse:void


type ListRequestGoogleStorage:void{
     bucketName:string
    
}

type ListResponseGoogleStorage:void{
   objects*:void{
     name:string 
     mime:string
   }
}



interface GoogleCloudStorageInterface {
    RequestResponse:
    connect (ConnectRequest)(ConnectResponse) throws IOException(string),
    upload(UploadRequest)(UploadResponse) throws IOException(string),
    download(DownloadRequest)(DownloadResponse) throws StorageException(string),
    copy(CopyRequest)(DownloadResponse) throws StorageException(string),
    rename(RenameRequest)(RenameResponse) throws StorageException(string),
    move(MoveRequest)(MoveResponse) throws StorageException(string),
    createBucket(CreateBucketRequest)(CreateBucketResponse) throws StorageException(string),
    generateGetObjectSignedUrl(GenerateGetObjectSignedUrlRequest)(GenerateGetObjectSignedUrlResponse) throws IOException(string),
    delete(DeleteRequest)(DeleteResponse) ,
    list(ListRequestGoogleStorage)(ListResponseGoogleStorage)
}



service GoogleCloudStorage {
  
inputPort ip {
        location:"local"
        interfaces: GoogleCloudStorageInterface
    }

foreign java {
  class: "joliex.google.GoogleCloudStorageService" 
  }
}



