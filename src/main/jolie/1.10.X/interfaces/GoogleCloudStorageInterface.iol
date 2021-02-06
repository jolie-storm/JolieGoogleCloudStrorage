
type ConnectRequest:void{
  projectId:string
  certificateFilePath:string
}

type ConnectResponse:void

type UploadRequest:void{
 bucketName:string
 objectName:string
 content:raw
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


type RenameRequestGoogleStorage:void{
      bucketName:string
      objectName:string
      newObjectName:string
}

type RenameResponseGoogleStorage:void

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


type DeleteRequestGoogleStorage:void{
     bucketName:string
     objectName:string
}

type DeleteResponseGoogleStorage:void





interface GoogleCloudStorageInterface {
    RequestResponse:
    connect (ConnectRequest)(ConnectResponse) throws IOException(string),
    upload(UploadRequest)(UploadResponse) throws IOException(string),
    download(DownloadRequest)(DownloadResponse) throws StorageException(string),
    copy(CopyRequest)(DownloadResponse) throws StorageException(string),
    rename(RenameRequestGoogleStorage)(RenameResponseGoogleStorage) throws StorageException(string),
    move(MoveRequest)(MoveResponse) throws StorageException(string),
    createBucket(CreateBucketRequest)(CreateBucketResponse) throws StorageException(string),
    generateGetObjectSignedUrl(GenerateGetObjectSignedUrlRequest)(GenerateGetObjectSignedUrlResponse) throws IOException(string),
    delete(DeleteRequestGoogleStorage)(DeleteResponseGoogleStorage) 
}


outputPort GoogleCloudStorage {
  Interfaces: GoogleCloudStorageInterface
}


embedded {
  Java:
    "joliex.google.GoogleCloudStorageService" in GoogleCloudStorage
}



